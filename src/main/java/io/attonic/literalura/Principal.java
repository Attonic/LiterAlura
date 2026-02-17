package io.attonic.literalura;

import io.attonic.literalura.client.ConsumoApi;
import io.attonic.literalura.model.dto.DadosLivro;
import io.attonic.literalura.model.dto.DadosResposta;
import io.attonic.literalura.util.ConvertDados;

import java.util.Scanner;

public class Principal {
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvertDados conversor = new ConvertDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private Scanner leitura = new Scanner(System.in);

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

        System.out.println("\n--- RESULTADOS ---");
        if (!resposta.resultados().isEmpty()) {
            DadosLivro livro = resposta.resultados().get(0);
            System.out.println("Título: " + livro.titulo());
            System.out.println("Autor: " + livro.autores().get(0).nome());
            System.out.println("Idioma: " + livro.idiomas().get(0));
            System.out.println("Downloads: " + livro.numeroDownloads());
        } else {
            System.out.println("Nenhum livro encontrado.");
        }
    }

    private void listarLivros() {
        System.out.println("Você escolheu: Listar todos os livros (Lógica em construção...)");
    }

    private void listarPorIdioma() {
        System.out.println("Você escolheu: Listar por idioma (Lógica em construção...)");
    }

    private void listarAutoresVivos() {
        System.out.println("Você escolheu: Listar autores vivos (Lógica em construção...)");
    }
}
