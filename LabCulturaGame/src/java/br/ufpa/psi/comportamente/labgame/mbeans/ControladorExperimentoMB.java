/*~
M-Trix - Social Experiments Application.
Copyright (C) 2015-2016 The M-Trix authors.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
~*/
package br.ufpa.psi.comportamente.labgame.mbeans;

import br.ufpa.psi.comportamente.labgame.dao.CondicaoDAO;
import br.ufpa.psi.comportamente.labgame.dao.ExperimentoDAO;
import br.ufpa.psi.comportamente.labgame.dao.JogadaDAO;
import br.ufpa.psi.comportamente.labgame.dao.JogadorDAO;
import br.ufpa.psi.comportamente.labgame.dao.PontuacaoCulturalDAO;
import br.ufpa.psi.comportamente.labgame.entidades.Condicao;
import br.ufpa.psi.comportamente.labgame.entidades.CondicaoExperimento;
import br.ufpa.psi.comportamente.labgame.entidades.Experimento;
import br.ufpa.psi.comportamente.labgame.entidades.Jogada;
import br.ufpa.psi.comportamente.labgame.entidades.Jogador;
import br.ufpa.psi.comportamente.labgame.entidades.Pesquisador;
import br.ufpa.psi.comportamente.labgame.entidades.PontuacaoCultural;
import br.ufpa.psi.comportamente.labgame.entidades.PontuacaoIndividual;
import br.ufpa.psi.comportamente.labgame.mbeans.interfaces.ExperimentoObservable;
import br.ufpa.psi.comportamente.labgame.mbeans.interfaces.TableObserver;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

/**
 *
 * @author Weslley
 */
@ManagedBean
@SessionScoped
public class ControladorExperimentoMB implements ExperimentoObservable, Serializable {

      private static final long serialVersionUID = 1L;

      private Experimento experimento;
      private List<Jogador> listaJogadores;
      private List<Jogador> listaJogadoresReservas;

      private JogadorDAO jogadorDAO;
      private Jogada jogada;

      private List<Jogador> listaJogadoresTotais;

      private int pontuacaoCult;

      private int contCiclo;

      //representa o estado atual
      private int estadoExecucao = -1;

      // Justificativa para cancelamento do experimento
      private String justificativa = "";

      /// constantes dos valores dos estados na sequencia
      private static final int coletandoDadosJogadores = 2;
      private static final int calculandoPontuacaoIndividual = 4;
      private static final int calculandoPontuacaoCultural = 5;
      private static final int estadoFinal = 6;
      private static final int registrandoJogadores = 7;
      private static final int aguardandoJogadorEntrar = 8;

      ///variaveis atuais
      private Condicao condicaoAtual = null;
      private Jogador jogadorAtual = null;
      private PontuacaoIndividual pontIndivAtualPorJogador = null;

      private int contadorAtualCondicao;
      private int contadorAtualJogador;
      private int contCicloCondicaoAtual;
      private int contJogadoresPontCiclo;
      private int contPontCult;
      private int contGeracao;
      private int contOrdem;

      ///Listas que armazenam valores usados para cálculos da média de pontuação da condição
      private List<Integer> listaAcertoPontIndivCond;
      private List<Integer> listaAcertoPontCultCond;

      ///Listas que armazenam valores usados para cálculos da média de pontuação do experimento
      private List<Integer> listaAcertoPontIndivExp;
      private List<Integer> listaAcertoPontCultExp;

      //Boolean que vai indicar se a pontuacao individual eh do tipo DIFERENTE
      private boolean isPontIndivDiferente = false;

      //Boolean que vai indicar se o calculo da pontuacao individual eh feito no final do ciclo
      private boolean isCalculaPontIndivFinalCiclo = false;

      //tabela para armazenar as referencias dos observadores
      //ja esta definido que a chave é long e o valor só pode ser do tipo da interface TableObserver
      private Hashtable<Long, TableObserver> listaTableBeans;
      private Hashtable<Long, TableObserver> listaTableBeansReserva;
      private Hashtable<Long, Jogada> listaJogadoresJogadas;
      private Hashtable<Long, Jogador> tabelaJogadores;
      private Hashtable<Integer, CondicaoExperimento> tableCondicaoExperimento;
      private Hashtable<Long, TableObserver> tablePesquisador;

      //Variáveis usadas para manipulação das pontuações, individuais e culturais, aleatórias por jogadas
      private boolean existePontuacaoIndivAleatoriaPorJogadasNoCiclo = false;
      private boolean existePontuacaoCultAleatoriaPorJogadasNoCiclo = false;
      private HashMap<Long, PontuacaoIndividual> tablePontuacoesIndivAleatorias;
      private HashMap<Long, PontuacaoCultural> tablePontuacoesCultAleatorias;

      //Lista que contém as jogadas do último ciclo. Utilizada na tela de acompanhamento do pesquisador.
      private List<Jogada> listaUltimasJogadas;

      public ControladorExperimentoMB() {
            experimento = new Experimento();
            listaJogadoresTotais = new ArrayList<>();
            listaJogadores = new ArrayList<>();
            listaJogadoresReservas = new ArrayList<>();
            condicaoAtual = new Condicao();
            jogadorAtual = new Jogador();
            listaAcertoPontIndivCond = new ArrayList<>();
            listaAcertoPontCultCond = new ArrayList<>();
            contCiclo = 0;
            pontuacaoCult = 0;
            listaAcertoPontIndivExp = new ArrayList<>();
            listaAcertoPontCultExp = new ArrayList<>();
            pontIndivAtualPorJogador = null;
            contadorAtualCondicao = 1;
            contadorAtualJogador = 0;
            contCicloCondicaoAtual = 0;
            contJogadoresPontCiclo = 0;
            contPontCult = 0;
            contGeracao = 1;
            contOrdem = 1;
            listaTableBeans = new Hashtable<>();
            listaTableBeansReserva = new Hashtable<>();
            listaJogadoresJogadas = new Hashtable<>();
            tabelaJogadores = new Hashtable<>();
            tableCondicaoExperimento = new Hashtable<>();
            tablePesquisador = new Hashtable<>();
            tablePontuacoesIndivAleatorias = new HashMap<>();
            tablePontuacoesCultAleatorias = new HashMap<>();
      }

      /**
       *
       * PASSO 1: INICIA PREPARACAO DO EXPERIMENTO
       *
       * ACOES: REGISTRAR CONTROLADOR
       *
       * @param experimento
       */
      public void preparaExecucao(Experimento experimento) {
            this.experimento = experimento;
            defineDataExecucaoExperimento(this.experimento);
            //indica a pontuacao cultural inicial definida no experimento
            if (this.experimento.getPontInicialCultural() != null && this.experimento.getPontInicialCultural() != 0) {
                  this.pontuacaoCult = this.experimento.getPontInicialCultural();
                  atualizaPontuacaoCulturalInicial(this.pontuacaoCult);
            }
            //se tiver algum numero para pontuacao inicial individual, este valor deve ser setado para todos
            if (this.experimento.getPontInicialIndividual() != null && this.experimento.getPontInicialIndividual() != 0) {
                  atualizaPontuacaoInicial(this.experimento.getPontInicialIndividual());
            }
            listarJogadoresExperimento();
            listarCondicoesExperimento();
            contCiclo += 1;
            atualizaListaJogadoresAtuais();
            alteraStatusExperimentoParaExecutando();
            estadoExecucao = registrandoJogadores;
            /*
             Recupera a condicao de acordo com o ponteiro atual do experimento(contadorAtualCondicao)
        
             A logica do ponteiro continua igual a antes, com esse contador sendo incrementando conforme as condicoes de parada forem sendo ativadas e passando pra proxima condicao do experimento
             */
            condicaoAtual = tableCondicaoExperimento.get(contadorAtualCondicao).getCondicao();
            //registra este controlador no repositorio central para este experimento
            RegistroDeControladores.setControladorExperimentoInstance(experimento.getId(), this);
      }

      private void defineDataExecucaoExperimento(Experimento experimento) {
            ExperimentoDAO dao = new ExperimentoDAO();
            experimento.setDataExecucao(new Date());
            dao.beginTransaction();
            dao.update(experimento);
            dao.stopOperation(true);
      }

