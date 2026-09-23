package es.pdat.webappnotas.services;

import java.util.Date;

import org.springframework.stereotype.Service;

import es.pdat.webappnotas.dto.UsuarioCrearRequest;
import es.pdat.webappnotas.dto.UsuarioResponse;
import es.pdat.webappnotas.entity.Usuario;
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

    public UsuarioResponse insertUsuario (UsuarioCrearRequest usuarioRequest){
        Usuario usuario= new Usuario();
        usuario.setNombreUsuario(usuarioRequest.nombreUsuario());
        usuario.setCorreo(usuarioRequest.correo());
        usuario.setContrasena(usuarioRequest.contrasena());
        usuario.setFechaCreacion(new Date());
        usuario.setRol("USER");

        usuario = usuarioRepository.save(usuario);
        return new UsuarioResponse(usuario.getNombreUsuario(), usuario.getCorreo());
    }

}
