package br.com.gerenciadordeprojetos.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Projeto {
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataTerminoPrevista;
    private Status status;
    private Usuario gerente;
    private final List<Equipe> equipes = new ArrayList<>();

    public enum Status {
        PLANEJADO,
        EM_ANDAMENTO,
        CONCLUIDO,
        CANCELADO
    }

    // Construtores
    public Projeto() {}

    public Projeto(String nome, String descricao, LocalDate dataInicio, LocalDate dataTerminoPrevista, Usuario gerente) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataTerminoPrevista = dataTerminoPrevista;
        this.status = Status.PLANEJADO; // padrão inicial
        this.gerente = gerente;
    }

    // Getters e Setters
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

    // Equipes - protegido contra manipulação externa
    public List<Equipe> getEquipes() {
        return Collections.unmodifiableList(equipes);
    }

    public void adicionarEquipe(Equipe equipe) {
        if (equipe != null && !equipes.contains(equipe)) {
            equipes.add(equipe);
        }
    }

    public void removerEquipe(Equipe equipe) {
        equipes.remove(equipe);
    }

    public int getQuantidadeEquipes() {
        return equipes.size();
    }

    @Override
    public String toString() {
        return "Projeto{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataInicio=" + dataInicio +
                ", dataTerminoPrevista=" + dataTerminoPrevista +
                ", status=" + status +
                ", gerente=" + (gerente != null ? gerente.getLogin() : "N/A") +
                ", equipes=" + equipes.size() +
                '}';
    }
}
