package br.com.gerenciadordeprojetos.domain;

import java.time.LocalDate;

public class Projeto {
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataTerminoPrevista;
    private Status status;
    private Usuario gerente;
    private java.util.List<Equipe> equipes = new java.util.ArrayList<>();

    public enum Status {
        PLANEJADO,
        EM_ANDAMENTO,
        CONCLUIDO,
        CANCELADO
    }

    // Métodos de acesso (getters e setters)
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public LocalDate getDataTerminoPrevista() { return dataTerminoPrevista; }
    public void setDataTerminoPrevista(LocalDate dataTerminoPrevista) { this.dataTerminoPrevista = dataTerminoPrevista; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public Usuario getGerente() { return gerente; }
    public void setGerente(Usuario gerente) { this.gerente = gerente; }
    public java.util.List<Equipe> getEquipes() { return equipes; }
    public void setEquipes(java.util.List<Equipe> equipes) { this.equipes = equipes; }
    public void adicionarEquipe(Equipe equipe) { this.equipes.add(equipe); }
}
