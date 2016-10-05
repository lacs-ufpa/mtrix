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
package br.ufpa.psi.comportamente.labgame.dao;

import br.ufpa.psi.comportamente.labgame.entidades.Premio;
import javax.persistence.Query;

/**
 *
 * @author Weslley
 */
public class PremioDAO extends BaseDAO<Premio> {

    /**
     * Verifica se já existe um prêmio do mesmo tipo.
     * 
     * Caso não exista, ele retorna 'true'.
     * 
     * @param tipo
     * @return boolean 
     */
    public boolean verificaTipoIgual(String tipo) {
        Query query = getEntityManager().createQuery("SELECT p FROM " + Premio.class.getName() + " p WHERE p.tipo = :tipo");
        query.setParameter("tipo", tipo);

        return query.getResultList().isEmpty();
    }

}
