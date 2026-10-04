package es.pdat.webappnotas.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.pdat.webappnotas.dto.NotaRequest;
import es.pdat.webappnotas.dto.NotaResponse;
import es.pdat.webappnotas.services.NotasService;

@RestController
public class NotaController {
    private NotasService notaService;
    
    public NotaController(NotasService notaService) {
        this.notaService = notaService;
    }

    @PostMapping("/api/nota")
    public NotaResponse crear(@RequestBody NotaRequest request) {
        return notaService.insertNota(request);
    }
}
