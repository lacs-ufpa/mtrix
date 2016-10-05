/*~
M-Trix - Social Experiments Application.
Copyright (C) 2015-2016 The M-Trix authors.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
~*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.psi.comportamente.labgame.mbeans;

import br.ufpa.psi.comportamente.labgame.dao.CondicaoDAO;
import br.ufpa.psi.comportamente.labgame.dao.ExperimentoDAO;
import br.ufpa.psi.comportamente.labgame.dao.PontuacaoCulturalDAO;
import br.ufpa.psi.comportamente.labgame.dao.PontuacaoIndividualDAO;
import br.ufpa.psi.comportamente.labgame.entidades.Condicao;
import br.ufpa.psi.comportamente.labgame.entidades.CondicaoExperimento;
import br.ufpa.psi.comportamente.labgame.entidades.Experimento;
import br.ufpa.psi.comportamente.labgame.entidades.PontuacaoCultural;
import br.ufpa.psi.comportamente.labgame.entidades.PontuacaoIndividual;
import br.ufpa.psi.comportamente.labgame.mbeans.models.CondicoesDataModel;
import br.ufpa.psi.comportamente.labgame.mbeans.models.CondicoesLazyDataModel;
import br.ufpa.psi.comportamente.labgame.mbeans.models.PontuacaoCulturalDataModel;
import br.ufpa.psi.comportamente.labgame.mbeans.models.PontuacaoCulturalLazyDataModel;
import br.ufpa.psi.comportamente.labgame.mbeans.models.PontuacaoIndivLazyDataModel;
import br.ufpa.psi.comportamente.labgame.mbeans.models.PontuacaoIndividualDataModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Weslley
 */
@ManagedBean
@ViewScoped
public class CondicaoMB implements Serializable {

      private static final long serialVersionUID = 1L;

      private Condicao condicao;
      private Condicao condicaoADetalhar;
      private CondicaoDAO condicaoDAO;

      //pontuacao selecionada na pagina
      private PontuacaoIndividual pontIndv;
      private PontuacaoCultural pontCult;

      private Long idPontIndv;
      private Long idPontCult;

      private CondicoesLazyDataModel condicoesModel;
      private CondicoesDataModel condicoesModelExperimento;

      private PontuacaoCulturalLazyDataModel pontuacoesCultLazyModel;
      private PontuacaoIndivLazyDataModel pontuacoesIndivLazyModel;
      private PontuacaoIndividualDataModel listPontIndivModel;
      private PontuacaoCulturalDataModel listPontCultModel;

      private List<PontuacaoIndividual> listaPontIndiv;
      private List<PontuacaoCultural> listaPontCult;

      private PontuacaoIndividualDAO pontIndivDAO;
      private PontuacaoCulturalDAO pontCultDAO;

      private String criterio;

      //para uso em datatables que listam estes itens na tela e permitem selecao
      private PontuacaoIndividual pontuacaoIndividualSelecionada;
      private PontuacaoCultural pontuacaoCulturalSelecionada;

      public CondicaoMB() {
            condicao = new Condicao();
            condicaoADetalhar = new Condicao();
            condicaoDAO = new CondicaoDAO();
            pontIndv = new PontuacaoIndividual();
            pontCult = new PontuacaoCultural();

            idPontIndv = null;
            idPontCult = null;

            listarPontCult();

            listarPontIndiv();

            listarPontCultIndivModel();

            listarCondicoesModel();

      }

      private List<Condicao> listarCondicoes() {
            condicaoDAO = new CondicaoDAO();

            condicaoDAO.beginTransaction();
            List<Condicao> listaCondicoes = condicaoDAO.findAll(Condicao.class);
            condicaoDAO.stopOperation(false);

            return listaCondicoes;
      }

      private void listarPontIndiv() {

            listaPontIndiv = new ArrayList<>();
            pontIndivDAO = new PontuacaoIndividualDAO();

            pontIndivDAO.beginTransaction();
            listaPontIndiv = pontIndivDAO.findAll();
            pontIndivDAO.stopOperation(false);

      }

