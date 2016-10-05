/*~
M-Trix - Social Experiments Application.
Copyright (C) 2015-2016 The M-Trix authors.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
~*/
package br.ufpa.psi.comportamente.labgame.seguranca;

import br.ufpa.psi.comportamente.labgame.dao.UsuarioDAO;
import br.ufpa.psi.comportamente.labgame.entidades.Jogador;
import br.ufpa.psi.comportamente.labgame.entidades.Pesquisador;
import br.ufpa.psi.comportamente.labgame.entidades.Usuario;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


/**

* @author Adailton Lima

Esta classe implementa o serviço que é chamado pelo sistema na hora de carregar um usuário na sessão e manter suas credenciais de autoridade na memoria.

*/
public class ServicoDetalhesUsuario implements UserDetailsService {

        @Override
	public UserDetails loadUserByUsername(String login)
			throws UsernameNotFoundException, DataAccessException {

		UsuarioDAO dao = new UsuarioDAO();
				
		Usuario user = dao.encontrarPorLogin(login);
		
		if(user==null) throw new UsernameNotFoundException(" Login de usuario "+login+" não encontrado");
		// 4. Return an authenticated token, containing user data and
		// authorities
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
                if (user instanceof Jogador) {
                    authorities.add(new GrantedAuthorityImpl("ROLE_JOGADOR")); // nao errar estas strings, pq senao nao funciona o sistema
                } else if (user instanceof Pesquisador) {
                    authorities.add(new GrantedAuthorityImpl("ROLE_PESQUISADOR"));
                }
		
		// aqui retorna o objeto já o usuário e sua lista de autoridade, no caso dessa aplicacao a lista so vai ter 1 elemente sempre.
		DetalhesUsuario detalhes = new DetalhesUsuario(user, authorities);

		return detalhes;
	}

    

}
