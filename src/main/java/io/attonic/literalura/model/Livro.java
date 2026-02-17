package io.attonic.literalura.model;

import io.attonic.literalura.model.dto.DadosLivro;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "livros")
@Data
@NoArgsConstructor
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String idioma;

    private Double numeroDownloads;

    @ManyToOne
    private Autor autor;

    public Livro(DadosLivro dadosLivro){
        this.titulo = dadosLivro.titulo();
        this.numeroDownloads = dadosLivro.numeroDownloads();

        if (dadosLivro.idiomas() != null && !dadosLivro.idiomas().isEmpty()){
            this.idioma = dadosLivro.idiomas().get(0);
        }
    }
}
