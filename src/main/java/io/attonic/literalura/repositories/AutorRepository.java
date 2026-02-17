package io.attonic.literalura.repositories;

import io.attonic.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a WHERE a.anoNascimento <= :ano AND (a.anoFalescimento IS NULL OR a.anoFalescimento >= :ano)")
    List<Autor> buscarAutorVivosNoAno(Integer ano);

    Optional<Autor> findByNomeContainingIgnoreCase(String nome);
}
