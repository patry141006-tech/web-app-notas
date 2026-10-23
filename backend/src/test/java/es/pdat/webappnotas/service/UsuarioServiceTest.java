package es.pdat.webappnotas.service;

import es.pdat.webappnotas.dto.UsuarioCrearRequest;
import es.pdat.webappnotas.dto.UsuarioResponse;
import es.pdat.webappnotas.entity.Usuario;
import es.pdat.webappnotas.exception.ConflictoException;
import es.pdat.webappnotas.repository.UsuarioRepository;
import es.pdat.webappnotas.services.UsuarioService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Date;

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
        assertThrows(ConflictoException.class, () -> usuarioService.insertUsuario(request), "Debe lanzar excepcion cuando el nombre de usuario ya existe");
    }

    @Test
    void testInsertUsuarioThrowExceptionCorreo() {
        when(usuarioRepository.existsByNombreUsuario(any())).thenReturn(false);
        when(usuarioRepository.existsByCorreo(anyString())).thenReturn(true);
        UsuarioCrearRequest request = new UsuarioCrearRequest(null, "Dani@Dani.com", null);
        assertThrows(ConflictoException.class, () -> usuarioService.insertUsuario(request), "Debe lanzar excepcion cuando el correo ya existe");
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

}