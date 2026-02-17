package io.attonic.literalura.model;

import io.attonic.literalura.model.dto.DadosAutor;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Integer anoNascimento;

    private Integer anoFalescimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livro = new ArrayList<>();

    public Autor(DadosAutor dadosAutor){
        this.nome = dadosAutor.nome();
        this.anoFalescimento = dadosAutor.anoFalecimento();
        this.anoNascimento = dadosAutor.anoNascimento();
    }
}
