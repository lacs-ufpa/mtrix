/*~
M-Trix - Social Experiments Application.
Copyright (C) 2015-2016 The M-Trix authors.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
~*/
package br.ufpa.psi.comportamente.labgame.mbeans;

import br.ufpa.psi.comportamente.labgame.dao.JogadorDAO;
import br.ufpa.psi.comportamente.labgame.entidades.Jogador;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Weslley Tavares <weslleysammyr@gmail.com>
 */
@ManagedBean
@ViewScoped
public class CadastraVariosJogadoresMB implements Serializable {

      private static final long serialVersionUID = 1L;

      private String codigoInicial;
      private String senha;
      private int quantidadeJogadores;

      public CadastraVariosJogadoresMB() {

      }

      public void cadastraJogadores() {
            JogadorDAO dao = new JogadorDAO();
            dao.beginTransaction();
            if (dao.verificaCodigoInicialJogadores(codigoInicial) == false) {
                  dao.stopOperation(false);
                  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Já existem participantes com esse código inicial cadastrados. Escolha outro.", null));
            } else {
                  for (int i = 0; i < quantidadeJogadores; i++) {
                        Jogador jogador = new Jogador();
                        jogador.setNome(codigoInicial + i);
                        jogador.setUsername(codigoInicial + i);
                        jogador.setPassword(senha);
                        dao.save(jogador);
                  }
                  dao.stopOperation(true);
                  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Participantes cadastrados.", null));
            }
      }

      public String getCodigoInicial() {
            return codigoInicial;
      }

      public void setCodigoInicial(String codigoInicial) {
            this.codigoInicial = codigoInicial;
      }

      public String getSenha() {
            return senha;
      }

      public void setSenha(String senha) {
            this.senha = senha;
      }

      public int getQuantidadeJogadores() {
            return quantidadeJogadores;
      }

      public void setQuantidadeJogadores(int quantidadeJogadores) {
            this.quantidadeJogadores = quantidadeJogadores;
      }

}
