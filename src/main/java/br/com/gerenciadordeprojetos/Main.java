package br.com.gerenciadordeprojetos;
import br.com.gerenciadordeprojetos.domain.Usuario;
import br.com.gerenciadordeprojetos.domain.Equipe;
import br.com.gerenciadordeprojetos.domain.Projeto;
import br.com.gerenciadordeprojetos.service.UsuarioService;
import br.com.gerenciadordeprojetos.service.EquipeService;
import br.com.gerenciadordeprojetos.service.ProjetoService;
import java.util.Scanner;

public class Main {
    private static UsuarioService usuarioService = new UsuarioService();
    private static ProjetoService projetoService = new ProjetoService();
    private static EquipeService equipeService = new EquipeService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n===== Sistema de Gestão de Projetos =====");
            System.out.println("1. Cadastrar Usuário");
            System.out.println("2. Listar Usuários");
            System.out.println("3. Cadastrar Projeto");
            System.out.println("4. Listar Projetos");
            System.out.println("5. Cadastrar Equipe");
            System.out.println("6. Listar Equipes");
            System.out.println("7. Vincular usuário à equipe");
            System.out.println("8. Vincular equipe a projeto");
            System.out.println("9. Relatório de projetos");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            while (!scanner.hasNextInt()) {
                System.out.println("⚠ Entrada inválida! Digite um número.");
                scanner.next();
                System.out.print("Escolha uma opção: ");
            }
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer após nextInt()
            switch (opcao) {
                case 1:
                    cadastrarUsuario();
                    break;
                case 2:
                    listarUsuarios();
                    break;
                case 3:
                    cadastrarProjeto();
                    break;
                case 4:
                    listarProjetos();
                    break;
                case 5:
                    cadastrarEquipe();
                    break;
                case 6:
                    listarEquipes();
                    break;
                case 7:
                    vincularUsuarioEquipe();
                    break;
                case 8:
                    vincularEquipeProjeto();
                    break;
                case 9:
                    relatorioProjetos();
                    break;
                case 0:
                    System.out.println("Saindo do programa...");
                    break;
                default:
                    System.out.println("⚠ Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);
        scanner.close();
    }

    private static void vincularUsuarioEquipe() {
        System.out.print("Login do usuário: ");
        String login = scanner.nextLine();
        Usuario usuario = usuarioService.buscarPorLogin(login);
        if (usuario == null) {
            System.out.println("Usuário não encontrado!");
            return;
        }
        System.out.print("Nome da equipe: ");
        String nomeEquipe = scanner.nextLine();
        Equipe equipe = equipeService.buscarPorNome(nomeEquipe);
        if (equipe == null) {
            System.out.println("Equipe não encontrada!");
            return;
        }
        equipe.adicionarMembro(usuario);
        equipeService.editarEquipe(nomeEquipe, equipe);
        System.out.println("Usuário vinculado à equipe com sucesso!");
    }

    private static void vincularEquipeProjeto() {
        System.out.print("Nome do projeto: ");
        String nomeProjeto = scanner.nextLine();
        Projeto projeto = projetoService.buscarPorNome(nomeProjeto);
        if (projeto == null) {
            System.out.println("Projeto não encontrado!");
            return;
        }
        System.out.print("Nome da equipe: ");
        String nomeEquipe = scanner.nextLine();
        Equipe equipe = equipeService.buscarPorNome(nomeEquipe);
        if (equipe == null) {
            System.out.println("Equipe não encontrada!");
            return;
        }
        boolean vinculado = projetoService.vincularEquipe(nomeProjeto, equipe);
        if (vinculado) {
            System.out.println("Equipe vinculada ao projeto com sucesso!");
        } else {
            System.out.println("Falha ao vincular equipe ao projeto.");
        }
    }

    private static void relatorioProjetos() {
        System.out.println("\n--- Relatório de Projetos ---");
        for (Projeto p : projetoService.listarProjetos()) {
            System.out.println("Projeto: " + p.getNome());
            System.out.println("  Descrição: " + p.getDescricao());
            System.out.println("  Status: " + p.getStatus());
            System.out.println("  Gerente: " + (p.getGerente() != null ? p.getGerente().getLogin() : "N/A"));
            System.out.print("  Equipes vinculadas: ");
            if (p.getEquipes() != null && !p.getEquipes().isEmpty()) {
                for (Equipe e : p.getEquipes()) {
                    System.out.print(e.getNome() + " ");
                }
                System.out.println();
            } else {
                System.out.println("Nenhuma equipe vinculada.");
            }
            System.out.println("-----------------------------");
        }
    }

    private static void cadastrarUsuario() {
        System.out.print("Nome completo: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Cargo: ");
        String cargo = scanner.nextLine();
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("Perfil (ADMINISTRADOR, GERENTE, COLABORADOR): ");
        String perfilStr = scanner.nextLine();
        Usuario.Perfil perfil;
        try {
            perfil = Usuario.Perfil.valueOf(perfilStr.toUpperCase());
        } catch (Exception e) {
            perfil = Usuario.Perfil.COLABORADOR;
        }
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(nome);
        usuario.setCpf(cpf);
        usuario.setEmail(email);
        usuario.setCargo(cargo);
        usuario.setLogin(login);
        usuario.setSenha(senha);
        usuario.setPerfil(perfil);
        usuarioService.cadastrarUsuario(usuario);
        System.out.println("Usuário cadastrado com sucesso!");
    }

    private static void listarUsuarios() {
        System.out.println("\n--- Usuários ---");
        for (Usuario u : usuarioService.listarUsuarios()) {
            System.out.println("Nome: " + u.getNomeCompleto() + ", Login: " + u.getLogin() + ", Perfil: " + u.getPerfil());
        }
    }

    private static void cadastrarProjeto() {
        System.out.print("Nome do projeto: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Data de início (AAAA-MM-DD): ");
        String dataInicioStr = scanner.nextLine();
        System.out.print("Data de término prevista (AAAA-MM-DD): ");
        String dataTerminoStr = scanner.nextLine();
        System.out.print("Status (PLANEJADO, EM_ANDAMENTO, CONCLUIDO, CANCELADO): ");
        String statusStr = scanner.nextLine();
        System.out.print("Login do gerente do projeto: ");
        String gerenteLogin = scanner.nextLine();
        Projeto.Status status;
        java.time.LocalDate dataInicio = null;
        java.time.LocalDate dataTermino = null;
        try {
            if (!dataInicioStr.isEmpty()) dataInicio = java.time.LocalDate.parse(dataInicioStr);
        } catch (Exception e) {
            System.out.println("Data de início inválida! Deixando em branco.");
        }
        try {
            if (!dataTerminoStr.isEmpty()) dataTermino = java.time.LocalDate.parse(dataTerminoStr);
        } catch (Exception e) {
            System.out.println("Data de término inválida! Deixando em branco.");
        }
        try {
            status = Projeto.Status.valueOf(statusStr.toUpperCase());
        } catch (Exception e) {
            System.out.println("Status inválido! Usando PLANEJADO como padrão.");
            status = Projeto.Status.PLANEJADO;
        }
        Usuario gerente = usuarioService.buscarPorLogin(gerenteLogin);
        if (gerente == null) {
            System.out.println("Gerente não encontrado! O projeto será cadastrado sem gerente.");
        }
        Projeto projeto = new Projeto();
        projeto.setNome(nome);
        projeto.setDescricao(descricao);
        projeto.setDataInicio(dataInicio);
        projeto.setDataTerminoPrevista(dataTermino);
        projeto.setStatus(status);
        projeto.setGerente(gerente);
        projetoService.cadastrarProjeto(projeto);
        System.out.println("Projeto cadastrado com sucesso!");
    }

    private static void listarProjetos() {
        System.out.println("\n--- Projetos ---");
        for (Projeto p : projetoService.listarProjetos()) {
            System.out.println("Nome: " + p.getNome() + ", Descrição: " + p.getDescricao() + ", Status: " + (p.getStatus() != null ? p.getStatus() : "N/A"));
        }
    }

    private static void cadastrarEquipe() {
    System.out.print("Nome da equipe: ");
    String nome = scanner.nextLine();
    System.out.print("Descrição da equipe: ");
    String descricao = scanner.nextLine();
    Equipe equipe = new Equipe();
    equipe.setNome(nome);
    equipe.setDescricao(descricao);
    equipeService.cadastrarEquipe(equipe);
    System.out.println("Equipe cadastrada com sucesso!");
    }

    private static void listarEquipes() {
        System.out.println("\n--- Equipes ---");
        for (Equipe e : equipeService.listarEquipes()) {
            System.out.println("Nome: " + e.getNome());
        }
    }
}
