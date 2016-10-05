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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Weslley
 */
@Entity
@Table(name = "jogador")
public class Jogador extends Usuario implements Comparable<Object>, Serializable {

      private static final long serialVersionUID = 1L;

      @OneToMany(mappedBy = "jogador", targetEntity = Jogada.class)
      private List<Jogada> jogadas;

      @ManyToOne
      @JoinColumn(name = "experimento_id")
      private Experimento experimento;

      private int pontuacao;
      private int pontuacaoCult;

      private int ordem;

      private int geracao;
      private int rodadaInicio;
      private int rodadaFim;

      @Transient
      private boolean ativo = true;

      @Transient
      private Jogada ultimaJogada;

      @Transient
      private int pontuacaoExibidaRelatorio;

      @Transient
      private String ultimaPontuacao;

      public Jogador() {
            super();
            jogadas = new ArrayList();
      }

      public void incrementaPontuacaoRelatorio() {
            pontuacaoExibidaRelatorio += ultimaJogada.getPontuacaoIndividual();
      }

      @Override
      public int compareTo(Object o) {
            Jogador jogador = (Jogador) o;
            if (getOrdem() > jogador.getOrdem()) {
                  return 1;
            } else {
                  if (getOrdem() < jogador.getOrdem()) {
                        return -1;
                  } else {
                        return 0;
                  }
            }
      }

      @Override
      public boolean equals(Object obj) {
            if (obj == null) {
                  return false;
            }
            if (getClass() != obj.getClass()) {
                  return false;
            }
            final Jogador other = (Jogador) obj;
            return this.ordem == other.ordem;
      }
      
      @Override
      public int hashCode() {
            int hash = 7;
            hash = 43 * hash + Objects.hashCode(this.jogadas);
            hash = 43 * hash + Objects.hashCode(this.experimento);
            hash = 43 * hash + this.pontuacao;
            return hash;
      }

      public List<Jogada> getJogadas() {
            return jogadas;
      }

      public Experimento getExperimento() {
            return experimento;
      }

      public void setExperimento(Experimento experimento) {
            this.experimento = experimento;
      }

      public int getPontuacao() {
            return pontuacao;
      }

      public void setPontuacao(int pontuacao) {
            this.pontuacao = pontuacao;
      }

      public Jogada getUltimaJogada() {
            return ultimaJogada;
      }

      public void setUltimaJogada(Jogada ultimaJogada) {
            this.ultimaJogada = ultimaJogada;
      }

      public int getPontuacaoCult() {
            return pontuacaoCult;
      }

      public void setPontuacaoCult(int pontuacaoCult) {
            this.pontuacaoCult = pontuacaoCult;
      }

      public int getGeracao() {
            return geracao;
      }

      public void setGeracao(int geracao) {
            this.geracao = geracao;
      }

      public int getRodadaInicio() {
            return rodadaInicio;
      }

      public void setRodadaInicio(int rodadaInicio) {
            this.rodadaInicio = rodadaInicio;
      }

      public int getRodadaFim() {
            return rodadaFim;
      }

      public void setRodadaFim(int rodadaFim) {
            this.rodadaFim = rodadaFim;
      }

      public int getOrdem() {
            return ordem;
      }

      public void setOrdem(int ordem) {
            this.ordem = ordem;
      }

      public String getUltimaPontuacao() {
            return ultimaPontuacao;
      }

      public void setUltimaPontuacao(String ultimaPontuacao) {
            this.ultimaPontuacao = ultimaPontuacao;
      }

      public boolean isAtivo() {
            return ativo;
      }

      public void setAtivo(boolean ativo) {
            this.ativo = ativo;
      }

      public int getPontuacaoExibidaRelatorio() {
            return pontuacaoExibidaRelatorio;
      }

      public void setPontuacaoExibidaRelatorio(int pontuacaoExibidaRelatorio) {
            this.pontuacaoExibidaRelatorio = pontuacaoExibidaRelatorio;
      }

}
