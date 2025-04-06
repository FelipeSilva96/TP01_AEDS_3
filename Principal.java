
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {

        Scanner scan;

        try {
            scan = new Scanner(System.in);
            int opcao;
            do {

                System.out.println("\n\nAEDsIII");
                System.out.println("-------");
                System.out.println("> Inicio");
                System.out.println("\n1) Series");
                System.out.println("2) Episodios");
                System.out.println("3) Atores"); // ignorar por enquanto
                System.out.println("4) Sair");

                System.out.print("\nOpcao: ");
                try {
                    opcao = Integer.valueOf(scan.nextLine());
                } catch (NumberFormatException e) {
                    opcao = -1;
                }

                switch (opcao) {
                    case 1:
                        new MenuSeries().menu();
                        break;
                    case 2:
                        new MenuEpisodios().menu();
                        break;
                    case 3:
                    //  (new MenuAtores().menu) // Ignorar por enquanto
                    //   break;
                    case 4:
                        break;
                    default:
                        System.out.println("Opcao invalida!");
                        break;
                }

            } while (opcao != 4);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
