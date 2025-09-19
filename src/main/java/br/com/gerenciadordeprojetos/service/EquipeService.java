package br.com.gerenciadordeprojetos.service;

import br.com.gerenciadordeprojetos.domain.Equipe;
import br.com.gerenciadordeprojetos.domain.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EquipeService {
    private final List<Equipe> equipes = Collections.synchronizedList(new ArrayList<>());
    private final String arquivo = "dados/equipes.txt";

    public EquipeService() {
        carregarEquipes();
    }

    public void cadastrarEquipe(Equipe equipe) {
        equipes.add(equipe);
        salvarEquipes();
    }

    public List<Equipe> listarEquipes() {
        return new ArrayList<>(equipes); // cópia defensiva
    }

    public Equipe buscarPorNome(String nome) {
        if (nome == null) return null;
        for (Equipe e : equipes) {
            if (e.getNome() != null && nome.equalsIgnoreCase(e.getNome())) {
                return e;
            }
        }
        return null;
    }

    public void removerEquipe(String nome) {
        if (nome == null) return;
        equipes.removeIf(e -> e.getNome() != null && nome.equalsIgnoreCase(e.getNome()));
        salvarEquipes();
    }

    public boolean editarEquipe(String nome, Equipe novaEquipe) {
        Equipe equipe = buscarPorNome(nome);
        if (equipe != null) {
            equipe.setDescricao(novaEquipe.getDescricao());
            equipe.setMembros(novaEquipe.getMembros());
            salvarEquipes();
            return true;
        }
        return false;
    }

    private void salvarEquipes() {
        try {
            new File("dados").mkdirs(); // garante diretório
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
                for (Equipe e : equipes) {
                    StringBuilder membrosStr = new StringBuilder();
                    if (e.getMembros() != null && !e.getMembros().isEmpty()) {
                        for (Usuario u : e.getMembros()) {
                            membrosStr.append(u.getLogin()).append(",");
                        }
                        if (membrosStr.length() > 0) {
                            membrosStr.setLength(membrosStr.length() - 1); // remove última vírgula
                        }
                    }
                    bw.write(e.getNome() + ";" + e.getDescricao() + ";" + membrosStr);
                    bw.newLine();
                }
            }
        } catch (IOException ex) {
            System.err.println("Erro ao salvar equipes: " + ex.getMessage());
        }
    }

    private void carregarEquipes() {
        equipes.clear();
        File file = new File(arquivo);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";", -1); // mantém campos vazios
                if (dados.length >= 3) {
                    Equipe e = new Equipe();
                    e.setNome(dados[0]);
                    e.setDescricao(dados[1]);
                    List<Usuario> membros = new ArrayList<>();
                    if (!dados[2].isEmpty()) {
                        String[] logins = dados[2].split(",");
                        for (String login : logins) {
                            Usuario u = new Usuario();
                            u.setLogin(login);
                            membros.add(u);
                        }
                    }
                    e.setMembros(membros);
                    equipes.add(e);
                }
            }
        } catch (IOException ex) {
            System.err.println("Erro ao carregar equipes: " + ex.getMessage());
        }
    }
}
