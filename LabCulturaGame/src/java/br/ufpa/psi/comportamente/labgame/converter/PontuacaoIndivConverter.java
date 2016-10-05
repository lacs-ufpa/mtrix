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
package br.ufpa.psi.comportamente.labgame.converter;

import br.ufpa.psi.comportamente.labgame.dao.PontuacaoIndividualDAO;
import br.ufpa.psi.comportamente.labgame.entidades.PontuacaoIndividual;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Weslley
 */
@FacesConverter(value="pontIndividual")
public class PontuacaoIndivConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        
        PontuacaoIndividualDAO pontIndiDAO = new PontuacaoIndividualDAO();
        pontIndiDAO.beginTransaction();
        PontuacaoIndividual pontInd = null;
        if ((value != null) && (!value.trim().equals(""))) {
            pontInd = pontIndiDAO.find(Long.valueOf(value));
        }
        pontIndiDAO.commitAndCloseTransaction();
        
        
        return pontInd;
    }

    @Override
    @SuppressWarnings("empty-statement")
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        String retorno = null;
        
        if (!(value == null)) {
            if(value instanceof PontuacaoIndividual) {
                
            PontuacaoIndividual pontInd = (PontuacaoIndividual) value;
            if(pontInd.getId()!=null){
                retorno = pontInd.getId().toString();
            }else retorno = "";
            
            }  else { return (String) value;}
        }
        
        return retorno;
    }

}
