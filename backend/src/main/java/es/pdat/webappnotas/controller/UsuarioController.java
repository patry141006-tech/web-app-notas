package es.pdat.webappnotas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.pdat.webappnotas.dto.UsuarioResponse;
import es.pdat.webappnotas.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/count")
    public long contar() {
        return usuarioService.contar();
    }

   @GetMapping ("/{id}")
   public ResponseEntity<UsuarioResponse> getUsuario(@PathVariable("id") Long id){
        UsuarioResponse usu= usuarioService.findById(id);
        return ResponseEntity.ok(usu);
   } 
   @GetMapping ()
   public List<UsuarioResponse> getUsuarios(){
        return usuarioService.getUsuarios();
   }

}
