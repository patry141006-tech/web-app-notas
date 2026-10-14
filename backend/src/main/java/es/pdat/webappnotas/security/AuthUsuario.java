package es.pdat.webappnotas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import es.pdat.webappnotas.entity.Usuario;
import es.pdat.webappnotas.exception.RecursoNoEncontradoException;
import es.pdat.webappnotas.services.UsuarioService;

public class AuthUsuario {
    @Autowired
    private UsuarioService usuarioService;

    protected String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    protected Long getIdUsuario() {
        Usuario usuario = usuarioService.findByNombreUsuario(getUserName())
                .orElseThrow(() -> new RecursoNoEncontradoException("La nota que buscas no existe"));
        return usuario.getId();
    }
}
