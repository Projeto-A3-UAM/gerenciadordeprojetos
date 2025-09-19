package br.com.gerenciadordeprojetos.service;

import br.com.gerenciadordeprojetos.domain.Projeto;
import br.com.gerenciadordeprojetos.domain.Equipe;
import br.com.gerenciadordeprojetos.domain.Usuario;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjetoService {
    private final List<Projeto> projetos = Collections.synchronizedList(new ArrayList<>());
    private final String arquivo = "dados/projetos.txt";

    public ProjetoService() {
        carregarProjetos();
    }

    public void cadastrarProjeto(Projeto projeto) {
        projetos.add(projeto);
        salvarProjetos();
    }

    public List<Projeto> listarProjetos() {
        return new ArrayList<>(projetos); // cópia defensiva
    }

    public Projeto buscarPorNome(String nome) {
        if (nome == null) return null;
        for (Projeto p : projetos) {
            if (p.getNome() != null && nome.equalsIgnoreCase(p.getNome())) {
                return p;
            }
        }
        return null;
    }

    public void removerProjeto(String nome) {
        if (nome == null) return;
        projetos.removeIf(p -> p.getNome() != null && nome.equalsIgnoreCase(p.getNome()));
        salvarProjetos();
    }

    public boolean vincularEquipe(String nomeProjeto, Equipe equipe) {
        Projeto projeto = buscarPorNome(nomeProjeto);
        if (projeto != null && equipe != null) {
            projeto.adicionarEquipe(equipe);
            salvarProjetos();
            return true;
        }
        return false;
    }

    public boolean editarProjeto(String nome, Projeto novoProjeto) {
        Projeto projeto = buscarPorNome(nome);
        if (projeto != null) {
            projeto.setDescricao(novoProjeto.getDescricao());
            projeto.setDataInicio(novoProjeto.getDataInicio());
            projeto.setDataTerminoPrevista(novoProjeto.getDataTerminoPrevista());
            projeto.setStatus(novoProjeto.getStatus());
            projeto.setGerente(novoProjeto.getGerente());
            salvarProjetos();
            return true;
        }
        return false;
    }

    private void salvarProjetos() {
        try {
            new File("dados").mkdirs(); // garante diretório
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
                for (Projeto p : projetos) {
                    String gerenteLogin = (p.getGerente() != null) ? p.getGerente().getLogin() : "";
                    String status = (p.getStatus() != null) ? p.getStatus().name() : "";

                    StringBuilder equipesStr = new StringBuilder();
                    if (p.getEquipes() != null && !p.getEquipes().isEmpty()) {
                        for (Equipe eq : p.getEquipes()) {
                            if (eq.getNome() != null) {
                                equipesStr.append(eq.getNome()).append(",");
                            }
                        }
                        if (equipesStr.length() > 0) {
                            equipesStr.setLength(equipesStr.length() - 1); // remove última vírgula
                        }
                    }

                    bw.write(
                        p.getNome() + ";" +
                        p.getDescricao() + ";" +
                        (p.getDataInicio() != null ? p.getDataInicio() : "") + ";" +
                        (p.getDataTerminoPrevista() != null ? p.getDataTerminoPrevista() : "") + ";" +
                        status + ";" +
                        gerenteLogin + ";" +
                        equipesStr
                    );
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar projetos: " + e.getMessage());
        }
    }

    private void carregarProjetos() {
        projetos.clear();
        File file = new File(arquivo);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";", -1); // mantém campos vazios
                if (dados.length >= 7) {
                    Projeto p = new Projeto();
                    p.setNome(dados[0]);
                    p.setDescricao(dados[1]);

                    // Datas com validação
                    if (!dados[2].isEmpty()) {
                        try { p.setDataInicio(LocalDate.parse(dados[2])); }
                        catch (DateTimeParseException ex) { System.err.println("Data inválida: " + dados[2]); }
                    }
                    if (!dados[3].isEmpty()) {
                        try { p.setDataTerminoPrevista(LocalDate.parse(dados[3])); }
                        catch (DateTimeParseException ex) { System.err.println("Data inválida: " + dados[3]); }
                    }

                    // Status com segurança
                    if (!dados[4].isEmpty()) {
                        try { p.setStatus(Projeto.Status.valueOf(dados[4])); }
                        catch (IllegalArgumentException ex) { System.err.println("Status inválido: " + dados[4]); }
                    }

                    // Gerente apenas login (usuário real deve ser vinculado depois)
                    if (!dados[5].isEmpty()) {
                        Usuario gerente = new Usuario();
                        gerente.setLogin(dados[5]);
                        p.setGerente(gerente);
                    }

                    // Equipes apenas com nome
                    if (!dados[6].isEmpty()) {
                        String[] eqNomes = dados[6].split(",");
                        for (String eqNome : eqNomes) {
                            Equipe eq = new Equipe();
                            eq.setNome(eqNome);
                            p.adicionarEquipe(eq);
                        }
                    }

                    projetos.add(p);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar projetos: " + e.getMessage());
        }
    }
}
