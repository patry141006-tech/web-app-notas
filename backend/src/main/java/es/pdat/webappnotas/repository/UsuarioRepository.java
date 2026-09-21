package es.pdat.webappnotas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.pdat.webappnotas.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
