/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.psi.comportamente.labgame.entidades;

/**
 *
 * @author Weslley
 */
public enum TipoComplexidade {

    SIMPLES(0),
    MEDIO(1),
    COMPLEXO(2);

    private int valor;

    TipoComplexidade(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return this.valor;
    }
}
