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

package br.ufpa.psi.comportamente.labgame.mbeans.models;

import br.ufpa.psi.comportamente.labgame.entidades.PontuacaoIndividual;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Weslley
 */
public class PontuacaoIndividualDataModel extends ListDataModel<PontuacaoIndividual> implements SelectableDataModel<PontuacaoIndividual> {

    public PontuacaoIndividualDataModel() {
        
    }

    public PontuacaoIndividualDataModel(List<PontuacaoIndividual> data) {
        super(data);
    }

    @Override
    public Object getRowKey(PontuacaoIndividual pontIndiv) {
        return pontIndiv.getId();
    }

    @Override
    public PontuacaoIndividual getRowData(String rowKey) {
        List<PontuacaoIndividual> listPontIndiv = (List<PontuacaoIndividual>) getWrappedData();

        for (PontuacaoIndividual pontIndiv : listPontIndiv) {
            if (pontIndiv.getId() == Integer.parseInt(rowKey)) {
                return pontIndiv;
            }
        }

        return null;
    }

}
