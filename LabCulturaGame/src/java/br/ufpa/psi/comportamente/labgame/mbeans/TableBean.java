package br.ufpa.psi.comportamente.labgame.mbeans;

import br.ufpa.psi.comportamente.labgame.Linha;
import br.ufpa.psi.comportamente.labgame.dao.ExperimentoDAO;
import br.ufpa.psi.comportamente.labgame.dao.JogadorDAO;
import br.ufpa.psi.comportamente.labgame.entidades.Experimento;
import br.ufpa.psi.comportamente.labgame.entidades.Jogada;
import br.ufpa.psi.comportamente.labgame.entidades.Jogador;
import br.ufpa.psi.comportamente.labgame.entidades.Pesquisador;
import br.ufpa.psi.comportamente.labgame.mbeans.interfaces.ExperimentoObservable;
import br.ufpa.psi.comportamente.labgame.mbeans.interfaces.TableObserver;
import br.ufpa.psi.comportamente.labgame.mbeans.models.LinhasDataModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ManagedBean(name = "tableBean")
@SessionScoped
public class TableBean implements TableObserver, Serializable {

      private static final long serialVersionUID = 1L;

      private ExperimentoObservable obs;

      private List<Linha> linhas = new ArrayList<>();
      private Linha selectedLinha;
      private LinhasDataModel model;
      private Jogada jogada;
      private JogadorDAO jogadorDAO;
      private Jogador jogador;
      private Long experimentoID;
      private int pontuacaoCultural;

      private Experimento experimentoEmExecucao;

      //Variáveis para exibir no placar de resultados
      private Jogador jogadorUltimaJogada;
      private String corUltimaJogada;
      private Integer ultimoResultado;
      private int geracaoAtual = 1;

      private int linhaSelecionada;
      private String colunaSelecionada;

      private List<Jogador> jogadoresExperimento;

      private Pesquisador pesquisador;

      //propriedada para controlar a habilitação ou não da tela pra jogada
      private boolean jogadaLiberada = false;
      private boolean liberaDialogUltimaJogada = false;
      private boolean experimentoConcluido = false;
      private boolean jogadorRemovido = false;

      public TableBean() {
            super();

            jogadoresExperimento = new ArrayList<>();
            experimentoEmExecucao = new Experimento();

      }

      /**
       * Método usado pelo Jogador para que o mesmo tenha acesso a matriz do
       * experimento que estiver sendo executado.
       *
       */
      public void carregaConstrutorJogador() {

            this.jogador = new Jogador();

            SecurityContext context = SecurityContextHolder.getContext();

            if (context instanceof SecurityContext) {
                  Authentication authentication = context.getAuthentication();
                  if (authentication instanceof Authentication && authentication.getPrincipal() instanceof Jogador) {

                        jogador = (Jogador) authentication.getPrincipal();
                  }
            }

        /// buscar no r egistro do M-Mtrix, a instância do controlador do Experimento específico.
            /// 1º - Paramentro ID do Experimento recebe o ponteiro pra instancia do controlador do experimento
            /// 2º - Enviar mensagem pro controlador do experimento avisando que o jogador ta online
            /// 3º - O controlador guarda o ponteiro pro TableBean, pra ter como notifica-lo quando tiver iniciado o jogo
            experimentoID = jogador.getExperimento().getId();

            ExperimentoDAO dao = new ExperimentoDAO();
            dao.beginTransaction();
            experimentoEmExecucao = dao.find(Experimento.class, experimentoID);
            dao.stopOperation(false);

            obs = RegistroDeControladores.getControladorExperimentoInstance(experimentoID);
            if (obs == null) {
           //lanca algum erro pois o experimento nao esta disponivel

                  //aqui deve encerrar a criacao da tela e retornar o jogador ao inicio.
            } else {
                  // se registra para ficar observando eventos pro jogador
                  obs.registraObservador(this, jogador);

            }

            ///Pega todos os jogadores que estão cadastrados no experimento em execução.
            if (!(jogador == null)) {
                  jogadoresExperimento = listarJogadoresExperimento();
            }

            //cria objetos linhas para representar a tabela e os pontos que possuem ou nao itens
            for (int i = 1; i < 11; i++) {
                  Linha l;
                  if (i % 2 == 0) {
                        l = new Linha(i, "", false, true, false, true, false, true, false, true, false, true);
                  } else {
                        l = new Linha(i, "", true, false, true, false, true, false, true, false, true, false);
                  }
                  linhas.add(l);
            }

            //define estaticamente a escala de cores utilizadas atualmente nos experimentos
            linhas.get(0).setCor(Constantes.AMARELO);
            linhas.get(1).setCor(Constantes.VERDE);
            linhas.get(2).setCor(Constantes.VERMELHO);
            linhas.get(3).setCor(Constantes.AZUL);
            linhas.get(4).setCor(Constantes.ROXO);
            linhas.get(5).setCor(Constantes.VERMELHO);
            linhas.get(6).setCor(Constantes.VERDE);
            linhas.get(7).setCor(Constantes.AMARELO);
            linhas.get(8).setCor(Constantes.AZUL);
            linhas.get(9).setCor(Constantes.ROXO);

            model = new LinhasDataModel(linhas);

      }

