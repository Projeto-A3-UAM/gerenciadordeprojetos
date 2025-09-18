package br.com.gerenciadordeprojetos.domain;

import java.util.List;

public class Equipe {
    private String nome;
    private String descricao;
    private List<Usuario> membros;

    // Métodos de acesso (getters e setters)
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public List<Usuario> getMembros() { return membros; }
    public void setMembros(List<Usuario> membros) { this.membros = membros; }
}