      /**
       *
       * PASSO 2: COLETA DADOS DE ENTRADA
       *
       * ACOES: REGISTRAR CONTROLADOR
       *
       */
      private void getDadosJogador() {
            //pega o jogador pra recuperar dados
            //se o contador ja for maior que o tamanho, ja acabou a coleta de dados e precisa 
            //passar pro proximo passo
            if (contadorAtualJogador >= (listaJogadores.size())) {
                  if (isCalculaPontIndivFinalCiclo == true && isPontIndivDiferente == false) {
                        for (Jogador jog : listaJogadores) {
                              calculandoPontuacaoIndividual(tabelaJogadores.get(jog.getId()));
                        }
                        atualizaPontuacaoTodosJogadores();
                  } else if (isPontIndivDiferente == true) {
                        calculandoPontuacaoIndividual();
                        atualizaPontuacaoTodosJogadores();
                  }
                  salvaJogadas();
                  //Volta pro valor default essas duas variáveis.
                  isCalculaPontIndivFinalCiclo = false;
                  isPontIndivDiferente = false;
                  /**
                   * ENCAMINHA PRO PASSO 4, final do ciclo
                   */
                  calculandoPontuacaoCultural();
            } else {
                  jogadorAtual = listaJogadores.get(contadorAtualJogador);
                  contadorAtualJogador += 1;
                  //muda o estado para continuar coleta de dados
                  estadoExecucao = coletandoDadosJogadores;
                  /**
                   * VOLTA AO PASSO 2 NOTIFICA OBSERVADOR PARA COLETAR DADOS DE
                   * ENTRADA
                   *
                   */
                  if (!(pontIndivAtualPorJogador == null) && pontIndivAtualPorJogador.getSinalSelecionado().equalsIgnoreCase(Constantes.MAIS) && !(pontIndivAtualPorJogador.getConsequenciaVerbalPositiva().trim().length() == 0)) {
                        listaTableBeans.get(jogadorAtual.getId()).coletaEntradaJogada(this.experimento.isLiberaAlertaPontuacao(), jogadorAtual, pontIndivAtualPorJogador.getConsequenciaVerbalPositiva());
                  } else if (!(pontIndivAtualPorJogador == null) && pontIndivAtualPorJogador.getSinalSelecionado().equalsIgnoreCase(Constantes.MENOS) && !(pontIndivAtualPorJogador.getConsequenciaVerbalNegativa().trim().length() == 0)) {
                        listaTableBeans.get(jogadorAtual.getId()).coletaEntradaJogada(this.experimento.isLiberaAlertaPontuacao(), jogadorAtual, pontIndivAtualPorJogador.getConsequenciaVerbalNegativa());
                  } else {
                        listaTableBeans.get(jogadorAtual.getId()).coletaEntradaJogada(this.experimento.isLiberaAlertaPontuacao(), jogadorAtual);
                  }
                  pontIndivAtualPorJogador = null;
            }
      }

      /**
       *
       * PASSO 3: REGISTRO DE JOGADA REALIZADA
       *
       * ACOES: - GUARDA INFORMACOES DE LINHA E COLUNA SELECIONADOS - CALCULA
       * PONTUACAO INDIVIDUAL - NOTIFICA OUTROS OBSERVADORES DA PONTUACAO
       * INDIVIDUAL CALCULADA - VOLTA AO PASSO 2 PARA COLETAR NOVOS DADOS
       *
       * @param jogador
       * @param linha
       * @param coluna
       */
      @Override
      public void registrarJogada(Jogador jogador, int linha, String coluna) {
            if ((estadoExecucao == coletandoDadosJogadores)) {
                  jogada = new Jogada();
                  jogada.setRodada(this.contCiclo);
                  jogada.setLinhaSelecionada(linha);
                  jogada.setColunaSelecionada(coluna);
                  jogada.setJogador(jogador);
                  jogada.setExperimento(experimento);
                  jogada.setIdCondicao(condicaoAtual.getId());
                  jogada.setCorSelecionada(getCorPelaLinhaSelecionada(linha));
                  tabelaJogadores.put(jogador.getId(), jogador);
                  //insere jogada na hash para que possa ser feito o calculo da pontuacao
                  listaJogadoresJogadas.put(jogador.getId(), jogada);
                  if (this.condicaoAtual.getListPontIndiv().isEmpty() == false) {
                        if (isPontIndivDiferente == false && isCalculaPontIndivFinalCiclo == true) {
                              //Quando chega nesse estado do sistema, nao precisa mais entrar no fluxo de verificação.
                        } else if (isPontIndivDiferente == false && isCalculaPontIndivFinalCiclo == false && listaJogadoresJogadas.size() > 1) {
                              //Quando chega nesse estado do sistema, nao precisa mais entrar no fluxo de verificação.
                        } else if (isPontIndivDiferente == true && isCalculaPontIndivFinalCiclo == true) {
                              verificaJogadasDiferentes();
                        } else {
                              for (PontuacaoIndividual pi : this.condicaoAtual.getListPontIndiv()) {
                                    //Procura se existe um critério de pontuacao para cores diferentes
                                    if (pi.getCorSelecionada().equalsIgnoreCase(Constantes.DIFERENTES)) {
                                          if (listaJogadoresJogadas.size() == 1) {
                                                isPontIndivDiferente = true;
                                                isCalculaPontIndivFinalCiclo = true;
                                          } else if (isPontIndivDiferente == true) {
                                                verificaJogadasDiferentes();
                                          }
                                    }
                              }
                        }
                  }
                  //Caso não
                  if (isCalculaPontIndivFinalCiclo == false) {
                        if (this.condicaoAtual.getListPontIndiv().isEmpty() == false) {
                              calculandoPontuacaoIndividual(tabelaJogadores.get(jogador.getId()));
                        }
                        //atualiza objeto jogada com o novo atualizado dentro do calculo do pontuacao individual
                        jogada = listaJogadoresJogadas.get(jogador.getId());
                        Integer pontuacaoIndCalculada = jogada.getPontuacaoIndividual();
                        atualizaPontuacaoTodosJogadores();
                        //Envia as informações da última jogada realizada para todos os jogadores
                        for (Jogador jog : listaJogadores) {
                              listaTableBeans.get(jog.getId()).exibeUltimaJogada(tabelaJogadores.get(jogador.getId()), getCorPelaLinhaSelecionada(listaJogadoresJogadas.get(tabelaJogadores.get(jogador.getId()).getId()).getLinhaSelecionada()),
                                      listaJogadoresJogadas.get(tabelaJogadores.get(jogador.getId()).getId()), pontuacaoIndCalculada);
                        }
                        //Verifica e, caso o Pesquisador esteja monitorando a matriz, envia as informações 
                        //da última jogada realizada para o mesmo.
                        if (tablePesquisador.isEmpty() == false) {
                              tablePesquisador.get(experimento.getPesquisador().getId()).exibeUltimaJogada(tabelaJogadores.get(jogador.getId()), getCorPelaLinhaSelecionada(listaJogadoresJogadas.get(tabelaJogadores.get(jogador.getId()).getId()).getLinhaSelecionada()),
                                      listaJogadoresJogadas.get(tabelaJogadores.get(jogador.getId()).getId()), pontuacaoIndCalculada);
                        }
                        String tipoAlertaSonoroIndividual = verificaTipoAlerta(pontuacaoIndCalculada);
                        //bloqueia a tela do jogador que acabou de fazer a jogada
                        listaTableBeans.get(tabelaJogadores.get(jogador.getId()).getId()).bloqueiaTela(tipoAlertaSonoroIndividual);
                  } else {
                        //Envia as informações da última jogada realizada para todos os jogadores
                        for (Jogador jog : listaJogadores) {
                              listaTableBeans.get(jog.getId()).exibeUltimaJogada(tabelaJogadores.get(jogador.getId()), getCorPelaLinhaSelecionada(listaJogadoresJogadas.get(tabelaJogadores.get(jogador.getId()).getId()).getLinhaSelecionada()),
                                      listaJogadoresJogadas.get(tabelaJogadores.get(jogador.getId()).getId()));
                        }
                        //Verifica e, caso o Pesquisador esteja monitorando a matriz, envia as informações 
                        //da última jogada realizada para o mesmo.
                        if (tablePesquisador.isEmpty() == false) {
                              tablePesquisador.get(experimento.getPesquisador().getId()).exibeUltimaJogada(tabelaJogadores.get(jogador.getId()), getCorPelaLinhaSelecionada(listaJogadoresJogadas.get(tabelaJogadores.get(jogador.getId()).getId()).getLinhaSelecionada()),
                                      listaJogadoresJogadas.get(tabelaJogadores.get(jogador.getId()).getId()));
                        }
                        //bloqueia a tela do jogador que acabou de fazer a jogada
                        listaTableBeans.get(tabelaJogadores.get(jogador.getId()).getId()).bloqueiaTela();
                  }
                  //ao guardar a jogada, precisa verifica se ha ainda jogadores a jogar 
                  getDadosJogador();
            } else {
                  // Lança excessão aqui
            }
      }

      private void verificaJogadasDiferentes() {
            for (Jogada jog : (List<Jogada>) Collections.list(listaJogadoresJogadas.elements())) {
                  List<Jogada> jogadasAux = (List<Jogada>) Collections.list(listaJogadoresJogadas.elements());
                  jogadasAux.remove(jog);
                  for (Jogada jogAux : jogadasAux) {
                        if (jogAux.getCorSelecionada().equalsIgnoreCase(jog.getCorSelecionada())) {
                              isPontIndivDiferente = false;
                        }
                  }
            }
      }

      private String verificaTipoAlerta(Integer pontIndiv) {
            String tipo = Constantes.NULA;
            if (pontIndiv < 0) {
                  tipo = Constantes.NEGATIVA;
            } else if (pontIndiv > 0) {
                  tipo = Constantes.POSITIVA;
            }
            return tipo;
      }

      /**
       *
       * PASSO 4: FINALIZA CICLO
       *
       * ACOES: REGISTRAR CONTROLADOR
       *
       */
      private void mudancaCiclo() {
            verificaSeExistemPontuacoesAleatorias();
            consultaGeracaoJogadores();
            //reinicia a quantidade de jogadores que acertaram neste ciclo
            contJogadoresPontCiclo = 0;
            contCiclo += 1;
            verificaCondicao();
            //zera contadores
            contadorAtualJogador = 0;
            atualizaUltimasJogadas();
            limpaListaUltimasJogadas();
            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish("/atualizarExperimento", new FacesMessage("Atualizando Experimento...", null));
            /// apos zerar o ciclo, chamar o proximo passo, que eh coletar novos dados, e reinicia o ciclo
            if (estadoExecucao != estadoFinal && estadoExecucao != aguardandoJogadorEntrar) {
                  getDadosJogador();
            } else if (estadoExecucao == estadoFinal) {
                  finalizarExperimento();
            }
      }

