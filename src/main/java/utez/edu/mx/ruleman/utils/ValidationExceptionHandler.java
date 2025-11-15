package utez.edu.mx.ruleman.utils;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import utez.edu.mx.ruleman.config.MessagesGlobals;
import utez.edu.mx.ruleman.config.exception.*;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ValidationExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Message<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.warn("Errores de validación en la solicitud");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        Message<Map<String, String>> body = Message.error(HttpStatus.BAD_REQUEST, MessagesGlobals.ERROR_VALIDATION, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Message<String>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.warn("Error al leer el mensaje HTTP: {}", ex.getMessage());

        String message = MessagesGlobals.ERROR_VALIDATION;
        String detail = null;

        // Detectar propiedades desconocidas
        if (ex.getCause() instanceof UnrecognizedPropertyException) {
            UnrecognizedPropertyException upex = (UnrecognizedPropertyException) ex.getCause();
            String propertyName = upex.getPropertyName();
            message = "Campo no reconocido: '" + propertyName + "'. Verifique el nombre del campo.";
            detail = "Campos válidos disponibles: " + upex.getKnownPropertyIds();
            log.warn("Propiedad desconocida detectada: {}", propertyName);
        } else if (ex.getMessage().contains("JSON parse error")) {
            message = "Error en el formato JSON de la solicitud";
            detail = "Verifique que el JSON esté correctamente formateado";
        }

        Message<String> body = Message.error(HttpStatus.BAD_REQUEST, message, detail);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Message<String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.warn("Error de tipo en parámetro: {} - {}", ex.getName(), ex.getMessage());
        String message = String.format("El parámetro '%s' debe ser de tipo %s",
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconocido");
        Message<String> body = Message.error(HttpStatus.BAD_REQUEST, message, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Message<Void>> handleResourceNotFound(ResourceNotFoundException ex) {
        log.warn("Recurso no encontrado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Message.error(HttpStatus.NOT_FOUND, ex.getMessage(), null));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Message<Void>> handleConflict(ConflictException ex) {
        log.warn("Conflicto: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Message.error(HttpStatus.CONFLICT, ex.getMessage(), null));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Message<Void>> handleBadRequest(BadRequestException ex) {
        log.warn("Solicitud incorrecta: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Message.error(HttpStatus.BAD_REQUEST, ex.getMessage(), null));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Message<Void>> handleDataIntegrityViolation(DataIntegrityViolationException ex){
        log.error("Violación de integridad de datos: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Message.error(HttpStatus.CONFLICT, MessagesGlobals.ERROR_DATA_INTEGRITY, null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Message<Void>> handleGenericException(Exception ex) {
        log.error("Error inesperado en el servidor", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Message.error(HttpStatus.INTERNAL_SERVER_ERROR, MessagesGlobals.ERROR_INTERNAL, null));
    }

    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<Message<Void>> handleEmailSendingException(EmailSendingException ex) {
        log.error("Error al enviar correo: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Message.error(HttpStatus.SERVICE_UNAVAILABLE, MessagesGlobals.ERROR_EMAIL_SENDING, null));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Message<Void>> handleInvalidToken(InvalidTokenException ex) {
        log.warn("Token inválido: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Message.error(HttpStatus.BAD_REQUEST, ex.getMessage(), null));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Message<Void>> handleAccessDenied(AccessDeniedException ex) {
        log.warn("Acceso denegado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Message.error(HttpStatus.FORBIDDEN, ex.getMessage(), null));
    }

}
