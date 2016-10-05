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

package br.ufpa.psi.comportamente.labgame.mbeans;

import br.ufpa.psi.comportamente.labgame.entidades.Condicao;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Weslley
 */
public class PredicadoFiltroCondicoes implements Predicate<Condicao>{
    
    List<Long> ids = null;
    
    public PredicadoFiltroCondicoes(List<Long> listaIds){
        this.ids=listaIds;
    }
    
    @Override
    public boolean test(Condicao t) {
                 if(ids.contains(t.getId()))
                {    return false;
                }
                else return true;    
    
    }
          
}
