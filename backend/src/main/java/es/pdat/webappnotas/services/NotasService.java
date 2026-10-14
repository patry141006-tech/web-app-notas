package es.pdat.webappnotas.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import es.pdat.webappnotas.dto.NotaRequest;
import es.pdat.webappnotas.dto.NotaResponse;
import es.pdat.webappnotas.entity.Nota;
import es.pdat.webappnotas.entity.Usuario;
import es.pdat.webappnotas.exception.RecursoNoEncontradoException;
import es.pdat.webappnotas.repository.NotaRepository;

@Service
public class NotasService {
    private NotaRepository notaRepository;
    private UsuarioService usuarioService;

    public NotasService(NotaRepository notaRepository, UsuarioService usuarioService) {
        this.notaRepository = notaRepository;
        this.usuarioService = usuarioService;
    }

    public List<NotaResponse> getNotas(String usuario) {
        Optional<Usuario> usuarioOpt = usuarioService.findByNombreUsuario(usuario);
        if (usuarioOpt.isEmpty()) {
            throw new RecursoNoEncontradoException("El usuario no existe");
        }

        List<Nota> notas = notaRepository.findByIdUsuario(usuarioOpt.get().getId());
        List<NotaResponse> notasResponse = new ArrayList<>();
        for (int i = 0; i < notas.size(); i++) {
            notasResponse.add(new NotaResponse(notas.get(i)));
        }
        return notasResponse;
    }

    public List<NotaResponse> getNotas() {
        List<Nota> notas = notaRepository.findAll();
        List<NotaResponse> notasResponse = new ArrayList<>();
        for (int i = 0; i < notas.size(); i++) {
            notasResponse.add(new NotaResponse(notas.get(i)));
        }
        return notasResponse;
    }

    public NotaResponse getNota(long id, String usuario) {
        Optional<Usuario> usuarioOpt = usuarioService.findByNombreUsuario(usuario);
        if (usuarioOpt.isEmpty()) {
            throw new RecursoNoEncontradoException("El usuario no existe");
        }

        Optional<Nota> nota = notaRepository.findByIdUsuarioAndId(usuarioOpt.get().getId(), id);

        return new NotaResponse(
                nota.orElseThrow(() -> new RecursoNoEncontradoException("La nota que buscas no existe")));
    }

    public NotaResponse insertNota(NotaRequest notaRequest, String usuario) {
        Optional<Usuario> usuarioOpt = usuarioService.findByNombreUsuario(usuario);
        if (usuarioOpt.isEmpty()) {
            throw new RecursoNoEncontradoException("El usuario no existe");
        }

        Nota nota = new Nota(notaRequest);
        nota.setIdUsuario(usuarioOpt.get().getId());
        nota = notaRepository.save(nota);
        return new NotaResponse(nota);
    }

    public NotaResponse updateNota(NotaRequest nota, long id, String usuario) {
        Optional<Usuario> usuarioOpt = usuarioService.findByNombreUsuario(usuario);
        if (usuarioOpt.isEmpty()) {
            throw new RecursoNoEncontradoException("El usuario no existe");
        }

        Nota notaGuardada = notaRepository.findByIdUsuarioAndId(usuarioOpt.get().getId(), id)
                .orElseThrow(() -> new RecursoNoEncontradoException("La nota que buscas no existe"));
        notaGuardada.setTitulo(nota.titulo());
        notaGuardada.setContenido(nota.contenido());
        notaGuardada.setFechaActualizacion(new Date());

        notaGuardada = notaRepository.save(notaGuardada);

        return new NotaResponse(notaGuardada);
    }

    public void deleteNota(long id, String usuario) {
        Optional<Usuario> usuarioOpt = usuarioService.findByNombreUsuario(usuario);
        if (usuarioOpt.isEmpty()) {
            throw new RecursoNoEncontradoException("El usuario no existe");
        }

        Optional<Nota> nota = notaRepository.findByIdUsuarioAndId(usuarioOpt.get().getId(), id);
        if (nota.isPresent()) {
            notaRepository.deleteById(id);
        }
    }
}
