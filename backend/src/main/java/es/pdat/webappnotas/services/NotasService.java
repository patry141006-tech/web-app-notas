package es.pdat.webappnotas.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import es.pdat.webappnotas.dto.NotaRequest;
import es.pdat.webappnotas.dto.NotaResponse;
import es.pdat.webappnotas.entity.Nota;
import es.pdat.webappnotas.exception.RecursoNoEncontradoException;
import es.pdat.webappnotas.repository.NotaRepository;

@Service
public class NotasService {
    private NotaRepository notaRepository;

    public NotasService(NotaRepository notaRepository) {
        this.notaRepository = notaRepository;
    }

    public List<NotaResponse> getNotas() {
        List<Nota> notas = notaRepository.findAll();
        List<NotaResponse> notasResponse = new ArrayList<>();
        for (int i = 0; i < notas.size(); i++) {
            notasResponse.add(new NotaResponse(notas.get(i)));
        }
        return notasResponse;
    }

    public NotaResponse getNota(long id) {
        return new NotaResponse(notaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("La nota que buscas no existe")));
    }

    public NotaResponse insertNota(NotaRequest notaRequest) {
        Nota nota = new Nota(notaRequest);
        nota.setIdUsuario(1l);
        nota = notaRepository.save(nota);
        return new NotaResponse(nota);
    }

    public NotaResponse updateNota(NotaRequest nota, long id) {
        Nota notaGuardada = notaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("La nota que buscas no existe"));
        notaGuardada.setTitulo(nota.titulo());
        notaGuardada.setContenido(nota.contenido());
        notaGuardada.setFechaActualizacion(new Date());

        notaGuardada = notaRepository.save(notaGuardada);

        return new NotaResponse(notaGuardada);
    }

    public void deleteNota(long id) {
        notaRepository.deleteById(id);
    }

}
