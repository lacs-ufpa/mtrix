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
package br.ufpa.psi.comportamente.labgame.entidades;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Weslley
 */
@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Usuario implements Serializable {

      private static final long serialVersionUID = 1L;

      @Id
      @GeneratedValue(strategy = GenerationType.TABLE)
      private Long id;

      private String username;
      private String password;

      @OneToOne
      @JoinColumn(name = "contato_id")
      private Contato contato;

      private String nome;

      public Usuario() {

      }
      
      private String criptografaSenha(String senha) {
            try {
                  MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
                  byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));

                  StringBuilder hexString = new StringBuilder();
                  for (byte b : messageDigest) {
                        hexString.append(String.format("%02X", 0xFF & b));
                  }
                  String senhaCript = hexString.toString();
                  return senhaCript;
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                  return null;
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
            this.password = criptografaSenha(password);
      }

      public Long getId() {
            return id;
      }

      public String getNome() {
            return nome;
      }

      public void setNome(String nome) {
            this.nome = nome;
      }

      public Contato getContato() {
            return contato;
      }

      public void setContato(Contato contato) {
            this.contato = contato;
      }

}
