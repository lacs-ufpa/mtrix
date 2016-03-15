/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.psi.comportamente.labgame.entidades;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Weslley
 */
@Entity
@Table(name = "jogada")
public class Jogada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int linhaSelecionada;
    private String colunaSelecionada;
    private String corSelecionada;

    private Integer pontuacaoIndividual;
    private int pontuacaoCultural;
    private int rodada;

    private Long idCondicao;

    @Transient
    private String nomeJogador;

    @Temporal(TemporalType.TIMESTAMP)
    private Date momento;

    @ManyToOne
    @JoinColumn(name = "experimento_id", nullable = false)
    private Experimento experimento;

    @ManyToOne
    @JoinColumn(name = "jogador_id", nullable = false)
    private Jogador jogador;

    public Jogada() {
        momento = new Date();
        pontuacaoCultural = 0;
        pontuacaoIndividual = 0;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Jogada other = (Jogada) obj;
        return Objects.equals(this.id, other.id);
    }

    public int getLinhaSelecionada() {
        return linhaSelecionada;
    }

    public void setLinhaSelecionada(int linhaSelecionada) {
        this.linhaSelecionada = linhaSelecionada;
    }

    public String getColunaSelecionada() {
        return colunaSelecionada;
    }

    public void setColunaSelecionada(String colunaSelecionada) {
        this.colunaSelecionada = colunaSelecionada;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public String getDataFormatada() throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date data = fmt.parse(momento.toString());
        String str = fmt.format(data);
        return str;
    }

    public void setMomento(Date momento) {
        this.momento = momento;
    }

    public Experimento getExperimento() {
        return experimento;
    }

    public void setExperimento(Experimento experimento) {
        this.experimento = experimento;
    }

    public Integer getPontuacaoIndividual() {
        return pontuacaoIndividual;
    }

    public void setPontuacaoIndividual(Integer pontuacaoIndividual) {
        this.pontuacaoIndividual = pontuacaoIndividual;
    }

    public int getPontuacaoCultural() {
        return pontuacaoCultural;
    }

    public void setPontuacaoCultural(int pontuacaoCultural) {
        this.pontuacaoCultural = pontuacaoCultural;
    }

    public int getRodada() {
        return rodada;
    }

    public void setRodada(int rodada) {
        this.rodada = rodada;
    }

    public Long getIdCondicao() {
        return idCondicao;
    }

    public void setIdCondicao(Long idCondicao) {
        this.idCondicao = idCondicao;
    }

    public String getCorSelecionada() {
        return corSelecionada;
    }

    public void setCorSelecionada(String corSelecionada) {
        this.corSelecionada = corSelecionada;
    }

    public String getNomeJogador() {
        return nomeJogador;
    }

    public void setNomeJogador(String nomeJogador) {
        this.nomeJogador = nomeJogador;
    }

    public Date getMomento() {
        return momento;
    }

}
