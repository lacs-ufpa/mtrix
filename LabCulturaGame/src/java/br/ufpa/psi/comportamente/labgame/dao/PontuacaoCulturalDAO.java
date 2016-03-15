package br.ufpa.psi.comportamente.labgame.dao;

import br.ufpa.psi.comportamente.labgame.entidades.Condicao;
import br.ufpa.psi.comportamente.labgame.entidades.PontuacaoCultural;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Weslley
 */
public class PontuacaoCulturalDAO extends BaseDAO<PontuacaoCultural>{

    public List<PontuacaoCultural> findAll() {
        Query query = getEntityManager().createQuery(
                "SELECT pontuacaoCultural FROM "+ PontuacaoCultural.class.getName() +" pontuacaoCultural");
        return (List<PontuacaoCultural>) query.getResultList();
    }
    
    public List<PontuacaoCultural> encontraPontuacaoCultPorCondicao(Condicao cond) {
        Query query = getEntityManager().createQuery(
                "SELECT pc FROM "+ Condicao.class.getName() +" c, IN(c.listPontCult) pc WHERE c.id = :id");
        query.setParameter("id", cond.getId());
        return query.getResultList();
    }
    
    public List<PontuacaoCultural> findAll(int first, int max) {
        Query query = getEntityManager().createQuery(
                "SELECT pontuacaoCultural FROM "+ PontuacaoCultural.class.getName() +" pontuacaoCultural");
        query.setFirstResult(first);
        query.setMaxResults(max);
        
        return query.getResultList();
    }
    
    public int contaPontuacoesTotal() {
        Query query = getEntityManager().createQuery(
                "SELECT pontuacaoCultural FROM "+ PontuacaoCultural.class.getName() +" pontuacaoCultural");
        
        return query.getResultList().size();
    }

    public PontuacaoCultural find(Long id) {
        Query query = getEntityManager().createQuery(
                "SELECT pontuacaoCultural FROM PontuacaoCultural pontuacaoCultural WHERE pontuacaoCultural.id = :id");
        query.setParameter("id", id);

        return (PontuacaoCultural) query.getSingleResult();
    }
    
    public boolean procuraPontCultRepetida(PontuacaoCultural pc) {
        Query query = getEntityManager().createQuery(
                "SELECT pontuacaoCultural FROM PontuacaoCultural pontuacaoCultural WHERE pontuacaoCultural.ciclos = :ciclos AND"
                        + " pontuacaoCultural.condicional = :condicional AND pontuacaoCultural.corSelecionada = :cor AND"
                        + " pontuacaoCultural.incrementoPontoAcertoFixo = :incremento AND pontuacaoCultural.linhaSelecionada = :linha AND"
                        + " pontuacaoCultural.maxIncrementoPontoAcertoRand = :max_acerto_rand AND pontuacaoCultural.minIncrementoPontoAcertoRand = :min_acerto_rand AND"
                        + " pontuacaoCultural.pontoAcerto = :ponto_acerto AND pontuacaoCultural.sinalSelecionado = :sinal AND"
                        + " pontuacaoCultural.tipoAcrescimoPont = :acresc_pont");
        query.setParameter("ciclos", pc.getCiclos());
        query.setParameter("condicional", pc.getCondicional());
        query.setParameter("cor", pc.getCoresSelecionadas());
        query.setParameter("incremento", pc.getIncrementoPontoAcertoFixo());
        query.setParameter("linha", pc.getLinhasSelecionadas());
        query.setParameter("max_acerto_rand", pc.getMaxIncrementoPontoAcertoRand());
        query.setParameter("min_acerto_rand", pc.getMinIncrementoPontoAcertoRand());
        query.setParameter("ponto_acerto", pc.getPontoAcerto());
        query.setParameter("sinal", pc.getSinalSelecionado());
        query.setParameter("acresc_pont", pc.getTipoAcrescimoPont());
        
        if (query.getResultList().isEmpty()) {
            return true;
        }
        
        return false;
    }

}
