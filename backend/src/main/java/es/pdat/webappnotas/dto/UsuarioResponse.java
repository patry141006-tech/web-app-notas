package es.pdat.webappnotas.dto;

import java.util.Date;

import es.pdat.webappnotas.entity.Usuario;

public record UsuarioResponse(
                Long id,
                String nombreUsuario,
                String correo,
                String rol,
                boolean habilitado,
                Date fechaCreacion) {

        public UsuarioResponse(Usuario usuario) {
                this(usuario.getId(), usuario.getNombreUsuario(), usuario.getCorreo(), usuario.getRol(),
                                usuario.isHabilitado(), usuario.getFechaCreacion());
        }
}
