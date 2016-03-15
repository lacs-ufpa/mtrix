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
public class CondicaoDAO extends BaseDAO<Condicao> {

    public List<Condicao> encontraPorExperimento(Experimento experimento) {

        Query query = getEntityManager().createQuery("SELECT condicao FROM Condicao condicao inner join condicao.experimentos experimento WHERE experimento in :experimento");
        query.setParameter("experimento", experimento);

        return (List<Condicao>) query.getResultList();
    }

    public List<Condicao> encontraPorOutrosExperimentos(Experimento experimento) {

        Query query = getEntityManager().createQuery("SELECT condicao FROM Condicao condicao inner join condicao.experimentos experimento WHERE experimento != :experimento");
        query.setParameter("experimento", experimento);

        return (List<Condicao>) query.getResultList();
    }

    public List<CondicaoExperimento> encontraCondExpPorExperimento(Experimento experimento) {

        Query query;
        query = getEntityManager().createQuery("SELECT ce FROM " + CondicaoExperimento.class.getName() + " as ce WHERE ce.experimento.id = :experimentoid");
        query.setParameter("experimentoid", experimento.getId());

        return (List<CondicaoExperimento>) query.getResultList();
    }

    public List<CondicaoExperimento> encontraCondExpPorCondicao(Condicao condicao) {

        Query query;
        query = getEntityManager().createQuery("SELECT ce FROM " + CondicaoExperimento.class.getName() + " as ce WHERE ce.condicao.id = :condicaoid");
        query.setParameter("condicaoid", condicao.getId());

        if (!query.getResultList().isEmpty()) {
            return (List<CondicaoExperimento>) query.getResultList();
        } else {
            return null;
        }
    }

      public List<Condicao> findCondicoes(int first, int max) {
            Query query = getEntityManager().createQuery("SELECT condicao FROM " + Condicao.class.getName() + " AS condicao");
            query.setFirstResult(first);
            query.setMaxResults(max);

            return query.getResultList();
      }

    public int contaCondicoesTotal() {
        Query query = getEntityManager().createQuery("SELECT condicao FROM " + Condicao.class.getName() + " AS condicao");
        return query.getResultList().size();
    }

    public List<Condicao> findCondicoesNaoCadastradasExperimento(int first, int max, Experimento exp) {
        Query query = getEntityManager().createQuery("SELECT condicao FROM "+ Condicao.class.getName() +" AS condicao WHERE condicao.id NOT IN (SELECT ce.condicao.id FROM "+ CondicaoExperimento.class.getName() +" AS ce WHERE ce.experimento.id = :id)");
        query.setParameter("id", exp.getId());
        query.setFirstResult(first);
        query.setMaxResults(max);

        return query.getResultList();
    }
    
    public int contaCondicoesNaoCadastradasExperimento(Experimento exp) {
        Query query = getEntityManager().createQuery("SELECT condicao FROM "+ Condicao.class.getName() +" AS condicao WHERE condicao.id NOT IN (SELECT ce.condicao.id FROM "+ CondicaoExperimento.class.getName() +" AS ce WHERE ce.experimento.id = :id)");
        query.setParameter("id", exp.getId());
        
        return query.getResultList().size();
    }
    
    public List<Condicao> findCondicoesCadastradasExperimento(int first, int max, Experimento exp) {
        Query query = getEntityManager().createQuery("SELECT DISTINCT c FROM "+ Condicao.class.getName() +" c INNER JOIN "+ CondicaoExperimento.class.getName() +" ce "
                + "WHERE ce.condicao.id = c.id AND ce.experimento.id = :id");
        query.setParameter("id", exp.getId());
        query.setFirstResult(first);
        query.setMaxResults(max);
        
        return query.getResultList();
    }
    
    public int contaCondicoesCadastradasExperimento(Experimento exp) {
        Query query = getEntityManager().createQuery("SELECT DISTINCT c FROM "+ Condicao.class.getName() +" c INNER JOIN "+ CondicaoExperimento.class.getName() +" ce "
                + "WHERE ce.condicao.id = c.id AND ce.experimento.id = :id");
        query.setParameter("id", exp.getId());
        
        return query.getResultList().size();
    }

}
