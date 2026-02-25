package com.mcpd.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para la aplicación.
 *
 * <p>
 * Centraliza el tratamiento de errores lanzados desde controllers,
 * services o capas de seguridad, transformándolos en respuestas
 * HTTP coherentes para el cliente.
 * </p>
 *
 * <h3>Responsabilidades</h3>
 * <ul>
 *   <li>Convertir excepciones de negocio en respuestas HTTP 400</li>
 *   <li>Convertir errores de autenticación/autorización en 401</li>
 *   <li>Manejar errores inesperados con 500</li>
 *   <li>Estandarizar el formato de error</li>
 * </ul>
 *
 * <p>
 * Anotado con {@link org.springframework.web.bind.annotation.ControllerAdvice}
 * para aplicar globalmente a todos los {@code @RestController}.
 * </p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de tipo {@link StockInsuficienteException}.
     *
     * <p>
     * Se dispara cuando se intenta realizar una operación que requiere
     * una cantidad mayor de stock disponible.
     * </p>
     *
     * <p>
     * Devuelve:
     * <ul>
     *   <li>HTTP 400 (Bad Request)</li>
     *   <li>JSON con el mensaje de error</li>
     * </ul>
     * </p>
     *
     * @param ex excepción lanzada desde la capa de negocio.
     * @return ResponseEntity con mensaje estructurado.
     */
    @ExceptionHandler(StockInsuficienteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleStockInsuficiente(StockInsuficienteException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja errores relacionados con autenticación y autorización.
     *
     * <p>
     * Captura:
     * <ul>
     *   <li>{@link JwtException} → Token inválido o expirado</li>
     *   <li>{@link AccessDeniedException} → Acceso no permitido</li>
     * </ul>
     * </p>
     *
     * <p>
     * Devuelve HTTP 401 (Unauthorized).
     * </p>
     *
     * @param ex excepción de seguridad.
     * @return respuesta HTTP 401 con mensaje.
     */
    // 🔹 Cualquier error de autorización: 401
    @ExceptionHandler({ JwtException.class, AccessDeniedException.class })
    public ResponseEntity<String> handleAuthExceptions(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("No autorizado: " + ex.getMessage());
    }

    /**
     * Maneja excepciones de tipo {@link RuntimeException}.
     *
     * <p>
     * Se utiliza para errores de negocio o validaciones
     * no contempladas por excepciones específicas.
     * </p>
     *
     * <p>
     * Devuelve HTTP 400 (Bad Request).
     * </p>
     *
     * @param ex excepción lanzada en tiempo de ejecución.
     * @return respuesta HTTP 400 con el mensaje de error.
     */
    // 🔹 Cualquier otro error de negocio/controlador: 400
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    /**
     * Maneja cualquier excepción no capturada previamente.
     *
     * <p>
     * Representa errores inesperados del sistema.
     * </p>
     *
     * <p>
     * Devuelve HTTP 500 (Internal Server Error).
     * </p>
     *
     * @param ex excepción inesperada.
     * @return respuesta HTTP 500 con mensaje genérico.
     */
    // 🔹 Cualquier otra excepción inesperada: 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAll(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + ex.getMessage());
    }
}