      private void listarPontIndivDataModel() {
            pontuacoesIndivLazyModel = new PontuacaoIndivLazyDataModel();
      }
      
      private void listarPontCultIndivModel() {
            listarPontIndivDataModel();
            listarPontCultDataModel();
      }

      private void listarPontCultDataModel() {
            pontuacoesCultLazyModel = new PontuacaoCulturalLazyDataModel();
      }

      private void listarPontCult() {

            listaPontCult = new ArrayList<>();
            pontCultDAO = new PontuacaoCulturalDAO();

            pontCultDAO.beginTransaction();
            listaPontCult = pontCultDAO.findAll();
            pontCultDAO.stopOperation(false);

      }

      public void criaCondicao(String criador) {

            if (this.condicao.getListPontCult().isEmpty() && this.condicao.getListPontIndiv().isEmpty()) {
                  FacesContext.getCurrentInstance().addMessage("form4:nome", new FacesMessage("Não foi possível cadastrar sua condição. Precisa selecionar pelo menos uma forma de pontuação."));
            } else {

                  if (condicao.getFimPorQuantCiclo() != 0) {
                        condicao.setFimPorQuantCiclo(condicao.getFimPorQuantCiclo() - 1);
                  }

                  condicaoDAO = new CondicaoDAO();

                  condicao.setCriador(criador);

                  condicaoDAO.beginTransaction();
                  condicaoDAO.save(condicao);
                  condicaoDAO.stopOperation(true);

                  FacesContext.getCurrentInstance().addMessage("form4:nome", new FacesMessage("Condição cadastrada!"));

            }

            listarCondicoesModel();

            condicao = new Condicao();

      }

      private void listarCondicoesModel() {

            condicoesModel = new CondicoesLazyDataModel();

      }

      /**
       * Método que lista todas as condições e seta os experimentos que utilizam
       * as mesmas.
       *
       * @return CondicoesDataModel
       */
      public CondicoesDataModel listarCondicoesModelComExperimentos() {
            
            List<Condicao> condicoes = listarCondicoes();

            CondicaoDAO dao = new CondicaoDAO();
            for (Condicao condicaoAux : condicoes) {
                  dao.beginTransaction();
                  List<CondicaoExperimento> condExp = dao.encontraCondExpPorCondicao(condicaoAux);
                  if (condExp != null) {
                        condicaoAux.setNomesExperimentos(buscaNomeExperimento(condExp));
                  } else {
                        condicaoAux.setNomesExperimentos("");
                  }
                  dao.stopOperation(false);
            }

            return condicoesModelExperimento = new CondicoesDataModel(condicoes);

      }

      private String buscaNomeExperimento(List<CondicaoExperimento> listCondExp) {
            String nomes = "";
            List<Long> idList = new ArrayList<>();

            // Pega o id de cada experimento que contém a condicao.
            for (CondicaoExperimento condExp : listCondExp) {
                  if (idList.contains(condExp.getExperimento().getId())) {
                        //Faz nada
                  } else {
                        idList.add(condExp.getExperimento().getId());
                  }
            }

            // Pega o nome de cada experimento.
            for (Long id : idList) {
                  String nome;
                  ExperimentoDAO dao = new ExperimentoDAO();
                  dao.beginTransaction();
                  nome = dao.find(Experimento.class, id).getNome();
                  if ("".equals(nomes)) {
                        nomes = nome;
                  } else {
                        nomes = nomes + ", " + nome;
                  }
                  dao.stopOperation(false);
            }
            return nomes;
      }

      public Condicao getCondicao() {
            return condicao;
      }

      public void setCondicao(Condicao condicao) {
            this.condicao = condicao;
      }

      public PontuacaoIndividual getPontIndv() {
            return pontIndv;
      }

