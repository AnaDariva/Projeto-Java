package org.example;

import org.example.model.Categoria;
import org.example.model.Tarefa;
import org.example.model.Usuario;
import org.example.service.CategoriaService;
import org.example.service.TarefaService;
import org.example.service.UsuarioService;
import org.example.vo.ResumoTarefasVO;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Inicializa os serviços
        UsuarioService usuarioService = new UsuarioService();
        TarefaService tarefaService = new TarefaService();
        CategoriaService categoriaService = new CategoriaService();

        // Menu
        while (true) {
            System.out.println("\n===== Menu =====");
            System.out.println("1. Inserir usuário");
            System.out.println("2. Alterar usuário");
            System.out.println("3. Excluir usuário");
            System.out.println("4. Inserir tarefa");
            System.out.println("5. Alterar tarefa");
            System.out.println("6. Excluir tarefa");
            System.out.println("7. Inserir categoria");
            System.out.println("8. Alterar categoria");
            System.out.println("9. Excluir categoria");
            System.out.println("10. Consultar usuário");
            System.out.println("11. Consultar tarefa");
            System.out.println("12. Consultar categoria");
            System.out.println("13. Relatório Resumido de Tarefas");
            System.out.println("14. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();  // Limpar o buffer

            switch (opcao) {
                case 1 -> inserirUsuario(scanner, usuarioService);
                case 2 -> alterarUsuario(scanner, usuarioService);
                case 3 -> excluirUsuario(scanner, usuarioService);
                case 4 -> inserirTarefa(scanner, tarefaService);
                case 5 -> alterarTarefa(scanner, tarefaService);
                case 6 -> excluirTarefa(scanner, tarefaService);
                case 7 -> inserirCategoria(scanner, categoriaService);
                case 8 -> alterarCategoria(scanner, categoriaService);
                case 9 -> excluirCategoria(scanner, categoriaService);
                case 10 -> consultarUsuario(scanner, usuarioService);
                case 11 -> consultarTarefa(scanner, tarefaService);
                case 12 -> consultarCategoria(scanner, categoriaService);
                case 13 -> gerarRelatorioResumoTarefas(tarefaService);
                case 14 -> {
                    System.out.println("Saindo...");
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void inserirUsuario(Scanner scanner, UsuarioService usuarioService) {
        System.out.print("Digite o nome do usuário: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o email do usuário: ");
        String email = scanner.nextLine();
        System.out.print("Digite a senha do usuário: ");
        String senha = scanner.nextLine();

        usuarioService.cadastrarUsuario(nome, email, senha);
        System.out.println("Usuário inserido com sucesso!");
    }

    private static void alterarUsuario(Scanner scanner, UsuarioService usuarioService) {
        System.out.print("Digite o ID do usuário a ser alterado: ");
        Long id = scanner.nextLong();
        scanner.nextLine();  // Limpar o buffer

        Usuario usuario = usuarioService.buscarUsuario(id);
        if (usuario != null) {
            System.out.print("Digite o novo nome do usuário: ");
            String nome = scanner.nextLine();
            System.out.print("Digite o novo email do usuário: ");
            String email = scanner.nextLine();

            // Chama o método correto no serviço para alterar o usuário
            usuarioService.alterarUsuario(id, nome, email);
            System.out.println("Usuário alterado com sucesso!");
        } else {
            System.out.println("Usuário não encontrado!");
        }
    }

    private static void excluirUsuario(Scanner scanner, UsuarioService usuarioService) {
        System.out.print("Digite o ID do usuário a ser excluído: ");
        Long id = scanner.nextLong();

        usuarioService.excluirUsuario(id);
        System.out.println("Usuário excluído com sucesso!");
    }

    private static void inserirTarefa(Scanner scanner, TarefaService tarefaService) {
        System.out.print("Digite o nome da tarefa: ");
        String nome = scanner.nextLine();
        System.out.print("Digite a descrição da tarefa: ");
        String descricao = scanner.nextLine();
        System.out.print("Digite o ID do usuário responsável: ");
        Long usuarioId = scanner.nextLong();
        System.out.print("Digite o ID da categoria da tarefa: ");
        Long categoriaId = scanner.nextLong();

        tarefaService.cadastrarTarefa(nome, descricao, usuarioId, categoriaId);
        System.out.println("Tarefa inserida com sucesso!");
    }

    private static void alterarTarefa(Scanner scanner, TarefaService tarefaService) {
        System.out.print("Digite o ID da tarefa a ser alterada: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Limpar o buffer

        Tarefa tarefa = tarefaService.buscarTarefa(id);
        if (tarefa != null) {
            System.out.print("Digite o novo nome da tarefa: ");
            String nome = scanner.nextLine();
            System.out.print("Digite a nova descrição da tarefa: ");
            String descricao = scanner.nextLine();
            System.out.print("Digite o novo ID do usuário responsável: ");
            Long usuarioId = scanner.nextLong();
            System.out.print("Digite o novo ID da categoria da tarefa: ");
            Long categoriaId = scanner.nextLong();

            // Atualizar os campos da tarefa
            tarefa.setNome(nome);
            tarefa.setDescricao(descricao);

            // Buscar ou criar o usuário e categoria
            Usuario usuario = new Usuario(); // Ou buscar usuário no banco se necessário
            usuario.setId(usuarioId);
            tarefa.setUsuario(usuario);

            Categoria categoria = new Categoria(); // Ou buscar categoria no banco se necessário
            categoria.setId(categoriaId);
            tarefa.setCategoria(categoria);

            // Passar o objeto tarefa para o método alterarTarefa
            Tarefa tarefaAlterada = new Tarefa();
            tarefaAlterada.setId(tarefa.getId());
            tarefaAlterada.setNome(tarefa.getNome());
            tarefaAlterada.setDescricao(tarefa.getDescricao());
            tarefaAlterada.setUsuario(new Usuario());
            tarefaAlterada.getUsuario().setId(tarefa.getUsuario().getId());
            tarefaAlterada.setCategoria(new Categoria());
            tarefaAlterada.getCategoria().setId(tarefa.getCategoria().getId());

            tarefaService.alterarTarefa(tarefaAlterada);
            System.out.println("Tarefa alterada com sucesso!");
        } else {
            System.out.println("Tarefa não encontrada!");
        }
    }

    private static void excluirTarefa(Scanner scanner, TarefaService tarefaService) {
        System.out.print("Digite o ID da tarefa a ser excluída: ");
        Long id = scanner.nextLong();

        tarefaService.excluirTarefa(id);
        System.out.println("Tarefa excluída com sucesso!");
    }

    private static void inserirCategoria(Scanner scanner, CategoriaService categoriaService) {
        System.out.print("Digite o nome da categoria: ");
        String nome = scanner.nextLine();

        categoriaService.cadastrarCategoria(nome);
        System.out.println("Categoria inserida com sucesso!");
    }

    private static void alterarCategoria(Scanner scanner, CategoriaService categoriaService) {
        System.out.print("Digite o ID da categoria a ser alterada: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Categoria categoria = categoriaService.buscarCategoriaPorId(id);;
        if (categoria != null) {
            System.out.print("Digite o novo nome da categoria: ");
            String nome = scanner.nextLine();

            categoria.setNome(nome);
            categoriaService.alterarCategoria(categoria);
            System.out.println("Categoria alterada com sucesso!");
        } else {
            System.out.println("Categoria não encontrada!");
        }
    }

    private static void consultarUsuario(Scanner scanner, UsuarioService usuarioService) {
        System.out.print("Digite o ID do usuário a ser consultado: ");
        Long id = scanner.nextLong();
        Usuario usuario = usuarioService.buscarUsuario(id);

        System.out.println(usuario != null ? usuario : "Usuário não encontrado!");
    }

    private static void gerarRelatorioResumoTarefas(TarefaService tarefaService) {
        List<ResumoTarefasVO> relatorio = tarefaService.gerarRelatorioResumoTarefas();
        System.out.println("\n===== Relatório Resumido de Tarefas =====");
        relatorio.forEach(System.out::println);
    }
    private static void excluirCategoria(Scanner scanner, CategoriaService categoriaService) {
        System.out.print("Digite o ID da categoria a ser excluída: ");
        Long id = scanner.nextLong();
        categoriaService.excluirCategoria(id);
        System.out.println("Categoria excluída com sucesso!");
    }
    private static void consultarTarefa(Scanner scanner, TarefaService tarefaService) {
        System.out.print("Digite o ID da tarefa a ser consultada: ");
        Long id = scanner.nextLong();
        Tarefa tarefa = tarefaService.buscarTarefa(id);
        if (tarefa != null) {
            System.out.println(tarefa);
        } else {
            System.out.println("Tarefa não encontrada!");
        }
    }
    // Consultar Categoria
    private static void consultarCategoria(Scanner scanner, CategoriaService categoriaService) {
        System.out.print("Digite o ID da categoria a ser consultada: ");
        Long id = scanner.nextLong();
        Categoria categoria = categoriaService.buscarCategoriaPorId(id);
        if (categoria != null) {
            System.out.println(categoria);
        } else {
            System.out.println("Categoria não encontrada!");
        }
    }
}
