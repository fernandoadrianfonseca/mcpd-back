package com.mcpd.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Aspecto de logging para controllers REST.
 *
 * <p>
 * Intercepta la ejecución de métodos dentro de clases anotadas con {@link org.springframework.web.bind.annotation.RestController}
 * para registrar:
 * <ul>
 *   <li>IP de origen (X-Forwarded-For o remote address)</li>
 *   <li>Usuario asociado a la IP (si fue registrado desde AuthController)</li>
 *   <li>Método ejecutado y parámetros (serializados a JSON cuando es posible)</li>
 *   <li>Excepciones lanzadas por controllers (con contexto de usuario e IP)</li>
 * </ul>
 *
 * <h3>Asociación IP → Usuario</h3>
 * <p>
 * Cuando se invoca un endpoint del {@code AuthController}, se asocia la IP con el primer parámetro
 * recibido (por convención, el usuario). Esa asociación se reutiliza luego para loguear el usuario
 * en las siguientes requests de esa IP.
 *
 * <h3>Exclusiones</h3>
 * <p>
 * Se excluyen explícitamente controllers de logs para evitar ruido/loops:
 * {@code *LogController} y {@code *ReporteLogController}.
 *
 * <p>
 * Nota: este enfoque asume que la IP identifica al usuario en una sesión de uso,
 * lo cual puede no ser válido si existe NAT, proxies compartidos o múltiples usuarios
 * detrás de la misma IP.
 */
@Aspect
@Component
public class ControllerLoggingAspect {

    @Autowired
    private HttpServletRequest request;

    private static final Logger logger = LoggerFactory.getLogger(ControllerLoggingAspect.class);

    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    // 🧠 Asociación IP → Usuario
    private static final Map<String, String> ipUsuarioMap = new ConcurrentHashMap<>();

    /**
     * Pointcut que intercepta todos los métodos públicos ejecutados dentro de controllers REST.
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    //@Pointcut("execution(public * com.mcpd.controller..*(..))")
    public void restControllerMethods() {}

    /**
     * Log previo a la ejecución de un endpoint REST.
     *
     * <p>
     * - Determina IP desde X-Forwarded-For o remoteAddr.
     * - Normaliza localhost IPv6 a 127.0.0.1.
     * - Si el controller es AuthController, registra la asociación IP → usuario.
     * - Para el resto, serializa parámetros y loguea con el usuario asociado (si existe).
     *
     * @param joinPoint contexto AOP del método invocado.
     */
    @Before("restControllerMethods()")
    public void logBefore(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String method = joinPoint.getSignature().toShortString();

        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        // Normalizar localhost
        if ("0:0:0:0:0:0:0:1".equals(ipAddress)) {
            ipAddress = "127.0.0.1";
        }

        // ❌ No loguear nada para estos controllers
        if (className.contains("LogController") || className.contains("ReporteLogController")) {
            return;
        }

        Object[] args = joinPoint.getArgs();

        if (className.contains("AuthController")) {
            // ✅ Asociar IP con el primer argumento (legajo o usuario)
            if (args.length > 0) {
                ipUsuarioMap.put(ipAddress, args[0].toString());
                logger.info("🡺 [{}] Ejecutando: {} Usuario: {}", ipAddress, method, args[0]);
            } else {
                logger.info("🡺 [{}] Ejecutando: {} (sin parámetros)", ipAddress, method);
            }
        } else {
            String argsJson;
            try {
                argsJson = mapper.writeValueAsString(args);
            } catch (Exception e) {
                argsJson = "[no serializable]";
            }
            String usuario = ipUsuarioMap.getOrDefault(ipAddress, "desconocido");
            logger.info("🡺 [{}] Usuario: {} → Ejecutando: {} con parámetros: {}", ipAddress, usuario, method, argsJson);
        }
    }

    /*@AfterReturning(pointcut = "restControllerMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String method = joinPoint.getSignature().toShortString();
        logger.info("✅ Retorno de {}: {}", method, result);
    }*/

    /**
     * Log de excepciones lanzadas por endpoints REST.
     *
     * <p>
     * Registra:
     * - IP
     * - método invocado
     * - clase
     * - usuario asociado
     * - parámetros (JSON si es posible)
     * - tipo de excepción y stacktrace
     *
     * @param joinPoint contexto AOP.
     * @param ex excepción lanzada.
     */
    @AfterThrowing(pointcut = "restControllerMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        String method = joinPoint.getSignature().toShortString();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        Object[] args = joinPoint.getArgs();

        // Obtener IP del request
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        // Verificar usuario asociado a la IP
        String usuario = ipUsuarioMap.getOrDefault(ipAddress, "Desconocido");

        String argsJson;
        try {
            argsJson = mapper.writeValueAsString(args);
        } catch (Exception e) {
            argsJson = "[no serializable]";
        }

        logger.error(
                "❌ Excepción en [{}] {} - Clase: {} - Usuario: {} - Parámetros: {} - Tipo: {}",
                ipAddress,
                method,
                className,
                usuario,
                argsJson,
                ex.getClass().getSimpleName(),
                ex
        );
    }
}
