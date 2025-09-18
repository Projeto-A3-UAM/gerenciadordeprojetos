package br.com.gerenciadordeprojetos.service;

import br.com.gerenciadordeprojetos.domain.Projeto;
import br.com.gerenciadordeprojetos.domain.Equipe;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class ProjetoService {
    private List<Projeto> projetos = new ArrayList<>();
    private final String arquivo = "dados/projetos.txt";

    public ProjetoService() {
        carregarProjetos();
    }

    public void cadastrarProjeto(Projeto projeto) {
        projetos.add(projeto);
        salvarProjetos();
    }

    public List<Projeto> listarProjetos() {
        return projetos;
    }

    public Projeto buscarPorNome(String nome) {
        for (Projeto p : projetos) {
            if (p.getNome().equals(nome)) {
                return p;
            }
        }
        return null;
    }

    public void removerProjeto(String nome) {
        projetos.removeIf(p -> p.getNome().equals(nome));
        salvarProjetos();
    }

    public boolean vincularEquipe(String nomeProjeto, Equipe equipe) {
        Projeto projeto = buscarPorNome(nomeProjeto);
        if (projeto != null) {
            projeto.adicionarEquipe(equipe);
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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            for (Projeto p : projetos) {
                String gerenteLogin = (p.getGerente() != null) ? p.getGerente().getLogin() : "";
                String status = (p.getStatus() != null) ? p.getStatus().name() : "";
                String equipesStr = "";
                if (p.getEquipes() != null && !p.getEquipes().isEmpty()) {
                    for (Equipe eq : p.getEquipes()) {
                        equipesStr += eq.getNome() + ",";
                    }
                    if (!equipesStr.isEmpty()) equipesStr = equipesStr.substring(0, equipesStr.length()-1);
                }
                bw.write(p.getNome() + ";" + p.getDescricao() + ";" + p.getDataInicio() + ";" + p.getDataTerminoPrevista() + ";" + status + ";" + gerenteLogin + ";" + equipesStr);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar projetos: " + e.getMessage());
        }
    }

    private void carregarProjetos() {
        File file = new File(arquivo);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length >= 7) {
                    Projeto p = new Projeto();
                    p.setNome(dados[0]);
                    p.setDescricao(dados[1]);
                    p.setDataInicio(java.time.LocalDate.parse(dados[2]));
                    p.setDataTerminoPrevista(java.time.LocalDate.parse(dados[3]));
                    p.setStatus(Projeto.Status.valueOf(dados[4]));
                    // Gerente será vinculado manualmente após carregar usuários
                    // Equipes
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
            System.out.println("Erro ao carregar projetos: " + e.getMessage());
        }
    }
}
