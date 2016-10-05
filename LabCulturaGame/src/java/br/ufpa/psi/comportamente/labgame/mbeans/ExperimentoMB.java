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

import br.ufpa.psi.comportamente.labgame.dao.CondicaoDAO;
import br.ufpa.psi.comportamente.labgame.dao.CondicaoExperimentoDAO;
import br.ufpa.psi.comportamente.labgame.dao.ExperimentoDAO;
import br.ufpa.psi.comportamente.labgame.dao.JogadorDAO;
import br.ufpa.psi.comportamente.labgame.entidades.Condicao;
import br.ufpa.psi.comportamente.labgame.entidades.CondicaoExperimento;
import br.ufpa.psi.comportamente.labgame.entidades.Experimento;
import br.ufpa.psi.comportamente.labgame.entidades.Jogador;
import br.ufpa.psi.comportamente.labgame.entidades.Pesquisador;
import br.ufpa.psi.comportamente.labgame.mbeans.models.CondicoesDataModel;
import br.ufpa.psi.comportamente.labgame.mbeans.models.CondicoesLazyDataModel;
import br.ufpa.psi.comportamente.labgame.mbeans.models.CondicoesNaoCadastradasExperimentoLazyDataModel;
import br.ufpa.psi.comportamente.labgame.mbeans.models.ExperimentosLazyDataModel;
import br.ufpa.psi.comportamente.labgame.mbeans.models.JogadoresDataModel;
import br.ufpa.psi.comportamente.labgame.mbeans.models.JogadoresLazyDataModel;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Weslley
 */
@ManagedBean
@SessionScoped
public class ExperimentoMB implements Serializable {

      private static final long serialVersionUID = 1L;

      private List<Experimento> listaExperimentos;
      private List<Jogador> listaJogadores;

      private Experimento experimento;
      private ExperimentoDAO experimentoDAO;
      private JogadorDAO jogadorDAO;
      private CondicaoDAO condicaoDAO;

      private JogadoresDataModel jogadoresModel;
      private LazyDataModel<Experimento> experimentosModel;
      private CondicoesDataModel condicoesNoExperimentoModel;
      private LazyDataModel<Condicao> condicoesNaoCadastradasExperimentoModel;
      private LazyDataModel<Jogador> listaJogadoresLivresModel;

      private List<CondicaoExperimento> listaCondicoesExperimento;

      private Experimento experimentoSelecionado;
      private List<Jogador> jogadoresSelecionados;
      private List<Condicao> condicoesSelecionadas;
      private List<Condicao> condicoesSelecionadasAdicionadas;
      private List<String> posicoesDefinidas;

      private List<Condicao> condicoesSource;
      private List<Condicao> condicoesTarget;
      private DualListModel<Condicao> dualListCondicoes;

      private Jogador jogador;

      private Jogador jogadorSelecionado;

      private LazyDataModel<Condicao> condicoesModel;
      private List<Condicao> listaCondicoes;

      private String rotacaoPorcentAcerto;
      private String rotacaoXJogadas;
      private String rotacaoNivelComplex;

      //Lista com jogadores selecionados na tela de edição do experimento
      private List<Jogador> listaJogadoresLivresSelec;
      private List<Jogador> listaJogadoresExperSelec;

      private String criterio;

      private String nomeExperimentoCopiado = "";

      private boolean isAtualizando = false;

      public ExperimentoMB() {
            experimento = new Experimento();
            listaCondicoes = new ArrayList<>();
            jogador = new Jogador();
            condicoesSelecionadas = new ArrayList<>();
            posicoesDefinidas = new ArrayList<>();
            carregaListaExperimentos();
            listarTiposRotacao();
            nomeExperimentoCopiado = "";
            experimentosModel = new ExperimentosLazyDataModel();
            condicoesModel = new CondicoesLazyDataModel();
      }

      private void listarTiposRotacao() {

            rotacaoNivelComplex = Constantes.APOS_CONDICAO_COMPLEXA;
            rotacaoPorcentAcerto = Constantes.APOS_NIVEL_ACERTO;
            rotacaoXJogadas = Constantes.APOS_X_JOGADAS;
      }

