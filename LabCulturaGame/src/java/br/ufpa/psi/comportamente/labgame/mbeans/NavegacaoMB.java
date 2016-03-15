/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.psi.comportamente.labgame.mbeans;

import br.ufpa.psi.comportamente.labgame.entidades.Jogador;
import br.ufpa.psi.comportamente.labgame.entidades.Pesquisador;
import br.ufpa.psi.comportamente.labgame.entidades.Usuario;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Weslley
 */
@ManagedBean(name = "navegacaoMB")
@ViewScoped
public class NavegacaoMB implements Serializable {

      private static final long serialVersionUID = 1L;

      public NavegacaoMB() {

      }

      public void irPaginaInicialUsuario() {

            Usuario usuario = new Usuario();

            usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (usuario instanceof Jogador) {
                  voltarInicio();
            } else if (usuario instanceof Pesquisador) {
                  voltarInicioPesquisador();
            }
      }

      public void voltarInicio() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();

            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/paginas/publico/jogador.jsf");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public void irTelaManual() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();

            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/paginas/manual/manual.html");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public void irTelaListaCondicoes() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();

            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/paginas/pesquisador/listaCondicao.jsf");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public void irTelaCadastraPremio() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();

            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/paginas/pesquisador/cadastraPremio.jsf");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public void irPaginaRecuperaSenha() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();

            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/paginas/senha/recuperaSenha.jsf");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public void irPaginaAlteraSenhaPesquisador() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();

            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/paginas/pesquisador/alteraSenha.jsf");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public void irPaginaLogin() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();

            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/j_spring_security_logout");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public void voltarInicioPesquisador() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();

            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/paginas/pesquisador/pesquisador.jsf");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public String irPaginaLogoff() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();
            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/j_spring_security_logout");
            } catch (IOException e) {
                  e.printStackTrace();
            }
            return "";
      }

      public void irTelaCadastroExperimento() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();

            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/paginas/pesquisador/cadastraExperimentos.jsf");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public void irTelaCadastraJogadores() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();

            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/paginas/pesquisador/cadastraJogadores.jsf");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public void irTelaEditaPesquisador() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();

            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/paginas/pesquisador/alteraDados.jsf");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public void irTelaCadastroPesquisador() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();

            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/paginas/pesquisador/cadastraPesquisador.jsf");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public void irTelaGeraRelatorioJogadas() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();

            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/paginas/pesquisador/geraRelatorioJogadas.jsf");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public void irTelaGeraRelatorioPontuacaoPorJogadas() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();

            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/paginas/pesquisador/geraRelatorioPontuacaoJogadas.jsf");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public void irTelaListaJogadores() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();

            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/paginas/pesquisador/listaJogadores.jsf");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public void irTelaEditaExperimento() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();
            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/paginas/pesquisador/editaExperimento.jsf");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public void irTelaEditaParticipanteExperimento() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();
            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/paginas/pesquisador/editaParticipanteExperimento.jsf");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public void irTelaCadastraJogador() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();
            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/paginas/pesquisador/cadastraJogador.jsf");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public void irTelaCadastraPontIndiv() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();

            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/paginas/pesquisador/cadastraPontuacaoIndividual.jsf");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public void irTelaCadastraPontCult() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();

            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/paginas/pesquisador/cadastraPontuacaoCultural.jsf");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public void irTelaCadastraCondicao() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();

            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/paginas/pesquisador/cadastraCondicao.jsf");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public void irTelaExecutaExperimento() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();
            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/paginas/pesquisador/executaExperimento.jsf");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public void irTelaMatrixExperimento() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();
            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/paginas/publico/index.jsf");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public void irTelaFimExperimento() {
            ExternalContext externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();
            try {
                  externalContext.redirect(externalContext.getRequestContextPath()
                          + "/paginas/publico/finalExperimento.jsf");
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

}
