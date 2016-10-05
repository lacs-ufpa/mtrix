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
package br.ufpa.psi.comportamente.labgame.entidades;

import br.ufpa.psi.comportamente.labgame.mbeans.Constantes;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Weslley
 */
@Entity
@Table(name = "pontuacaoindividual")
public class PontuacaoIndividual implements Serializable {

      private static final long serialVersionUID = 1L;

      @Id
      @GeneratedValue
      private Long id;

      private String linhaSelecionada;
      private String corSelecionada;
      private Integer pontoAcerto;
      private String sinalSelecionado;
      private String condicional;

      private Integer ciclos;

      private String tipoAcrescimoPont;

      private Integer incrementoPontoAcertoFixo;

      private Integer minIncrementoPontoAcertoRand;
      private Integer maxIncrementoPontoAcertoRand;

      //Atributos de alteração da pontuação aleatória
      private String tipoPontuacaoAleatoria; // Pode ser FIXO ou VARIAVEL
      private Integer quantidadeJogadasFixas;
      private Integer quantidadeMinJogadasAleatorias;
      private Integer quantidadeMaxJogadasAleatorias;
      private Integer variacaoMinima;
      private Integer variacaoMaxima;

      //Atributos usados quando a pontuacao for aleatoria
      @Transient
      private Integer quantidadeOcorrencia;

      @Transient
      private Integer contOcorrencia;

      @Transient
      private boolean incrementaPontuacaoCiclo;

      /*  São textos que , quando preenchidos, devem ser exibidos aos participantes na ativacao ou nao de uma regra */
      private String consequenciaVerbalPositiva = "";
      private String consequenciaVerbalNegativa = "";

      @ManyToMany(mappedBy = "listPontIndiv", fetch = FetchType.EAGER)
      private List<Condicao> condicoes;

      public PontuacaoIndividual() {
            this.tipoAcrescimoPont = "";
            this.tipoPontuacaoAleatoria = "";
      }

      public void iniciaAsVariaveisTransient() {
            this.incrementaPontuacaoCiclo = false;
            this.contOcorrencia = 0;
            if (this.quantidadeJogadasFixas == null) {
                  calculaQuantidadeJogadasAleatorias();
            } else {
                  this.quantidadeOcorrencia = this.quantidadeJogadasFixas;
                  calculaPontuacaoAleatoriaPorJogadas();
            }
      }

      private void calculaQuantidadeJogadasAleatorias() {
            this.quantidadeOcorrencia = (int) Math.round(this.quantidadeMinJogadasAleatorias + Math.random() * (this.quantidadeMaxJogadasAleatorias - this.quantidadeMinJogadasAleatorias));
            calculaPontuacaoAleatoriaPorJogadas();
      }

      private void calculaPontuacaoAleatoriaPorJogadas() {
            this.pontoAcerto = (int) Math.round(this.variacaoMinima + Math.random() * (this.variacaoMaxima - this.variacaoMinima));
      }

      public void incrementaContadorOcorrencia() {
            this.contOcorrencia++;
            this.incrementaPontuacaoCiclo = true;
      }

      public void atualizaValoresDaPontuacao() {
            if (this.incrementaPontuacaoCiclo == true) {
                  if (this.quantidadeOcorrencia <= this.contOcorrencia) {
                        iniciaAsVariaveisTransient();
                  } else {
                        this.incrementaPontuacaoCiclo = false;
                  }
            }
      }

      public boolean existeOcorrenciaNoCicloAtual() {
            return this.incrementaPontuacaoCiclo;
      }

      public boolean comPontuacaoAleatoria() {
          if (this.tipoPontuacaoAleatoria == null) {
              return false;
          }
            return !this.tipoPontuacaoAleatoria.equalsIgnoreCase("");
      }
      
      // Retorna uma string informando se a pontuação é fixa ou variável.
      public String getPontuacaoFixaOuVariavel() {
            if (tipoAcrescimoPont != null) {
                  if (tipoAcrescimoPont.equalsIgnoreCase(Constantes.VARIAVEL)) {
                        return "VARIÁVEL";
                  } else {
                        return "FIXA";
                  }
            } else if (tipoPontuacaoAleatoria != null) {
                  if (tipoPontuacaoAleatoria.equalsIgnoreCase(Constantes.VARIAVEL)) {
                        return "VARIÁVEL";
                  } else {
                        return "FIXA";
                  }
            } else {
                  return "FIXA";
            }
      }
      
      public boolean temQtdMaxJogadasAleatMenorQueMin() {
            return this.quantidadeMaxJogadasAleatorias < this.quantidadeMinJogadasAleatorias;
      }
      
