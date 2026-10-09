package es.pdat.webappnotas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.pdat.webappnotas.dto.NotaRequest;
import es.pdat.webappnotas.dto.NotaResponse;
import es.pdat.webappnotas.services.NotasService;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/notas")
public class NotaController {
    private NotasService notaService;

    public NotaController(NotasService notaService) {
        this.notaService = notaService;
    }

    @GetMapping()
    public List<NotaResponse> getNotas() {
        return notaService.getNotas();
    }

    @GetMapping("/{id}")
    public NotaResponse getNota(@PathParam("id") Long id) {
        return notaService.getNota(id);
    }

    @PostMapping()
    public NotaResponse crear(@RequestBody NotaRequest request) {
        return notaService.insertNota(request);
    }

    @PutMapping("/{id}")
    public NotaResponse update(@RequestBody NotaRequest request, @PathParam("id") Long id) {
        return notaService.updateNota(request, id);
    }

    @DeleteMapping("/{id}")
    public BodyBuilder delete(@PathParam("id") Long id) {
        notaService.deleteNota(id);
        return ResponseEntity.ok();
    }
}
