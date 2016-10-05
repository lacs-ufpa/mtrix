/*~
M-Trix - Social Experiments Application.
Copyright (C) 2015-2016 The M-Trix authors.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
~*/
package br.ufpa.psi.comportamente.labgame.resources.message;

import br.ufpa.psi.comportamente.labgame.entidades.Mensagem;
import org.primefaces.push.Decoder;

/**
 *
 * @author Weslley Tavares <weslleysammyr@gmail.com>
 */
public class MessageDecoder implements Decoder<String, Mensagem> {

      @Override
      public Mensagem decode(String s) {
            String[] userAndMessage = s.split(": ");
            return new Mensagem(userAndMessage[0], userAndMessage[1]);
      }
}
