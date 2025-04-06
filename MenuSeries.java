
import java.util.Scanner;
import java.util.ArrayList;

public class MenuSeries {

    ArquivoSerie arqSeries;
    ArquivoEpisodio arqEpisodios;

    private static Scanner scan = new Scanner(System.in);

    public MenuSeries() throws Exception {
        arqSeries = new ArquivoSerie();
        arqEpisodios = new ArquivoEpisodio();
    }

    public void menu() throws Exception {

        int opcao;
        do {

            System.out.println("\n\nAEDsIII");
            System.out.println("-------");
            System.out.println("> Início > Series");
            System.out.println("\n1 - Buscar");
            System.out.println("2 - Incluir");
            System.out.println("3 - Alterar");
            System.out.println("4 - Excluir");
            System.out.println("5 - Temporada");
            System.out.println("6 - Todos os Episodios");
            System.out.println("0 - Voltar");

            System.out.print("\nOpção: ");
            try {
                opcao = Integer.valueOf(scan.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    buscarSerie();
                    break;
                case 2:
                    incluirSerie();
                    break;
                case 3:
                    alterarSerie();
                    break;
                case 4:
                    excluirSerie();
                    break;
                case 5:
                    buscarTemporada();
                    break;
                case 6:
                    buscarEpisodios();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }

    public void buscarTemporada() throws Exception {
        System.out.print("\nNome da Serie: ");
        String nome = scan.nextLine();
        System.out.println("\nTemporada: ");
        int temporada = scan.nextInt();
        scan.nextLine();  
        Episodio ep;

        if (nome != null) {
            try {
                ArrayList<Episodio> series = arqEpisodios.readSerie(nome);  // Chama o método de leitura da classe Arquivo
                if (series != null) {
                    for (int i = 0; i < series.size(); i++) {
                        ep = series.get(i);
                        if (ep != null && ep.temporada == temporada) {
                            ep.mostraEpisodio();
                        }

                    }
                } else {
                    System.out.println("Nenhum episodio encontrado.");
                }
            } catch (Exception e) {
                System.out.println("Erro do sistema. Não foi possível buscar a serie!");
                e.printStackTrace();
            }
        } else {
            System.out.println("ID inválido.");
        }
    }

    public void buscarEpisodios() throws Exception {
        System.out.print("\nNome da Serie: ");
        String nome = scan.nextLine();  // Lê o ID digitado pelo usuário
        Episodio ep;

        if (nome != null) {
            try {
                ArrayList<Episodio> series = arqEpisodios.readSerie(nome);  // Chama o método de leitura da classe Arquivo
                if (series != null) {
                    for (int i = 0; i < series.size(); i++) {
                        ep = series.get(i);
                        if (ep != null) {
                            ep.mostraEpisodio();
                        }
                    }
                } else {
                    System.out.println("Nenhum episodio encontrado.");
                }
            } catch (Exception e) {
                System.out.println("Erro do sistema. Não foi possível buscar VELHO a serie!");
                e.printStackTrace();
            }
        } else {
            System.out.println("ID inválido.");
        }
    }

    public void buscarSerie() {
        System.out.print("\nnome da Serie: ");
        String nome = scan.nextLine();  // Lê o ID digitado pelo usuário
        // Limpar o buffer após o nextInt()

        if (nome != null) {
            try {
                Serie serie = arqSeries.read(nome);  // Chama o método de leitura da classe Arquivo
                if (serie != null) {
                    mostraSerie(serie);  // Exibe os detalhes do serie encontrado
                } else {
                    System.out.println("Serie não encontrada.");
                }
            } catch (Exception e) {
                System.out.println("Erro do sistema. Não foi possível buscar VELHO a serie!");
                e.printStackTrace();
            }
        } else {
            System.out.println("ID inválido.");
        }
    }

    public void incluirSerie() {

        String nome = "";
        short anoLancamento = 0;
        String sinopse = "";
        String streaming = "";

        boolean dadosCorretos = false;

        System.out.println("\nInclusão de Serie");

        do {
            System.out.print("\nNome (min. de 2 letras ou vazio para cancelar): ");
            nome = scan.nextLine();
            if (nome.length() == 0) {
                return;
            }
            if (nome.length() < 2) {
                System.err.println("O nome da serie deve ter no mínimo 2 caracteres.");
            }
        } while (nome.length() < 2);

        do {

            dadosCorretos = false;
            System.out.print("Ano de lançamento: ");
            if (scan.hasNextInt()) {
                anoLancamento = scan.nextShort();
                dadosCorretos = true;
            } else {
                System.err.println("Ano de Lançamento inválido! Por favor, insira um número válido.");
            }

            scan.nextLine(); // Limpar o buffer 
        } while (!dadosCorretos);

        do {
            System.out.print("\nSinopse (min. de 10 letras ou vazio para cancelar): ");
            sinopse = scan.nextLine();
            if (sinopse.length() == 0) {
                return;
            }
            if (sinopse.length() < 10) {
                System.err.println("A sinopse da serie deve ter no mínimo 10 caracteres.");
            }
        } while (sinopse.length() < 10);

        do {
            System.out.print("\nNome do streaming (min. de 4 letras ou vazio para cancelar): ");
            streaming = scan.nextLine();
            if (streaming.length() == 0) {
                return;
            }
            if (streaming.length() < 4) {
                System.err.println("O nome do streaming deve ter no mínimo 4 caracteres.");
            }
        } while (streaming.length() < 4);

        System.out.print("\nConfirma a inclusão da serie? (S/N) ");

        char resp = scan.nextLine().charAt(0);
        if (resp == 'S' || resp == 's') {
            try {
                Serie s = new Serie(nome, anoLancamento, sinopse, streaming);
                arqSeries.create(s);

                System.out.println("Serie incluída com sucesso.");

            } catch (Exception e) {
                System.out.println("Erro do sistema. Não foi possível incluir o serie!");
            }
        }
    }

    public void alterarSerie() throws Exception {

        System.out.print("\nDigite o Nome do serie a ser alterada: ");
        String nome = scan.nextLine();
        Serie serie;

        if (nome != null) {
            try {
                // Tenta ler o serie com o ID fornecido
                serie = arqSeries.read(nome);
                if (serie != null) {
                    System.out.println("Serie encontrada:");
                    mostraSerie(serie);  // Exibe os dados do serie para confirmação

                    // Alteração de Nome
                    System.out.print("\nNovo nome (deixe em branco para manter o anterior): ");
                    String novoNome = scan.nextLine();
                    if (!novoNome.isEmpty()) {
                        serie.nome = novoNome;  // Atualiza o nome se fornecido
                    }

                    // Alteração de ano de Lançamento
                    System.out.print("Novo Ano de Lançamento: ");

                    if (scan.hasNextInt()) {
                        short novoAno = scan.nextShort();
                        serie.anoLancamento = novoAno;
                    } else {
                        System.out.println("Insira um Ano de Lançamento válido");
                    }

                    // Alteração de sinopse
                    System.out.print("\nNova sinopse (deixe um espaco em branco para manter a anterior): ");
                    if (scan.hasNext()) {
                        String novaSinopse = scan.nextLine();
                        novaSinopse = scan.nextLine();
                        if (!novaSinopse.equals("") || !novaSinopse.equals(" ")) {
                            serie.sinopse = novaSinopse;
                        } else {
                            System.out.print("\nSinopse nao pode ser espaco em branco");
                        }
                    }

                    System.out.print("\nNome do streaming (min. de 4 letras ou vazio para cancelar): ");
                    String novoStreaming = scan.nextLine();
                    if (!novoStreaming.isEmpty()) {
                        serie.streaming = novoStreaming;
                    }

                    // Confirmação da alteração
                    System.out.print("\nConfirma as alterações? (S/N) ");
                    char resp = scan.next().charAt(0);
                    if (resp == 'S' || resp == 's') {
                        // Salva as alterações no arquivo
                        boolean alterado = arqSeries.update(serie, nome);

                        if (alterado) {

                            System.out.println("Serie alterada com sucesso.");

                        } else {

                            System.out.println("Erro ao alterar a Serie.");
                        }
                    } else {
                        System.out.println("Alterações canceladas.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Erro do sistema. Não foi possível alterar a Serie!");
                e.printStackTrace();
            }
        } else {
            System.out.println("ID inválido.");
        }
    }

    public void excluirSerie() {
        System.out.print("\nDigite o nome da serie a ser excluída: ");
        String nome = scan.nextLine();
        //  scan.nextInt();
        if (nome != null) {
            try {
                // Tenta ler o serie com o ID fornecido
                Serie serie = arqSeries.read(nome);
                if (serie != null) {
                    System.out.println("Serie encontrada:");
                    mostraSerie(serie);  // Exibe os dados do serie para confirmação

                    if (hasEpisodios(serie.nome)) {
                        System.out.print("\nSerie possui episodios associados.\nPara excluir serie, exclua todos os episodios associados a ela");
                    } else {
                        System.out.print("\nConfirma a exclusão do serie? (S/N) ");
                        char resp = scan.next().charAt(0);  // Lê a resposta do usuário

                        if (resp == 'S' || resp == 's') {
                            boolean excluido = arqSeries.delete(nome);  // Chama o método de exclusão no arquivo
                            if (excluido) {
                                System.out.println("Serie excluída com sucesso.");
                            } else {
                                System.out.println("Erro ao excluir a serie.");
                            }
                        } else {
                            System.out.println("Exclusão cancelada.");
                        }
                    }

                } else {
                    System.out.println("Serie não encontrada.");
                }
            } catch (Exception e) {
                System.out.println("Erro do sistema. Não foi possível excluir a serie!");
                e.printStackTrace();
            }
        } else {
            System.out.println("ID inválido.");
        }
    }

    public boolean hasEpisodios(String nome) throws Exception {
        boolean res = true;

        ArrayList<Episodio> lista = arqEpisodios.readSerie(nome);

        if (lista == null) {
            res = false;
        }

        return res;
    }

    public void mostraSerie(Serie serie) {

        if (serie != null) {
            System.out.println("\nDetalhes do serie:");
            System.out.println("----------------------");
            System.out.print("\nNome.............: " + serie.nome);
            System.out.print("\nAno de lançamento: " + serie.anoLancamento);
            System.out.print("\nSinopse..........: " + serie.sinopse);
            System.out.print("\nStreaming........: " + serie.streaming);
            System.out.println();

            System.out.println("----------------------");
        }
    }
}
