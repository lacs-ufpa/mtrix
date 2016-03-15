/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.psi.comportamente.labgame.mbeans.interfaces;

import br.ufpa.psi.comportamente.labgame.entidades.Jogador;
import br.ufpa.psi.comportamente.labgame.entidades.Pesquisador;

/**
 *
 * @author adailton
 */
public interface ExperimentoObservable {

    public void registraObservador(TableObserver observer, Jogador jogador);

    public void registraObservador(TableObserver observer, Pesquisador pesquisador);

    public void removeRegistroObservador(Jogador jogador);

    public void removeRegistroObservador(Pesquisador pesquisador);

    public void registrarJogada(Jogador jogador, int linha, String coluna);

    public int getTipoMatriz();

}