      public boolean temVariacaoMaxMenorQueMin() {
            return this.variacaoMaxima < this.variacaoMinima;
      }
      
      public boolean temVariacaoAcrescimoMaxMenorQueMin() {
            return this.maxIncrementoPontoAcertoRand < this.minIncrementoPontoAcertoRand;
      }

      @Override
      public int hashCode() {
            int hash = 3;
            hash = 37 * hash + Objects.hashCode(this.linhaSelecionada);
            hash = 37 * hash + Objects.hashCode(this.corSelecionada);
            hash = 37 * hash + Objects.hashCode(this.pontoAcerto);
            hash = 37 * hash + Objects.hashCode(this.sinalSelecionado);
            hash = 37 * hash + Objects.hashCode(this.condicional);
            hash = 37 * hash + Objects.hashCode(this.ciclos);
            hash = 37 * hash + Objects.hashCode(this.tipoAcrescimoPont);
            hash = 37 * hash + Objects.hashCode(this.incrementoPontoAcertoFixo);
            hash = 37 * hash + Objects.hashCode(this.minIncrementoPontoAcertoRand);
            hash = 37 * hash + Objects.hashCode(this.maxIncrementoPontoAcertoRand);
            hash = 37 * hash + Objects.hashCode(this.tipoPontuacaoAleatoria);
            hash = 37 * hash + Objects.hashCode(this.quantidadeJogadasFixas);
            hash = 37 * hash + Objects.hashCode(this.quantidadeMinJogadasAleatorias);
            hash = 37 * hash + Objects.hashCode(this.quantidadeMaxJogadasAleatorias);
            hash = 37 * hash + Objects.hashCode(this.variacaoMinima);
            hash = 37 * hash + Objects.hashCode(this.variacaoMaxima);
            hash = 37 * hash + Objects.hashCode(this.consequenciaVerbalPositiva);
            hash = 37 * hash + Objects.hashCode(this.consequenciaVerbalNegativa);
            return hash;
      }

      @Override
      public boolean equals(Object obj) {
            if (obj == null) {
                  return false;
            }
            if (getClass() != obj.getClass()) {
                  return false;
            }
            final PontuacaoIndividual other = (PontuacaoIndividual) obj;
            if (!Objects.equals(this.linhaSelecionada, other.linhaSelecionada)) {
                  return false;
            }
            if (!Objects.equals(this.corSelecionada, other.corSelecionada)) {
                  return false;
            }
            if (!Objects.equals(this.pontoAcerto, other.pontoAcerto)) {
                  return false;
            }
            if (!Objects.equals(this.sinalSelecionado, other.sinalSelecionado)) {
                  return false;
            }
            if (!Objects.equals(this.condicional, other.condicional)) {
                  return false;
            }
            if (!Objects.equals(this.ciclos, other.ciclos)) {
                  return false;
            }
            if (!Objects.equals(this.tipoAcrescimoPont, other.tipoAcrescimoPont)) {
                  return false;
            }
            if (!Objects.equals(this.incrementoPontoAcertoFixo, other.incrementoPontoAcertoFixo)) {
                  return false;
            }
            if (!Objects.equals(this.minIncrementoPontoAcertoRand, other.minIncrementoPontoAcertoRand)) {
                  return false;
            }
            if (!Objects.equals(this.maxIncrementoPontoAcertoRand, other.maxIncrementoPontoAcertoRand)) {
                  return false;
            }
            if (!Objects.equals(this.tipoPontuacaoAleatoria, other.tipoPontuacaoAleatoria)) {
                  return false;
            }
            if (!Objects.equals(this.quantidadeJogadasFixas, other.quantidadeJogadasFixas)) {
                  return false;
            }
            if (!Objects.equals(this.quantidadeMinJogadasAleatorias, other.quantidadeMinJogadasAleatorias)) {
                  return false;
            }
            if (!Objects.equals(this.quantidadeMaxJogadasAleatorias, other.quantidadeMaxJogadasAleatorias)) {
                  return false;
            }
            if (!Objects.equals(this.variacaoMinima, other.variacaoMinima)) {
                  return false;
            }
            if (!Objects.equals(this.variacaoMaxima, other.variacaoMaxima)) {
                  return false;
            }
            if (!Objects.equals(this.consequenciaVerbalPositiva, other.consequenciaVerbalPositiva)) {
                  return false;
            }
            return Objects.equals(this.consequenciaVerbalNegativa, other.consequenciaVerbalNegativa);
      }
      
      public String getExibePontoAcerto() {
            if (this.pontoAcerto == null) {
                  return "Aleatória";
            }
            return pontoAcerto.toString();
      }

      public Long getId() {
            return id;
      }