      private void verificaSeExistemPontuacoesAleatorias() {
            if (existePontuacaoIndivAleatoriaPorJogadasNoCiclo) {
                  for (PontuacaoIndividual pi : tablePontuacoesIndivAleatorias.values()) {
                        pi.atualizaValoresDaPontuacao();
                  }
                  existePontuacaoIndivAleatoriaPorJogadasNoCiclo = false;
            }
            if (existePontuacaoCultAleatoriaPorJogadasNoCiclo) {
                  for (PontuacaoCultural pc : tablePontuacoesCultAleatorias.values()) {
                        pc.atualizaValoresDaPontuacao();
                  }
                  existePontuacaoCultAleatoriaPorJogadasNoCiclo = false;
            }
      }

      private void listarCondicoesExperimento() {
            CondicaoDAO dao = new CondicaoDAO();
            dao.beginTransaction();
            //recupera do banco da lista de CondicaoExperimento
            List<CondicaoExperimento> listaCondicoes;
            listaCondicoes = dao.encontraCondExpPorExperimento(experimento);
            /*
             adiciona na lista de hash de usando a posicao como chave para facilitar a posterior busca da condicao que será usada na hora precisa
             Não esquecer de sempre que essa lista de condicaoExperimento for recarregada, precisa apagar o hash e incluir novos dados como aqui
             */
            for (CondicaoExperimento ce : listaCondicoes) {
                  if (ce != null && ce.getCondicao() != null) {
                        tableCondicaoExperimento.put(ce.getPosicao(), ce);
                  }
            }
            dao.stopOperation(false);
      }

      /**
       * Calcula a pontuacao individual de cada jogador quando o criterio de
       * pontuacao individual nao envolver escolhas diferentes de cores entre os
       * participantes ou, caso o criterio envolva jogadas diferentes mas as
       * jogadas nao atendam o criterio.
       *
       * @param jogador
       */
      private void calculandoPontuacaoIndividual(Jogador jogador) {
            estadoExecucao = calculandoPontuacaoIndividual;
            calculaPontuacaoIndiv(listaJogadoresJogadas.get(jogador.getId()), tabelaJogadores.get(jogador.getId()));
            listaAcertoPontIndivCond.add(contJogadoresPontCiclo);
            if (!(this.experimento.getTipoRotacao() == null) && this.experimento.getTipoRotacao().equals(Constantes.APOS_NIVEL_ACERTO)) {
                  listaAcertoPontIndivExp.add(contJogadoresPontCiclo);
            }
            estadoExecucao = coletandoDadosJogadores;
      }

      /**
       * Calcula a pontuacao individual dos jogadores quando o criterio de
       * pontuacao individual for para escolhas diferentes de cores e esse
       * criterio for atendido.
       */
      private void calculandoPontuacaoIndividual() {
            estadoExecucao = calculandoPontuacaoIndividual;
            calculaPontuacaoIndiv();
            listaAcertoPontIndivCond.add(contJogadoresPontCiclo);
            if (!(this.experimento.getTipoRotacao() == null) && this.experimento.getTipoRotacao().equals(Constantes.APOS_NIVEL_ACERTO)) {
                  listaAcertoPontIndivExp.add(contJogadoresPontCiclo);
            }
      }

      /**
       * Último método de um ciclo a ser chamado, quando é calculada a pontuação
       * cultural e um novo ciclo é iniciado ao chamar o método mudancaCiclo
       */
      private void calculandoPontuacaoCultural() {
            estadoExecucao = calculandoPontuacaoCultural;
            PontuacaoCulturalDAO dao = new PontuacaoCulturalDAO();
            dao.beginTransaction();
            List<PontuacaoCultural> lista = dao.encontraPontuacaoCultPorCondicao(this.condicaoAtual);
            dao.stopOperation(false);
            ////CALCULO AQUI
            if (lista.isEmpty() == false) {
                  calculaPontuacaoCult();
            }
            //tem que retomar ao estado anterior
            estadoExecucao = coletandoDadosJogadores;
            //apos calcular, chamar o calculo a mudanca de ciclo
            mudancaCiclo();
      }

      private void calculaPontuacaoCult() {
            Condicao condicao;
            CondicaoDAO dao = new CondicaoDAO();
            dao.beginTransaction();
            condicao = dao.find(Condicao.class, this.condicaoAtual.getId());
            PontuacaoCultural pontCult = null;
            contPontCult = 0;
            boolean liberaPontuacao = false;
            // Faz essa verificação porque a condição pode ser cadastrada com pontuação cultural nula.
            if (!condicao.getListPontCult().isEmpty()) {
                  for (PontuacaoCultural pontCultAux : condicao.getListPontCult()) {
                        // usar listaJogadoresJogadas para obter as jogadas.
                        if (pontCultAux.getCondicional().equalsIgnoreCase(Constantes.E)) {
                              boolean encontrouPontCult = true;
                              //Lista que armazena as cores de cada jogada para realizar a comparação entre as cores
                              List<String> coresJogadas = new ArrayList<>();
                              for (Jogador jgdr : listaJogadores) {
                                    Jogada jogadaAux = listaJogadoresJogadas.get(jgdr.getId());
                                    if (pontCultAux.getCoresSelecionadas().equalsIgnoreCase(Constantes.DIFERENTES)) {
                                          if (coresJogadas.contains(jogadaAux.getCorSelecionada())) {
                                                encontrouPontCult = false;
                                                break;
                                          } else if (pontCultAux.getLinhasSelecionadas().equalsIgnoreCase(Constantes.INDIFERENTE)) {
                                                coresJogadas.add(jogadaAux.getCorSelecionada());
                                          } else {
                                                if (pontCultAux.getLinhasSelecionadas().equalsIgnoreCase(getPAROUIMPAR(jogadaAux.getLinhaSelecionada()))) {
                                                      coresJogadas.add(jogadaAux.getCorSelecionada());
                                                      //Continua na verificação
                                                } else {
                                                      encontrouPontCult = false;
                                                      break;
                                                }
                                          }
                                    } else if (pontCultAux.getCoresSelecionadas().equalsIgnoreCase(jogadaAux.getCorSelecionada())
                                            && pontCultAux.getLinhasSelecionadas().equalsIgnoreCase(getPAROUIMPAR(jogadaAux.getLinhaSelecionada()))) {
                                          //Continua na verificação
                                    } else {
                                          encontrouPontCult = false;
                                          break;
                                    }
                              }
                              if (encontrouPontCult == true) {
                                    liberaPontuacao = true;
                                    pontCult = pontCultAux;
                                    break;
                              }
                        } else {
                              List<Jogada> listaJogadasAux = new ArrayList<>();
                              boolean encontrouPontCult = true;
                              if (pontCultAux.getLinhasSelecionadas().equalsIgnoreCase(Constantes.INDIFERENTE)) {
                                    //Pega o critério que atende todas as formas de jogadas.
                              } else {
                                    for (Jogador jgdr : listaJogadores) {
                                          Jogada jogadaAux = listaJogadoresJogadas.get(jgdr.getId());
                                          if (pontCultAux.getCoresSelecionadas().equalsIgnoreCase(Constantes.DIFERENTES)) {
                                                boolean encontrouJogadaNaoPermitida = false;
                                                for (Jogada jgd : listaJogadasAux) {
                                                      if (jgd.getCorSelecionada().equalsIgnoreCase(jogadaAux.getCorSelecionada())) {
                                                            if (pontCultAux.getLinhasSelecionadas().equalsIgnoreCase(getPAROUIMPAR(jogadaAux.getLinhaSelecionada()))) {
                                                                  //continua verificação
                                                            } else {
                                                                  encontrouJogadaNaoPermitida = true;
                                                                  encontrouPontCult = false;
                                                                  break;
                                                            }
                                                      }
                                                }
                                                if (encontrouJogadaNaoPermitida == false) {
                                                      listaJogadasAux.add(jogadaAux);
                                                }
                                          } else if (pontCultAux.getCoresSelecionadas().equalsIgnoreCase(jogadaAux.getCorSelecionada())
                                                  || pontCultAux.getLinhasSelecionadas().equalsIgnoreCase(getPAROUIMPAR(jogadaAux.getLinhaSelecionada()))) {

                                          } else {
                                                encontrouPontCult = false;
                                          }
                                          if (encontrouPontCult == false) {
                                                break;
                                          }
                                    }
                              }
                              if (encontrouPontCult == true) {
                                    liberaPontuacao = true;
                                    pontCult = pontCultAux;
                                    break;
                              }
                        }
                  }
            }
            dao.stopOperation(false);
            String tipoAlertaPontCult = Constantes.NULA;
            if (liberaPontuacao == true) {
                  if (pontCult != null) {
                        if (pontCult.comPontuacaoAleatoria()) {
                              if (tablePontuacoesCultAleatorias.containsKey(pontCult.getId()) == false) {
                                    pontCult.iniciaAsVariaveisTransient();
                                    tablePontuacoesCultAleatorias.put(pontCult.getId(), pontCult);
                                    existePontuacaoCultAleatoriaPorJogadasNoCiclo = true;
                              }
                              if (tablePontuacoesCultAleatorias.get(pontCult.getId()).existeOcorrenciaNoCicloAtual() == false) {
                                    tablePontuacoesCultAleatorias.get(pontCult.getId()).incrementaContadorOcorrencia();
                                    existePontuacaoCultAleatoriaPorJogadasNoCiclo = true;
                              }
                        }
                  }
                  contPontCult = 1;
                  tipoAlertaPontCult = calculaPontCult(pontCult);
            } else {
                  for (Jogador jogador : listaJogadores) {
                        listaJogadoresJogadas.get(jogador.getId()).setPontuacaoCultural(0);
                  }
                  persisteUltimasJogadas();
            }
            listaAcertoPontCultCond.add(contPontCult);
            if (!(this.experimento.getTipoRotacao() == null) && this.experimento.getTipoRotacao().equals(Constantes.APOS_NIVEL_ACERTO)) {
                  listaAcertoPontCultExp.add(contPontCult);
            }
            atualizaPontuacaoTodosJogadores(tipoAlertaPontCult);
      }

