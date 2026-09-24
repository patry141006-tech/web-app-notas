package es.pdat.webappnotas.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.pdat.webappnotas.dto.UsuarioCrearRequest;
import es.pdat.webappnotas.dto.UsuarioResponse;
import es.pdat.webappnotas.entity.Usuario;
import es.pdat.webappnotas.exception.ConflictoException;
import es.pdat.webappnotas.exception.RecursoNoEncontradoException;
import es.pdat.webappnotas.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public long contar() {
        return usuarioRepository.count();
    }

    public UsuarioResponse insertUsuario(UsuarioCrearRequest usuarioRequest) {
        if (usuarioRepository.existsByNombreUsuario(usuarioRequest.nombreUsuario())) {
            throw new ConflictoException("El nombre de usuario " + usuarioRequest.nombreUsuario() + " ya está en uso");
        }
        if(usuarioRepository.existsByCorreo(usuarioRequest.correo())){
            throw new ConflictoException("El correo " + usuarioRequest.correo() + " ya está en uso");
        }


        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(usuarioRequest.nombreUsuario());
        usuario.setCorreo(usuarioRequest.correo());
        usuario.setContrasena(usuarioRequest.contrasena());
        usuario.setFechaCreacion(new Date());
        usuario.setRol("USER");

        usuario = usuarioRepository.save(usuario);
        return new UsuarioResponse(usuario.getId(), usuario.getNombreUsuario(), usuario.getCorreo(), usuario.getRol(),
                usuario.isHabilitado(), usuario.getFechaCreacion());
    }

    public UsuarioResponse findById(Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            throw new RecursoNoEncontradoException("Usuario con ID " + id + " no encontrado");
        }

        Usuario usuario = usuarioOpt.get();
        return new UsuarioResponse(usuario.getId(), usuario.getNombreUsuario(), usuario.getCorreo(), usuario.getRol(),
                usuario.isHabilitado(), usuario.getFechaCreacion());
    }

}
