package es.pdat.webappnotas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import es.pdat.webappnotas.services.UsuarioService;

@RestController
public class UsuarioController {  

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/api/usuarios/count")
    public long contar() {
        return usuarioService.contar();
    }
    
}