      private void atualizaPontuacaoCulturaldaJogada(int pCult, Long jogadorID) {
            Jogada jgd = listaJogadoresJogadas.get(jogadorID);
            if (jgd != null) {
                  jgd.setPontuacaoCultural(pCult);
                  listaJogadoresJogadas.put(jogadorID, jgd);
            }
      }

      private String calculaPontCult(PontuacaoCultural pc) {
            int pontuacao;
            String tipoPontCult = Constantes.NULA;
            if (pc.getPontoAcerto() == null) {
                  pc.iniciaAsVariaveisTransient();
            }
            switch (pc.getSinalSelecionado()) {
                  case Constantes.MAIS:
                        pontuacao = pontuacaoCult + (pc.getPontoAcerto());//so faz uma vez o calculo
                        for (Jogador jogador : listaJogadores) {
                              //Busca no banco o objeto Jogador atualizado
                              tabelaJogadores.get(jogador.getId()).setPontuacaoCult(pontuacao);
                              //inclui somente o valor do incremento nesta jogada
                              atualizaPontuacaoCulturaldaJogada(pc.getPontoAcerto(), tabelaJogadores.get(jogador.getId()).getId());
                              salvaPontuacaoJogador(tabelaJogadores.get(jogador.getId()));
                        }
                        pontuacaoCult += pc.getPontoAcerto();
                        tipoPontCult = Constantes.POSITIVA;
                        atualizaMensagemPontuacaoCulturalJogadores(pc);
                        break;
                  case Constantes.MENOS:
                        pontuacao = pontuacaoCult - (pc.getPontoAcerto());//so faz uma vez o calculo
                        for (Jogador jogador : listaJogadores) {
                              //Busca no banco o objeto Jogador atualizado
                              tabelaJogadores.get(jogador.getId()).setPontuacaoCult(pontuacao);
                              //inclui somente o valor do incremento nesta jogada
                              atualizaPontuacaoCulturaldaJogada(pc.getPontoAcerto(), tabelaJogadores.get(jogador.getId()).getId());
                              salvaPontuacaoJogador(tabelaJogadores.get(jogador.getId()));
                        }
                        pontuacaoCult -= pc.getPontoAcerto();
                        tipoPontCult = Constantes.NEGATIVA;
                        atualizaMensagemPontuacaoCulturalJogadores(pc);
                        break;
            }
            persisteUltimasJogadas();
            return tipoPontCult;
      }

      private void atualizaListasDeJogadores(Jogador jogador) {
            if (experimento.isOrdemRandomicaJogadores()) {
                  Collections.sort(listaJogadores);
            }
            // Remove o primeiro da fila
            listaTableBeans.get(listaJogadores.get(0).getId()).informaSaidaDoExperimento();
            removeRegistroObservador(listaJogadores.get(0));
            tabelaJogadores.remove(listaJogadores.get(0).getId());
            listaJogadores.remove(0);

            if (listaTableBeansReserva.containsKey(jogador.getId())) {
                  // Adiciona (na lista de titulares) o primeiro da fila de reservas.
                  listaTableBeans.put(jogador.getId(), listaTableBeansReserva.get(jogador.getId()));
                  listaJogadoresReservas.remove(jogador);
                  listaJogadores.add(jogador);
                  tabelaJogadores.put(jogador.getId(), jogador);
                  if (experimento.isOrdemRandomicaJogadores()) {
                        for (int i = 0; i < listaJogadores.size(); i++) {
                              Random random = new Random();
                              int j = random.nextInt(listaJogadores.size() - 1);
                              Collections.swap(listaJogadores, i, j);
                        }
                  }
            } else {
                  estadoExecucao = aguardandoJogadorEntrar;
                  listaJogadores.add(jogador);
                  if (experimento.isOrdemRandomicaJogadores()) {
                        for (int i = 0; i < listaJogadores.size(); i++) {
                              Random random = new Random();
                              int j = random.nextInt(listaJogadores.size() - 1);
                              Collections.swap(listaJogadores, i, j);
                        }
                  }
                  informaJogadoresEstadoEspera(jogador);
            }
      }

      private void informaJogadoresEstadoEspera(Jogador jogador) {
            for (Jogador jogadorAux : listaJogadores) {
                  if (listaTableBeans.containsKey(jogadorAux.getId())) {
                        listaTableBeans.get(jogadorAux.getId()).aguardandoProximoJogador(jogador);
                  }
            }
            if (tablePesquisador.isEmpty() == false) {
                  tablePesquisador.get(experimento.getPesquisador().getId()).aguardandoProximoJogador(jogador);
            }
      }

      private void consultaGeracaoJogadores() {
            boolean isMudanca = false;
            Jogador jogadorAux = new Jogador();
            if (!(this.experimento.getTipoRotacao() == null)) {
                  switch (this.experimento.getTipoRotacao()) {
                        case Constantes.APOS_X_JOGADAS:
                              if ((this.experimento.getQuantidadeJogadas() * this.experimento.getContQuantidadeCiclos()) == contCiclo && listaJogadoresTotais.size() >= contOrdem) {
                                    for (Jogador jogador : listaJogadoresTotais) {
                                          if (jogador.getOrdem() == contOrdem) {
                                                jogadorAux = jogador;
                                                isMudanca = true;
                                          }
                                    }
                                    this.experimento.setContQuantidadeCiclos(this.experimento.getContQuantidadeCiclos() + 1);
                              }
                              break;
                        case Constantes.APOS_NIVEL_ACERTO:
                              if (this.experimento.getUltimosXCiclos() < listaAcertoPontCultCond.size()) {
                                    removeCicloMenorXCiclos(Constantes.EXPERIMENTO);
                              }
                              if (this.experimento.getPorcentagemAcertoIndiv() <= mediaAcertoIndiv(listaAcertoPontIndivExp, this.experimento.getUltimosXCiclos())
                                      || this.experimento.getPorcentagemAcertoCult() <= mediaAcertoCult(listaAcertoPontCultExp, this.experimento.getUltimosXCiclos())) {
                                    for (Jogador jogador : listaJogadoresTotais) {
                                          if (jogador.getOrdem() == contOrdem) {
                                                jogadorAux = jogador;
                                                isMudanca = true;
                                          }
                                    }
                                    esvaziaListasMediaAcerto(Constantes.EXPERIMENTO);
                              }
                              break;
                        case Constantes.APOS_CONDICAO_COMPLEXA:
                              if (this.experimento.getNivelComplexidade() == null ? condicaoAtual.getNivelComplexidade() == null : this.experimento.getNivelComplexidade().equals(condicaoAtual.getNivelComplexidade())) {
                                    for (Jogador jogador : listaJogadoresTotais) {
                                          if (jogador.getOrdem() == contOrdem) {
                                                jogadorAux = jogador;
                                                isMudanca = true;
                                          }
                                    }
                              }
                              break;
                  }
            }
            if (isMudanca) {
                  contGeracao++;
                  contOrdem++;
                  atualizaListasDeJogadores(jogadorAux);
            } else {
                  if (experimento.isOrdemRandomicaJogadores()) {
                        for (int i = 0; i < listaJogadores.size(); i++) {
                              Random random = new Random();
                              int j = random.nextInt(listaJogadores.size() - 1);
                              Collections.swap(listaJogadores, i, j);
                        }
                  }
            }
      }

      private void atualizaAlertaSonoroGeracao() {
            Enumeration<TableObserver> referencias = listaTableBeans.elements();
            while (referencias.hasMoreElements()) { // fica no loop enquanto tem elementos pra listar
                  TableObserver obs = referencias.nextElement(); //ao chamar nextElement, ele ja muda o ponteiro pro proximo
                  obs.emiteAlertaMucancaGeracao();
            }
      }

      private void atualizaListaJogadoresAtuais() {
            for (Jogador jogador : listaJogadoresTotais) {
                  if (jogador.getOrdem() == contOrdem && contOrdem <= experimento.getTamanhoFilaJogadores()) {
                        listaJogadores.add(jogador);
                        tabelaJogadores.put(jogador.getId(), jogador);
                        contOrdem++;
                  }
            }
            listaJogadoresReservas.removeAll(listaJogadores);
            if (experimento.isOrdemRandomicaJogadores()) {
                  for (int i = 0; i < listaJogadores.size(); i++) {
                        Random random = new Random();
                        int j = random.nextInt(listaJogadores.size() - 1);
                        Collections.swap(listaJogadores, i, j);
                  }
            }
      }

