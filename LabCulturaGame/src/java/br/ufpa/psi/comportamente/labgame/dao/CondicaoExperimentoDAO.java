/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.psi.comportamente.labgame.dao;

import br.ufpa.psi.comportamente.labgame.entidades.Condicao;
import br.ufpa.psi.comportamente.labgame.entidades.CondicaoExperimento;
import br.ufpa.psi.comportamente.labgame.entidades.Experimento;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Weslley
 */
public class CondicaoExperimentoDAO extends BaseDAO<CondicaoExperimento> {

    public List<CondicaoExperimento> encontraCondExpPorExperimento(Experimento experimento) {

        Query query = getEntityManager().createQuery("SELECT ce FROM " + CondicaoExperimento.class.getName() + " as ce "
                + "WHERE ce.experimento.id = :experimentoid");
        query.setParameter("experimentoid", experimento.getId());

        return (List<CondicaoExperimento>) query.getResultList();
    }
    
    public List<CondicaoExperimento> encontraCondExpPorCondicaoExperimento(Experimento experimento, Condicao condicao) {

        Query query = getEntityManager().createQuery("SELECT ce FROM " + CondicaoExperimento.class.getName() + " as ce "
                + "WHERE ce.experimento.id = :experimentoid AND ce.condicao.id = :condicaoid");
        query.setParameter("experimentoid", experimento.getId());
        query.setParameter("condicaoid", condicao.getId());

        return (List<CondicaoExperimento>) query.getResultList();
    }

}
