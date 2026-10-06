package es.pdat.webappnotas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.pdat.webappnotas.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByCorreo(String correo);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}
