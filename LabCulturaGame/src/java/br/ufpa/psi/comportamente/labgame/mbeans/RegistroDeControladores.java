/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpa.psi.comportamente.labgame.mbeans;

import br.ufpa.psi.comportamente.labgame.mbeans.interfaces.ExperimentoObservable;
import java.util.Hashtable;

/**
 *
 * @author adailton
 * 
 * Classe que representa o registro central onde todas as classes procuram as instancias dos controladores de cada experimento
 * Como os atributos sao estaticos, serao unicos para todo o sistema, e sao acessiveis sem a necessidade de criar instancia da classe
 * 
 * 
 */
public  class RegistroDeControladores {
    
    private static Hashtable<Long,ExperimentoObservable> registroControladores = new  Hashtable<Long,ExperimentoObservable>();
    
    
    public static ExperimentoObservable getControladorExperimentoInstance(Long idExperimento){
        return registroControladores.get(idExperimento);
    }

    
    public static void setControladorExperimentoInstance(Long idExperimento, ExperimentoObservable obs){
        registroControladores.put(idExperimento, obs);
    }

    
}
