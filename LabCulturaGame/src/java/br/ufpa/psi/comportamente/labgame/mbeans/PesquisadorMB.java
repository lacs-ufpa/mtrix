/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.psi.comportamente.labgame.mbeans;

import br.ufpa.psi.comportamente.labgame.dao.ContatoDAO;
import br.ufpa.psi.comportamente.labgame.dao.JogadorDAO;
import br.ufpa.psi.comportamente.labgame.dao.PesquisadorDAO;
import br.ufpa.psi.comportamente.labgame.entidades.Contato;
import br.ufpa.psi.comportamente.labgame.entidades.Jogador;
import br.ufpa.psi.comportamente.labgame.entidades.Pesquisador;
import br.ufpa.psi.comportamente.labgame.mbeans.models.PesquisadoresLazyDataModel;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.LazyDataModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Weslley
 */
@ManagedBean
@SessionScoped
public class PesquisadorMB implements Serializable {

      private static final long serialVersionUID = 1L;

      private Pesquisador pesquisador; // <--- Variável que armazena o pesquisador na sessão.
      private Pesquisador pesquisadorManipulado; // Variável utilizada para realizar operações de CRUD de um determinado objeto.
      private Jogador jogador;
      private JogadorDAO jogadorDAO;
      private Contato contato;
      private String email;
      private String novaSenha;

      private LazyDataModel<Pesquisador> pesquisadoresDataModel;

      public PesquisadorMB() throws IOException {

            pesquisador = new Pesquisador();

            pesquisadorManipulado = new Pesquisador();

            jogador = new Jogador();

            contato = new Contato();

            SecurityContext context = SecurityContextHolder.getContext();

            if (context != null) {
                  if (context instanceof SecurityContext) {
                        Authentication authentication = context.getAuthentication();
                        if (authentication instanceof Authentication) {
                              pesquisador = (Pesquisador) authentication.getPrincipal();
                        }
                  }
            }
      }

      public void cadastraJogador() {

            jogadorDAO = new JogadorDAO();

            if (jogadorDAO.encontraPorLogin(jogador)) {

                  if (!"".equals(this.contato.getRua()) || !"".equals(this.contato.getBairro()) || !"".equals(this.contato.getCep())
                          || !"".equals(this.contato.getCidade()) || !"".equals(this.contato.getEmail1()) || !"".equals(this.contato.getTelefone1())) {
                        cadastraContato();
                        this.jogador.setContato(this.contato);
                  }

                  jogadorDAO.beginTransaction();
                  jogadorDAO.save(this.jogador);
                  jogadorDAO.stopOperation(true);

                  FacesContext.getCurrentInstance().addMessage("form4:nome", new FacesMessage("Jogador cadastrado com sucesso"));

            } else {
                  FacesContext.getCurrentInstance().addMessage("form4:nome", new FacesMessage("Já existe um jogador com esse username!"));
            }

            this.contato = new Contato();
            this.jogador = new Jogador();

      }

      public void cadastraPesquisador() {

            PesquisadorDAO dao = new PesquisadorDAO();

            if (dao.encontraPorLogin(pesquisadorManipulado)) {

                  if (!"".equals(this.contato.getRua()) || !"".equals(this.contato.getBairro()) || !"".equals(this.contato.getCep())
                          || !"".equals(this.contato.getCidade()) || !"".equals(this.contato.getEmail1()) || !"".equals(this.contato.getTelefone1())) {
                        cadastraContato();
                        this.pesquisadorManipulado.setContato(this.contato);
                  }

                  dao.beginTransaction();

                  // Converter a senha para criptografada.
                  this.pesquisadorManipulado.setPassword(this.pesquisadorManipulado.getPassword());

                  dao.save(this.pesquisadorManipulado);

                  FacesContext.getCurrentInstance().addMessage("formCadPesq:nome", new FacesMessage("Pesquisador cadastrado com sucesso"));

                  dao.stopOperation(true);

            } else {
                  FacesContext.getCurrentInstance().addMessage("formCadPesq:nome", new FacesMessage("Já existe um pesquisador com esse username!"));
            }

            this.contato = new Contato();
            this.pesquisadorManipulado = new Pesquisador();

      }

      public void atualizaSenhaPesquisador() {
            try {
                  pesquisador.setPassword(novaSenha);
                  PesquisadorDAO dao = new PesquisadorDAO();

                  dao.beginTransaction();
                  dao.update(pesquisador);
                  dao.stopOperation(true);

                  FacesMessage msg = new FacesMessage("Senha atualizada!");
                  FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (Exception e) {
                  FacesMessage msg = new FacesMessage("Não foi possível atualizar a sua senha. Verifique se foi digitada corretamente");
                  FacesContext.getCurrentInstance().addMessage(null, msg);
            }
      }

      public void atualizaPesquisador() {
            PesquisadorDAO dao = new PesquisadorDAO();

            dao.beginTransaction();
            dao.update(pesquisador);
            dao.stopOperation(true);

            FacesMessage msg = new FacesMessage("Dados atualizados!");
            FacesContext.getCurrentInstance().addMessage(null, msg);

      }

      private void cadastraContato() {
            ContatoDAO contatoDAO = new ContatoDAO();
            contatoDAO.beginTransaction();
            contatoDAO.save(this.contato);
            contatoDAO.stopOperation(true);
      }

      public void listaPesquisadoresModel() {
            pesquisadoresDataModel = new PesquisadoresLazyDataModel();
      }

      public Pesquisador getPesquisador() {
            return pesquisador;
      }

      public void setPesquisador(Pesquisador pesquisador) {
            this.pesquisador = pesquisador;
      }

      public Jogador getJogador() {
            return jogador;
      }

      public void setJogador(Jogador jogador) {
            this.jogador = jogador;
      }

      public Contato getContato() {
            return contato;
      }

      public void setContato(Contato contato) {
            this.contato = contato;
      }

      public Pesquisador getPesquisadorManipulado() {
            return pesquisadorManipulado;
      }

      public void setPesquisadorManipulado(Pesquisador pesquisadorManipulado) {
            this.pesquisadorManipulado = pesquisadorManipulado;
      }

      public String getEmail() {
            return email;
      }

      public void setEmail(String email) {
            this.email = email;
      }

      public String getNovaSenha() {
            return novaSenha;
      }

      public void setNovaSenha(String novaSenha) {
            this.novaSenha = novaSenha;
      }

      public LazyDataModel<Pesquisador> getPesquisadoresDataModel() {
            return pesquisadoresDataModel;
      }

      public void setPesquisadoresDataModel(LazyDataModel<Pesquisador> pesquisadoresDataModel) {
            this.pesquisadoresDataModel = pesquisadoresDataModel;
      }

}
