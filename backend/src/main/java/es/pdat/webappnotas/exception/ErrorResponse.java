package es.pdat.webappnotas.exception;

import java.util.Date;

public record ErrorResponse(
        Date timestamp,
        int status,
        String error,
        String message,
        String path) {
}
