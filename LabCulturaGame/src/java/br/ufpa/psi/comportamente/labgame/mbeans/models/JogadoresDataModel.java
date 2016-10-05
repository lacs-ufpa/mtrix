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

import br.ufpa.psi.comportamente.labgame.entidades.Jogador;
import java.util.List;
import java.util.Objects;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Weslley
 */
public class JogadoresDataModel extends ListDataModel<Jogador> implements SelectableDataModel<Jogador> {

    public JogadoresDataModel() {

    }

    public JogadoresDataModel(List<Jogador> data) {
        super(data);
    }

    @Override
    public Object getRowKey(Jogador jogador) {
        return jogador.getId();
    }

    @Override
    public Jogador getRowData(String rowKey) {
        List<Jogador> jogadores = (List<Jogador>) getWrappedData();

        for (Jogador jogador : jogadores) {
            if (Objects.equals(jogador.getId(), Long.valueOf(rowKey))) {
                return jogador;
            }
        }

        return null;
    }

}
