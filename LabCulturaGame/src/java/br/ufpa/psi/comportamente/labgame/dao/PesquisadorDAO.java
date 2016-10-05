/*~
M-Trix - Social Experiments Application.
Copyright (C) 2015-2016 The M-Trix authors.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
~*/
package br.ufpa.psi.comportamente.labgame.dao;

import br.ufpa.psi.comportamente.labgame.entidades.Contato;
import br.ufpa.psi.comportamente.labgame.entidades.Pesquisador;
import br.ufpa.psi.comportamente.labgame.entidades.Usuario;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Weslley
 */
public class PesquisadorDAO extends BaseDAO<Pesquisador> {

      public Pesquisador find(Pesquisador pesquisador) {
            Query query = getEntityManager().createQuery(
                    "SELECT usuario FROM " + Usuario.class.getCanonicalName() + " usuario WHERE usuario.username = :login AND usuario.password = :senha");
            query.setParameter("login", pesquisador.getUsername());
            query.setParameter("senha", pesquisador.getPassword());

            return (Pesquisador) query.getSingleResult();
      }

      public Usuario encontrarPorLogin(String login) {
            Query query = getEntityManager().createQuery(
                    "SELECT usuario FROM " + Usuario.class.getName() + " as usuario WHERE  usuario.username= ?1 ");
            query.setParameter(1, login);
            List<Usuario> usuarios = query.getResultList();
            return usuarios.size() > 0 ? usuarios.get(0) : null;
      }

      public boolean encontraPorLogin(Pesquisador pesquisador) {
            Query query = getEntityManager().createQuery(
                    "SELECT pesquisador FROM " + Pesquisador.class.getName() + " pesquisador WHERE pesquisador.username = :login");
            query.setParameter("login", pesquisador.getUsername());
            return query.getResultList().isEmpty();
      }

      public Pesquisador recuperaPorLogin(Pesquisador pesquisador) {
            Query query = getEntityManager().createQuery(
                    "SELECT pesquisador FROM " + Pesquisador.class.getName() + " pesquisador WHERE pesquisador.username = :login");
            query.setParameter("login", pesquisador.getUsername());
            return (Pesquisador) query.getSingleResult();
      }

      public Pesquisador recuperaPorEmail(String email) {
            Query query = getEntityManager().createQuery(
                    "SELECT pesquisador FROM " + Pesquisador.class.getName() + " pesquisador, " + Contato.class.getName() + " contato "
                    + "WHERE pesquisador.contato = contato.id AND contato.email1 = :email");
            query.setParameter("email", email);

            if (query.getResultList().size() == 1) {
                  return (Pesquisador) query.getSingleResult();
            } else {
                  return null;
            }
      }

      public int contaPesquisadoresTotal() {
            Query query = getEntityManager().createQuery(
                    "SELECT pesquisador FROM " + Pesquisador.class.getName() + " pesquisador");
            return query.getResultList().size();
      }

}