      public void pausarExperimento() {
            alteraStatusExperimentoParaPausado();
            if (!listaTableBeans.isEmpty() && jogadorAtual != null) {
                  listaTableBeans.get(jogadorAtual.getId()).bloqueiaTela();
            }
      }

      public void continuarExperimento() {
            alteraStatusExperimentoParaExecutando();
            if (!listaTableBeans.isEmpty() && jogadorAtual != null) {
                  listaTableBeans.get(jogadorAtual.getId()).coletaEntradaJogada();
            }
      }

      private void finalizarExperimento() {
            alteraStatusExperimentoParaConcluido();
            for (Jogador jgdr : listaJogadores) {
                  listaTableBeans.get(jgdr.getId()).finalizarJogadas();
            }
            zerarListasEVariaveis();
            ///TODO: Criar uma forma de informar o pesquisador que o experimento acabou.
      }

      private void zerarListasEVariaveis() {
            experimento = new Experimento();
            listaJogadoresTotais = new ArrayList<>();
            listaJogadores = new ArrayList<>();
            condicaoAtual = new Condicao();
            jogadorAtual = new Jogador();
            listaAcertoPontIndivCond = new ArrayList<>();
            listaAcertoPontCultCond = new ArrayList<>();
            contCiclo = 0;
            pontuacaoCult = 0;
            listaAcertoPontIndivExp = new ArrayList<>();
            listaAcertoPontCultExp = new ArrayList<>();
            pontIndivAtualPorJogador = null;
            contadorAtualCondicao = 1;
            contadorAtualJogador = 0;
            contCicloCondicaoAtual = 0;
            contJogadoresPontCiclo = 0;
            contPontCult = 0;
            contGeracao = 1;
            contOrdem = 1;
            listaTableBeans = new Hashtable<>();
            listaTableBeansReserva = new Hashtable<>();
            listaJogadoresJogadas = new Hashtable<>();
            tabelaJogadores = new Hashtable<>();
            tableCondicaoExperimento = new Hashtable<>();
            tablePesquisador = new Hashtable<>();
      }

      public void cancelarExperimento() {
            if (!(justificativa == null) && !(justificativa.equals(""))) {
                  alteraStatusExperimentoParaCancelado(justificativa);
                  if (!listaTableBeans.isEmpty()) {
                        for (Jogador jgdr : listaJogadores) {
                              listaTableBeans.get(jgdr.getId()).finalizarJogadas();
                        }
                  }
                  zerarListasEVariaveis();
                  justificativa = null;
                  FacesContext.getCurrentInstance().addMessage(null,
                          new FacesMessage("Experimento cancelado!", ""));
            } else {
                  FacesContext.getCurrentInstance().addMessage(null,
                          new FacesMessage("Não foi possível cancelar o Experimento. Verifique se o campo \"Justificativa\" foi preenchido.", ""));
            }
      }

      private void alteraStatusExperimentoParaPausado() {
            ExperimentoDAO dao = new ExperimentoDAO();
            dao.beginTransaction();
            experimento.setStatus(Constantes.PAUSADO);
            dao.update(experimento);
            dao.stopOperation(true);
      }

      private void alteraStatusExperimentoParaExecutando() {
            ExperimentoDAO dao = new ExperimentoDAO();
            dao.beginTransaction();
            experimento.setStatus(Constantes.EXECUTANDO);
            dao.update(experimento);
            dao.stopOperation(true);
      }

      private void alteraStatusExperimentoParaConcluido() {
            ExperimentoDAO dao = new ExperimentoDAO();
            dao.beginTransaction();
            experimento.setStatus(Constantes.CONCLUIDO);
            dao.update(experimento);
            dao.stopOperation(true);
      }

      private void alteraStatusExperimentoParaCancelado(String justificativa) {
            ExperimentoDAO dao = new ExperimentoDAO();
            dao.beginTransaction();
            experimento.setJustificativa(justificativa);
            experimento.setStatus(Constantes.CANCELADO);
            dao.update(experimento);
            dao.stopOperation(true);
      }

      public void bloqueiaVisualizacaoPontos() {
            ExperimentoDAO dao = new ExperimentoDAO();
            dao.beginTransaction();
            experimento.setLiberaVisualizarPontosIndivJogador(false);
            dao.update(experimento);
            dao.stopOperation(true);
            atualizaVisualizacaoPontosJogadores();
      }

      public void habilitaVisualizacaoPontos() {
            ExperimentoDAO dao = new ExperimentoDAO();
            dao.beginTransaction();
            experimento.setLiberaVisualizarPontosIndivJogador(true);
            dao.update(experimento);
            dao.stopOperation(true);
            atualizaVisualizacaoPontosJogadores();
      }

      private void atualizaVisualizacaoPontosJogadores() {
            if (!listaTableBeans.isEmpty()) {
                  for (Jogador jgdr : listaJogadores) {
                        listaTableBeans.get(jgdr.getId()).atualizaExperimentoEmExecucao(experimento);
                  }
            }
            if (tablePesquisador.isEmpty() == false) {
                  tablePesquisador.get(experimento.getPesquisador().getId()).atualizaExperimentoEmExecucao(experimento);
            }
      }

      private void esvaziaListasMediaAcerto(String tipo) {
            switch (tipo) {
                  case Constantes.CONDICAO:
                        listaAcertoPontCultCond = new ArrayList<>();
                        listaAcertoPontIndivCond = new ArrayList<>();
                        break;
                  case Constantes.EXPERIMENTO:
                        listaAcertoPontIndivExp = new ArrayList<>();
                        listaAcertoPontCultExp = new ArrayList<>();
                        break;
            }
      }

      private void removeCicloMenorXCiclos(String tipo) {
            switch (tipo) {
                  case Constantes.CONDICAO:
                        listaAcertoPontIndivCond.remove(0);
                        listaAcertoPontCultCond.remove(0);
                        break;
                  case Constantes.EXPERIMENTO:
                        listaAcertoPontIndivExp.remove(0);
                        listaAcertoPontCultExp.remove(0);
                        break;
            }
      }

      private void informaPesquisadorMudancaCondicao() {
            // TODO: Alterar para o novo modelo de push.
            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish("/atualizarCondicao", new FacesMessage("Mudando a Condição do Experimento!", "Agora está valendo uma nova condição."));
      }

      private void atualizaUltimasJogadas() {
            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish("/atualizarUltimasJogadas", new FacesMessage("Últimas jogadas atualizadas!", null));
      }

      /**
       * Método que realiza verificação da condição.
       *
       * Responsável por verificar se ainda continua a valer uma determinada
       * Condição ou se já é era de mudar para a próxima da lista de condições
       * cadastradas no Experimento.
       *
       */
      private void verificaCondicao() {
            if (contadorAtualCondicao >= (tableCondicaoExperimento.size()) && condicaoAtual.getFimPorQuantCiclo() <= contCicloCondicaoAtual) {
                  estadoExecucao = estadoFinal;
            } else if (condicaoAtual.getMinimoCiclos() != null && condicaoAtual.getMinimoCiclos() > contCicloCondicaoAtual) {
                  contCicloCondicaoAtual += 1;
            } else if (condicaoAtual.getUltimosXCiclos() != null && condicaoAtual.getUltimosXCiclos() > contCicloCondicaoAtual) {
                  contCicloCondicaoAtual += 1;
            } else if ((condicaoAtual.getMinimoCiclos() != null && condicaoAtual.getUltimosXCiclos() != null && condicaoAtual.getFimPorQuantCiclo() != null) && condicaoAtual.getMinimoCiclos() > 0 && condicaoAtual.getUltimosXCiclos() <= contCicloCondicaoAtual
                    && condicaoAtual.getFimPorQuantCiclo() > contCicloCondicaoAtual) {
                  if (condicaoAtual.getUltimosXCiclos() > 0 && condicaoAtual.getUltimosXCiclos() < contCicloCondicaoAtual) {
                        removeCicloMenorXCiclos(Constantes.CONDICAO);
                  }
                  if (condicaoAtual.getFimPorPorcentAcertoIndiv() != null
                          && condicaoAtual.getFimPorPorcentAcertoIndiv() <= mediaAcertoIndiv(listaAcertoPontIndivCond, condicaoAtual.getUltimosXCiclos())) {
                        contadorAtualCondicao += 1;
                        if (tableCondicaoExperimento.size() >= contadorAtualCondicao) {
                              condicaoAtual = tableCondicaoExperimento.get(contadorAtualCondicao).getCondicao();
                              informaPesquisadorMudancaCondicao();
                        } else {
                              estadoExecucao = estadoFinal;
                        }
                        contCicloCondicaoAtual = 0;
                        esvaziaListasMediaAcerto(Constantes.CONDICAO);
                  } else if (condicaoAtual.getFimPorPorcentAcertoCult() != null
                          && condicaoAtual.getFimPorPorcentAcertoCult() <= mediaAcertoCult(listaAcertoPontCultCond, condicaoAtual.getUltimosXCiclos())) {
                        contadorAtualCondicao += 1;
                        if (tableCondicaoExperimento.size() >= contadorAtualCondicao) {
                              condicaoAtual = tableCondicaoExperimento.get(contadorAtualCondicao).getCondicao();
                              informaPesquisadorMudancaCondicao();
                        } else {
                              estadoExecucao = estadoFinal;
                        }
                        contCicloCondicaoAtual = 0;
                        esvaziaListasMediaAcerto(Constantes.CONDICAO);
                  } else {
                        contCicloCondicaoAtual += 1;
                  }
            } else if ((condicaoAtual.getFimPorQuantCiclo() != null && condicaoAtual.getFimPorQuantCiclo() != null) && condicaoAtual.getFimPorQuantCiclo() > 0 && condicaoAtual.getFimPorQuantCiclo() <= contCicloCondicaoAtual && tableCondicaoExperimento.size() > contadorAtualCondicao) {
                  contadorAtualCondicao += 1;
                  condicaoAtual = tableCondicaoExperimento.get(contadorAtualCondicao).getCondicao();
                  informaPesquisadorMudancaCondicao();
                  contCicloCondicaoAtual = 0;
                  esvaziaListasMediaAcerto(Constantes.CONDICAO);
            } else {
                  contCicloCondicaoAtual += 1;
            }
      }

