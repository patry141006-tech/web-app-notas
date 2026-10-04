package es.pdat.webappnotas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NotaRequest(
        @NotBlank @Size(min = 2, max = 150) String titulo,
        @NotBlank @Email @Size(max = 6000) String contenido) {
}
