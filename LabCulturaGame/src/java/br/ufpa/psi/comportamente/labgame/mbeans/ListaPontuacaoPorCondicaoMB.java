package br.ufpa.psi.comportamente.labgame.mbeans;

import br.ufpa.psi.comportamente.labgame.dao.PontuacaoCulturalDAO;
import br.ufpa.psi.comportamente.labgame.dao.PontuacaoIndividualDAO;
import br.ufpa.psi.comportamente.labgame.entidades.Condicao;
import br.ufpa.psi.comportamente.labgame.entidades.PontuacaoCultural;
import br.ufpa.psi.comportamente.labgame.entidades.PontuacaoIndividual;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Weslley Tavares <weslleysammyr@gmail.com>
 */
@ManagedBean (name = "listaPontCondMB")
@ViewScoped
public class ListaPontuacaoPorCondicaoMB implements Serializable{
      
      private static final long serialVersionUID = 1L;
      
      private List<PontuacaoCultural> listPontCult;
      private List<PontuacaoIndividual> listPontIndiv;
      
      private final PontuacaoCulturalDAO pontCultDAO;
      private final PontuacaoIndividualDAO pontIndivDAO;
      
      public ListaPontuacaoPorCondicaoMB() {
            pontCultDAO = new PontuacaoCulturalDAO();
            pontIndivDAO = new PontuacaoIndividualDAO();
            listPontCult = new ArrayList<>();
            listPontIndiv = new ArrayList<>();
      }
      
      public void listaPontuacoesPorCondicao(Condicao c) {
            listaPontuacoesCultural(c);
            listaPontuacoesIndividual(c);
      }
      
      private void listaPontuacoesIndividual(Condicao c) {
            pontIndivDAO.beginTransaction();
            listPontIndiv = pontIndivDAO.encontraPontuacaoIndivPorCondicao(c);
            pontIndivDAO.stopOperation(false);
      }
      
      private void listaPontuacoesCultural(Condicao c) {
            pontCultDAO.beginTransaction();
            listPontCult = pontCultDAO.encontraPontuacaoCultPorCondicao(c);
            pontCultDAO.stopOperation(false);
      }

      public List<PontuacaoCultural> getListPontCult() {
            return listPontCult;
      }

      public void setListPontCult(List<PontuacaoCultural> listPontCult) {
            this.listPontCult = listPontCult;
      }

      public List<PontuacaoIndividual> getListPontIndiv() {
            return listPontIndiv;
      }

      public void setListPontIndiv(List<PontuacaoIndividual> listPontIndiv) {
            this.listPontIndiv = listPontIndiv;
      }

}