      private int mediaAcertoCult(List<Integer> listaAcertoPontCult, int ultimosXCiclos) {
            int somaMediaPorCiclo = 0;
            for (int i = 0; i < ultimosXCiclos; i++) {
                  if (listaAcertoPontCult.get(i) == 1) {
                        somaMediaPorCiclo += 1;
                  }
            }
            return (somaMediaPorCiclo * 100) / (ultimosXCiclos);
      }

      private int mediaAcertoIndiv(List<Integer> listaAcertoPontIndiv, int ultimosXCiclos) {
            int somaMediasPorCiclo = 0;
            int media = 0;
            for (int i = 0; i < ultimosXCiclos; i++) {
                  somaMediasPorCiclo += listaAcertoPontIndiv.get(i);
            }
            if (somaMediasPorCiclo > 0) {
                  media = (somaMediasPorCiclo * 100) / (listaJogadores.size() * ultimosXCiclos);
            }
            return media;
      }

      private void salvaJogadas() {
            JogadaDAO dao = new JogadaDAO();
            for (Jogador jogador : listaJogadores) {
                  Jogada ultimaJogada;
                  ultimaJogada = listaJogadoresJogadas.get(jogador.getId());
                  dao.beginTransaction();
                  dao.save(ultimaJogada);
                  dao.stopOperation(true);
                  tabelaJogadores.get(jogador.getId()).setUltimaJogada(ultimaJogada);
            }
      }

      private void persisteUltimasJogadas() {
            JogadaDAO dao = new JogadaDAO();
            for (Jogador jogador : listaJogadores) {
                  Jogada ultimaJogada;
                  ultimaJogada = listaJogadoresJogadas.get(jogador.getId());
                  dao.beginTransaction();
                  dao.update(ultimaJogada);
                  dao.stopOperation(true);
                  jogador.setUltimaJogada(ultimaJogada);
            }
      }

      private void limpaListaUltimasJogadas() {
            listaUltimasJogadas = (List<Jogada>) Collections.list(listaJogadoresJogadas.elements());
            listaJogadoresJogadas.clear();
      }

      private String getCorPelaLinhaSelecionada(int linha) {
            if (linha == 1 || linha == 8) {
                  return Constantes.AMARELO;
            } else if (linha == 2 || linha == 7) {
                  return Constantes.VERDE;
            } else if (linha == 3 || linha == 6) {
                  return Constantes.VERMELHO;
            } else if (linha == 4 || linha == 9) {
                  return Constantes.AZUL;
            } else if (linha == 5 || linha == 10) {
                  return Constantes.ROXO;
            }
            return null;
      }

      private String getCorPelaLinhaSelecionada(int linha, PontuacaoIndividual pi) {
            if (pi.getCorSelecionada().equalsIgnoreCase(Constantes.INDIFERENTE)) {
                  return Constantes.INDIFERENTE;
            } else {
                  return getCorPelaLinhaSelecionada(linha);
            }
      }

      private String getPAROUIMPAR(int linha) {
            if (linha % 2 == 0) {
                  return Constantes.PAR;
            } else {
                  return Constantes.IMPAR;
            }
      }

      /**
       * Utilizado quando a pontuacao individual for calculada em conjunto.
       * Quando existir um critério de pontuacao individual que exija escolha de
       * linhas diferentes entre os participantes.
       */
      private void calculaPontuacaoIndiv() {
            List<Jogada> jogadas = (List<Jogada>) Collections.list(listaJogadoresJogadas.elements());
            pontIndivAtualPorJogador = null;
            for (PontuacaoIndividual pi : this.condicaoAtual.getListPontIndiv()) {
                  if (pi.getCorSelecionada().equalsIgnoreCase(Constantes.DIFERENTES)
                          && pi.getCondicional().equalsIgnoreCase(Constantes.E)) {
                        if (pi.getLinhaSelecionada().equalsIgnoreCase(Constantes.PAR)) {
                              boolean encontrouPontInd = verificaParidadeDasJogadas(jogadas, Constantes.PAR);
                              if (encontrouPontInd) {
                                    pontIndivAtualPorJogador = pi;
                                    calculaValorPontIndiv();
                                    break;
                              } else {

                              }
                        } else if (pi.getLinhaSelecionada().equalsIgnoreCase(Constantes.IMPAR)) {
                              boolean encontrouPontInd = verificaParidadeDasJogadas(jogadas, Constantes.IMPAR);
                              if (encontrouPontInd) {
                                    pontIndivAtualPorJogador = pi;
                                    calculaValorPontIndiv();
                                    break;
                              }
                        } else if (pi.getLinhaSelecionada().equalsIgnoreCase(Constantes.INDIFERENTE)) {
                              pontIndivAtualPorJogador = pi;
                              calculaValorPontIndiv();
                              break;
                        }
                  } else if (pi.getCorSelecionada().equalsIgnoreCase(Constantes.DIFERENTES)
                          && pi.getCondicional().equalsIgnoreCase(Constantes.OU)) {
                        if (pi.getLinhaSelecionada().equalsIgnoreCase(Constantes.PAR)) {
                              boolean encontrouPontInd = verificaParidadeDasJogadas(jogadas, Constantes.PAR);
                              if (encontrouPontInd) {
                                    pontIndivAtualPorJogador = pi;
                                    calculaValorPontIndiv();
                                    break;
                              }
                        } else if (pi.getLinhaSelecionada().equalsIgnoreCase(Constantes.IMPAR)) {
                              boolean encontrouPontInd = verificaParidadeDasJogadas(jogadas, Constantes.IMPAR);
                              if (encontrouPontInd) {
                                    pontIndivAtualPorJogador = pi;
                                    calculaValorPontIndiv();
                                    break;
                              }
                        } else if (pi.getLinhaSelecionada().equalsIgnoreCase(Constantes.INDIFERENTE)) {
                              pontIndivAtualPorJogador = pi;
                              calculaValorPontIndiv();
                              break;
                        }
                  }
            }
            if (pontIndivAtualPorJogador == null) {
                  atualizaJogadasSemPontuacao();
            } else {
                  if (pontIndivAtualPorJogador.comPontuacaoAleatoria()) {
                        if (tablePontuacoesIndivAleatorias.containsKey(pontIndivAtualPorJogador.getId()) == false) {
                              pontIndivAtualPorJogador.iniciaAsVariaveisTransient();
                              tablePontuacoesIndivAleatorias.put(pontIndivAtualPorJogador.getId(), pontIndivAtualPorJogador);
                              existePontuacaoIndivAleatoriaPorJogadasNoCiclo = true;
                        }
                        if (tablePontuacoesIndivAleatorias.get(pontIndivAtualPorJogador.getId()).existeOcorrenciaNoCicloAtual() == false) {
                              tablePontuacoesIndivAleatorias.get(pontIndivAtualPorJogador.getId()).incrementaContadorOcorrencia();
                              existePontuacaoIndivAleatoriaPorJogadasNoCiclo = true;
                        }
                  }
            }
      }

      private void atualizaJogadasSemPontuacao() {
            List<Jogador> jogadores = (List<Jogador>) Collections.list(tabelaJogadores.elements());
            for (Jogador jog : jogadores) {
                  Jogada joga = listaJogadoresJogadas.get(jog.getId());
                  joga.setNomeJogador(jog.getNome());
                  joga.setPontuacaoIndividual(0);
                  listaJogadoresJogadas.put(jog.getId(), joga);
                  tabelaJogadores.get(jog.getId()).setUltimaJogada(joga);
            }
      }

      private boolean verificaParidadeDasJogadas(List<Jogada> jogadas, String paridade) {
            boolean jogadasComMesmaParidade = true;
            for (Jogada j : jogadas) {
                  if (!getPAROUIMPAR(j.getLinhaSelecionada()).equalsIgnoreCase(paridade)) {
                        jogadasComMesmaParidade = false;
                  }
            }
            return jogadasComMesmaParidade;
      }

