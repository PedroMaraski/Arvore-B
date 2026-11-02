import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = 0;
        while (t < 2) {
            System.out.print("Grau mínimo t (>= 2): ");
            t = sc.nextInt();
        }
        ArvoreB arvore = new ArvoreB(t);

        int opcao = -1;
        while (opcao != 0) {
            System.out.println();
            System.out.println("1-Inserir  2-Buscar  3-Remover  4-Imprimir  0-Sair");
            System.out.print("Opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            if (opcao == 1) {
                System.out.print("Código: ");
                int cod = sc.nextInt();
                sc.nextLine();
                System.out.print("Nome: ");
                String nome = sc.nextLine();
                if (arvore.inserir(cod, nome)) {
                    System.out.println("Cadastrado.");
                } else {
                    System.out.println("Código já existe.");
                }
            } else if (opcao == 2) {
                System.out.print("Código: ");
                int cod = sc.nextInt();
                sc.nextLine();
                String nome = arvore.buscar(cod);
                if (nome != null) {
                    System.out.println("Encontrado: " + nome);
                } else {
                    System.out.println("Não encontrado.");
                }
            } else if (opcao == 3) {
                System.out.print("Código: ");
                int cod = sc.nextInt();
                sc.nextLine();
                if (arvore.remover(cod)) {
                    System.out.println("Removido.");
                } else {
                    System.out.println("Não encontrado.");
                }
            } else if (opcao == 4) {
                arvore.imprimir();
            } else if (opcao != 0) {
                System.out.println("Opção inválida.");
            }
        }
        sc.close();
    }
}