      /**
       * Método usado pelo Pesquisador para que o mesmo tenha acesso a matriz do
       * experimento que estiver sendo executado.
       *
       * @param idExp
       */
      public void carregaConstrutorPesquisador(Long idExp) {

            this.pesquisador = new Pesquisador();

            SecurityContext context = SecurityContextHolder.getContext();

            if (context instanceof SecurityContext) {
                  Authentication authentication = context.getAuthentication();
                  if (authentication instanceof Authentication && authentication.getPrincipal() instanceof Pesquisador) {

                        pesquisador = (Pesquisador) authentication.getPrincipal();
                  }
            }

            ExperimentoDAO dao = new ExperimentoDAO();
            dao.beginTransaction();
            experimentoEmExecucao = dao.find(Experimento.class, idExp);
            dao.stopOperation(false);

            obs = RegistroDeControladores.getControladorExperimentoInstance(idExp);
            if (obs == null) {
           //lanca algum erro pois o experimento nao esta disponivel

                  //aqui deve encerrar a criacao da tela e retornar o jogador ao inicio.
            } else {
                  // se registra para ficar observando eventos pro jogador
                  obs.registraObservador(this, pesquisador);

            }

            jogadoresExperimento = listarJogadoresExperimento();

            //cria objetos linhas para representar a tabela e os pontos que possuem ou nao itens
            for (int i = 1; i < 11; i++) {
                  Linha l;
                  if (i % 2 == 0) {
                        l = new Linha(i, "", false, true, false, true, false, true, false, true, false, true);
                  } else {
                        l = new Linha(i, "", true, false, true, false, true, false, true, false, true, false);
                  }
                  linhas.add(l);
            }

            //define estaticamente a escala de cores utilizadas atualmente nos experimentos
            linhas.get(0).setCor(Constantes.AMARELO);
            linhas.get(1).setCor(Constantes.VERDE);
            linhas.get(2).setCor(Constantes.VERMELHO);
            linhas.get(3).setCor(Constantes.AZUL);
            linhas.get(4).setCor(Constantes.ROXO);
            linhas.get(5).setCor(Constantes.VERMELHO);
            linhas.get(6).setCor(Constantes.VERDE);
            linhas.get(7).setCor(Constantes.AMARELO);
            linhas.get(8).setCor(Constantes.AZUL);
            linhas.get(9).setCor(Constantes.ROXO);

            model = new LinhasDataModel(linhas);
      }

      private List<Jogador> listarJogadoresExperimento() {
            List<Jogador> jogadores;
            jogadorDAO = new JogadorDAO();
            jogadorDAO.beginTransaction();
            if (jogador == null) {
                  jogadores = jogadorDAO.encontraPorExperimento(experimentoEmExecucao);
            } else {
                  jogadores = jogadorDAO.encontraPorExperimentoExcluindoLogado(jogador);
            }
            jogadorDAO.stopOperation(false);
            return jogadores;
      }