      /**
       * Recebe uma string do tipo "1,3,6" e entende que são 3 posições pra
       * condição de entrada, nas poições 1 3 e 6.
       */
      private List<CondicaoExperimento> processaStringEntrada(Condicao condicao) {

            List<CondicaoExperimento> list = new ArrayList<>();

            // Tratar posições repetidas.
            String posicoes;

            if (isAtualizando) {
                  posicoes = removePosicaoRepetida(condicao.getPosicao());
                  condicao.setPosicao(posicoes);
            }

            posicoes = condicao.getPosicao();

            isAtualizando = false;
            /// passa a string e o delimitador pra quebrar os valores
            StringTokenizer tnzer = new StringTokenizer(posicoes, ",");

            String token;

            while (tnzer.hasMoreTokens()) {
                  token = tnzer.nextToken();
                  token = token.replaceAll(" ", "");
                  Integer posicao = Integer.parseInt(token);
                  CondicaoExperimento ce = new CondicaoExperimento();
                  ce.setPosicao(posicao);
                  ce.setCondicao(condicao);
                  list.add(ce);
            }

            return list;
      }

      private String removePosicaoRepetida(String posicoes) {
            List<Character> listaAux = new ArrayList<>();
            String novaListaPosicoes = "";
            char aux;

            for (int i = 0; i < posicoes.length(); i++) {
                  aux = posicoes.charAt(i);
                  if (aux == ',' || aux == ' ') {
                        listaAux.add(aux);
                        novaListaPosicoes = novaListaPosicoes + aux;
                  } else if (!listaAux.contains(aux)) {
                        listaAux.add(aux);
                        novaListaPosicoes = novaListaPosicoes + aux;
                  }
            }

            return novaListaPosicoes;
      }

      public void geraCopiaExperimento() {
            Experimento expAux = new Experimento();
            expAux.setNome(nomeExperimentoCopiado);
            expAux.setContQuantidadeCiclos(experimentoSelecionado.getContQuantidadeCiclos());
            expAux.setLiberaVisualizarPontosIndivJogador(experimentoSelecionado.isLiberaVisualizarPontosIndivJogador());
            expAux.setLiberaVisualizarUltimaJogada(experimentoSelecionado.isLiberaVisualizarUltimaJogada());
            expAux.setNivelComplexidade(experimentoSelecionado.getNivelComplexidade());
            expAux.setObjetivo(experimentoSelecionado.getObjetivo());
            expAux.setPesquisador(experimentoSelecionado.getPesquisador());
            expAux.setPontInicialCultural(experimentoSelecionado.getPontInicialCultural());
            expAux.setPontInicialIndividual(experimentoSelecionado.getPontInicialIndividual());
            expAux.setPorcentagemAcertoCult(experimentoSelecionado.getPorcentagemAcertoCult());
            expAux.setPorcentagemAcertoIndiv(experimentoSelecionado.getPorcentagemAcertoIndiv());
            expAux.setQuantidadeJogadas(experimentoSelecionado.getQuantidadeJogadas());
            expAux.setTipoMatriz(experimentoSelecionado.getTipoMatriz());
            expAux.setTamanhoFilaJogadores(experimentoSelecionado.getTamanhoFilaJogadores());
            expAux.setChat(experimentoSelecionado.getChat());
            expAux.setTipoRotacao(experimentoSelecionado.getTipoRotacao());
            expAux.setUltimosXCiclos(experimentoSelecionado.getUltimosXCiclos());
            expAux.setStatus(Constantes.CRIADO);

            experimentoDAO = new ExperimentoDAO();
            experimentoDAO.beginTransaction();
            experimentoDAO.save(expAux);
            experimentoDAO.stopOperation(true);

            try {
                  copiaJogadoresExperimento(expAux);
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                  Logger.getLogger(ExperimentoMB.class.getName()).log(Level.SEVERE, null, ex);
            }

            geraCopiaListaCondicaoExperimento(expAux, experimentoSelecionado);

            carregaListaExperimentos();

            condicoesSelecionadas = new ArrayList<>();

            nomeExperimentoCopiado = "";

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Experimento copiado com sucesso!", "Seu experimento foi cadastrado"));

      }

