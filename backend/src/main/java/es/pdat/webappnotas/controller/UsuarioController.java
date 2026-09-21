package es.pdat.webappnotas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import es.pdat.webappnotas.services.UsuarioService;

@RestController
public class UsuarioController {
   @Autowired  
    private UsuarioService usuarioService;

    @GetMapping("/api/usuarios/count")
    public long contar() {
        return usuarioService.contar();
    }
}
