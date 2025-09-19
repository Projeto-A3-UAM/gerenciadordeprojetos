package br.com.gerenciadordeprojetos.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Equipe {
    private String nome;
    private String descricao;
    private final List<Usuario> membros = new ArrayList<>();

    // Construtores
    public Equipe() {}

    public Equipe(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    // Getters e Setters
    public String getNome() { 
        return nome; 
    }

    public void setNome(String nome) { 
        this.nome = nome; 
    }

    public String getDescricao() { 
        return descricao; 
    }

    public void setDescricao(String descricao) { 
        this.descricao = descricao; 
    }

    // Retorna lista imutável para evitar modificações externas indevidas
    public List<Usuario> getMembros() {
        return Collections.unmodifiableList(membros);
    }

    // Permite atualizar a lista de membros
    public void setMembros(List<Usuario> novosMembros) {
        membros.clear();
        if (novosMembros != null) {
            membros.addAll(novosMembros);
        }
    }

    // Métodos de manipulação de membros
    public void adicionarMembro(Usuario usuario) {
        if (usuario != null && !membros.contains(usuario)) {
            membros.add(usuario);
        }
    }

    public void removerMembro(Usuario usuario) {
        membros.remove(usuario);
    }

    public int getQuantidadeMembros() {
        return membros.size();
    }

    @Override
    public String toString() {
        return "Equipe{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", membros=" + membros.size() +
                '}';
    }
}
