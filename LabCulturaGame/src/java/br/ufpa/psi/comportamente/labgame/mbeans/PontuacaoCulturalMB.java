/*~
M-Trix - Social Experiments Application.
Copyright (C) 2015-2016 The M-Trix authors.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
~*/
package br.ufpa.psi.comportamente.labgame.mbeans;

import br.ufpa.psi.comportamente.labgame.dao.PontuacaoCulturalDAO;
import br.ufpa.psi.comportamente.labgame.entidades.PontuacaoCultural;
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
@ManagedBean(name = "pontCultMB")
@ViewScoped
public class PontuacaoCulturalMB implements Serializable {

      private static final long serialVersionUID = 1L;

      private List<String> cores;
      private List<String> linhas;
      private List<String> sinais;
      private List<String> condicionais;

      private PontuacaoCultural pontCult;

      private PontuacaoCulturalDAO pontCultDAO;

      //flag para a tela de cadastro de pontuacao individual
      private boolean pontVariavel = false;

      private boolean pontVariavelAcertoJogadas = false;

      public PontuacaoCulturalMB() {
            cores = new ArrayList<>();
            linhas = new ArrayList<>();
            sinais = new ArrayList<>();
            condicionais = new ArrayList<>();

            pontCult = new PontuacaoCultural();

            populaCores();
            populaLinhas();
            populaSinais();
            populaCondicionais();
      }

