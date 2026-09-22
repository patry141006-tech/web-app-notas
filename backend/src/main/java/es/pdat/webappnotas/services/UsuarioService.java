package es.pdat.webappnotas.services;

import org.springframework.stereotype.Service;

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

}
