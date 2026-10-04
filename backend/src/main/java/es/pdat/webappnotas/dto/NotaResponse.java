package es.pdat.webappnotas.dto;

import java.util.Date;

import es.pdat.webappnotas.entity.Nota;

public record NotaResponse(
    long id,
    String titulo,
    String contenido,
    Date fechaCreacion,
    Date fechaActualizacion
) {
    public NotaResponse(Nota nota){
        this(nota.getId(), nota.getTitulo(), nota.getContenido(), nota.getFechaCreacion(), nota.getFechaActualizacion());
    }
}