      public void setSelectedCell(int linha, String coluna) {
            //+1 pq as Celulas comecam a contar do zero na tabela, so que pro usuario o valor exibido é 1
            this.linhaSelecionada = linha + 1;
            this.colunaSelecionada = coluna;
      }

      public void setSelectedCell(int linha) {
            //+1 pq as Celulas comecam a contar do zero na tabela, so que pro usuario o valor exibido é 1
            this.linhaSelecionada = linha + 1;
            Random rd = new Random();
            char ch = (char) (65 + rd.nextInt(10));
            String str = String.valueOf(ch);
            this.colunaSelecionada = str;
      }

      public void saveCurrentRound() {

            ////AQUI VAI NOTIFICAR O CONTROLADOR DA JOGADA QUE ACONTECEU, PQ ELE TA LA ESPERANDO ISSO PRA COMECAR A PROXIMA JOGADA
            this.obs.registrarJogada(this.jogador, linhaSelecionada, colunaSelecionada);

      }

      @Override
      public void bloqueiaTela(String tipoAlertaSonoroIndiv) {

            //muda flag para nao liberar mais jogadas pra esta tela
            this.jogadaLiberada = false;

      }

      @Override
      public void bloqueiaTela() {

            //muda flag para nao liberar mais jogadas pra esta tela
            this.jogadaLiberada = false;

      }

      @Override
      public void aguardandoProximoJogador(Jogador jogador) {
            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish("/informaEsperaNovoJogador", new FacesMessage(FacesMessage.SEVERITY_INFO, "Aguardando o próximo participante", null));
      }

      @Override
      public void coletaEntradaJogada() {
            this.jogadaLiberada = true;

            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish("/atualizaTable", new FacesMessage("Atualizando..."));
            eventBus.publish("/atualizaPlacar", new FacesMessage("Atualizando..."));
            if (liberaDialogUltimaJogada) {
                  eventBus.publish("/atualizaPontuacao", new FacesMessage("Jogada Realizada =D", "O próximo a jogar é o: " + jogador.getNome()));
            }

      }

      @Override
      public void coletaEntradaJogada(boolean alertaPontuacao, Jogador jogdr) {
            this.jogadaLiberada = true;

            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish("/atualizaTable", new FacesMessage("Atualizando..."));
            eventBus.publish("/atualizaPlacar", new FacesMessage("Atualizando..."));
            if (liberaDialogUltimaJogada) {

                  if (this.ultimoResultado == null) {
                        eventBus.publish("/atualizaPontuacaoMensagemDefault", new FacesMessage("Jogada Realizada"));
                  } else if (alertaPontuacao == true && this.ultimoResultado > 0) {
                        eventBus.publish("/atualizaPontuacaoMensagemDefault", new FacesMessage("Participante: " + jogadorUltimaJogada.getNome() + " ganhou " + this.ultimoResultado + " pontos"));
                  } else if (alertaPontuacao == true && this.ultimoResultado < 0) {
                        eventBus.publish("/atualizaPontuacaoMensagemDefault", new FacesMessage("Participante: " + jogadorUltimaJogada.getNome() + " perdeu " + this.ultimoResultado + " pontos"));
                  } else if (alertaPontuacao == true && this.ultimoResultado == 0) {
                        eventBus.publish("/atualizaPontuacaoMensagemDefault", new FacesMessage("Participante: " + jogadorUltimaJogada.getNome() + " manteve os pontos"));
                  } else {
                        eventBus.publish("/atualizaPontuacaoMensagemDefault", new FacesMessage("Jogada Realizada"));
                  }
            }

      }

      @Override
      public void exibeMensagemPontuacaoCultural(String mensagem) {
            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish("/atualizaGrowlPontCult", new FacesMessage((mensagem), " "));
      }