      private void calculaValorPontIndiv() {
            List<Jogador> jogadores = (List<Jogador>) Collections.list(tabelaJogadores.elements());
            if (pontIndivAtualPorJogador.getPontoAcerto() == null) {
                  pontIndivAtualPorJogador.iniciaAsVariaveisTransient();
            }
            switch (pontIndivAtualPorJogador.getSinalSelecionado()) {
                  case Constantes.MAIS:
                        for (Jogador jogador : jogadores) {
                              Jogada joga = listaJogadoresJogadas.get(jogador.getId());
                              int resultado_pontuacao;
                              int pontuacaoAAcumular = pontIndivAtualPorJogador.getPontoAcerto();
                              Integer ultimoResultadoAAcumular;
                              resultado_pontuacao = pontuacaoAAcumular + tabelaJogadores.get(jogador.getId()).getPontuacao();
                              tabelaJogadores.get(jogador.getId()).setPontuacao(resultado_pontuacao);
                              ultimoResultadoAAcumular = Integer.parseInt(Constantes.MAIS + pontuacaoAAcumular);
                              joga.setPontuacaoIndividual(ultimoResultadoAAcumular);
                              contJogadoresPontCiclo += 1;//guarda a info de que alguem pontuou nesse ciclo
                              joga.setNomeJogador(jogador.getNome());
                              listaJogadoresJogadas.put(jogador.getId(), joga);
                              tabelaJogadores.get(jogador.getId()).setUltimaJogada(joga);
                              if (ultimoResultadoAAcumular != 0) {
                                    salvaPontuacaoJogador(tabelaJogadores.get(jogador.getId()));
                              }
                        }
                        break;
                  case Constantes.MENOS:
                        for (Jogador jogador : jogadores) {
                              Jogada joga = listaJogadoresJogadas.get(jogador.getId());
                              int resultado_pontuacao;
                              int pontuacaoAAcumular = pontIndivAtualPorJogador.getPontoAcerto();
                              Integer ultimoResultadoAAcumular;
                              resultado_pontuacao = pontuacaoAAcumular - tabelaJogadores.get(jogador.getId()).getPontuacao();
                              tabelaJogadores.get(jogador.getId()).setPontuacao(resultado_pontuacao);
                              ultimoResultadoAAcumular = Integer.parseInt(Constantes.MENOS + pontuacaoAAcumular);
                              joga.setPontuacaoIndividual(ultimoResultadoAAcumular);
                              joga.setNomeJogador(jogador.getNome());
                              listaJogadoresJogadas.put(jogador.getId(), joga);
                              tabelaJogadores.get(jogador.getId()).setUltimaJogada(joga);
                              if (ultimoResultadoAAcumular != 0) {
                                    salvaPontuacaoJogador(tabelaJogadores.get(jogador.getId()));
                              }
                        }
                        break;
            }
      }

      /**
       * Utilizado quando a pontuacao individual for calculada de forma
       * independente. Nesse caso, não existe critério de pontuacao individual
       * que exija escolha de cores diferentes entre os participantes.
       *
       * @param jogada
       * @param jogador
       */
      private void calculaPontuacaoIndiv(Jogada jogada, Jogador jogador) {
            int resultado_pontuacao;
            int pontuacaoAAcumular;
            List<PontuacaoIndividual> listaPontIndiv = new ArrayList<>();
            Integer ultimoResultadoAAcumular = 0;
            for (PontuacaoIndividual pontIndiv : condicaoAtual.getListPontIndiv()) {
                  if (pontIndiv.getLinhaSelecionada().equalsIgnoreCase(Constantes.INDIFERENTE)) {
                        listaPontIndiv.add(pontIndiv);
                  }
            }
            //Caso linha selecionada for par
            if (getPAROUIMPAR(jogada.getLinhaSelecionada()).equalsIgnoreCase(Constantes.PAR)) {
                  for (int i = 0; i < condicaoAtual.getListPontIndiv().size(); i++) {
                        if (condicaoAtual.getListPontIndiv().get(i).getLinhaSelecionada().equalsIgnoreCase(Constantes.PAR)) {
                              listaPontIndiv.add(condicaoAtual.getListPontIndiv().get(i));
                        }
                  }
            } //Caso a linha selecionada for ímpar
            else if (getPAROUIMPAR(jogada.getLinhaSelecionada()).equalsIgnoreCase(Constantes.IMPAR)) {
                  for (int i = 0; i < condicaoAtual.getListPontIndiv().size(); i++) {
                        if (condicaoAtual.getListPontIndiv().get(i).getLinhaSelecionada().equalsIgnoreCase(Constantes.IMPAR)) {
                              listaPontIndiv.add(condicaoAtual.getListPontIndiv().get(i));
                        }
                  }
            }
            if (!(listaPontIndiv.isEmpty())) {
                  pontuacaoAAcumular = calculaPontuacaoAcumulada(listaPontIndiv, jogada);
                  if ((pontIndivAtualPorJogador != null)) {
                        if (pontIndivAtualPorJogador.comPontuacaoAleatoria()) {
                              if (tablePontuacoesIndivAleatorias.containsKey(pontIndivAtualPorJogador.getId()) == false) {
                                    tablePontuacoesIndivAleatorias.put(pontIndivAtualPorJogador.getId(), pontIndivAtualPorJogador);
                                    existePontuacaoIndivAleatoriaPorJogadasNoCiclo = true;
                              }
                              if (tablePontuacoesIndivAleatorias.get(pontIndivAtualPorJogador.getId()).existeOcorrenciaNoCicloAtual() == false) {
                                    tablePontuacoesIndivAleatorias.get(pontIndivAtualPorJogador.getId()).incrementaContadorOcorrencia();
                                    existePontuacaoIndivAleatoriaPorJogadasNoCiclo = true;
                              }
                        }
                        switch (pontIndivAtualPorJogador.getSinalSelecionado()) {
                              case Constantes.MAIS:
                                    resultado_pontuacao = pontuacaoAAcumular + tabelaJogadores.get(jogador.getId()).getPontuacao();
                                    tabelaJogadores.get(jogador.getId()).setPontuacao(resultado_pontuacao);
                                    ultimoResultadoAAcumular = Integer.parseInt(Constantes.MAIS + pontuacaoAAcumular);
                                    jogada.setPontuacaoIndividual(ultimoResultadoAAcumular);
                                    contJogadoresPontCiclo += 1;//guarda a info de que alguem pontuou nesse ciclo
                                    break;
                              case Constantes.MENOS:
                                    resultado_pontuacao = pontuacaoAAcumular - tabelaJogadores.get(jogador.getId()).getPontuacao();
                                    tabelaJogadores.get(jogador.getId()).setPontuacao(resultado_pontuacao);
                                    ultimoResultadoAAcumular = Integer.parseInt(Constantes.MENOS + pontuacaoAAcumular);
                                    jogada.setPontuacaoIndividual(ultimoResultadoAAcumular);
                                    break;
                        }
                  } else {
                        jogada.setPontuacaoIndividual(ultimoResultadoAAcumular);
                  }
                  //sobrescreve valor já com jogada atualizada para depois a leitura funcionar
                  jogada.setNomeJogador(jogador.getNome());
                  listaJogadoresJogadas.put(jogador.getId(), jogada);
                  tabelaJogadores.get(jogador.getId()).setUltimaJogada(jogada);
                  if (ultimoResultadoAAcumular != 0) {
                        salvaPontuacaoJogador(tabelaJogadores.get(jogador.getId()));
                  }
            } else {
                  jogada.setNomeJogador(jogador.getNome());
                  jogada.setPontuacaoIndividual(ultimoResultadoAAcumular);
                  listaJogadoresJogadas.put(jogador.getId(), jogada);
                  tabelaJogadores.get(jogador.getId()).setUltimaJogada(jogada);
            }
      }

      private int calculaPontuacaoAcumulada(List<PontuacaoIndividual> listaPontIndiv, Jogada jogada) {
            int pontuacaoAcumulada = 0;
            pontIndivAtualPorJogador = null;
            for (PontuacaoIndividual pi : listaPontIndiv) {
                  switch (pi.getCondicional()) {
                        case Constantes.E:
                              if (pi.getCorSelecionada().equalsIgnoreCase(getCorPelaLinhaSelecionada(jogada.getLinhaSelecionada(), pi)) && pi.getLinhaSelecionada().equalsIgnoreCase(getPAROUIMPAR(jogada.getLinhaSelecionada()))) {
                                    if (pi.getPontoAcerto() == null) {
                                          pi.iniciaAsVariaveisTransient();
                                    }
                                    pontuacaoAcumulada = pi.getPontoAcerto();
                                    pontIndivAtualPorJogador = pi;
                              }
                              break;
                        case Constantes.OU:
                              if (pi.getCorSelecionada().equalsIgnoreCase(getCorPelaLinhaSelecionada(jogada.getLinhaSelecionada(), pi)) || pi.getLinhaSelecionada().equalsIgnoreCase(getPAROUIMPAR(jogada.getLinhaSelecionada()))) {
                                    if (pi.getPontoAcerto() == null) {
                                          pi.iniciaAsVariaveisTransient();
                                    }
                                    pontuacaoAcumulada = pi.getPontoAcerto();
                                    pontIndivAtualPorJogador = pi;
                              }
                              break;
                  }
                  if (pontuacaoAcumulada != 0 || !(pontIndivAtualPorJogador == null)) {
                        break;
                  }
            }
            return pontuacaoAcumulada;
      }

