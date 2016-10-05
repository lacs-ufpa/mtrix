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

import br.ufpa.psi.comportamente.labgame.dao.PontuacaoIndividualDAO;
import br.ufpa.psi.comportamente.labgame.entidades.PontuacaoIndividual;
import br.ufpa.psi.comportamente.labgame.mbeans.models.PontuacaoIndividualDataModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Weslley
 */
@ManagedBean(name = "pontIndivMB")
@ViewScoped
public class PontuacaoIndividualMB implements Serializable {

      private static final long serialVersionUID = 1L;

      private String FIXA = Constantes.FIXA;
      private String VARIAVEL = Constantes.VARIAVEL;

      //CRIAR OS ARRAYS COM VALORES FIXOS PARA ALIMENTAR O SELECTONEMENU DROP DOWN
      private List<String> cores;
      private List<String> linhas;
      private List<String> sinais;
      private List<String> condicionais;

      private PontuacaoIndividual pontIndiv;
      private PontuacaoIndividualDataModel listPontDataModel;

      private PontuacaoIndividualDAO pontIndivDAO;

      //flag para a tela de cadastro de pontuacao individual
      private boolean pontVariavel = false;

      private boolean pontVariavelAcertoJogadas = false;

      public PontuacaoIndividualMB() {
            cores = new ArrayList<>();
            linhas = new ArrayList<>();
            sinais = new ArrayList<>();
            condicionais = new ArrayList<>();

            pontIndiv = new PontuacaoIndividual();

            populaCores();
            populaLinhas();
            populaSinais();
            populaCondicionais();

      }

      private void populaCores() {
            cores = new ArrayList<>();

            cores.add(Constantes.DIFERENTES);
            cores.add(Constantes.AMARELO);
            cores.add(Constantes.VERDE);
            cores.add(Constantes.VERMELHO);
            cores.add(Constantes.ROXO);
            cores.add(Constantes.AZUL);
            cores.add(Constantes.INDIFERENTE);

      }

      private void populaLinhas() {
            linhas = new ArrayList<>();

            linhas.add(Constantes.IMPAR);
            linhas.add(Constantes.PAR);
            linhas.add(Constantes.INDIFERENTE);
      }

      private void populaSinais() {
            sinais = new ArrayList<>();

            sinais.add(Constantes.MAIS);
            sinais.add(Constantes.MENOS);
      }

      private void populaCondicionais() {
            condicionais = new ArrayList<>();

            condicionais.add(Constantes.E);
            condicionais.add(Constantes.OU);
      }

