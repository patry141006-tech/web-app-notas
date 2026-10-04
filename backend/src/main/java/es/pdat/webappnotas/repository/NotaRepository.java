package es.pdat.webappnotas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.pdat.webappnotas.entity.Nota;

public interface NotaRepository
        extends JpaRepository<Nota, Long> {
}
