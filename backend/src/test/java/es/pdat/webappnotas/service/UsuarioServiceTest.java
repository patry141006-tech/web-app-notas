package es.pdat.webappnotas.service;

import es.pdat.webappnotas.dto.UsuarioCrearRequest;
import es.pdat.webappnotas.dto.UsuarioResponse;
import es.pdat.webappnotas.entity.Usuario;
import es.pdat.webappnotas.exception.ConflictoException;
import es.pdat.webappnotas.exception.RecursoNoEncontradoException;
import es.pdat.webappnotas.repository.UsuarioRepository;
import es.pdat.webappnotas.services.UsuarioService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @InjectMocks
    private UsuarioService usuarioService;
    @Mock
    private PasswordEncoder passwordEncoder;

    

    @Test
    void debeContarUsuarios() {
        when(usuarioRepository.count()).thenReturn(3L);
        long resultado = usuarioService.contar();
        assertEquals(3L, resultado);
    }

    @Test
    void testInsertUsuarioThrowExceptionNombreUsuario() {
        when(usuarioRepository.existsByNombreUsuario(anyString())).thenReturn(true);
        UsuarioCrearRequest request = new UsuarioCrearRequest("Dani", null, null);
        assertThrows(ConflictoException.class, () -> usuarioService.insertUsuario(request),
                "Debe lanzar excepcion cuando el nombre de usuario ya existe");
    }

    @Test
    void testInsertUsuarioThrowExceptionCorreo() {
        when(usuarioRepository.existsByNombreUsuario(any())).thenReturn(false);
        when(usuarioRepository.existsByCorreo(anyString())).thenReturn(true);
        UsuarioCrearRequest request = new UsuarioCrearRequest(null, "Dani@Dani.com", null);
        assertThrows(ConflictoException.class, () -> usuarioService.insertUsuario(request),
                "Debe lanzar excepcion cuando el correo ya existe");
    }

    @Test
    void testInsertUsuario() {
        when(usuarioRepository.existsByNombreUsuario(any())).thenReturn(false);
        when(usuarioRepository.existsByCorreo(anyString())).thenReturn(false);
        Date date = new Date();
        Usuario usuario = new Usuario(1L, "Dani", "Dani@Dani.com", "1234", "ADMIN", true, date);
        when(usuarioRepository.save(any())).thenReturn(usuario);

        UsuarioCrearRequest request = new UsuarioCrearRequest("Dani", "Dani@Dani.com", "1234");
        UsuarioResponse response = usuarioService.insertUsuario(request);
        assertNotNull(response);
        assertEquals("Dani", response.nombreUsuario());
        assertEquals("Dani@Dani.com", response.correo());
        assertEquals("ADMIN", response.rol());
        assertEquals(true, response.habilitado());
        assertEquals(date, response.fechaCreacion());
    }

    @Test
    void testFindById() {
        Date date = new Date();
        Usuario usuario = new Usuario(1L, "dani", "dani@dani.com", "12345678", "ADMIN", true, date);
        when(usuarioRepository.findById(any())).thenReturn(Optional.of(usuario));
        UsuarioResponse usuarioResponse = usuarioService.findById(1L);
        assertEquals(usuario.getId(), usuarioResponse.id());
        assertEquals(usuario.getNombreUsuario(), usuarioResponse.nombreUsuario());
        assertEquals(usuario.getCorreo(), usuarioResponse.correo());
        assertEquals(usuario.getFechaCreacion(), usuarioResponse.fechaCreacion());
        assertEquals(usuario.getRol(), usuarioResponse.rol());
    }

    @Test
    void testFindByIdThrowException() {
        when(usuarioRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> usuarioService.findById(1L),
                "El campo esta vacio");
    }

    @Test
    void testIsLoginCorrect() {
        Date date = new Date();
        Usuario usuario = new Usuario(1L, "dani", "dani@dani.com", "12345678", "ADMIN", true, date);
        when(usuarioRepository.findByNombreUsuario(any())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(usuario.getContrasena(), usuario.getContrasena())).thenReturn(true);
        boolean result = usuarioService.isLoginCorrect(usuario.getNombreUsuario(), usuario.getContrasena());
        assertTrue(result);
    }

    @Test
    void testIsLoginCorrectFalse() {
        when(usuarioRepository.findByNombreUsuario(any())).thenReturn(Optional.empty());
        boolean result = usuarioService.isLoginCorrect("", "");
        assertFalse(result);
    }

    @Test
    void testFindByNombreUsuario() {
        Date date = new Date();
        Usuario usuario = new Usuario(1L, "dani", "dani@dani.com", "12345678", "ADMIN", true, date);
        when(usuarioRepository.findByNombreUsuario("dani")).thenReturn(Optional.of(usuario));
        Optional<Usuario> user = usuarioService.findByNombreUsuario(usuario.getNombreUsuario());
        assertEquals(usuario, user.get());
    }

    @Test
    void testGetUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        Date date = new Date();
        Usuario usuario = new Usuario(1L, "dani", "dani@dani.com", "12345678", "ADMIN", true, date);
        when(usuarioRepository.findAll()).thenReturn(listaUsuarios);

        when(usuarioRepository.findByNombreUsuario("dani")).thenReturn(Optional.of(usuario));

        List<UsuarioResponse> response = usuarioService.getUsuarios();

        assertTrue(!response.isEmpty());
    }
}