      public void salvaPontuacaoIndiv() {
            boolean liberaSave = true;
            if (pontVariavel == true) {
                  if (pontIndiv.getTipoAcrescimoPont() == null) {
                        liberaSave = false;
                        FacesContext.getCurrentInstance().addMessage("pontIndivForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "É necessário escolher o tipo de acréscimo para esse tipo de configuração"));
                  } else if (pontIndiv.getTipoAcrescimoPont().equalsIgnoreCase(Constantes.FIXA)) {
                        if (pontIndiv.getIncrementoPontoAcertoFixo() == null) {
                              liberaSave = false;
                              FacesContext.getCurrentInstance().addMessage("pontIndivForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "É necessário definir o acréscimo fixo para esse tipo de configuração"));
                        }
                  } else if (pontIndiv.getTipoAcrescimoPont().equalsIgnoreCase(Constantes.VARIAVEL)) {
                        if (pontIndiv.getMinIncrementoPontoAcertoRand() == null || pontIndiv.getMaxIncrementoPontoAcertoRand() == null) {
                              liberaSave = false;
                              FacesContext.getCurrentInstance().addMessage("pontIndivForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "É necessário definir a quantidade mínima e máxima que o acréscimo pode assumir na pontuação para esse tipo de configuração"));
                        } else if (pontIndiv.temVariacaoAcrescimoMaxMenorQueMin()) {
                              liberaSave = false;
                              FacesContext.getCurrentInstance().addMessage("pontIndivForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "Verifique se a quantidade mínima não é maior que a máxima do acréscimo da pontuação"));
                        }
                  }
            } else if (pontVariavelAcertoJogadas == true) {
                  if (pontIndiv.getTipoPontuacaoAleatoria() == null) {
                        liberaSave = false;
                        FacesContext.getCurrentInstance().addMessage("pontIndivForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "É necessário definir se a pontuação aleatória será por jogadas fixas ou variáveis para esse tipo de configuração"));
                  } else {
                        if (pontIndiv.getTipoPontuacaoAleatoria().equalsIgnoreCase(Constantes.VARIAVEL)) {
                              if (pontIndiv.getQuantidadeMaxJogadasAleatorias() == null || pontIndiv.getQuantidadeMinJogadasAleatorias() == null) {
                                    liberaSave = false;
                                    FacesContext.getCurrentInstance().addMessage("pontIndivForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "Os campos de quantidade mínima de jogadas e quantidade máxima de jogadas não podem ficar vazios para esse tipo de configuração"));
                              } else if (pontIndiv.temQtdMaxJogadasAleatMenorQueMin()) {
                                    liberaSave = false;
                                    FacesContext.getCurrentInstance().addMessage("pontIndivForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "Por favor, certifique-se que a quantidade mínima de jogadas é menor que a quantidade máxima de jogadas"));
                              } else {
                                    if (pontIndiv.getVariacaoMaxima() == null || pontIndiv.getVariacaoMinima() == null) {
                                          liberaSave = false;
                                          FacesContext.getCurrentInstance().addMessage("pontIndivForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "É necessário definir um valor mínimo e um máximo que a pontuação pode assumir para esse tipo de configuração"));
                                    } else if (pontIndiv.temVariacaoMaxMenorQueMin()) {
                                          liberaSave = false;
                                          FacesContext.getCurrentInstance().addMessage("pontIndivForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "Por favor, certifique-se que a variação mínima da pontuação esteja menor que a variação máxima da pontuação"));
                                    }
                              }
                        } else if (pontIndiv.getTipoPontuacaoAleatoria().equalsIgnoreCase(Constantes.FIXA)) {
                              if (pontIndiv.getQuantidadeJogadasFixas() == null) {
                                    liberaSave = false;
                                    FacesContext.getCurrentInstance().addMessage("pontIndivForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "O campo de quantidade fixa de jogadas não pode ficar vazio para esse tipo de configuração"));
                              } else if (pontIndiv.getVariacaoMaxima() == null || pontIndiv.getVariacaoMinima() == null) {
                                    liberaSave = false;
                                    FacesContext.getCurrentInstance().addMessage("pontIndivForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "É necessário definir um valor mínimo e um máximo que a pontuação pode assumir para esse tipo de configuração"));
                              } else if (pontIndiv.temVariacaoMaxMenorQueMin()) {
                                    liberaSave = false;
                                    FacesContext.getCurrentInstance().addMessage("pontIndivForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "Por favor, certifique-se que a variação mínima da pontuação esteja menor que a variação máxima da pontuação"));
                              }
                        }
                  }
            }
            if (liberaSave) {
                  pontIndivDAO = new PontuacaoIndividualDAO();
                  pontIndivDAO.beginTransaction();
                  List<PontuacaoIndividual> lista = pontIndivDAO.findAll();
                  
                  for (PontuacaoIndividual pi : lista) {
                        if (pi.equals(pontIndiv)) {
                              liberaSave = false;
                              break;
                        }
                  }

                  if (liberaSave == true) {
                        pontIndivDAO.save(pontIndiv);
                        pontIndivDAO.stopOperation(true);
                        FacesContext.getCurrentInstance().addMessage("pontIndivForm", new FacesMessage(FacesMessage.SEVERITY_INFO, "Critério de Pontuação Individual cadastrado", ""));
                  } else {
                        pontIndivDAO.stopOperation(false);
                        FacesContext.getCurrentInstance().addMessage("pontIndivForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Já existe um critério de Pontuação Individual com essas características.", "Por favor, utilize o critério já existente :)"));
                  }
            }

            pontIndiv = new PontuacaoIndividual();

      }

      public List<String> getCores() {
            return cores;
      }

      public void setCores(List<String> cores) {
            this.cores = cores;
      }

      public List<String> getLinhas() {
            return linhas;
      }

      public void setLinhas(List<String> linhas) {
            this.linhas = linhas;
      }

      public List<String> getSinais() {
            return sinais;
      }

      public void setSinais(List<String> sinais) {
            this.sinais = sinais;
      }

      public PontuacaoIndividual getPontIndiv() {
            return pontIndiv;
      }

      public void setPontIndiv(PontuacaoIndividual pontIndiv) {
            this.pontIndiv = pontIndiv;
      }

      public List<String> getCondicionais() {
            return condicionais;
      }

      public void setCondicionais(List<String> condicionais) {
            this.condicionais = condicionais;
      }

      public PontuacaoIndividualDataModel getListPontDataModel() {
            return listPontDataModel;
      }

      public void setListPontDataModel(PontuacaoIndividualDataModel listPontDataModel) {
            this.listPontDataModel = listPontDataModel;
      }

      public boolean isPontVariavel() {
            return pontVariavel;
      }

      public void setPontVariavel(boolean pontVariavel) {
            this.pontVariavel = pontVariavel;
      }

      public void habilitaPontuacaoIndividual() {
            this.pontVariavel = this.pontVariavel != true;
            if (this.pontVariavel == true) {
                  this.pontVariavelAcertoJogadas = false;
            } else {
                  RequestContext.getCurrentInstance().reset("pontIndivForm:painelPontuacaoIndividual");
            }
            RequestContext.getCurrentInstance().reset("pontIndivForm:painel-acertos-jogadas");
      }

      public void habilitaAcertoJogadas() {
            this.pontVariavelAcertoJogadas = this.pontVariavelAcertoJogadas != true;
            if (this.pontVariavelAcertoJogadas == true) {
                  this.pontVariavel = false;
            } else {
                  RequestContext.getCurrentInstance().reset("pontIndivForm:painel-acertos-jogadas");
                  this.pontIndiv.setTipoPontuacaoAleatoria("");
                  this.pontIndiv.setTipoAcrescimoPont("");
            }
            RequestContext.getCurrentInstance().reset("pontIndivForm:painelPontuacaoIndividual");
            RequestContext.getCurrentInstance().reset("pontIndivForm:input-pontuacao");
      }

      public String getFIXA() {
            return FIXA;
      }

      public String getVARIAVEL() {
            return VARIAVEL;
      }

      public void setFIXA(String FIXA) {
            this.FIXA = FIXA;
      }

      public void setVARIAVEL(String VARIAVEL) {
            this.VARIAVEL = VARIAVEL;
      }

      public boolean isPontVariavelAcertoJogadas() {
            return pontVariavelAcertoJogadas;
      }

      public void setPontVariavelAcertoJogadas(boolean pontVariavelAcertoJogadas) {
            this.pontVariavelAcertoJogadas = pontVariavelAcertoJogadas;
      }

}