      @Override
      public void coletaEntradaJogada(boolean alertaPontuacao, Jogador jogdr, String mensagem) {
            this.jogadaLiberada = true;

            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish("/atualizaTable", new FacesMessage("Atualizando..."));
            eventBus.publish("/atualizaPlacar", new FacesMessage("Atualizando..."));
            if (liberaDialogUltimaJogada) {
                  if (!(mensagem == null) || !(mensagem.equalsIgnoreCase(""))) {
                        if (this.ultimoResultado < 0) {
                              eventBus.publish("/atualizaPontuacao", new FacesMessage((mensagem), "Participante " + jogadorUltimaJogada.getUsername() + " perdeu " + this.ultimoResultado + " pontos."));
                        } else if (this.ultimoResultado > 0) {
                              eventBus.publish("/atualizaPontuacao", new FacesMessage((mensagem), "Participante " + jogadorUltimaJogada.getUsername() + " ganhou " + this.ultimoResultado + " pontos."));
                        } else {
                              eventBus.publish("/atualizaPontuacao", new FacesMessage((mensagem), "Participante " + jogadorUltimaJogada.getUsername() + " manteve seus pontos."));
                        }
                  } else if (this.ultimoResultado == null && alertaPontuacao == true) {
                        eventBus.publish("/atualizaPontuacaoMensagemDefault", new FacesMessage("Jogada Realizada"));
                  } else if (alertaPontuacao == true && this.ultimoResultado > 0) {
                        eventBus.publish("/atualizaPontuacaoMensagemDefault", new FacesMessage("Participante: " + jogadorUltimaJogada.getUsername() + " ganhou " + this.ultimoResultado + " pontos."));
                  } else if (alertaPontuacao == true && this.ultimoResultado < 0) {
                        eventBus.publish("/atualizaPontuacaoMensagemDefault", new FacesMessage("Participante: " + jogadorUltimaJogada.getUsername() + " perdeu " + this.ultimoResultado + " pontos."));
                  } else if (alertaPontuacao == true && this.ultimoResultado == 0) {
                        eventBus.publish("/atualizaPontuacaoMensagemDefault", new FacesMessage("Participante: " + jogadorUltimaJogada.getUsername() + " manteve seus pontos."));
                  } else {
                        eventBus.publish("/atualizaPontuacaoMensagemDefault", new FacesMessage("Jogada Realizada"));
                  }
            }
      }

      @Override
      public void atualizaPontuacao(List<Jogador> listaJogadoresPontuacoes, int pontuacaoCult, int geracao, String tipoAlertaPontCult) {

            liberaDialogUltimaJogada = true;

            geracaoAtual = geracao;

            pontuacaoCultural = pontuacaoCult;
            List<Jogador> listaJogadores = new ArrayList<>();

            if (jogador != null) {
                  for (Jogador jog : listaJogadoresPontuacoes) {
                        if (Objects.equals(jog.getId(), jogador.getId())) {
                    //JogadorDAO dao = new JogadorDAO();
                              //dao.beginTransaction();
                              jogador = jog;
                              //dao.closeTransaction();
                              break;
                        }
                  }
            }

            listaJogadores.addAll(listaJogadoresPontuacoes);

            listaJogadores.remove(jogador);

            jogadoresExperimento.clear();
            jogadoresExperimento.addAll(listaJogadores);

            /*
             PushContext pushContext = PushContextFactory.getDefault().getPushContext();
             pushContext.push("/alertaSonoroCult", new FacesMessage(tipoAlertaPontCult));
             */
      }

      /**
       * Método responsável por informar ao jogador em questão, que ele está
       * fora da execução do experimento.
       *
       * Atualiza o boolean "ativo" do jogador para false.
       *
       */
      @Override
      public void informaSaidaDoExperimento() {
            jogadorRemovido = true;
            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish("/" + this.jogador.getUsername(), new FacesMessage("Jogador removido!"));
      }

      @Override
      public void emiteAlertaMucancaGeracao() {
        //PushContext pushContext = PushContextFactory.getDefault().getPushContext();
            //pushContext.push("/alertaMudancaGeracao", "Emite Alerta!");
      }

