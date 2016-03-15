package br.ufpa.psi.comportamente.labgame.dao;

import br.ufpa.psi.comportamente.labgame.entidades.Experimento;
import br.ufpa.psi.comportamente.labgame.entidades.Jogador;
import br.ufpa.psi.comportamente.labgame.entidades.Usuario;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Weslley
 */
public class JogadorDAO extends BaseDAO<Jogador> {

      public Jogador find(Jogador jogador) {
            Query query = getEntityManager().createQuery(
                    "SELECT usuario FROM " + Usuario.class.getName() + " usuario WHERE usuario.username = :login AND usuario.password = :senha");
            query.setParameter("login", jogador.getUsername());
            query.setParameter("senha", jogador.getPassword());

            return (Jogador) query.getSingleResult();
      }

      //MÉTODO PARA TESTE UTILIZADO NA CLASSE TESTECADASTRA JOGADOR EM EXPERIMENTO
      public Jogador find(Long id) {
            Query query = getEntityManager().createQuery(
                    "SELECT jogador FROM " + Jogador.class.getName() + " jogador WHERE jogador.id = :id");
            query.setParameter("id", id);

            return (Jogador) query.getSingleResult();
      }

      public List<Jogador> findAll(Jogador jogador) {
            // List<Jogador> jogadores = new ArrayList<>();
            Query query = getEntityManager().createQuery(
                    "SELECT jogador FROM " + Jogador.class.getName() + " jogador WHERE jogador.username <> :login");
            query.setParameter("login", jogador.getUsername());

            //jogadores.addAll(query.getResultList());
            return (List<Jogador>) query.getResultList();
      }

      public boolean encontraPorLogin(Jogador jogador) {
            Query query = getEntityManager().createQuery(
                    "SELECT jogador FROM " + Jogador.class.getName() + " jogador WHERE jogador.username = :login");
            query.setParameter("login", jogador.getUsername());
            return query.getResultList().isEmpty();
      }

      public List<Jogador> encontraPorExperimento(Experimento experimento) {

            Query query = getEntityManager().createQuery("SELECT jogador FROM " + Jogador.class.getName() + " jogador WHERE jogador.experimento.id = :experimentoid");
            query.setParameter("experimentoid", experimento.getId());

            return (List<Jogador>) query.getResultList();
      }

      public List<Jogador> encontraPorExperimentoExcluindoLogado(Jogador jogador) {

            Query query = getEntityManager().createQuery("SELECT jogador FROM " + Jogador.class.getName() + " jogador WHERE jogador.experimento.id = :experimentoid AND jogador.id <> :jogadorid");
            query.setParameter("experimentoid", jogador.getExperimento().getId());
            query.setParameter("jogadorid", jogador.getId());

            return (List<Jogador>) query.getResultList();
      }

      public List<Jogador> encontraJogadorPorExperimentoNulo(int first, int max) {
            Query query = getEntityManager().createQuery("SELECT jogador FROM " + Jogador.class.getName() + " jogador WHERE jogador.experimento IS NULL");
            query.setFirstResult(first);
            query.setMaxResults(max);

            return (List<Jogador>) query.getResultList();
      }

      public int contaJogadoresExperimentoNulo() {
            Query query = getEntityManager().createQuery("SELECT jogador FROM " + Jogador.class.getName() + " jogador WHERE jogador.experimento IS NULL");
            return query.getResultList().size();
      }
      
      public List<Jogador> findJogadores(int first, int max) {
        Query query = getEntityManager().createQuery("SELECT jogador FROM " + Jogador.class.getName() + " AS jogador");
        query.setFirstResult(first);
        query.setMaxResults(max);

        return query.getResultList();
    }

      public int contaJogadoresTotal() {
            Query query = getEntityManager().createQuery("SELECT jogador FROM " + Jogador.class.getName() + " AS jogador");
            return query.getResultList().size();
      }
      
      public boolean verificaCodigoInicialJogadores(String ci) {
            Query query = getEntityManager().createQuery("SELECT jogador FROM " + Jogador.class.getName() + " AS jogador WHERE jogador.username like :codigo");
            query.setParameter("codigo", ci + "%");
            
            return query.getResultList().isEmpty();
      }

}
