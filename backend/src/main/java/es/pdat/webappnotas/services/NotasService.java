package es.pdat.webappnotas.services;

import org.springframework.stereotype.Service;
import es.pdat.webappnotas.dto.NotaRequest;
import es.pdat.webappnotas.dto.NotaResponse;
import es.pdat.webappnotas.entity.Nota;
import es.pdat.webappnotas.repository.NotaRepository;

@Service
public class NotasService {
    private NotaRepository notaRepository;
    

    public NotasService(NotaRepository notaRepository) {
        this.notaRepository = notaRepository;
    }

    public NotaResponse insertNota(NotaRequest notaRequest) {
        Nota nota = new Nota(notaRequest);
        nota.setIdUsuario(1l);
        nota = notaRepository.save(nota);
        return new NotaResponse(nota);  
    }

}