      private void copiaJogadoresExperimento(Experimento exp) throws NoSuchAlgorithmException, UnsupportedEncodingException {
            JogadorDAO dao = new JogadorDAO();
            dao.beginTransaction();
            List<Jogador> jogadores = dao.encontraPorExperimento(experimentoSelecionado);
            dao.stopOperation(false);

            for (Jogador jgdr : jogadores) {
                  Jogador jogAux = new Jogador();
                  jogAux.setNome(jgdr.getNome() + exp.getNome());
                  jogAux.setUsername(jgdr.getUsername() + exp.getNome());

                  String passwd = "123";
                  MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
                  byte messageDigest[] = algorithm.digest(passwd.getBytes("UTF-8"));

                  StringBuilder hexString = new StringBuilder();
                  for (byte b : messageDigest) {
                        hexString.append(String.format("%02X", 0xFF & b));
                  }
                  String senhaCript = hexString.toString();

                  jogAux.setPassword(senhaCript);
                  jogAux.setExperimento(exp);
                  jogAux.setOrdem(jgdr.getOrdem());

                  dao.beginTransaction();
                  dao.save(jogAux);
                  dao.stopOperation(true);
            }
      }

      private void geraCopiaListaCondicaoExperimento(Experimento experimento, Experimento experimentoSelecionado) {
            CondicaoDAO dao = new CondicaoDAO();
            dao.beginTransaction();
            List<CondicaoExperimento> lista = dao.encontraCondExpPorExperimento(experimentoSelecionado);
            dao.stopOperation(false);

            CondicaoExperimentoDAO condExpDAO = new CondicaoExperimentoDAO();
            condExpDAO.beginTransaction();
            for (CondicaoExperimento ce : lista) {
                  CondicaoExperimento novaCE = new CondicaoExperimento();
                  novaCE.setCondicao(ce.getCondicao());
                  novaCE.setExperimento(experimento);
                  novaCE.setPosicao(ce.getPosicao());
                  condExpDAO.save(novaCE);
            }

            condExpDAO.stopOperation(true);
      }

