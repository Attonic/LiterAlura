package io.attonic.literalura;

import io.attonic.literalura.client.ConsumoApi;
import io.attonic.literalura.model.Autor;
import io.attonic.literalura.model.Livro;
import io.attonic.literalura.model.dto.DadosAutor;
import io.attonic.literalura.model.dto.DadosLivro;
import io.attonic.literalura.model.dto.DadosResposta;
import io.attonic.literalura.repositories.AutorRepository;
import io.attonic.literalura.repositories.LivroRepository;
import io.attonic.literalura.util.ConvertDados;
import lombok.RequiredArgsConstructor;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class Principal {
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvertDados conversor = new ConvertDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private Scanner leitura = new Scanner(System.in);



    private final LivroRepository livroRepositorio;
    private final AutorRepository autorRepositorio;

    public void exibirMenu(){
        var opcao = -1;

        while (opcao != 0) {
            var menu = """
            *** Bem-vindo ao LiterAlura! ***
                    Escolha uma opção pelo número:

            1 - Buscar livro pelo título na API
            2 - Listar todos os livros cadastrados no banco
            3 - Exibir quantidade de livros em um idioma específico
            4 - Listar autores vivos em um determinado ano

            0 - Sair
            """;
            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao){
                case 1:
                    buscarLivroWeb();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    listarPorIdioma();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 0:
                    System.out.println("Encerrando o LiterAlura.");
                    break;
                default:
                    System.out.println("Opção inalida! Tente Novamente.");
            }
        }

    }

    private void buscarLivroWeb() {
        System.out.println("Digite o nome do Livro para buscar: ");
        var nomeLivro = leitura.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeLivro.replace(" ", "%20"));
        DadosResposta resposta = conversor.obterDados(json, DadosResposta.class);

        if (!resposta.resultados().isEmpty()) {
            DadosLivro dadosLivro = resposta.resultados().get(0);

            DadosAutor dadosAutor = dadosLivro.autores().get(0);
            Autor autor = autorRepositorio.findByNomeContainingIgnoreCase(dadosAutor.nome())
                    .orElseGet(() -> autorRepositorio.save(new Autor(dadosAutor)));


            Livro livro = new Livro(dadosLivro);
            livro.setAutor(autor);


            System.out.println("\n--- LIVRO ENCONTRADO ---");
            System.out.println("Título: " + livro.getTitulo());
            System.out.println("Autor: " + autor.getNome());
            System.out.println("Idioma: " + livro.getIdioma());
            System.out.println("Downloads: " + livro.getNumeroDownloads());


            livroRepositorio.save(livro);
            System.out.println("\nLivro salvo com sucesso no banco de dados!");

        } else {
            System.out.println("Livro não encontrado na API.");
        }
    }


    private void listarLivros() {
        List<Livro> livros = livroRepositorio.findAll();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado no banco de dados.");
        } else {
            System.out.println("\n--- TODOS OS LIVROS CADASTRADOS ---");
            livros.forEach(l -> {
                System.out.println("Título: " + l.getTitulo() +
                        " | Autor: " + l.getAutor().getNome() +
                        " | Idioma: " + l.getIdioma() +
                        " | Downloads: " + l.getNumeroDownloads());
            });
        }
    }

    private void listarPorIdioma() {
        System.out.println("Digite o idioma para busca (ex: en, pt, es):");
        var idioma = leitura.nextLine();

        List<Livro> livrosPorIdioma = livroRepositorio.findByIdioma(idioma);

        if (livrosPorIdioma.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o idioma informado.");
        } else {
            System.out.println("\n--- LIVROS NO IDIOMA " + idioma.toUpperCase() + " ---");
            livrosPorIdioma.forEach(l -> System.out.println("- " + l.getTitulo()));
        }
    }

    private void listarAutoresVivos() {
        System.out.println("Digite o ano que deseja pesquisar:");
        try {
            var ano = leitura.nextInt();
            leitura.nextLine();

            List<Autor> autoresVivos = autorRepositorio.buscarAutorVivosNoAno(ano);

            if (autoresVivos.isEmpty()) {
                System.out.println("Nenhum autor encontrado vivo no ano de " + ano);
            } else {
                System.out.println("\n--- AUTORES VIVOS EM " + ano + " ---");
                autoresVivos.forEach(a -> System.out.println("- " + a.getNome()));
            }
        } catch (InputMismatchException e) {
            System.out.println("Por favor, digite um número de ano válido.");
            leitura.nextLine();
        }
    }
}
