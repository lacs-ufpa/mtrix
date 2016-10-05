/*~
M-Trix - Social Experiments Application.
Copyright (C) 2015-2016 The M-Trix authors.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
~*/
package br.ufpa.psi.comportamente.labgame.mbeans.models;




import br.ufpa.psi.comportamente.labgame.Linha;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class LinhasDataModel extends ListDataModel<Linha> implements SelectableDataModel<Linha> {

    public LinhasDataModel() {
    }

    public LinhasDataModel(List<Linha> data) {
        super(data);
    }

    public Object getRowKey(Linha t) {
        return t.getPosicao();
    }
    
    public Linha getRowData(String rowkey) {
        List<Linha> linhas = (List<Linha>) getWrappedData();
        for (Linha l : linhas) {
            if ( (new Integer(l.getPosicao()) ).equals(rowkey)) {
                return l;
            }
        }
        return null;
    }

    
    

}
