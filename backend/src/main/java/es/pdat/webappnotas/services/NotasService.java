package es.pdat.webappnotas.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import es.pdat.webappnotas.dto.NotaRequest;
import es.pdat.webappnotas.dto.NotaResponse;
import es.pdat.webappnotas.entity.Nota;
import es.pdat.webappnotas.exception.RecursoNoEncontradoException;
import es.pdat.webappnotas.repository.NotaRepository;
import es.pdat.webappnotas.security.AuthUsuario;

@Service
public class NotasService extends AuthUsuario {
    private NotaRepository notaRepository;

    public NotasService(NotaRepository notaRepository) {
        this.notaRepository = notaRepository;
    }

    public List<NotaResponse> getNotas() {
        List<Nota> notas = notaRepository.findByIdUsuario(getIdUsuario());
        List<NotaResponse> notasResponse = new ArrayList<>();
        for (int i = 0; i < notas.size(); i++) {
            notasResponse.add(new NotaResponse(notas.get(i)));
        }
        return notasResponse;
    }

    public NotaResponse getNota(long id) {
        Optional<Nota> nota = notaRepository.findByIdUsuarioAndId(getIdUsuario(), id);

        return new NotaResponse(
                nota.orElseThrow(() -> new RecursoNoEncontradoException("La nota que buscas no existe")));
    }

    public NotaResponse insertNota(NotaRequest notaRequest) {
        Nota nota = new Nota(notaRequest);
        nota.setIdUsuario(getIdUsuario());
        nota = notaRepository.save(nota);
        return new NotaResponse(nota);
    }

    public NotaResponse updateNota(NotaRequest nota, long id) {
        Nota notaGuardada = notaRepository.findByIdUsuarioAndId(getIdUsuario(), id)
                .orElseThrow(() -> new RecursoNoEncontradoException("La nota que buscas no existe"));
        notaGuardada.setTitulo(nota.titulo());
        notaGuardada.setContenido(nota.contenido());
        notaGuardada.setFechaActualizacion(new Date());

        notaGuardada = notaRepository.save(notaGuardada);

        return new NotaResponse(notaGuardada);
    }

    public void deleteNota(long id) {
        Optional<Nota> nota = notaRepository.findByIdUsuarioAndId(getIdUsuario(), id);
        if (nota.isPresent()) {
            notaRepository.deleteById(id);
        }
    }
}
