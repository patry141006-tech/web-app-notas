package es.pdat.webappnotas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.pdat.webappnotas.entity.Nota;

public interface NotaRepository
        extends JpaRepository<Nota, Long> {

        public List<Nota> findByIdUsuario (Long idUsuario);
}
