/*~
M-Trix - Social Experiments Application.
Copyright (C) 2015-2016 The M-Trix authors.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
~*/
package br.ufpa.psi.comportamente.labgame.filter;

import br.ufpa.psi.comportamente.labgame.entidades.Jogador;
import br.ufpa.psi.comportamente.labgame.entidades.Pesquisador;
import br.ufpa.psi.comportamente.labgame.entidades.Usuario;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Weslley Tavares <weslleysammyr@gmail.com>
 */
@WebFilter(urlPatterns = "/login.jsf")
public class LoginPageFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (request.getRemoteUser() != null) {
            if (usuarioLogado() instanceof Pesquisador) {
                response.sendRedirect(request.getContextPath() + "/paginas/pesquisador/pesquisador.jsf");
            } else if (usuarioLogado() instanceof Jogador) {
                response.sendRedirect(request.getContextPath() + "/paginas/publico/jogador.jsf");
            } else {
                chain.doFilter(req, res);
            }
        } else {
            chain.doFilter(req, res); // User is not logged-in, so just continue request.
        }
    }

    private Usuario usuarioLogado() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public void init(FilterConfig fc) throws ServletException {
        
    }

    @Override
    public void destroy() {
        
    }
}

