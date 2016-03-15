/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpa.psi.comportamente.labgame.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Weslley
 */
@Entity
@Table(name="autorizacao")
public class Autorizacao implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    private String nome;
    
    public Autorizacao(){
        
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
