package es.pdat.webappnotas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import es.pdat.webappnotas.dto.UsuarioResponse;
import es.pdat.webappnotas.services.UsuarioService;
import jakarta.websocket.server.PathParam;

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

   @GetMapping ("/api/usuarios/{id}")
   public ResponseEntity<UsuarioResponse> getUsuario(@PathParam("id") Long id){
        UsuarioResponse usu= usuarioService.findById(id);
        return ResponseEntity.ok(usu);

   } 

}
