/*~
M-Trix - Social Experiments Application.
Copyright (C) 2015-2016 The M-Trix authors.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
~*/

package br.ufpa.psi.comportamente.labgame.converter;

import br.ufpa.psi.comportamente.labgame.dao.CondicaoDAO;
import br.ufpa.psi.comportamente.labgame.entidades.Condicao;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Weslley
 */
@FacesConverter(value = "PrimeFacesPickListConverterCondicao")
public class PrimeFacesPickListConverterCondicao implements Converter {
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        CondicaoDAO condicaoDAO = new CondicaoDAO();
        condicaoDAO.beginTransaction();
        Condicao condicao = null;
        
        if ((value != null) && (!value.equals(""))) {
            condicao = condicaoDAO.find(Condicao.class, Long.valueOf(value));
        }
        condicaoDAO.commitAndCloseTransaction();
        return condicao;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Long retorno = null;
        if (!(value == null)) {
            Condicao condicao = new Condicao();
            condicao = (Condicao) value;
            retorno = condicao.getId();
        }
        return retorno.toString();
    }
    
} 
