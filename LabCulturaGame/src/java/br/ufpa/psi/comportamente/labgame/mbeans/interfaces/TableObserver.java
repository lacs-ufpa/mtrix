/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpa.psi.comportamente.labgame.mbeans.interfaces;

import br.ufpa.psi.comportamente.labgame.entidades.Experimento;
import br.ufpa.psi.comportamente.labgame.entidades.Jogada;
import br.ufpa.psi.comportamente.labgame.entidades.Jogador;
import java.util.List;

/**
 *
 * @author adailton
 */
public interface TableObserver {
    
    
    public void bloqueiaTela(String tipoAlertaSonoroPontIndiv);
    
    public void bloqueiaTela();
    
    public void coletaEntradaJogada(boolean alertaPontuacao, Jogador jogador);
    
    public void coletaEntradaJogada(boolean alertaPontuacao, Jogador jogador, String mensagem);
    
    public void coletaEntradaJogada();
    
    public void atualizaPontuacao(List<Jogador> jogs, int p, int g, String alerta);
    
    public void atualizaPontuacao(List<Jogador> jogs, int p, int g);
    
    public void exibeMensagemPontuacaoCultural(String mensagem);
    
    public void atualizaPontuacaoCulturalInicial(int p);
    
    public void exibeUltimaJogada(Jogador jogador, String cor, Jogada jogada, Integer pontuacao);
    
    public void exibeUltimaJogada(Jogador jogador, String cor, Jogada jogada);
    
    public void finalizarJogadas();
    
    public void informaSaidaDoExperimento();
    
    public void atualizaExperimentoEmExecucao(Experimento experimento);
    
    public void emiteAlertaMucancaGeracao();
    
    public void aguardandoProximoJogador(Jogador jogador);
    
}
