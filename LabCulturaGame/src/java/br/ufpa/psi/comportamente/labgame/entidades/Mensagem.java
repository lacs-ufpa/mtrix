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
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Weslley
 */
@Entity
@Table(name = "mensagem")
public class Mensagem implements Serializable {

      private static final long serialVersionUID = 1L;

      @Id
      @GeneratedValue
      private Long id;

      @Temporal(TemporalType.TIMESTAMP)
      private Date momento;
      private Long emissor;
      private Long experimento;
      private String conteudoMensagem;
      private String cicloExperimento;
      private String estadoParticipante;
      
      @Transient
      private String emissorMsg;

      public Mensagem() {
            momento = new Date();
      }

      public Mensagem(String e, String c) {
            this.momento = new Date();
            this.emissorMsg = e;
            this.conteudoMensagem = c;
      }

      // GETTERS AND SETTERS
      public Long getId() {
            return id;
      }

      public void setId(Long id) {
            this.id = id;
      }

      public Date getMomento() {
            return momento;
      }

      public void setMomento(Date momento) {
            this.momento = momento;
      }

      public Long getEmissor() {
            return emissor;
      }

      public void setEmissor(Long emissor) {
            this.emissor = emissor;
      }

      public Long getExperimento() {
            return experimento;
      }

      public void setExperimento(Long experimento) {
            this.experimento = experimento;
      }

      public String getConteudoMensagem() {
            return conteudoMensagem;
      }

      public void setConteudoMensagem(String conteudoMensagem) {
            this.conteudoMensagem = conteudoMensagem;
      }

      public String getCicloExperimento() {
            return cicloExperimento;
      }

      public void setCicloExperimento(String cicloExperimento) {
            this.cicloExperimento = cicloExperimento;
      }

      public String getEstadoParticipante() {
            return estadoParticipante;
      }

      public void setEstadoParticipante(String estadoParticipante) {
            this.estadoParticipante = estadoParticipante;
      }

      public String getEmissorMsg() {
            return emissorMsg;
      }

      public void setEmissorMsg(String emissorMsg) {
            this.emissorMsg = emissorMsg;
      }

}
