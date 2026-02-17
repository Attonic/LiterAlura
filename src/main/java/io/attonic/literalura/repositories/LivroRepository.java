package io.attonic.literalura.repositories;

import io.attonic.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Long countById(String idioma);

    List<Livro> findByIdioma(String idioma);
}
