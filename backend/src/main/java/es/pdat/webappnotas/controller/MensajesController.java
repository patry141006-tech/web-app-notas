package es.pdat.webappnotas.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.pdat.webappnotas.dto.MensajeRequest;
import es.pdat.webappnotas.dto.MensajeResponse;

@RestController
public class MensajesController {
    @PostMapping("/api/mensajes")
    public MensajeResponse crear(@RequestBody MensajeRequest request) {
        return new MensajeResponse("Has enviado: " + request.autor() + " : "+ request.texto());
    }
}
