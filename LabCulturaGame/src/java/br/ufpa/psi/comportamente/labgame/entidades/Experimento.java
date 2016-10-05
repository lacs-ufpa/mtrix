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
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Weslley
 */
@Entity
@Table (name="experimento")
public class Experimento implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String nome;
    private String objetivo;
    private String mensagemEspera;
    private String status;
    
    private boolean liberaVisualizarPontosIndivJogador; // <---- Responsável por exibir ou não a pontuação na tela do Mtrix.
    private boolean liberaVisualizarUltimaJogada; // <---- Responsável por exibir ou não a última jogada na tela do Mtrix.
    private boolean liberaAlertaPontuacao; // <----- Responsável por exibir ou não popup informando a pontuação na tela do Mtrix.
    
    private String justificativa; // <---- Valor setado quando um experimento for cancelado.
    
    private String tipoRotacao; // <---- Vai informar o tipo de rotação entre os jogadores desse experimento.
    
    private int tamanhoFilaJogadores; // <--- Armazena o tamanho da lista de jogadores "titulares".
    
    /// Atributos que serão definidos de acordo com o o tipo de rotação definido.
    private int porcentagemAcertoIndiv; // <--- No caso de rotação por porcentagem de acertos individual.
    private int porcentagemAcertoCult; // <--- No caso de rotação por porcentagem de acertos cultural.
    private int quantidadeJogadas; // <---- No caso de rotação após X jogadas.
    private String nivelComplexidade; // <--- No caso de rotação quando uma condição de complexidade X for atingida.
    
    // Atributo que será definido quando o tipo de rotação for porcentagem de acertos
    private int ultimosXCiclos;
    
    private Integer pontInicialIndividual=0;
    private Integer pontInicialCultural=0;
    
    private String chat; // <---- Informa se o chat estará habilitado ou não para um experimento.
    
    public static final int SEM_CIRCULO=1;    
    public static final int CIRCULO_VAZADO=2;
    //novo atibuto, que ajuda a identificar qual o formato da matriz utilizada no experimento    
    
    private int tipoMatriz=2; //valor default eh para matriz vazada
    
    private String mensagemFinalExperimento;
    
    private boolean ordemRandomicaJogadores;
    
    
    @Temporal(TemporalType.DATE)
    private Date dataCriacao;
    
    @Temporal(TemporalType.DATE)
    private Date dataExecucao;
    
    
    //Estados de um experimento
    @javax.persistence.Transient
    public static final String EXECUCAO="EXECUCAO";
    @javax.persistence.Transient
    public static final String CRIADO="CRIADO";
    @javax.persistence.Transient
    public static final String PAUSADO="PAUSADO";
    @javax.persistence.Transient
    public static final String CANCELADO="CANCELADO";
    
    @javax.persistence.Transient
    private int contQuantidadeCiclos = 1;
    
    
    @OneToMany(mappedBy = "experimento",targetEntity=Jogador.class)
    private List<Jogador> jogadores;
    
    @ManyToOne
    @JoinColumn(name="pesquisador_id", nullable=false)
    private Pesquisador pesquisador;
    
    @OneToMany(mappedBy = "experimento")
    private List<CondicaoExperimento> condicaoExperimentos;
    
    public Experimento(){
        tamanhoFilaJogadores = 0;
        dataCriacao = new Date();
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

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public Pesquisador getPesquisador() {
        return pesquisador;
    }

    public void setPesquisador(Pesquisador pesquisador) {
        this.pesquisador = pesquisador;
    }

    public List<CondicaoExperimento> getCondicaoExperimentos() {
        return condicaoExperimentos;
    }

    public void setCondicaoExperimentos(List<CondicaoExperimento> condicaoExperimentos) {
        this.condicaoExperimentos = condicaoExperimentos;
    }
 

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPontInicialIndividual() {
        return pontInicialIndividual;
    }

    public void setPontInicialIndividual(Integer pontInicialIndividual) {
        this.pontInicialIndividual = pontInicialIndividual;
    }

    public Integer getPontInicialCultural() {
        return pontInicialCultural;
    }

    public void setPontInicialCultural(Integer pontInicialCultural) {
        this.pontInicialCultural = pontInicialCultural;
    }

    public int getTipoMatriz() {
        return tipoMatriz;
    }

    public void setTipoMatriz(int tipoMatriz) {
        this.tipoMatriz = tipoMatriz;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getTipoRotacao() {
        return tipoRotacao;
    }

    public void setTipoRotacao(String tipoRotacao) {
        this.tipoRotacao = tipoRotacao;
    }

    public int getQuantidadeJogadas() {
        return quantidadeJogadas;
    }

    public void setQuantidadeJogadas(int quantidadeJogadas) {
        this.quantidadeJogadas = quantidadeJogadas;
    }

    public String getNivelComplexidade() {
        return nivelComplexidade;
    }

    public void setNivelComplexidade(String nivelComplexidade) {
        this.nivelComplexidade = nivelComplexidade;
    }

    public int getTamanhoFilaJogadores() {
        return tamanhoFilaJogadores;
    }

    public void setTamanhoFilaJogadores(int tamanhoFilaJogadores) {
        this.tamanhoFilaJogadores = tamanhoFilaJogadores;
    }

    public int getUltimosXCiclos() {
        return ultimosXCiclos;
    }

    public void setUltimosXCiclos(int ultimosXCiclos) {
        this.ultimosXCiclos = ultimosXCiclos;
    }

    public int getPorcentagemAcertoIndiv() {
        return porcentagemAcertoIndiv;
    }

    public void setPorcentagemAcertoIndiv(int porcentagemAcertoIndiv) {
        this.porcentagemAcertoIndiv = porcentagemAcertoIndiv;
    }

    public int getPorcentagemAcertoCult() {
        return porcentagemAcertoCult;
    }

    public void setPorcentagemAcertoCult(int porcentagemAcertoCult) {
        this.porcentagemAcertoCult = porcentagemAcertoCult;
    }

    public int getContQuantidadeCiclos() {
        return contQuantidadeCiclos;
    }

    public void setContQuantidadeCiclos(int contQuantidadeCiclos) {
        this.contQuantidadeCiclos = contQuantidadeCiclos;
    }

    public boolean isLiberaVisualizarPontosIndivJogador() {
        return liberaVisualizarPontosIndivJogador;
    }

    public void setLiberaVisualizarPontosIndivJogador(boolean liberaVisualizarPontosIndivJogador) {
        this.liberaVisualizarPontosIndivJogador = liberaVisualizarPontosIndivJogador;
    }

    public boolean isLiberaVisualizarUltimaJogada() {
        return liberaVisualizarUltimaJogada;
    }

    public void setLiberaVisualizarUltimaJogada(boolean liberaVisualizarUltimaJogada) {
        this.liberaVisualizarUltimaJogada = liberaVisualizarUltimaJogada;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataExecucao() {
        return dataExecucao;
    }

    public void setDataExecucao(Date dataExecucao) {
        this.dataExecucao = dataExecucao;
    }

    public boolean isLiberaAlertaPontuacao() {
        return liberaAlertaPontuacao;
    }

    public void setLiberaAlertaPontuacao(boolean liberaAlertaPontuacao) {
        this.liberaAlertaPontuacao = liberaAlertaPontuacao;
    }

    public String getMensagemEspera() {
        return mensagemEspera;
    }

    public void setMensagemEspera(String mensagemEspera) {
        this.mensagemEspera = mensagemEspera;
    }

    public String getMensagemFinalExperimento() {
        return mensagemFinalExperimento;
    }

    public void setMensagemFinalExperimento(String mensagemFinalExperimento) {
        this.mensagemFinalExperimento = mensagemFinalExperimento;
    }

    public boolean isOrdemRandomicaJogadores() {
        return ordemRandomicaJogadores;
    }

    public void setOrdemRandomicaJogadores(boolean ordemRandomicaJogadores) {
        this.ordemRandomicaJogadores = ordemRandomicaJogadores;
    }
    
    
}
