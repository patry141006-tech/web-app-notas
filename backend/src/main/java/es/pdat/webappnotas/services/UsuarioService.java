package es.pdat.webappnotas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.pdat.webappnotas.repository.UsuarioRepository;

@Service
public class UsuarioService {
   @Autowired  
    private UsuarioRepository usuarioRepository;
    public long contar() {
        return usuarioRepository.count();
   }
}


