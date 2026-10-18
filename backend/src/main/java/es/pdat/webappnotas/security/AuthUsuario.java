package es.pdat.webappnotas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import es.pdat.webappnotas.entity.Usuario;
import es.pdat.webappnotas.exception.RecursoNoEncontradoException;
import es.pdat.webappnotas.repository.UsuarioRepository;

public class AuthUsuario {
    @Autowired
    private UsuarioRepository usuarioRepository;

    protected String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    protected Long getIdUsuario() {
        Usuario usuario = usuarioRepository.findByNombreUsuario(getUserName())
                .orElseThrow(() -> new RecursoNoEncontradoException("El usuario que buscas no existe"));
        return usuario.getId();
    }

    protected boolean isAdmin() {
        Usuario usuario = usuarioRepository.findByNombreUsuario(getUserName())
                .orElseThrow(() -> new RecursoNoEncontradoException("El usuario que buscas no existe"));
        return "ADMIN".equals(usuario.getRol());
    }
}
