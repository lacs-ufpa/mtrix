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

import br.ufpa.psi.comportamente.labgame.seguranca.AutenticadorUsuario;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;

/**
 *
 * @author Weslley
 */
@ManagedBean
@RequestScoped
public class LoginMB {

      private String username = "";
      private String password = "";
      private boolean logado = false;

      public void doLogin() throws IOException, ServletException {
            NavegacaoMB nav = new NavegacaoMB();
            AutenticadorUsuario autenticar = new AutenticadorUsuario();
            
            System.out.println("Autenticação iniciada");

            logado = autenticar.authenticateLogin(username, password);

            if (logado) {
                  nav.irPaginaInicialUsuario();

            } else {
                  FacesMessage message = new FacesMessage();
                  message.setSeverity(FacesMessage.SEVERITY_ERROR);
                  message.setSummary("Usuário e/ou senha incorretos!");
                  FacesContext.getCurrentInstance().addMessage(null, message);
            }
      }

      public String getUsername() {
            return username;
      }

      public void setUsername(String username) {
            this.username = username;
      }

      public String getPassword() {
            return password;
      }

      public void setPassword(String password) {
            this.password = password;
      }

      public boolean isLogado() {
            return logado;
      }

      public void setLogado(boolean logado) {
            this.logado = logado;
      }

}
