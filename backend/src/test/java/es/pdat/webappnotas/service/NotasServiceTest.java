package es.pdat.webappnotas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.pdat.webappnotas.dto.NotaResponse;
import es.pdat.webappnotas.entity.Nota;
import es.pdat.webappnotas.entity.Usuario;
import es.pdat.webappnotas.exception.RecursoNoEncontradoException;
import es.pdat.webappnotas.repository.NotaRepository;
import es.pdat.webappnotas.repository.UsuarioRepository;
import es.pdat.webappnotas.services.NotasService;

@ExtendWith(MockitoExtension.class)
public class NotasServiceTest {
    @Mock
    private NotaRepository notaRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @InjectMocks
    private NotasService notaService;

    @BeforeEach
    void setUser() {
        Date date = new Date();
        Usuario usuario = new Usuario(1L, "dani", "dani@dani.com", "12345678", "USER", true, date);

        when(usuarioRepository.findByNombreUsuario(anyString())).thenReturn(Optional.of(usuario));
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        when(authentication.getName()).thenReturn(usuario.getNombreUsuario());
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void getNotasTest() {
        List<Nota> listaNotas = new ArrayList<>();
        Nota nota = new Nota();
        nota.setId(56L);
        listaNotas.add(nota);
        when(notaRepository.findByIdUsuario(1L)).thenReturn(listaNotas);

        List<NotaResponse> response = notaService.getNotas();
        assertFalse(response.isEmpty());

    }

    @Test
    void getNotaTest() {
        Nota nota = new Nota();
        nota.setId(3L);
        when(notaRepository.findByIdUsuarioAndId(1L, 3L)).thenReturn(Optional.of(nota));
        NotaResponse response = notaService.getNota(3L);
        assertEquals(3L, response.id());

    }

    @Test
    void getNotaTestThrow() {
        when(notaRepository.findByIdUsuarioAndId(1L, 3L)).thenReturn(null);
       assertThrows(RecursoNoEncontradoException.class, () -> notaRepository.findByIdUsuarioAndId(1L,3L),
                "El campo esta vacio");
    }
}
