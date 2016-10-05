/*~
M-Trix - Social Experiments Application.
Copyright (C) 2015-2016 The M-Trix authors.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
~*/
package br.ufpa.psi.comportamente.labgame.resources.message;

import br.ufpa.psi.comportamente.labgame.entidades.Mensagem;
import org.primefaces.json.JSONObject;
import org.primefaces.push.Encoder;

/**
 *
 * @author Weslley Tavares <weslleysammyr@gmail.com>
 */
public class MessageEncoder implements Encoder<Mensagem, String> {

      @Override
      public String encode(Mensagem message) {
            return new JSONObject(message).toString();
      }
}
