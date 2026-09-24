package es.pdat.webappnotas.dto;

import java.util.Date;

public record UsuarioResponse(
        Long id,
        String nombreUsuario,
        String correo,
        String rol,
        boolean habilitado,
        Date fechaCreacion) {

}
