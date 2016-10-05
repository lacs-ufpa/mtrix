/*~
M-Trix - Social Experiments Application.
Copyright (C) 2015-2016 The M-Trix authors.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
~*/
package br.ufpa.psi.comportamente.labgame.dao;

import br.ufpa.psi.comportamente.labgame.entidades.Jogada;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Weslley
 */
public class JogadaDAO extends BaseDAO<Jogada>{
   
    public List<Jogada> encontrarPorExperimento(Long idExp) {
        Query q = getEntityManager().createQuery("SELECT jog FROM " + Jogada.class.getName() + " as jog WHERE jog.jogador.experimento.id= ?1 order by jog.momento asc");
        q.setParameter(1, idExp);
        List<Jogada> jogadas = q.getResultList();
        return jogadas;
    }

}