      @Override
      public void atualizaPontuacaoCulturalInicial(int pontuacao) {
            pontuacaoCultural = pontuacao;
      }

      @Override
      public void atualizaPontuacao(List<Jogador> listaJogadoresPontuacoes, int pontuacaoCult, int geracao) {

            liberaDialogUltimaJogada = true;

            geracaoAtual = geracao;

            pontuacaoCultural = pontuacaoCult;
            List<Jogador> listaJogadores = new ArrayList<>();

            if (jogador != null) {
                  for (Jogador jog : listaJogadoresPontuacoes) {
                        if (Objects.equals(jog.getId(), jogador.getId())) {
                              jogador = jog;
                              break;
                        }
                  }
            }

            listaJogadores.addAll(listaJogadoresPontuacoes);

            listaJogadores.remove(jogador);

            jogadoresExperimento.clear();
            jogadoresExperimento.addAll(listaJogadores);
      }

      @Override
      public void exibeUltimaJogada(Jogador jogador, String cor, Jogada jogada, Integer pontuacao) {
            jogadorUltimaJogada = new Jogador();
            this.jogada = new Jogada();

            jogadorUltimaJogada = jogador;
            this.jogada = jogada;
            corUltimaJogada = cor;
            ultimoResultado = pontuacao;
      }

      @Override
      public void exibeUltimaJogada(Jogador jogador, String cor, Jogada jogada) {
            jogadorUltimaJogada = new Jogador();
            this.jogada = new Jogada();

            jogadorUltimaJogada = jogador;
            this.jogada = jogada;
            corUltimaJogada = cor;
            ultimoResultado = 0;
      }

      @Override
      public void finalizarJogadas() {

            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish("/informaFimExperimento", new FacesMessage("Experimento concluído"));
            experimentoConcluido = true;
      }

      public String returnBackGroundColor(Jogador jogador) {
            return "background-color: " + retornaNomeCorJogada(jogador);
      }

      private String retornaNomeCorJogada(Jogador jogador) {
            String cor;
            if (jogador.getUltimaJogada() == null) {
                  cor = "white";
            } else {
                  cor = jogador.getUltimaJogada().getCorSelecionada();
                  switch (cor) {
                        case "vermelho":
                              cor = "red";
                              break;
                        case "verde":
                              cor = "green";
                              break;
                        case "azul":
                              cor = "blue";
                              break;
                        case "roxo":
                              cor = "purple";
                              break;
                        case "amarelo":
                              cor = "yellow";
                              break;
                  }
            }
            return cor;
      }

      // Método que retorna um boolean pra informar se deve habilitar ou desabilitar o chat
      public boolean verificaStatusChat() {
            ExperimentoDAO dao = new ExperimentoDAO();
            dao.beginTransaction();
            Experimento experimento = dao.find(Experimento.class, experimentoEmExecucao.getId());
            dao.stopOperation(false);

            return experimento.getChat().equals(Constantes.DESABILITADO);
      }

      @Override
      public void atualizaExperimentoEmExecucao(Experimento experimento) {
            experimentoEmExecucao = experimento;
      }

      public ExperimentoObservable getObs() {
            return obs;
      }

      public void setObs(ExperimentoObservable obs) {
            this.obs = obs;
      }

      public Jogador getJogador() {
            return jogador;
      }

      public void setJogador(Jogador jogador) {
            this.jogador = jogador;
      }

      public Long getExperimentoID() {
            return experimentoID;
      }

      public void setExperimentoID(Long experimentoID) {
            this.experimentoID = experimentoID;
      }

      public List<Jogador> getJogadoresExperimento() {
            return jogadoresExperimento;
      }

      public void setJogadoresExperimento(List<Jogador> jogadoresExperimento) {
            this.jogadoresExperimento = jogadoresExperimento;
      }

      public boolean isJogadaLiberada() {
            return jogadaLiberada;
      }