      private void salvaPontuacaoJogador(Jogador jogador) {
            jogadorDAO = new JogadorDAO();
            jogadorDAO.beginTransaction();
            jogadorDAO.update(jogador);
            jogadorDAO.stopOperation(true);
      }
      /* Atualiza a pontuacao de todos antes do inicio da partida
    
       So deve ser chamado no inicio da execucao de um experimento novo, nunca quando o experimento ja tiver iniciado
    
       */

      private void atualizaPontuacaoInicial(int pinicial) {
            JogadorDAO dao = new JogadorDAO();
            dao.beginTransaction();
            List<Jogador> listaJogadoresExp = dao.encontraPorExperimento(experimento);
            for (Jogador jogador : listaJogadoresExp) {
                  jogador.setPontuacao(pinicial);
                  dao.update(jogador);
            }
            dao.stopOperation(true);
      }

      private void atualizaPontuacaoCulturalInicial(int pCultInicial) {
            JogadorDAO dao = new JogadorDAO();
            dao.beginTransaction();
            List<Jogador> listaJogadoresExp = dao.encontraPorExperimento(experimento);
            for (Jogador jogador : listaJogadoresExp) {
                  jogador.setPontuacaoCult(pCultInicial);
                  dao.update(jogador);
            }
            dao.stopOperation(true);
            Enumeration<TableObserver> referencias = listaTableBeans.elements();
            while (referencias.hasMoreElements()) { // fica no loop enquanto tem elementos pra listar
                  TableObserver obs = referencias.nextElement(); //ao chamar nextElement, ele ja muda o ponteiro pro proximo
                  obs.atualizaPontuacaoCulturalInicial(pCultInicial);
            }
      }

      private void listarJogadoresExperimento() {
            JogadorDAO dao = new JogadorDAO();
            dao.beginTransaction();
            listaJogadoresTotais = dao.encontraPorExperimento(experimento);
            dao.stopOperation(false);
            listaJogadoresReservas.addAll(listaJogadoresTotais);
      }

      private void atualizaMensagemPontuacaoCulturalJogadores(PontuacaoCultural pc) {
            if (pc.getSinalSelecionado().equalsIgnoreCase(Constantes.MAIS) && pc.getConsequenciaVerbalPositiva().trim().length() > 0) {
                  listaTableBeans.get(listaJogadores.get(0).getId()).exibeMensagemPontuacaoCultural(pc.getConsequenciaVerbalPositiva());
            } else if (pc.getSinalSelecionado().equalsIgnoreCase(Constantes.MENOS) && pc.getConsequenciaVerbalNegativa().trim().length() > 0) {
                  listaTableBeans.get(listaJogadores.get(0).getId()).exibeMensagemPontuacaoCultural(pc.getConsequenciaVerbalNegativa());
            }
      }

      private void atualizaPontuacaoTodosJogadores(String tipoAlerta) {
            List<Jogador> jogadores = new ArrayList<>();
            for (Jogador jog : listaJogadores) {
                  jogadores.add(tabelaJogadores.get(jog.getId()));
            }
            Enumeration<TableObserver> referencias = listaTableBeans.elements();
            while (referencias.hasMoreElements()) { // fica no loop enquanto tem elementos pra listar
                  TableObserver obs = referencias.nextElement(); //ao chamar nextElement, ele ja muda o ponteiro pro proximo
                  obs.atualizaPontuacao(jogadores, pontuacaoCult, contGeracao, tipoAlerta);
            }
            if (tablePesquisador.isEmpty() == false) {
                  tablePesquisador.get(experimento.getPesquisador().getId()).atualizaPontuacao(jogadores, pontuacaoCult, contGeracao, tipoAlerta);
            }
      }

      private void atualizaPontuacaoTodosJogadores() {
            List<Jogador> jogadores = new ArrayList<>();
            for (Jogador jog : listaJogadores) {
                  jogadores.add(tabelaJogadores.get(jog.getId()));
            }
            Enumeration<TableObserver> referencias = listaTableBeans.elements();
            while (referencias.hasMoreElements()) { // fica no loop enquanto tem elementos pra listar
                  TableObserver obs = referencias.nextElement(); //ao chamar nextElement, ele ja muda o ponteiro pro proximo
                  obs.atualizaPontuacao(jogadores, pontuacaoCult, contGeracao);
            }
            if (tablePesquisador.isEmpty() == false) {
                  tablePesquisador.get(experimento.getPesquisador().getId()).atualizaPontuacao(jogadores, pontuacaoCult, contGeracao);
            }
      }

      /*
    
    
       Metodo que recebe como parametro a referencia pra objetos que representam cada tela de um jogador
       Quando todos os jogadores se registram é que o experimento passa a estar apt a iniciar
    
       É synchronized para evitar que quando mais de jogador se registre ao mesmo tempo dê problema de concorrência
       */
      @Override
      public synchronized void registraObservador(TableObserver observer, Jogador jogador) {
            //guarda na tabela hash a referencia do table bean
            //usando como chave o Id do jogador que aquele tableBean representa
            if (listaTableBeans.get(jogador.getId()) == null
                    && estadoExecucao == registrandoJogadores) {
                  if (listaTableBeans.size() < listaJogadores.size()) {
                        boolean obsAdicionado = false;
                        for (Jogador jgdr : listaJogadores) {
                              if (Objects.equals(jogador.getOrdem(), jgdr.getOrdem())) {
                                    listaTableBeans.put(jogador.getId(), observer);
                                    tabelaJogadores.put(jogador.getId(), jogador);
                                    obsAdicionado = true;
                                    break;
                              }
                        }
                        if (!obsAdicionado) {
                              listaTableBeansReserva.put(jogador.getId(), observer);
                        }
                  } else {
                        listaTableBeansReserva.put(jogador.getId(), observer);
                  }
                  if (listaTableBeans.size() == listaJogadores.size()) {
                        estadoExecucao = coletandoDadosJogadores;
                        getDadosJogador();
                  } else {
                  }
            } else if (listaTableBeans.get(jogador.getId()) == null
                    && estadoExecucao == aguardandoJogadorEntrar) {
                  //Verifica se a ordem é aleatória
                  if (listaJogadores.contains(jogador)) {
                        listaTableBeans.put(jogador.getId(), observer);
                        tabelaJogadores.put(jogador.getId(), jogador);
                        contadorAtualJogador = 0;
                        estadoExecucao = coletandoDadosJogadores;
                        getDadosJogador();
                  } else {
                        listaTableBeansReserva.put(jogador.getId(), observer);
                  }
            } else if (listaTableBeans.get(jogador.getId()) == null
                    && estadoExecucao != registrandoJogadores && estadoExecucao != aguardandoJogadorEntrar) {
                  listaTableBeansReserva.put(jogador.getId(), observer);
            } else {
                  // TODO: Informar erro de jogador já registrado no experimento!
            }
      }

      @Override
      public int getTipoMatriz() {
            if (experimento != null) {
                  return this.experimento.getTipoMatriz();
            } else {
                  return Experimento.CIRCULO_VAZADO; //matrix default
            }
      }

      @Override
      public void registraObservador(TableObserver t, Pesquisador p) {
            tablePesquisador.put(p.getId(), t);
      }

      @Override
      public void removeRegistroObservador(Pesquisador pesquisador) {
            tablePesquisador.clear();
      }

      @Override
      public void removeRegistroObservador(Jogador jogador) {
            if (listaTableBeans.get(jogador.getId()) == null) {
                  //lanca alguma excecao, indicando que já foi removido da lista
            } else {
                  // remove da lista
                  listaTableBeans.remove(jogador.getId());
            }
      }

      // Método que retorna um boolean pra informar se deve habilitar ou desabilitar o chat
      public boolean verificaStatusChat() {
            if (!(this.experimento.getId() == null)) {
                  return experimento.getChat().equals(Constantes.DESABILITADO);
            } else {
                  return true;
            }
      }

      public void bloquearChat() {
            this.experimento.setChat(Constantes.DESABILITADO);
            ExperimentoDAO dao = new ExperimentoDAO();
            dao.beginTransaction();
            dao.update(experimento);
            dao.stopOperation(true);
      }

      public Experimento getExperimento() {
            return experimento;
      }

      public void setExperimento(Experimento experimentoExec) {
            this.experimento = experimentoExec;
      }

      public List<Jogador> getListaJogadores() {
            return listaJogadores;
      }

      public void setListaJogadores(List<Jogador> listaJogadores) {
            this.listaJogadores = listaJogadores;
      }

      public Condicao getCondicaoAtual() {
            return condicaoAtual;
      }

      public void setCondicaoAtual(Condicao condicaoAtual) {
            this.condicaoAtual = condicaoAtual;
      }

      public PontuacaoIndividual getPontIndivAtualPorJogador() {
            return pontIndivAtualPorJogador;
      }

      public void setPontIndivAtualPorJogador(PontuacaoIndividual pontIndivAtualPorJogador) {
            this.pontIndivAtualPorJogador = pontIndivAtualPorJogador;
      }

      public String getJustificativa() {
            return justificativa;
      }

      public void setJustificativa(String justificativa) {
            this.justificativa = justificativa;
      }

      public List<Jogada> getListaUltimasJogadas() {
            return listaUltimasJogadas;
      }

      public void setListaUltimasJogadas(List<Jogada> listaUltimasJogadas) {
            this.listaUltimasJogadas = listaUltimasJogadas;
      }

      public List<Jogador> getListaJogadoresReservas() {
            return listaJogadoresReservas;
      }
}