      public void setPontIndv(PontuacaoIndividual pontIndv) {
            this.pontIndv = pontIndv;
      }

      public List<PontuacaoIndividual> getListaPontIndiv() {
            return listaPontIndiv;
      }

      public void setListaPontIndiv(List<PontuacaoIndividual> listaPontIndiv) {
            this.listaPontIndiv = listaPontIndiv;
      }

      public Long getIdPontIndv() {
            return idPontIndv;
      }

      public void setIdPontIndv(Long idPontIndv) {
            this.idPontIndv = idPontIndv;
      }

      public Long getIdPontCult() {
            return idPontCult;
      }

      public void setIdPontCult(Long idPontCult) {
            this.idPontCult = idPontCult;
      }

      public List<PontuacaoCultural> getListaPontCult() {
            return listaPontCult;
      }

      public void setListaPontCult(List<PontuacaoCultural> listaPontCult) {
            this.listaPontCult = listaPontCult;
      }

      public PontuacaoCultural getPontCult() {
            return pontCult;
      }

      public void setPontCult(PontuacaoCultural pontCult) {
            this.pontCult = pontCult;
      }

      public PontuacaoIndividualDataModel getListPontIndivModel() {
            return listPontIndivModel;
      }

      public void setListPontIndivModel(PontuacaoIndividualDataModel listPontIndivModel) {
            this.listPontIndivModel = listPontIndivModel;
      }

      public PontuacaoCulturalDataModel getListPontCultModel() {
            return listPontCultModel;
      }

      public void setListPontCultModel(PontuacaoCulturalDataModel listPontCultModel) {
            this.listPontCultModel = listPontCultModel;
      }

      public String getCriterio() {
            return criterio;
      }

      public void setCriterio(String criterio) {
            this.criterio = criterio;
      }

      public CondicoesDataModel getCondicoesModelExperimento() {
            return condicoesModelExperimento;
      }

      public void setCondicoesModelExperimento(CondicoesDataModel condicoesModelExperimento) {
            this.condicoesModelExperimento = condicoesModelExperimento;
      }

      public CondicoesLazyDataModel getCondicoesModel() {
            return condicoesModel;
      }

      public void setCondicoesModel(CondicoesLazyDataModel condicoesModel) {
            this.condicoesModel = condicoesModel;
      }

      public PontuacaoCulturalLazyDataModel getPontuacoesCultLazyModel() {
            return pontuacoesCultLazyModel;
      }

      public void setPontuacoesCultLazyModel(PontuacaoCulturalLazyDataModel pontuacoesCultLazyModel) {
            this.pontuacoesCultLazyModel = pontuacoesCultLazyModel;
      }

      public PontuacaoIndivLazyDataModel getPontuacoesIndivLazyModel() {
            return pontuacoesIndivLazyModel;
      }

      public void setPontuacoesIndivLazyModel(PontuacaoIndivLazyDataModel pontuacoesIndivLazyModel) {
            this.pontuacoesIndivLazyModel = pontuacoesIndivLazyModel;
      }

      public PontuacaoIndividual getPontuacaoIndividualSelecionada() {
            return pontuacaoIndividualSelecionada;
      }

      public void setPontuacaoIndividualSelecionada(PontuacaoIndividual pontuacaoIndividualSelecionada) {
            this.pontuacaoIndividualSelecionada = pontuacaoIndividualSelecionada;
      }

      public PontuacaoCultural getPontuacaoCulturalSelecionada() {
            return pontuacaoCulturalSelecionada;
      }

      public void setPontuacaoCulturalSelecionada(PontuacaoCultural pontuacaoCulturalSelecionada) {
            this.pontuacaoCulturalSelecionada = pontuacaoCulturalSelecionada;
      }

      public Condicao getCondicaoADetalhar() {
            return condicaoADetalhar;
      }

      public void setCondicaoADetalhar(Condicao condicaoADetalhar) {
            this.condicaoADetalhar = condicaoADetalhar;
      }

}
