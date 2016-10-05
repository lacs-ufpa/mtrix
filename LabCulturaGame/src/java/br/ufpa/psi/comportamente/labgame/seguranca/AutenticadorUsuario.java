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
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Weslley
 */
public class AutenticadorUsuario implements AuthenticationProvider {

      @Override
      public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;

            String login = String.valueOf(auth.getPrincipal());
            String senhaLimpa = String.valueOf(auth.getCredentials());
            String senha = senhaLimpa;

            UsuarioDAO dao = new UsuarioDAO();
            dao.beginTransaction();
            Usuario user = dao.encontrarPorLogin(login);
            try {
                  if (user == null) {
                        dao.stopOperation(false);
                        throw new org.springframework.security.authentication.AuthenticationCredentialsNotFoundException(
                                "Login não encontrado");
                  }
                  MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
                  byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));

                  StringBuilder hexString = new StringBuilder();
                  for (byte b : messageDigest) {
                        hexString.append(String.format("%02X", 0xFF & b));
                  }
                  String senhaCript = hexString.toString();
                  if (!senhaCript.equals(user.getPassword())) {
                        dao.stopOperation(false);
                        throw new BadCredentialsException("Senha Errada");
                  }

                  List<GrantedAuthority> autoridade = new ArrayList<>();

                  if (user instanceof Jogador) {
                        autoridade.add(new GrantedAuthorityImpl("ROLE_JOGADOR"));
                  } else if (user instanceof Pesquisador) {
                        autoridade.add(new GrantedAuthorityImpl("ROLE_PESQUISADOR"));
                  }
                  dao.stopOperation(false);
                  UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, user.getPassword(), autoridade);
                  SecurityContextHolder.getContext().setAuthentication(token);
                  return token;
            } catch (AuthenticationCredentialsNotFoundException | NoSuchAlgorithmException | UnsupportedEncodingException | BadCredentialsException e) {
                  dao.stopOperation(false);
                  return null;
            }

      }

      public boolean authenticateLogin(String login, String senha) {

            UsuarioDAO dao = new UsuarioDAO();

            dao.beginTransaction();

            Usuario user = dao.encontrarPorLogin(login);

            try {
                  if (user == null) {
                        System.out.println("Não encontrou usuário");
                        dao.stopOperation(false);
                        throw new org.springframework.security.authentication.AuthenticationCredentialsNotFoundException(
                                "Login não encontrado");
                  }
                  
                  MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
                  byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));

                  StringBuilder hexString = new StringBuilder();
                  for (byte b : messageDigest) {
                        hexString.append(String.format("%02X", 0xFF & b));
                  }
                  String senhaCript = hexString.toString();

                  if (!senhaCript.equals(user.getPassword())) {
                        dao.stopOperation(false);
                        return false;
                  }

                  List<GrantedAuthority> autoridade = new ArrayList<>();

                  if (user instanceof Jogador) {
                        autoridade.add(new GrantedAuthorityImpl("ROLE_JOGADOR"));
                  } else if (user instanceof Pesquisador) {
                        autoridade.add(new GrantedAuthorityImpl("ROLE_PESQUISADOR"));
                  }
                  //TODO: adicionar depois quando for Administrador

                  dao.stopOperation(false);

                  UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, user.getPassword(), autoridade);

                  SecurityContextHolder.getContext().setAuthentication(token);

                  return true;
            } catch (AuthenticationCredentialsNotFoundException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
                  dao.stopOperation(false);
                  return false;
            }

      }

      @Override
      public boolean supports(Class<? extends Object> type) {
            return true;
      }
}
