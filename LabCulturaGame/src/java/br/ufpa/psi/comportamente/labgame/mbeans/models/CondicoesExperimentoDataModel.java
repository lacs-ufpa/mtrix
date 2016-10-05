/*~
M-Trix - Social Experiments Application.
Copyright (C) 2015-2016 The M-Trix authors.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
~*/

package br.ufpa.psi.comportamente.labgame.mbeans.models;

import br.ufpa.psi.comportamente.labgame.entidades.CondicaoExperimento;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Weslley
 */
public class CondicoesExperimentoDataModel extends ListDataModel<CondicaoExperimento> implements SelectableDataModel<CondicaoExperimento> {

    public CondicoesExperimentoDataModel() {
        
    }

    public CondicoesExperimentoDataModel(List<CondicaoExperimento> data) {
        super(data);
    }

    @Override
    public Object getRowKey(CondicaoExperimento condicaoExp) {
        return condicaoExp.getId();
    }

    @Override
    public CondicaoExperimento getRowData(String rowKey) {
        List<CondicaoExperimento> condicoesExp = (List<CondicaoExperimento>) getWrappedData();

        for (CondicaoExperimento condicao : condicoesExp) {
            if (condicao.getId() == Integer.parseInt(rowKey)) {
                return condicao;
            }
        }

        return null;
    }

}