      public void setJogadaLiberada(boolean jogadaLiberada) {
            this.jogadaLiberada = jogadaLiberada;
      }

      public boolean isExperimentoConcluido() {
            return experimentoConcluido;
      }

      public void setExperimentoConcluido(boolean experimentoConcluido) {
            this.experimentoConcluido = experimentoConcluido;
      }

      public boolean isMatrizSemCirculo() {
            return this.obs.getTipoMatriz() == Experimento.SEM_CIRCULO;
      }

      public boolean isMatrizCirculoVazado() {
            return this.obs.getTipoMatriz() == Experimento.CIRCULO_VAZADO;
      }

      public Jogador getJogadorUltimaJogada() {
            return jogadorUltimaJogada;
      }

      public void setJogadorUltimaJogada(Jogador jogadorUltimaJogada) {
            this.jogadorUltimaJogada = jogadorUltimaJogada;
      }

      public String getCorUltimaJogada() {
            return corUltimaJogada;
      }

      public void setCorUltimaJogada(String corUltimaJogada) {
            this.corUltimaJogada = corUltimaJogada;
      }

      public List<Linha> getLinhas() {
            return linhas;
      }

      public void setLinhas(List<Linha> linhas) {
            this.linhas = linhas;
      }

      public Linha getSelectedLinha() {
            return selectedLinha;
      }

      public void setSelectedLinha(Linha selectedLinha) {
            this.selectedLinha = selectedLinha;
      }

      public LinhasDataModel getModel() {
            return model;
      }

      public void setModel(LinhasDataModel model) {
            this.model = model;
      }

      public String getUltimoJogador() {
            if (jogada == null) {
                  return " - \n";
            }
            return "Nome: " + jogadorUltimaJogada.getNome();
      }

      public String getUltimaJogada() {
            if (jogada == null) {
                  return "- | -";
            }
            return "LINHA " + jogada.getLinhaSelecionada() + " |  COR " + corUltimaJogada;
      }

      public int getLinhaSelecionada() {
            return linhaSelecionada;
      }

      public void setLinhaSelecionada(int linhaSelecionada) {
            this.linhaSelecionada = linhaSelecionada;
      }

      public String getColunaSelecionada() {
            return colunaSelecionada;
      }

      public void setColunaSelecionada(String colunaSelecionada) {
            this.colunaSelecionada = colunaSelecionada;
      }

      public Jogada getJogada() {
            return jogada;
      }

      public void setJogada(Jogada jogada) {
            this.jogada = jogada;
      }

      public boolean isLiberaDialogUltimaJogada() {
            return liberaDialogUltimaJogada;
      }

      public void setLiberaDialogUltimaJogada(boolean liberaDialogUltimaJogada) {
            this.liberaDialogUltimaJogada = liberaDialogUltimaJogada;
      }

      public Integer getUltimoResultado() {
            return ultimoResultado;
      }

      public void setUltimoResultado(Integer ultimoResultado) {
            this.ultimoResultado = ultimoResultado;
      }

      public int getPontuacaoCultural() {
            return pontuacaoCultural;
      }

      public void setPontuacaoCultural(int pontuacaoCultural) {
            this.pontuacaoCultural = pontuacaoCultural;
      }

      public int getGeracaoAtual() {
            return geracaoAtual;
      }

      public void setGeracaoAtual(int geracaoAtual) {
            this.geracaoAtual = geracaoAtual;
      }

      public Experimento getExperimentoEmExecucao() {
            return experimentoEmExecucao;
      }

      public void setExperimentoEmExecucao(Experimento experimentoEmExecucao) {
            this.experimentoEmExecucao = experimentoEmExecucao;
      }

      public boolean isJogadorRemovido() {
            return jogadorRemovido;
      }

      public void setJogadorRemovido(boolean jogadorRemovido) {
            this.jogadorRemovido = jogadorRemovido;
      }

      public Pesquisador getPesquisador() {
            return pesquisador;
      }

      public void setPesquisador(Pesquisador pesquisador) {
            this.pesquisador = pesquisador;
      }

}
