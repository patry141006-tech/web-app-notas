package es.pdat.webappnotas.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> manejarRecursoNoEncontrado(
            RecursoNoEncontradoException exception,
            HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                new Date(),
                HttpStatus.NOT_FOUND.value(),
                "NOT_FOUND",
                exception.getMessage(),
                request.getRequestURI());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(ConflictoException.class)
    public ResponseEntity<ErrorResponse> manejarConflicto(
            ConflictoException exception,
            HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                new Date(),
                HttpStatus.CONFLICT.value(),
                "CONFLICT",
                exception.getMessage(),
                request.getRequestURI());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                new Date(),
                HttpStatus.BAD_REQUEST.value(),
                "VALIDATION_ERROR",
                "Los datos enviados no son válidos",
                request.getRequestURI());
        return ResponseEntity
                .badRequest()
                .body(error);
    }
}
