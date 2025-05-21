package com.mcpd.config;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class ControllerLoggingAspect {

    @Autowired
    private HttpServletRequest request;

    private static final Logger logger = LoggerFactory.getLogger(ControllerLoggingAspect.class);
    private static final Gson gson = new Gson();

    // 🧠 Asociación IP → Usuario
    private static final Map<String, String> ipUsuarioMap = new ConcurrentHashMap<>();

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    //@Pointcut("execution(public * com.mcpd.controller..*(..))")
    public void restControllerMethods() {}

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
            String argsJson = gson.toJson(args);
            String usuario = ipUsuarioMap.getOrDefault(ipAddress, "desconocido");
            logger.info("🡺 [{}] Usuario: {} → Ejecutando: {} con parámetros: {}", ipAddress, usuario, method, argsJson);
        }
    }

    /*@AfterReturning(pointcut = "restControllerMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String method = joinPoint.getSignature().toShortString();
        logger.info("✅ Retorno de {}: {}", method, result);
    }*/

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

        logger.error(
                "❌ Excepción en [{}] {} - Clase: {} - Usuario: {} - Parámetros: {} - Tipo: {}",
                ipAddress,
                method,
                className,
                usuario,
                gson.toJson(args),
                ex.getClass().getSimpleName(),
                ex
        );
    }
}
