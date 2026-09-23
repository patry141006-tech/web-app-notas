package es.pdat.webappnotas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.pdat.webappnotas.dto.UsuarioCrearRequest;
import es.pdat.webappnotas.dto.UsuarioResponse;
import es.pdat.webappnotas.services.UsuarioService;
import jakarta.validation.Valid;

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

    @PostMapping
    public ResponseEntity<UsuarioResponse> crear(
            @Valid @RequestBody UsuarioCrearRequest request) {
        UsuarioResponse usu = usuarioService.insertUsuario(request);
        return ResponseEntity.ok(usu);
    }

}
