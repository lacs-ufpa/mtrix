/*~
M-Trix - Social Experiments Application.
Copyright (C) 2015-2016 The M-Trix authors.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
~*/
package br.ufpa.psi.comportamente.labgame.mbeans;

import br.ufpa.psi.comportamente.labgame.dao.JogadorDAO;
import br.ufpa.psi.comportamente.labgame.entidades.Jogador;
import br.ufpa.psi.comportamente.labgame.mbeans.models.JogadoresTotaisLazyDataModel;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Weslley Tavares <weslleysammyr@gmail.com>
 */
@ManagedBean
@ViewScoped
public class ListaJogadoresTotaisMB implements Serializable {

      private static final long serialVersionUID = 1L;
      
      private LazyDataModel<Jogador> jogadoresDataModel;
      private Jogador jogador;
      
      public ListaJogadoresTotaisMB() {
            jogadoresDataModel = new JogadoresTotaisLazyDataModel();
            jogador = new Jogador();
      }
      
      public void atualizaSenhaJogador() {
            JogadorDAO dao = new JogadorDAO();
            dao.beginTransaction();
            dao.update(jogador);
            dao.stopOperation(true);
            jogador = new Jogador();
            limpaCampoSenha();
      }
      
      public void onRowEdit(RowEditEvent event) {
            JogadorDAO jogadorDAO = new JogadorDAO();
            jogadorDAO.beginTransaction();
            Jogador jogd = (Jogador) event.getObject();
            jogadorDAO.update(jogd);
            jogadorDAO.stopOperation(true);

            FacesMessage msg = new FacesMessage("Jogador " + ((Jogador) event.getObject()).getNome() + " editado!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
      }
      
      public void limpaCampoSenha() {
            RequestContext.getCurrentInstance().reset("altera-senha-jog:input-senha");
      }

      public LazyDataModel<Jogador> getJogadoresDataModel() {
            return jogadoresDataModel;
      }

      public void setJogadoresDataModel(LazyDataModel<Jogador> jogadoresDataModel) {
            this.jogadoresDataModel = jogadoresDataModel;
      }

      public Jogador getJogador() {
            return jogador;
      }

      public void setJogador(Jogador jogador) {
            this.jogador = jogador;
      }

}
