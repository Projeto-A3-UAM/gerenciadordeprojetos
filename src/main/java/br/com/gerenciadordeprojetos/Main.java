package br.com.gerenciadordeprojetos;

import br.com.gerenciadordeprojetos.domain.*;
import br.com.gerenciadordeprojetos.service.*;
import java.util.Scanner;

public class Main {
    private static UsuarioService usuarioService = new UsuarioService();
    private static ProjetoService projetoService = new ProjetoService();
    private static EquipeService equipeService = new EquipeService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        sincronizarEntidades();
        int opcao;
        do {
            System.out.println("\n===== Sistema de Gestão de Projetos =====");
            System.out.println("1. Cadastrar Usuário");
            System.out.println("2. Listar Usuários");
            System.out.println("3. Cadastrar Projeto");
            System.out.println("4. Listar Projetos");
            System.out.println("5. Cadastrar Equipe");
            System.out.println("6. Listar Equipes");
            System.out.println("7. Vincular Equipe a Projeto");
            System.out.println("8. Editar Usuário");
            System.out.println("9. Editar Projeto");
            System.out.println("10. Editar Equipe");
            System.out.println("11. Excluir Usuário");
            System.out.println("12. Excluir Projeto");
            System.out.println("13. Excluir Equipe");
            System.out.println("14. Relatório de Projetos");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

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
                    vincularEquipeProjeto();
                    break;
                case 8:
                    editarUsuario();
                    break;
                case 9:
                    editarProjeto();
                    break;
                case 10:
                    editarEquipe();
                    break;
                case 11:
                    excluirUsuario();
                    break;
                case 12:
                    excluirProjeto();
                    break;
                case 13:
                    excluirEquipe();
                    break;
                case 14:
                    relatorioProjetos();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    // Sincroniza vínculos após carregar dados dos arquivos
    private static void sincronizarEntidades() {
        // Sincronizar membros das equipes
        for (Equipe equipe : equipeService.listarEquipes()) {
            if (equipe.getMembros() != null && !equipe.getMembros().isEmpty()) {
                java.util.List<Usuario> membrosSincronizados = new java.util.ArrayList<>();
                for (Usuario membro : equipe.getMembros()) {
                    Usuario completo = usuarioService.buscarPorLogin(membro.getLogin());
                    if (completo != null) membrosSincronizados.add(completo);
                }
                equipe.setMembros(membrosSincronizados);
            }
        }
        // Sincronizar equipes dos projetos
        for (Projeto projeto : projetoService.listarProjetos()) {
            if (projeto.getEquipes() != null && !projeto.getEquipes().isEmpty()) {
                java.util.List<Equipe> equipesSincronizadas = new java.util.ArrayList<>();
                for (Equipe eq : projeto.getEquipes()) {
                    Equipe completa = equipeService.buscarPorNome(eq.getNome());
                    if (completa != null) equipesSincronizadas.add(completa);
                }
                projeto.setEquipes(equipesSincronizadas);
            }
            // Sincronizar gerente do projeto
            if (projeto.getGerente() != null && projeto.getGerente().getLogin() != null) {
                Usuario gerenteCompleto = usuarioService.buscarPorLogin(projeto.getGerente().getLogin());
                if (gerenteCompleto != null) projeto.setGerente(gerenteCompleto);
            }
        }
    }

    private static void relatorioProjetos() {
        System.out.println("\n--- Relatório de Projetos ---");
        for (Projeto p : projetoService.listarProjetos()) {
            System.out.println("Projeto: " + p.getNome());
            System.out.println("  Descrição: " + p.getDescricao());
            System.out.println("  Status: " + p.getStatus());
            System.out.println("  Gerente: " + (p.getGerente() != null ? p.getGerente().getNomeCompleto() : "N/A"));
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
        System.out.println("[DEBUG] Iniciando cadastro de usuário...");
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
        System.out.print("Perfil (1-ADMINISTRADOR, 2-GERENTE, 3-COLABORADOR): ");
        int perfilOpcao = Integer.parseInt(scanner.nextLine());
        Usuario.Perfil perfil;
        switch (perfilOpcao) {
            case 1:
                perfil = Usuario.Perfil.ADMINISTRADOR;
                break;
            case 2:
                perfil = Usuario.Perfil.GERENTE;
                break;
            case 3:
                perfil = Usuario.Perfil.COLABORADOR;
                break;
            default:
                perfil = Usuario.Perfil.COLABORADOR;
                break;
        }
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(nome);
        usuario.setCpf(cpf);
        usuario.setEmail(email);
        usuario.setCargo(cargo);
        usuario.setLogin(login);
        usuario.setSenha(senha);
        usuario.setPerfil(perfil);
        System.out.println("[DEBUG] Chamando usuarioService.cadastrarUsuario...");
        usuarioService.cadastrarUsuario(usuario);
        System.out.println("[DEBUG] Cadastro de usuário finalizado!");
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
        String dataInicio = scanner.nextLine();
        System.out.print("Data de término prevista (AAAA-MM-DD): ");
        String dataTermino = scanner.nextLine();
        System.out.print("Status (1-PLANEJADO, 2-EM_ANDAMENTO, 3-CONCLUIDO, 4-CANCELADO): ");
        int statusOpcao = Integer.parseInt(scanner.nextLine());
        Projeto.Status status;
        switch (statusOpcao) {
            case 1:
                status = Projeto.Status.PLANEJADO;
                break;
            case 2:
                status = Projeto.Status.EM_ANDAMENTO;
                break;
            case 3:
                status = Projeto.Status.CONCLUIDO;
                break;
            case 4:
                status = Projeto.Status.CANCELADO;
                break;
            default:
                status = Projeto.Status.PLANEJADO;
                break;
        }
        System.out.print("Login do gerente responsável: ");
        String loginGerente = scanner.nextLine();
        Usuario gerente = usuarioService.buscarPorLogin(loginGerente);
        if (gerente == null || gerente.getPerfil() != Usuario.Perfil.GERENTE) {
            System.out.println("Gerente não encontrado ou perfil inválido!");
            return;
        }
        Projeto projeto = new Projeto();
        projeto.setNome(nome);
        projeto.setDescricao(descricao);
        projeto.setDataInicio(java.time.LocalDate.parse(dataInicio));
        projeto.setDataTerminoPrevista(java.time.LocalDate.parse(dataTermino));
        projeto.setStatus(status);
        projeto.setGerente(gerente);
        projetoService.cadastrarProjeto(projeto);
        System.out.println("Projeto cadastrado com sucesso!");
    }

    private static void listarProjetos() {
        System.out.println("\n--- Projetos ---");
        for (Projeto p : projetoService.listarProjetos()) {
            System.out.println("Nome: " + p.getNome() + ", Gerente: " + (p.getGerente() != null ? p.getGerente().getNomeCompleto() : "N/A") + ", Status: " + p.getStatus());
        }
    }

    private static void cadastrarEquipe() {
        System.out.print("Nome da equipe: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        Equipe equipe = new Equipe();
        equipe.setNome(nome);
        equipe.setDescricao(descricao);
        equipe.setMembros(new java.util.ArrayList<>());
        equipeService.cadastrarEquipe(equipe);
        System.out.println("Equipe cadastrada com sucesso!");
    }

    private static void listarEquipes() {
        System.out.println("\n--- Equipes ---");
        for (Equipe e : equipeService.listarEquipes()) {
            System.out.println("Nome: " + e.getNome() + ", Membros: " + (e.getMembros() != null ? e.getMembros().size() : 0));
        }
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
            if (projetoService.vincularEquipe(nomeProjeto, equipe)) {
                System.out.println("Equipe vinculada ao projeto com sucesso!");
            } else {
                System.out.println("Falha ao vincular equipe ao projeto.");
            }
        }

        private static void editarUsuario() {
            System.out.print("Login do usuário a editar: ");
            String login = scanner.nextLine();
            Usuario usuarioExistente = usuarioService.buscarPorLogin(login);
            if (usuarioExistente == null) {
                System.out.println("Usuário não encontrado!");
                return;
            }
            System.out.print("Novo nome completo: ");
            String nome = scanner.nextLine();
            System.out.print("Novo CPF: ");
            String cpf = scanner.nextLine();
            System.out.print("Novo e-mail: ");
            String email = scanner.nextLine();
            System.out.print("Novo cargo: ");
            String cargo = scanner.nextLine();
            System.out.print("Nova senha: ");
            String senha = scanner.nextLine();
            System.out.print("Novo perfil (1-ADMINISTRADOR, 2-GERENTE, 3-COLABORADOR): ");
            int perfilOpcao = Integer.parseInt(scanner.nextLine());
            Usuario.Perfil perfil;
            switch (perfilOpcao) {
                case 1:
                    perfil = Usuario.Perfil.ADMINISTRADOR;
                    break;
                case 2:
                    perfil = Usuario.Perfil.GERENTE;
                    break;
                case 3:
                    perfil = Usuario.Perfil.COLABORADOR;
                    break;
                default:
                    perfil = Usuario.Perfil.COLABORADOR;
                    break;
            }
            Usuario novoUsuario = new Usuario();
            novoUsuario.setNomeCompleto(nome);
            novoUsuario.setCpf(cpf);
            novoUsuario.setEmail(email);
            novoUsuario.setCargo(cargo);
            novoUsuario.setLogin(login);
            novoUsuario.setSenha(senha);
            novoUsuario.setPerfil(perfil);
            if (usuarioService.editarUsuario(login, novoUsuario)) {
                System.out.println("Usuário editado com sucesso!");
            } else {
                System.out.println("Falha ao editar usuário.");
            }
        }

        private static void editarProjeto() {
            System.out.print("Nome do projeto a editar: ");
            String nome = scanner.nextLine();
            Projeto projetoExistente = projetoService.buscarPorNome(nome);
            if (projetoExistente == null) {
                System.out.println("Projeto não encontrado!");
                return;
            }
            System.out.print("Nova descrição: ");
            String descricao = scanner.nextLine();
            System.out.print("Nova data de início (AAAA-MM-DD): ");
            String dataInicio = scanner.nextLine();
            System.out.print("Nova data de término prevista (AAAA-MM-DD): ");
            String dataTermino = scanner.nextLine();
            System.out.print("Novo status (1-PLANEJADO, 2-EM_ANDAMENTO, 3-CONCLUIDO, 4-CANCELADO): ");
            int statusOpcao = Integer.parseInt(scanner.nextLine());
            Projeto.Status status;
            switch (statusOpcao) {
                case 1:
                    status = Projeto.Status.PLANEJADO;
                    break;
                case 2:
                    status = Projeto.Status.EM_ANDAMENTO;
                    break;
                case 3:
                    status = Projeto.Status.CONCLUIDO;
                    break;
                case 4:
                    status = Projeto.Status.CANCELADO;
                    break;
                default:
                    status = Projeto.Status.PLANEJADO;
                    break;
            }
            System.out.print("Login do novo gerente responsável: ");
            String loginGerente = scanner.nextLine();
            Usuario gerente = usuarioService.buscarPorLogin(loginGerente);
            if (gerente == null || gerente.getPerfil() != Usuario.Perfil.GERENTE) {
                System.out.println("Gerente não encontrado ou perfil inválido!");
                return;
            }
            Projeto novoProjeto = new Projeto();
            novoProjeto.setDescricao(descricao);
            novoProjeto.setDataInicio(java.time.LocalDate.parse(dataInicio));
            novoProjeto.setDataTerminoPrevista(java.time.LocalDate.parse(dataTermino));
            novoProjeto.setStatus(status);
            novoProjeto.setGerente(gerente);
            if (projetoService.editarProjeto(nome, novoProjeto)) {
                System.out.println("Projeto editado com sucesso!");
            } else {
                System.out.println("Falha ao editar projeto.");
            }
        }

        private static void editarEquipe() {
            System.out.print("Nome da equipe a editar: ");
            String nome = scanner.nextLine();
            Equipe equipeExistente = equipeService.buscarPorNome(nome);
            if (equipeExistente == null) {
                System.out.println("Equipe não encontrada!");
                return;
            }
            System.out.print("Nova descrição: ");
            String descricao = scanner.nextLine();
            Equipe novaEquipe = new Equipe();
            novaEquipe.setDescricao(descricao);
            novaEquipe.setMembros(equipeExistente.getMembros());
            if (equipeService.editarEquipe(nome, novaEquipe)) {
                System.out.println("Equipe editada com sucesso!");
            } else {
                System.out.println("Falha ao editar equipe.");
            }
        }

        private static void excluirUsuario() {
            System.out.print("Login do usuário a excluir: ");
            String login = scanner.nextLine();
            usuarioService.removerUsuario(login);
            System.out.println("Usuário excluído (se existia).");
        }

        private static void excluirProjeto() {
            System.out.print("Nome do projeto a excluir: ");
            String nome = scanner.nextLine();
            projetoService.removerProjeto(nome);
            System.out.println("Projeto excluído (se existia).");
        }

        private static void excluirEquipe() {
            System.out.print("Nome da equipe a excluir: ");
            String nome = scanner.nextLine();
            equipeService.removerEquipe(nome);
            System.out.println("Equipe excluída (se existia).");
        }
}