      public List<Condicao> getCondicoes() {
            return condicoes;
      }

      public void setCondicoes(List<Condicao> condicoes) {
            this.condicoes = condicoes;
      }

      public String getLinhaSelecionada() {
            return linhaSelecionada;
      }

      public void setLinhaSelecionada(String linhaSelecionada) {
            this.linhaSelecionada = linhaSelecionada;
      }

      public String getCorSelecionada() {
            return corSelecionada;
      }

      public void setCorSelecionada(String corSelecionada) {
            this.corSelecionada = corSelecionada;
      }

      public Integer getPontoAcerto() {
            return pontoAcerto;
      }

      public void setPontoAcerto(Integer pontoAcerto) {
            this.pontoAcerto = pontoAcerto;
      }

      public String getSinalSelecionado() {
            return sinalSelecionado;
      }

      public void setSinalSelecionado(String sinalSelecionado) {
            this.sinalSelecionado = sinalSelecionado;
      }

      public String getCondicional() {
            return condicional;
      }

      public void setCondicional(String condicional) {
            this.condicional = condicional;
      }

      public Integer getCiclos() {
            return ciclos;
      }

      public void setCiclos(Integer ciclos) {
            this.ciclos = ciclos;
      }

      public Integer getIncrementoPontoAcertoFixo() {
            return incrementoPontoAcertoFixo;
      }

      public void setIncrementoPontoAcertoFixo(Integer incrementoPontoAcertoFixo) {
            this.incrementoPontoAcertoFixo = incrementoPontoAcertoFixo;
      }

      public Integer getMinIncrementoPontoAcertoRand() {
            return minIncrementoPontoAcertoRand;
      }

      public void setMinIncrementoPontoAcertoRand(Integer minIncrementoPontoAcertoRand) {
            this.minIncrementoPontoAcertoRand = minIncrementoPontoAcertoRand;
      }

      public Integer getMaxIncrementoPontoAcertoRand() {
            return maxIncrementoPontoAcertoRand;
      }

      public void setMaxIncrementoPontoAcertoRand(Integer maxIncrementoPontoAcertoRand) {
            this.maxIncrementoPontoAcertoRand = maxIncrementoPontoAcertoRand;
      }

      public String getTipoAcrescimoPont() {
            return tipoAcrescimoPont;
      }

      public void setTipoAcrescimoPont(String tipoAcrescimoPont) {
            this.tipoAcrescimoPont = tipoAcrescimoPont;
      }

      public String getConsequenciaVerbalPositiva() {
            return consequenciaVerbalPositiva;
      }

      public void setConsequenciaVerbalPositiva(String consequenciaVerbalPositiva) {
            this.consequenciaVerbalPositiva = consequenciaVerbalPositiva;
      }

      public String getConsequenciaVerbalNegativa() {
            return consequenciaVerbalNegativa;
      }

      public void setConsequenciaVerbalNegativa(String consequenciaVerbalNegativa) {
            this.consequenciaVerbalNegativa = consequenciaVerbalNegativa;
      }

      public Integer getQuantidadeJogadasFixas() {
            return quantidadeJogadasFixas;
      }

      public void setQuantidadeJogadasFixas(Integer quantidadeJogadasFixas) {
            this.quantidadeJogadasFixas = quantidadeJogadasFixas;
      }

      public Integer getQuantidadeMinJogadasAleatorias() {
            return quantidadeMinJogadasAleatorias;
      }

      public void setQuantidadeMinJogadasAleatorias(Integer quantidadeMinJogadasAleatorias) {
            this.quantidadeMinJogadasAleatorias = quantidadeMinJogadasAleatorias;
      }

      public Integer getQuantidadeMaxJogadasAleatorias() {
            return quantidadeMaxJogadasAleatorias;
      }

      public void setQuantidadeMaxJogadasAleatorias(Integer quantidadeMaxJogadasAleatorias) {
            this.quantidadeMaxJogadasAleatorias = quantidadeMaxJogadasAleatorias;
      }

      public String getTipoPontuacaoAleatoria() {
            return tipoPontuacaoAleatoria;
      }

      public void setTipoPontuacaoAleatoria(String tipoPontuacaoAleatoria) {
            this.tipoPontuacaoAleatoria = tipoPontuacaoAleatoria;
      }

      public Integer getVariacaoMinima() {
            return variacaoMinima;
      }

      public void setVariacaoMinima(Integer variacaoMinima) {
            this.variacaoMinima = variacaoMinima;
      }

      public Integer getVariacaoMaxima() {
            return variacaoMaxima;
      }

      public void setVariacaoMaxima(Integer variacaoMaxima) {
            this.variacaoMaxima = variacaoMaxima;
      }

}
