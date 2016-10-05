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

import br.ufpa.psi.comportamente.labgame.dao.ExperimentoDAO;
import br.ufpa.psi.comportamente.labgame.dao.JogadorDAO;
import br.ufpa.psi.comportamente.labgame.entidades.Experimento;
import br.ufpa.psi.comportamente.labgame.entidades.Jogador;
import br.ufpa.psi.comportamente.labgame.entidades.Pesquisador;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.TransferEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Weslley
 */
@ManagedBean
@SessionScoped
public class JogadorMB implements Serializable {

      private static final long serialVersionUID = 1L;

      private Jogador jogador;
      private Experimento experimento;

      private JogadorDAO jogadorDAO;

      private List<Jogador> jogadores;

      private boolean flagIniciarExp;

      private boolean exibeMsg;

      public JogadorMB() {

            jogador = null;
            experimento = new Experimento();

            SecurityContext context = SecurityContextHolder.getContext();

            if (context instanceof SecurityContext) {
                  Authentication authentication = context.getAuthentication();
                  if (authentication instanceof Authentication && authentication.getPrincipal() instanceof Jogador) {
                        jogador = (Jogador) authentication.getPrincipal();
                  }
            }

            if (jogador != null && jogador.getExperimento() != null) {
                  retornaExperimento();
                  verificaStatusExp();
            }

            this.jogadores = new ArrayList<>();

            jogadores = listaJogadores();
      }

      public JogadorMB(Pesquisador pesquisador) {
            this.jogadores = new ArrayList<>();
            jogadores = listaJogadores();
      }

      private void retornaExperimento() {
            ExperimentoDAO dao = new ExperimentoDAO();
            dao.beginTransaction();
            experimento = dao.find(jogador.getExperimento());
            dao.stopOperation(false);
      }

      private void verificaStatusExp() {
            if (jogador.getExperimento() != null) {
                  retornaExperimento();
                  if ((Objects.equals(experimento.getMensagemEspera(), "") || Objects.equals(experimento.getMensagemEspera(), null) || experimento.getTamanhoFilaJogadores() < jogador.getOrdem())) {
                        exibeMsg = false;
                  } else {
                        exibeMsg = true;
                        FacesContext.getCurrentInstance().addMessage("formJogador:growl", new FacesMessage(experimento.getMensagemEspera()));
                  }
                  if (experimento.getStatus().equalsIgnoreCase("executando")) {
                        setFlagIniciarExp(false);
                  } else {
                        setFlagIniciarExp(true);
                  }

            }
      }

      public Experimento pegaExperimento() {
            if (jogador.getExperimento() != null) {

                  ExperimentoDAO dao = new ExperimentoDAO();
                  dao.beginTransaction();
                  Experimento experimentoAux = dao.find(jogador.getExperimento());
                  dao.stopOperation(false);
                  return experimentoAux;
            } else {
                  return null;
            }
      }

      private List<Jogador> listaJogadores() {

            List<Jogador> listaJogadores;
            jogadorDAO = new JogadorDAO();

            if (!(jogador == null)) {
                  jogadorDAO.beginTransaction();
                  listaJogadores = jogadorDAO.findAll(jogador);
                  jogadorDAO.stopOperation(false);

                  return listaJogadores;
            } else {
                  jogadorDAO.beginTransaction();
                  listaJogadores = jogadorDAO.findAll(Jogador.class);
                  jogadorDAO.stopOperation(false);

                  return listaJogadores;
            }
      }

      public void onTransfer(TransferEvent event) {

            StringBuilder builder = new StringBuilder();
            for (Object item : event.getItems()) {
                  builder.append(((Jogador) item).getUsername()).append("<br />");
            }

            FacesMessage msg = new FacesMessage();
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            msg.setSummary("Jogadores Adicionados");
            msg.setDetail(builder.toString());

            FacesContext.getCurrentInstance().addMessage(null, msg);
      }

      public Jogador getJogador() {
            return jogador;
      }

      public void setJogador(Jogador jogador) {
            this.jogador = jogador;
      }

      public JogadorDAO getJogadorDAO() {
            return jogadorDAO;
      }

      public void setJogadorDAO(JogadorDAO jogadorDAO) {
            this.jogadorDAO = jogadorDAO;
      }

      public List<Jogador> getJogadores() {
            return jogadores;
      }

      public void setJogadores(List<Jogador> jogadores) {
            this.jogadores = jogadores;
      }

      public Experimento getExperimento() {
            return experimento;
      }

      public void setExperimento(Experimento experimento) {
            this.experimento = experimento;
      }

      public boolean isFlagIniciarExp() {
            return flagIniciarExp;
      }

      public void setFlagIniciarExp(boolean flagIniciarExp) {
            this.flagIniciarExp = flagIniciarExp;
      }

      public boolean isExibeMsg() {
            return exibeMsg;
      }

      public void setExibeMsg(boolean exibeMsg) {
            this.exibeMsg = exibeMsg;
      }

}
