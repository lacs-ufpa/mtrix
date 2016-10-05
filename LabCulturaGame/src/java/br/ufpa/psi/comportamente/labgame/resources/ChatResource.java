/*~
M-Trix - Social Experiments Application.
Copyright (C) 2015-2016 The M-Trix authors.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
~*/
package br.ufpa.psi.comportamente.labgame.resources;

import br.ufpa.psi.comportamente.labgame.entidades.Mensagem;
import br.ufpa.psi.comportamente.labgame.resources.message.MessageDecoder;
import br.ufpa.psi.comportamente.labgame.resources.message.MessageEncoder;
import javax.faces.application.FacesMessage;
import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.PushEndpoint;

/**
 *
 * @author Weslley Tavares <weslleysammyr@gmail.com>
 */
@PushEndpoint("/chat")
public class ChatResource {
      
      @OnMessage(decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
      public Mensagem onMessage(Mensagem message) {
            return message;
      }

}
