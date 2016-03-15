/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.psi.comportamente.labgame.mbeans;

import br.ufpa.psi.comportamente.labgame.dao.PesquisadorDAO;
import br.ufpa.psi.comportamente.labgame.email.Email;
import br.ufpa.psi.comportamente.labgame.email.EmailMsg;
import br.ufpa.psi.comportamente.labgame.email.SendMail;
import br.ufpa.psi.comportamente.labgame.entidades.Pesquisador;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Weslley
 */
@ManagedBean
@RequestScoped
public class RecuperaSenhaMB implements Serializable {

      private static final long serialVersionUID = 1L;

      private String email;

      public RecuperaSenhaMB() {

      }

      public void recuperaSenha() {
            PesquisadorDAO dao = new PesquisadorDAO();
            Pesquisador pesq = new Pesquisador();

            try {
                  dao.beginTransaction();
                  pesq = dao.recuperaPorEmail(email);
                  dao.stopOperation(false);

                  //Retorna a senha pelo email do cara.
                  String senhaDecript = gerarSenhaAleatoria();

                  MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
                  byte messageDigest[] = algorithm.digest(senhaDecript.getBytes("UTF-8"));

                  StringBuilder hexString = new StringBuilder();
                  for (byte b : messageDigest) {
                        hexString.append(String.format("%02X", 0xFF & b));
                  }
                  String senhaCript = hexString.toString();

                  pesq.setPassword(senhaCript);

                  dao.beginTransaction();
                  dao.update(pesq);
                  dao.stopOperation(true);

                  enviarEmailAlteracaoSenha(pesq.getUsername(), senhaDecript);

                  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nova senha enviada! Verifique seu e-mail"));

            } catch (NullPointerException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
                  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Não existe um usuário cadastrado com esse e-mail!"));
            }
      }

      private String gerarSenhaAleatoria() {
            int qtdeMaximaCaracteres = 8;
            String[] caracteres = {"a", "1", "b", "2", "4", "5", "6", "7", "8",
                  "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
                  "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
                  "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I",
                  "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
                  "V", "W", "X", "Y", "Z"};

            StringBuilder senha = new StringBuilder();

            for (int i = 0; i < qtdeMaximaCaracteres; i++) {
                  int posicao = (int) (Math.random() * caracteres.length);
                  senha.append(caracteres[posicao]);
            }
            return senha.toString();
      }

      private void enviarEmailAlteracaoSenha(String login, String senha) {
            Email email = new Email();
            SendMail send = new SendMail();

            email.setCorpo(EmailMsg.getMensagemRecuperarSenha(login, senha));
            email.setDestinatarios(this.email);
            send.sendMail(email);
      }

      public String getEmail() {
            return email;
      }

      public void setEmail(String email) {
            this.email = email;
      }
}
