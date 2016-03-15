package br.ufpa.psi.comportamente.labgame.dao;

import br.ufpa.psi.comportamente.labgame.entidades.Condicao;
import br.ufpa.psi.comportamente.labgame.entidades.PontuacaoIndividual;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Weslley
 */
public class PontuacaoIndividualDAO extends BaseDAO<PontuacaoIndividual> {

    public List<PontuacaoIndividual> findAll() {
        Query query = getEntityManager().createQuery(
                "SELECT pontuacaoIndividual FROM " + PontuacaoIndividual.class.getName() + " pontuacaoIndividual");
        return query.getResultList();
    }
    
    public List<PontuacaoIndividual> encontraPontuacaoIndivPorCondicao(Condicao cond) {
        Query query = getEntityManager().createQuery(
                "SELECT pc FROM "+ Condicao.class.getName() +" c, IN(c.listPontIndiv) pc WHERE c.id = :id");
        query.setParameter("id", cond.getId());
        return query.getResultList();
    }

    public List<PontuacaoIndividual> findAll(int first, int max) {
        Query query = getEntityManager().createQuery(
                "SELECT pontuacaoIndividual FROM " + PontuacaoIndividual.class.getName() + " pontuacaoIndividual");
        query.setFirstResult(first);
        query.setMaxResults(max);

        return query.getResultList();
    }

    public int contaPontuacoesTotal() {
        Query query = getEntityManager().createQuery(
                "SELECT pontuacaoIndividual FROM " + PontuacaoIndividual.class.getName() + " pontuacaoIndividual");
        return query.getResultList().size();
    }

    public PontuacaoIndividual find(Long id) {
        Query query = getEntityManager().createQuery(
                "SELECT pontuacaoIndividual FROM " + PontuacaoIndividual.class.getName() + " pontuacaoIndividual WHERE pontuacaoIndividual.id = :id");
        query.setParameter("id", id);

        return (PontuacaoIndividual) query.getSingleResult();
    }

    public boolean procuraPontIndivRepetida(PontuacaoIndividual pi) {
        Query query = getEntityManager().createQuery(
                "SELECT pontuacaoIndividual FROM PontuacaoIndividual pontuacaoIndividual WHERE pontuacaoIndividual.ciclos = :ciclos AND"
                + " pontuacaoIndividual.condicional = :condicional AND pontuacaoIndividual.corSelecionada = :cor AND"
                + " pontuacaoIndividual.incrementoPontoAcertoFixo = :incremento AND pontuacaoIndividual.linhaSelecionada = :linha AND"
                + " pontuacaoIndividual.maxIncrementoPontoAcertoRand = :max_acerto_rand AND pontuacaoIndividual.minIncrementoPontoAcertoRand = :min_acerto_rand AND"
                + " pontuacaoIndividual.pontoAcerto = :ponto_acerto AND pontuacaoIndividual.sinalSelecionado = :sinal AND"
                + " pontuacaoIndividual.tipoAcrescimoPont = :acresc_pont");
        query.setParameter("ciclos", pi.getCiclos());
        query.setParameter("condicional", pi.getCondicional());
        query.setParameter("cor", pi.getCorSelecionada());
        query.setParameter("incremento", pi.getIncrementoPontoAcertoFixo());
        query.setParameter("linha", pi.getLinhaSelecionada());
        query.setParameter("max_acerto_rand", pi.getMaxIncrementoPontoAcertoRand());
        query.setParameter("min_acerto_rand", pi.getMinIncrementoPontoAcertoRand());
        query.setParameter("ponto_acerto", pi.getPontoAcerto());
        query.setParameter("sinal", pi.getSinalSelecionado());
        query.setParameter("acresc_pont", pi.getTipoAcrescimoPont());

        return query.getResultList().isEmpty();
    }

}
