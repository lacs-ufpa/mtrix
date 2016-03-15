package br.ufpa.psi.comportamente.labgame.dao;

import br.ufpa.psi.comportamente.labgame.entidades.Experimento;
import br.ufpa.psi.comportamente.labgame.entidades.Pesquisador;
import br.ufpa.psi.comportamente.labgame.mbeans.Constantes;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Weslley
 */
public class ExperimentoDAO extends BaseDAO<Experimento> {

      public Experimento find(Experimento experimento) {
            Query query = getEntityManager().createQuery(
                    "SELECT experimento FROM " + Experimento.class.getName() + " AS experimento WHERE experimento.id = :id");
            query.setParameter("id", experimento.getId());

            if (!(query.getResultList().isEmpty())) {
                  return (Experimento) query.getSingleResult();
            } else {
                  return null;
            }
      }

      public List<Experimento> findPesquisador(Pesquisador pesquisador) {
            Query query = getEntityManager().createQuery(
                    "SELECT experimento FROM " + Experimento.class.getName() + " AS experimento WHERE experimento.pesquisador = :pesquisador");
            query.setParameter("pesquisador", pesquisador);

            return (List<Experimento>) query.getResultList();
      }

      public List<Experimento> findExperimentos(int first, int max) {
            Query query = getEntityManager().createQuery("SELECT experimento FROM " + Experimento.class.getName() + " AS experimento");
            query.setFirstResult(first);
            query.setMaxResults(max);

            return query.getResultList();
      }

      public List<Experimento> findExperimentosFiltro(int first, int max, String filter) {
            Query query = getEntityManager().createQuery("SELECT e FROM " + Experimento.class.getName() + " AS e WHERE e.nome like :filtro OR e.objetivo like :filtro OR e.status like :filtro");
            query.setFirstResult(first);
            query.setMaxResults(max);
            query.setParameter("filtro", "%" + filter + "%");

            return query.getResultList();
      }

      public List<Experimento> findExperimentosConcluido(int first, int max) {
            Query query = getEntityManager().createQuery("SELECT experimento FROM " + Experimento.class.getName() + " AS experimento WHERE experimento.status = :status");
            query.setParameter("status", Constantes.CONCLUIDO);
            query.setFirstResult(first);
            query.setMaxResults(max);

            return query.getResultList();
      }

      public int contaExperimentosConcluidoTotal() {
            Query query = getEntityManager().createQuery("SELECT experimento FROM " + Experimento.class.getName() + " AS experimento WHERE experimento.status = :status");
            query.setParameter("status", Constantes.CONCLUIDO);
            return query.getResultList().size();
      }

      public int contaExperimentosTotal() {
            Query query = getEntityManager().createQuery("SELECT experimento FROM " + Experimento.class.getName() + " AS experimento");
            return query.getResultList().size();
      }

}
