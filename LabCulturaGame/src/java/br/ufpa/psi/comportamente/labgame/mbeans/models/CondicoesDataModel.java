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

import br.ufpa.psi.comportamente.labgame.entidades.Condicao;
import java.util.List;
import java.util.Objects;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Weslley
 */
public class CondicoesDataModel extends ListDataModel<Condicao> implements SelectableDataModel<Condicao> {

    public CondicoesDataModel() {

    }

    public CondicoesDataModel(List<Condicao> data) {
        super(data);
    }

    @Override
    public Object getRowKey(Condicao condicao) {
        return condicao.getId();
    }

    @Override
    public Condicao getRowData(String rowKey) {
        List<Condicao> condicoes = (List<Condicao>) getWrappedData();

        for (Condicao condicao : condicoes) {
            if (Objects.equals(condicao.getId(), Long.valueOf(rowKey))) {
                return condicao;
            }
        }

        return null;
    }
}