      private void populaCondicionais() {
            condicionais = new ArrayList<>();

            condicionais.add(Constantes.E);
            condicionais.add(Constantes.OU);
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

      public void salvaPontuacaoCultural() {
            boolean liberaSave = true;
            if (pontVariavel == true) {
                  if (pontCult.getTipoAcrescimoPont() == null) {
                        liberaSave = false;
                        FacesContext.getCurrentInstance().addMessage("formPontuacaoCult", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "É necessário escolher o tipo de acréscimo para esse tipo de configuração"));
                  } else if (pontCult.getTipoAcrescimoPont().equalsIgnoreCase(Constantes.FIXA)) {
                        if (pontCult.getIncrementoPontoAcertoFixo() == null) {
                              liberaSave = false;
                              FacesContext.getCurrentInstance().addMessage("formPontuacaoCult", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "É necessário definir o acréscimo fixo para esse tipo de configuração"));
                        }
                  } else if (pontCult.getTipoAcrescimoPont().equalsIgnoreCase(Constantes.VARIAVEL)) {
                        if (pontCult.getMinIncrementoPontoAcertoRand() == null || pontCult.getMaxIncrementoPontoAcertoRand() == null) {
                              liberaSave = false;
                              FacesContext.getCurrentInstance().addMessage("formPontuacaoCult", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "É necessário definir a quantidade mínima e máxima que o acréscimo pode assumir na pontuação para esse tipo de configuração"));
                        } else if (pontCult.temVariacaoAcrescimoMaxMenorQueMin()) {
                              liberaSave = false;
                              FacesContext.getCurrentInstance().addMessage("formPontuacaoCult", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "Verifique se a quantidade mínima não é maior que a máxima do acréscimo da pontuação"));
                        }
                  }
            } else if (pontVariavelAcertoJogadas == true) {
                  if (pontCult.getTipoPontuacaoAleatoria() == null) {
                        liberaSave = false;
                        FacesContext.getCurrentInstance().addMessage("formPontuacaoCult", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "É necessário definir se a pontuação aleatória será por jogadas fixas ou variáveis para esse tipo de configuração"));
                  } else {
                        if (pontCult.getTipoPontuacaoAleatoria().equalsIgnoreCase(Constantes.VARIAVEL)) {
                              if (pontCult.getQuantidadeMaxJogadasAleatorias() == null || pontCult.getQuantidadeMinJogadasAleatorias() == null) {
                                    liberaSave = false;
                                    FacesContext.getCurrentInstance().addMessage("formPontuacaoCult", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "Os campos de quantidade mínima de jogadas e quantidade máxima de jogadas não podem ficar vazios para esse tipo de configuração"));
                              } else if (pontCult.temQtdMaxJogadasAleatMenorQueMin()) {
                                    liberaSave = false;
                                    FacesContext.getCurrentInstance().addMessage("formPontuacaoCult", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "Por favor, certifique-se que a quantidade mínima de jogadas é menor que a quantidade máxima de jogadas"));
                              } else {
                                    if (pontCult.getVariacaoMaxima() == null || pontCult.getVariacaoMinima() == null) {
                                          liberaSave = false;
                                          FacesContext.getCurrentInstance().addMessage("formPontuacaoCult", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "É necessário definir um valor mínimo e um máximo que a pontuação pode assumir para esse tipo de configuração"));
                                    } else if (pontCult.temVariacaoMaxMenorQueMin()) {
                                          liberaSave = false;
                                          FacesContext.getCurrentInstance().addMessage("formPontuacaoCult", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "Por favor, certifique-se que a variação mínima da pontuação esteja menor que a variação máxima da pontuação"));
                                    }
                              }
                        } else if (pontCult.getTipoPontuacaoAleatoria().equalsIgnoreCase(Constantes.FIXA)) {
                              if (pontCult.getQuantidadeJogadasFixas() == null) {
                                    liberaSave = false;
                                    FacesContext.getCurrentInstance().addMessage("formPontuacaoCult", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "O campo de quantidade fixa de jogadas não pode ficar vazio para esse tipo de configuração"));
                              } else if (pontCult.getVariacaoMaxima() == null || pontCult.getVariacaoMinima() == null) {
                                    liberaSave = false;
                                    FacesContext.getCurrentInstance().addMessage("formPontuacaoCult", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "É necessário definir um valor mínimo e um máximo que a pontuação pode assumir para esse tipo de configuração"));
                              } else if (pontCult.temVariacaoMaxMenorQueMin()) {
                                    liberaSave = false;
                                    FacesContext.getCurrentInstance().addMessage("formPontuacaoCult", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar esse critério.", "Por favor, certifique-se que a variação mínima da pontuação esteja menor que a variação máxima da pontuação"));
                              }
                        }
                  }
            }
            if (liberaSave) {
                  pontCultDAO = new PontuacaoCulturalDAO();
                  pontCultDAO.beginTransaction();

                  List<PontuacaoCultural> lista = pontCultDAO.findAll();

                  for (PontuacaoCultural pc : lista) {
                        if (pc.equals(pontCult)) {
                              liberaSave = false;
                              break;
                        }
                  }

                  if (liberaSave == true) {
                        pontCultDAO.save(pontCult);
                        pontCultDAO.stopOperation(true);
                        FacesContext.getCurrentInstance().addMessage("formPontuacaoCult", new FacesMessage(FacesMessage.SEVERITY_INFO, "Critério de Pontuação Cultural cadastrado", ""));
                  } else {
                        pontCultDAO.stopOperation(false);
                        FacesContext.getCurrentInstance().addMessage("formPontuacaoCult", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Já existe um critério de Pontuação Cultural com essas características.", "Por favor, utilize o critério já existente"));
                  }

                  pontCult = new PontuacaoCultural();
            }
      }

      public void habilitaPontuacaoCulturalVariavel() {
            this.pontVariavel = this.pontVariavel != true;
            if (this.pontVariavel == true) {
                  this.pontVariavelAcertoJogadas = false;
            } else {
                  RequestContext.getCurrentInstance().reset("formPontuacaoCult:painelPontuacaoCultural");
            }
            RequestContext.getCurrentInstance().reset("formPontuacaoCult:painel-acertos-jogadas");
      }

      public void habilitaAcertoJogadas() {
            this.pontVariavelAcertoJogadas = this.pontVariavelAcertoJogadas != true;
            if (this.pontVariavelAcertoJogadas == true) {
                  this.pontVariavel = false;
            } else {
                  RequestContext.getCurrentInstance().reset("formPontuacaoCult:painel-acertos-jogadas");
            }
            RequestContext.getCurrentInstance().reset("formPontuacaoCult:painelPontuacaoCultural");
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

      public PontuacaoCultural getPontCult() {
            return pontCult;
      }

      public void setPontCult(PontuacaoCultural pontCult) {
            this.pontCult = pontCult;
      }
      
      public List<String> getCondicionais() {
            return condicionais;
      }

      public void setCondicionais(List<String> condicionais) {
            this.condicionais = condicionais;
      }

      public boolean isPontVariavel() {
            return pontVariavel;
      }

      public void setPontVariavel(boolean pontVariavel) {
            this.pontVariavel = pontVariavel;
      }

      public boolean isPontVariavelAcertoJogadas() {
            return pontVariavelAcertoJogadas;
      }

      public void setPontVariavelAcertoJogadas(boolean pontVariavelAcertoJogadas) {
            this.pontVariavelAcertoJogadas = pontVariavelAcertoJogadas;
      }

}
