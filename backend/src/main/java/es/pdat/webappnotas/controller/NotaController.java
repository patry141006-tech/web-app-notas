package es.pdat.webappnotas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.pdat.webappnotas.dto.NotaRequest;
import es.pdat.webappnotas.dto.NotaResponse;
import es.pdat.webappnotas.services.NotasService;

@RestController
@RequestMapping("/api/notas")
public class NotaController {
    private NotasService notaService;

    public NotaController(NotasService notaService) {
        this.notaService = notaService;
    }

    @GetMapping()
    public List<NotaResponse> getNotas() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return notaService.getNotas(authentication.getName());
    }

    @GetMapping("/{id}")
    public NotaResponse getNota(@PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return notaService.getNota(id, authentication.getName());
    }

    @PostMapping()
    public NotaResponse crear(@RequestBody NotaRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return notaService.insertNota(request, authentication.getName());
    }

    @PutMapping("/{id}")
    public NotaResponse update(@RequestBody NotaRequest request, @PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return notaService.updateNota(request, id, authentication.getName());
    }

    @DeleteMapping("/{id}")
    public BodyBuilder delete(@PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        notaService.deleteNota(id, authentication.getName());
        return ResponseEntity.ok();
    }
}