      public void cadastraExperimento(Pesquisador pesquisador) {

            if (experimento.getTamanhoFilaJogadores() == 0) {
                  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Não foi possível cadastrar experimento!       "
                          + "Certifique-se de que foi definido um número de jogadores por geração."));
            } else if (jogadoresSelecionados.size() < experimento.getTamanhoFilaJogadores()) {
                  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Não foi possível cadastrar experimento!       "
                          + "Certifique-se de que o número de jogadores por geração não é MAIOR que "
                          + "o número total de jogadores escolhidos"));
            } else if (condicoesSelecionadas == null || condicoesSelecionadas.isEmpty()) {
                  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Não foi possível cadastrar experimento!       "
                          + "Certifique-se de que pelo menos uma condição tenha sido selecionada"));
            } else {

                  List<CondicaoExperimento> listaCondExp;

                  //Aqui está implementada a validação das posições definidas para as
                  //condições do expAux.
                  boolean validador = true;

                  List<CondicaoExperimento> listaTodasCondExp = new ArrayList<>();

                  for (Condicao condicaoSelecionada : condicoesSelecionadas) {
                        listaCondExp = processaStringEntrada(condicaoSelecionada);
                        listaTodasCondExp.addAll(listaCondExp);
                  }

                  List<Integer> posicoes = new ArrayList<>();
                  for (CondicaoExperimento condExp : listaTodasCondExp) {
                        posicoes.add(condExp.getPosicao());
                  }

                  for (int j = 0; j < posicoes.size(); j++) {
                        int cont = 0;
                        for (Integer i : posicoes) {
                              if (Objects.equals(i, j + 1)) {
                                    cont += 1;
                              }
                        }

                        if (cont < 1 || cont > 1) {
                              //Exibe a mensagem de erro, já que algum valor tá fora do intervalo
                              //ou se repete.
                              //TODO: Criar forma de finalizar a operação de cadastrar!
                              validador = false;
                              break;
                        }
                  }

                  if (validador == true) {

                        CondicaoExperimentoDAO condExpDAO = new CondicaoExperimentoDAO();
                        experimentoDAO = new ExperimentoDAO();

                        experimento.setPesquisador(pesquisador);
                        experimento.setStatus(Constantes.CRIADO);

                        boolean isVerificado = verificarMudancaGeracao();

                        if (isVerificado) {

                              // TODO: Inserir um bloco try-catch aqui para o tratamento de erro interno e cancelar o cadastro do
                              // do expAux passando um false dentro do método stopOperation do ExperimentoDAO.
                              experimentoDAO.beginTransaction();
                              experimentoDAO.save(experimento);
                              experimentoDAO.stopOperation(true);

                              //Aqui será feita a persistência das entidades CondicaoExperimento.
                              for (Condicao condicoesSelecionada : condicoesSelecionadas) {
                                    condExpDAO.beginTransaction();
                                    listaCondExp = processaStringEntrada(condicoesSelecionada);
                                    for (CondicaoExperimento condExp : listaCondExp) {
                                          condExp.setExperimento(experimento);
                                          condExpDAO.save(condExp);
                                    }
                                    condExpDAO.stopOperation(true);
                              }

                              if (jogadoresSelecionados != null) {
                                    jogadorDAO = new JogadorDAO();
                                    jogadorDAO.beginTransaction();

                                    for (Jogador jgdr : jogadoresSelecionados) {
                                          jgdr.setExperimento(experimento);
                                          jogadorDAO.update(jgdr);
                                    }
                                    jogadorDAO.stopOperation(true);
                              }
                              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Experimento cadastrado com sucesso!", "Seu experimento foi cadastrado"));

                        } else {
                              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Não foi possível cadastrar o experimento. "
                                      + "Favor verificar se foi definido um modelo de mudança de geração."));
                        }
                  } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Não foi possível cadastrar o experimento. "
                                + "Favor verificar as posições definidas para as condições."));
                  }

            }

            carregaListaExperimentos();

            experimento = new Experimento();
            condicoesSelecionadas = new ArrayList<>();

      }

      private boolean verificarMudancaGeracao() {
            try {
                  if (criterio.equalsIgnoreCase("NIVEL_ACERTO_INDIV") || criterio.equalsIgnoreCase("NIVEL_ACERTO_CULTURAL")) {
                        experimento.setTipoRotacao(Constantes.APOS_NIVEL_ACERTO);
                  } else if (criterio.equalsIgnoreCase("NIVEL_COMPLEXIDADE")) {
                        experimento.setTipoRotacao(Constantes.APOS_CONDICAO_COMPLEXA);
                  } else if (criterio.equalsIgnoreCase("NUMERO_CICLO")) {
                        experimento.setTipoRotacao(Constantes.APOS_X_JOGADAS);
                  }
                  return true;
            } catch (Exception e) {
                  return false;
            }
      }

      public void atualizaExperimento(Pesquisador pesquisador) {

            jogadoresSelecionados = new ArrayList<>();

            if (listaJogadoresExperSelec != null || listaJogadoresLivresSelec != null) {
                  jogadorDAO = new JogadorDAO();
                  jogadorDAO.beginTransaction();

                  if (listaJogadoresExperSelec != null) {
                        jogadoresSelecionados.addAll(listaJogadoresExperSelec);
                  }

                  if (listaJogadoresLivresSelec != null) {
                        jogadoresSelecionados.addAll(listaJogadoresLivresSelec);
                  }

                  boolean ordemGeracao = verificaOrdemJogadores(jogadoresSelecionados);

                  if (ordemGeracao) {
                        for (Jogador jgdr : jogadoresSelecionados) {
                              jgdr.setExperimento(experimentoSelecionado);
                              jogadorDAO.update(jgdr);
                        }
                        jogadorDAO.stopOperation(true);

                        if (jogadoresSelecionados.size() < experimento.getTamanhoFilaJogadores()) {
                              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Não foi possível cadastrar experimento!       "
                                      + "Certifique-se de que o número de jogadores por geração não é MAIOR que "
                                      + "o número total de jogadores escolhidos"));
                        } else {
                              List<CondicaoExperimento> listaCondExp;

                              List<Condicao> listaCondicoesSelecionadas = new ArrayList<>();

                              isAtualizando = true;

                              //Aqui está implementada a validação das posições definidas para as
                              //condições do expAux.
                              boolean validador = true;

                              if (!(condicoesSelecionadas.isEmpty()) || !(condicoesSelecionadasAdicionadas.isEmpty())) {

                                    if (condicoesSelecionadas.isEmpty() == false) {
                                          listaCondicoesSelecionadas.addAll(condicoesSelecionadas);
                                    }
                                    if (condicoesSelecionadasAdicionadas.isEmpty() == false) {
                                          listaCondicoesSelecionadas.addAll(condicoesSelecionadasAdicionadas);
                                    }

                                    List<CondicaoExperimento> listaTodasCondExp = new ArrayList<>();
                                    for (Condicao condicaoSelecionada : listaCondicoesSelecionadas) {
                                          listaCondExp = processaStringEntrada(condicaoSelecionada);
                                          listaTodasCondExp.addAll(listaCondExp);
                                    }

                                    List<Integer> posicoes = new ArrayList<>();
                                    for (CondicaoExperimento condExp : listaTodasCondExp) {
                                          posicoes.add(condExp.getPosicao());
                                    }

                                    for (int j = 0; j < posicoes.size(); j++) {
                                          int cont = 0;
                                          for (Integer i : posicoes) {
                                                if (Objects.equals(i, j + 1)) {
                                                      cont += 1;
                                                }
                                          }

                                          if (cont < 1 || cont > 1) {
                                                //Exibe a mensagem de erro, já que algum valor tá fora do intervalo
                                                //ou se repete.
                                                //TODO: Criar forma de finalizar a operação de cadastrar!
                                                validador = false;
                                                break;
                                          }
                                    }
                              }

                              if (validador == true) {

                                    CondicaoExperimentoDAO condExpDAO = new CondicaoExperimentoDAO();
                                    experimentoDAO = new ExperimentoDAO();

                                    experimentoSelecionado.setPesquisador(pesquisador);
                                    experimentoSelecionado.setStatus("CRIADO");

                                    boolean isVerificado = verificarMudancaGeracao();

                                    if (isVerificado) {

                                          experimentoDAO.beginTransaction();
                                          experimentoDAO.update(experimentoSelecionado);
                                          experimentoDAO.stopOperation(true);

                                          //Aqui será feita a persistência das entidades CondicaoExperimento.
                                          if (!listaCondicoesSelecionadas.isEmpty()) {
                                                for (Condicao listaCondicoesSelecionada : listaCondicoesSelecionadas) {
                                                      List<CondicaoExperimento> listaCondExpRemover = condExpDAO.encontraCondExpPorCondicaoExperimento(experimentoSelecionado, listaCondicoesSelecionada);
                                                      for (CondicaoExperimento condExp : listaCondExpRemover) {
                                                            condExpDAO.beginTransaction();
                                                            condExpDAO.delete(condExp);
                                                            condExpDAO.stopOperation(true);
                                                      }
                                                      listaCondExp = processaStringEntrada(listaCondicoesSelecionada);
                                                      for (CondicaoExperimento condExp : listaCondExp) {
                                                            condExp.setExperimento(experimentoSelecionado);
                                                            condExpDAO.beginTransaction();
                                                            condExpDAO.save(condExp);
                                                            condExpDAO.stopOperation(true);
                                                      }
                                                }
                                          }

                                          experimentoSelecionado = new Experimento();
                                          condicoesSelecionadas = new ArrayList<>();
                                          condicoesSelecionadasAdicionadas = new ArrayList<>();
                                          listaJogadoresExperSelec = new ArrayList<>();
                                          listaJogadoresLivresSelec = new ArrayList<>();
                                          condicoesNoExperimentoModel = null;

                                          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Experimento atualizado com sucesso!", "Seu experimento foi atualizado"));

                                    } else {
                                          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Não foi possível cadastrar o experimento. "
                                                  + "Favor verificar se foi definido um modelo de mudança de geração."));
                                    }
                              } else {
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Não foi possível atualizar o experimento. "
                                            + "Favor verificar as posições definidas para as condições."));
                              }

                        }

                  } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Não foi possível atualizar o experimento. "
                                + "Favor verificar a ordem definida para os jogadores."));
                  }
            }

            experimentoSelecionado = new Experimento();
            listaJogadoresExperSelec = new ArrayList<>();
            listaJogadoresLivresSelec = new ArrayList<>();

      }

      private boolean verificaOrdemJogadores(List<Jogador> jogadores) {
            boolean validador = true;
            int cont = 1;

            while (cont <= jogadores.size()) {
                  boolean incrementa = false;
                  for (Jogador jgdr : jogadores) {
                        if (jgdr.getOrdem() == cont) {
                              incrementa = true;
                              break;
                        }
                  }
                  if (incrementa) {
                        cont++;
                  } else {
                        validador = false;
                        break;
                  }
            }
            return validador;
      }

      private void carregaListaExperimentos() {
            experimentoDAO = new ExperimentoDAO();
            listaExperimentos = new ArrayList<>();

            Object pesquisadorLogado = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Pesquisador pesquisador;

            if (pesquisadorLogado instanceof UserDetails) {
                  pesquisador = (Pesquisador) ((UserDetails) pesquisadorLogado);
            } else {
                  pesquisador = (Pesquisador) pesquisadorLogado;
            }

            experimentoDAO.beginTransaction();
            listaExperimentos = experimentoDAO.findPesquisador(pesquisador);
            experimentoDAO.stopOperation(false);
      }

      public void listarJogadores() {

            jogadorDAO = new JogadorDAO();

            jogadorDAO.beginTransaction();
            List<Jogador> jogadores = jogadorDAO.encontraPorExperimento(experimentoSelecionado);
            jogadorDAO.stopOperation(true);

            jogadoresModel = new JogadoresDataModel(jogadores);

      }

      public void listarJogadoresExperimentoNulo() {
            criterio = "";

            listaJogadoresLivresModel = new JogadoresLazyDataModel();
      }

      public void listarCondicoesEJogadoresExperimento() {
            listarDataModelCondicoes();
            listarJogadoresExperimentoNulo();
      }

      /**
       * Método responsável por listar as condições e os jogadores manipulados
       * na Edição.
       *
       * Aqui será feita a listagem dos jogadores e das condições.Este método
       * será chamado pra popular a tela de edição do Experimento.
       *
       * @param experimento
       *
       */
      public void listarCondicoesEJogadoresExperimento(Experimento experimento) {
            this.experimentoSelecionado = experimento;
            listarDataModelCondicoes();
            listarJogadores();
            listarJogadoresExperimentoNulo();

      }

      private void listarDataModelCondicoes() {

            CondicaoExperimentoDAO dao = new CondicaoExperimentoDAO();
            listaCondicoesExperimento = new ArrayList<>();

            CondicaoDAO condDAO = new CondicaoDAO();

            dao.beginTransaction();
            listaCondicoesExperimento = dao.encontraCondExpPorExperimento(experimentoSelecionado);
            dao.stopOperation(false);

            condDAO.beginTransaction();

            List<Condicao> todasAsCondicoes = condDAO.findAll(Condicao.class);

            condDAO.stopOperation(false);

            List<Condicao> condicoesNoExperimento = concatenaCondicoesIguais(listaCondicoesExperimento, todasAsCondicoes);

            condicoesNoExperimentoModel = new CondicoesDataModel(condicoesNoExperimento);

            //todasAsCondicoes.removeAll(condicoesNoExperimento);
            List<Condicao> listaAux = new ArrayList<>();
            listaAux.addAll(todasAsCondicoes);
            for (Condicao condicao : condicoesNoExperimento) {
                  for (Condicao todasCond : listaAux) {
                        if (Objects.equals(todasCond.getId(), condicao.getId())) {
                              todasAsCondicoes.remove(todasCond);
                        }
                  }
            }

            condicoesNaoCadastradasExperimentoModel = new CondicoesNaoCadastradasExperimentoLazyDataModel(experimentoSelecionado);

      }

      /**
       * Método responsável por: -realizar a concatenação dos objetos
       * CondicaoExperimento que apresentarem a mesma Condição como atributo;
       * -listar as posições presentes em cada condição.
       */
      private List<Condicao> concatenaCondicoesIguais(List<CondicaoExperimento> listaCondicao, List<Condicao> listaTodos) {

            List<Condicao> listaCond = new ArrayList<>();
            List<Long> ids = new ArrayList<>();
            List<Condicao> lista = new ArrayList<>(listaTodos);

            String posicao;

            for (CondicaoExperimento condExp : listaCondicao) {
                  if (listaCond.isEmpty()) {
                        // TODO: Terminar essa lógica de manipulação de entidades!
                        posicao = String.valueOf(condExp.getPosicao());
                        Condicao cond = condExp.getCondicao();
                        cond.setPosicao(posicao);
                        listaCond.add(cond);
                        ids.add(cond.getId());
                  } else if (listaCond.contains(condExp.getCondicao())) {
                        posicao = String.valueOf(condExp.getPosicao());
                        int index = listaCond.indexOf(condExp.getCondicao());
                        listaCond.get(index).setPosicao(listaCond.get(index).getPosicao() + ',');
                        listaCond.get(index).setPosicao(listaCond.get(index).getPosicao() + posicao);

                  } else if (!listaCond.contains(condExp.getCondicao())) {
                        posicao = String.valueOf(condExp.getPosicao());
                        Condicao cond = condExp.getCondicao();
                        cond.setPosicao(posicao);
                        listaCond.add(cond);
                        ids.add(cond.getId());
                  }
            }

            Predicate<Condicao> predicado;

            predicado = new PredicadoFiltroCondicoes(ids);

            lista.removeIf(predicado);

            lista.clear();

            lista.addAll(listaCond);

            return lista;

      }

      public Experimento getExperimento() {
            return experimento;
      }

      public void setExperimento(Experimento experimento) {
            this.experimento = experimento;
      }

      public ExperimentoDAO getExperimentoDAO() {
            return experimentoDAO;
      }

      public void setExperimentoDAO(ExperimentoDAO experimentoDAO) {
            this.experimentoDAO = experimentoDAO;
      }

      public List<Experimento> getListaExperimentos() {
            return listaExperimentos;
      }

      public void setListaExperimentos(List<Experimento> listaExperimentos) {
            this.listaExperimentos = listaExperimentos;
      }

      public Experimento getExperimentoSelecionado() {
            return experimentoSelecionado;
      }

      public void setExperimentoSelecionado(Experimento experimentoSelecionado) {
            this.experimentoSelecionado = experimentoSelecionado;
      }

      public List<Jogador> getListaJogadores() {
            return listaJogadores;
      }

      public void setListaJogadores(List<Jogador> listaJogadores) {
            this.listaJogadores = listaJogadores;
      }

      public JogadoresDataModel getJogadoresModel() {
            return jogadoresModel;
      }

      public void setJogadoresModel(JogadoresDataModel jogadoresModel) {
            this.jogadoresModel = jogadoresModel;
      }

      public List<Jogador> getJogadoresSelecionados() {
            return jogadoresSelecionados;
      }

      public void setJogadoresSelecionados(List<Jogador> jogadoresSelecionados) {
            this.jogadoresSelecionados = jogadoresSelecionados;
      }

      public JogadorDAO getJogadorDAO() {
            return jogadorDAO;
      }

      public void setJogadorDAO(JogadorDAO jogadorDAO) {
            this.jogadorDAO = jogadorDAO;
      }

      public List<Condicao> getListaCondicoes() {
            return listaCondicoes;
      }

      public void setListaCondicoes(List<Condicao> listaCondicoes) {
            this.listaCondicoes = listaCondicoes;
      }

      public CondicaoDAO getCondicaoDAO() {
            return condicaoDAO;
      }

      public void setCondicaoDAO(CondicaoDAO condicaoDAO) {
            this.condicaoDAO = condicaoDAO;
      }

      public CondicoesDataModel getCondicoesNoExperimentoModel() {
            return condicoesNoExperimentoModel;
      }

      public void setCondicoesNoExperimentoModel(CondicoesDataModel condicoesNoExperimentoModel) {
            this.condicoesNoExperimentoModel = condicoesNoExperimentoModel;
      }

      public List<Condicao> getCondicoesSelecionadas() {
            return condicoesSelecionadas;
      }

      public void setCondicoesSelecionadas(List<Condicao> condicoesSelecionadas) {
            this.condicoesSelecionadas = condicoesSelecionadas;
      }

      public DualListModel<Condicao> getDualListCondicoes() {
            return dualListCondicoes;
      }

      public void setDualListCondicoes(DualListModel<Condicao> dualListCondicoes) {
            this.dualListCondicoes = dualListCondicoes;
      }

      public List<Condicao> getCondicoesSource() {
            return condicoesSource;
      }

      public void setCondicoesSource(List<Condicao> condicoesSource) {
            this.condicoesSource = condicoesSource;
      }

      public List<Condicao> getCondicoesTarget() {
            return condicoesTarget;
      }

      public void setCondicoesTarget(List<Condicao> condicoesTarget) {
            this.condicoesTarget = condicoesTarget;
      }

      public Jogador getJogador() {
            return jogador;
      }

      public void setJogador(Jogador jogador) {
            this.jogador = jogador;
      }

      public Jogador getJogadorSelecionado() {
            return jogadorSelecionado;
      }

      public void setJogadorSelecionado(Jogador jogadorSelecionado) {
            this.jogadorSelecionado = jogadorSelecionado;
      }

      public List<Condicao> getCondicoesSelecionadasAdicionadas() {
            return condicoesSelecionadasAdicionadas;
      }

      public void setCondicoesSelecionadasAdicionadas(List<Condicao> condicoesSelecionadasAdicionadas) {
            this.condicoesSelecionadasAdicionadas = condicoesSelecionadasAdicionadas;
      }

      public String getRotacaoPorcentAcerto() {
            return rotacaoPorcentAcerto;
      }

      public void setRotacaoPorcentAcerto(String rotacaoPorcentAcerto) {
            this.rotacaoPorcentAcerto = rotacaoPorcentAcerto;
      }

      public String getRotacaoXJogadas() {
            return rotacaoXJogadas;
      }

      public void setRotacaoXJogadas(String rotacaoXJogadas) {
            this.rotacaoXJogadas = rotacaoXJogadas;
      }

      public String getRotacaoNivelComplex() {
            return rotacaoNivelComplex;
      }

      public void setRotacaoNivelComplex(String rotacaoNivelComplex) {
            this.rotacaoNivelComplex = rotacaoNivelComplex;
      }

      public String getCriterio() {
            return criterio;
      }

      public void setCriterio(String criterio) {
            this.criterio = criterio;
      }

      public String getNomeExperimentoCopiado() {
            return nomeExperimentoCopiado;
      }

      public void setNomeExperimentoCopiado(String nomeExperimentoCopiado) {
            this.nomeExperimentoCopiado = nomeExperimentoCopiado;
      }

      public List<String> getPosicoesDefinidas() {
            return posicoesDefinidas;
      }

      public void setPosicoesDefinidas(List<String> posicoesDefinidas) {
            this.posicoesDefinidas = posicoesDefinidas;
      }

      public List<Jogador> getListaJogadoresLivresSelec() {
            return listaJogadoresLivresSelec;
      }

      public void setListaJogadoresLivresSelec(List<Jogador> listaJogadoresLivresSelec) {
            this.listaJogadoresLivresSelec = listaJogadoresLivresSelec;
      }

      public List<Jogador> getListaJogadoresExperSelec() {
            return listaJogadoresExperSelec;
      }

      public void setListaJogadoresExperSelec(List<Jogador> listaJogadoresExperSelec) {
            this.listaJogadoresExperSelec = listaJogadoresExperSelec;
      }

      public LazyDataModel<Experimento> getExperimentosModel() {
            return experimentosModel;
      }

      public void setExperimentosModel(LazyDataModel<Experimento> experimentosModel) {
            this.experimentosModel = experimentosModel;
      }

      public LazyDataModel<Condicao> getCondicoesModel() {
            return condicoesModel;
      }

      public void setCondicoesModel(LazyDataModel<Condicao> condicoesModel) {
            this.condicoesModel = condicoesModel;
      }

      public LazyDataModel<Condicao> getCondicoesNaoCadastradasExperimentoModel() {
            return condicoesNaoCadastradasExperimentoModel;
      }

      public void setCondicoesNaoCadastradasExperimentoModel(LazyDataModel<Condicao> condicoesNaoCadastradasExperimentoModel) {
            this.condicoesNaoCadastradasExperimentoModel = condicoesNaoCadastradasExperimentoModel;
      }

      public LazyDataModel<Jogador> getListaJogadoresLivresModel() {
            return listaJogadoresLivresModel;
      }

      public void setListaJogadoresLivresModel(LazyDataModel<Jogador> listaJogadoresLivresModel) {
            this.listaJogadoresLivresModel = listaJogadoresLivresModel;
      }

}
