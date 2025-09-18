package br.com.gerenciadordeprojetos.service;

import br.com.gerenciadordeprojetos.domain.Equipe;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class EquipeService {
    private List<Equipe> equipes = new ArrayList<>();
    private final String arquivo = "dados/equipes.txt";

    public EquipeService() {
        carregarEquipes();
    }

    public void cadastrarEquipe(Equipe equipe) {
        equipes.add(equipe);
        salvarEquipes();
    }

    public List<Equipe> listarEquipes() {
        return equipes;
    }

    public Equipe buscarPorNome(String nome) {
        for (Equipe e : equipes) {
            if (e.getNome().equals(nome)) {
                return e;
            }
        }
        return null;
    }

    public void removerEquipe(String nome) {
        equipes.removeIf(e -> e.getNome().equals(nome));
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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            for (Equipe e : equipes) {
                String membrosStr = "";
                if (e.getMembros() != null && !e.getMembros().isEmpty()) {
                    for (br.com.gerenciadordeprojetos.domain.Usuario u : e.getMembros()) {
                        membrosStr += u.getLogin() + ",";
                    }
                    if (!membrosStr.isEmpty()) membrosStr = membrosStr.substring(0, membrosStr.length()-1);
                }
                bw.write(e.getNome() + ";" + e.getDescricao() + ";" + membrosStr);
                bw.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Erro ao salvar equipes: " + ex.getMessage());
        }
    }

    private void carregarEquipes() {
        File file = new File(arquivo);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length >= 3) {
                    Equipe e = new Equipe();
                    e.setNome(dados[0]);
                    e.setDescricao(dados[1]);
                    List<br.com.gerenciadordeprojetos.domain.Usuario> membros = new ArrayList<>();
                    if (!dados[2].isEmpty()) {
                        String[] logins = dados[2].split(",");
                        for (String login : logins) {
                            br.com.gerenciadordeprojetos.domain.Usuario u = new br.com.gerenciadordeprojetos.domain.Usuario();
                            u.setLogin(login);
                            membros.add(u);
                        }
                    }
                    e.setMembros(membros);
                    equipes.add(e);
                }
            }
        } catch (IOException ex) {
            System.out.println("Erro ao carregar equipes: " + ex.getMessage());
        }
    }
}
