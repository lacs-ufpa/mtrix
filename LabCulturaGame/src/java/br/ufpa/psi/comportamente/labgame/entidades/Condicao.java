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
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Weslley
 */
@Entity
@Table(name = "condicao")
public class Condicao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private String nome;
    private String objetivo;
    private String criador;
    private String identificador;

    private String incrementoPont;

    private String limiteRodadasIncrem;
    private String limitePorcenAcertoIncrem;
   

    @Transient
    private String posicao;
    
    @Transient
    private String nomesExperimentos;

    private String nivelComplexidade;
    
    //Indica se a condicao ja foi usada em algum experimento.
    private boolean emUso;

    /// criterio principal de parada
    private Integer fimPorQuantCiclo;
    private Integer ultimosXCiclos;
    private Integer minimoCiclos;
    private Integer fimPorPorcentAcertoIndiv;
    private Integer fimPorPorcentAcertoCult;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "condicao_pontindiv", joinColumns = @JoinColumn(name = "condicao_id"), inverseJoinColumns = @JoinColumn(name = "pontIndiv_id"))
    private List<PontuacaoIndividual> listPontIndiv;

    @ManyToMany
    @JoinTable(name = "condicao_pontcult", joinColumns = @JoinColumn(name = "condicao_id"), inverseJoinColumns = @JoinColumn(name = "pontCult_id"))
    private List<PontuacaoCultural> listPontCult;

    @OneToMany(mappedBy = "condicao")
    private List<CondicaoExperimento> condicaoExperimentos;

    public Condicao() {
        this.emUso = false;
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

    public List<CondicaoExperimento> getCondicaoExperimentos() {
        return condicaoExperimentos;
    }

    public void setCondicaoExperimentos(List<CondicaoExperimento> condicaoExperimentos) {
        this.condicaoExperimentos = condicaoExperimentos;
    }

    public String getCriador() {
        return criador;
    }

    public void setCriador(String criador) {
        this.criador = criador;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getIncrementoPont() {
        return incrementoPont;
    }

    public void setIncrementoPont(String incrementoPont) {
        this.incrementoPont = incrementoPont;
    }

    public String getLimiteRodadasIncrem() {
        return limiteRodadasIncrem;
    }

    public void setLimiteRodadasIncrem(String limiteRodadasIncrem) {
        this.limiteRodadasIncrem = limiteRodadasIncrem;
    }

    public String getLimitePorcenAcertoIncrem() {
        return limitePorcenAcertoIncrem;
    }

    public void setLimitePorcenAcertoIncrem(String limitePorcenAcertoIncrem) {
        this.limitePorcenAcertoIncrem = limitePorcenAcertoIncrem;
    }

    public Integer getFimPorQuantCiclo() {
        return fimPorQuantCiclo;
    }

    public void setFimPorQuantCiclo(Integer fimPorQuantCiclo) {
        this.fimPorQuantCiclo = fimPorQuantCiclo;
    }

    public List<PontuacaoIndividual> getListPontIndiv() {
        return listPontIndiv;
    }

    public void setListPontIndiv(List<PontuacaoIndividual> listPontInd) {
        this.listPontIndiv = listPontInd;
    }

    public Integer getUltimosXCiclos() {
        return ultimosXCiclos;
    }

    public void setUltimosXCiclos(Integer ultimosXCiclos) {
        this.ultimosXCiclos = ultimosXCiclos;
    }

    public Integer getFimPorPorcentAcertoIndiv() {
        return fimPorPorcentAcertoIndiv;
    }

    public void setFimPorPorcentAcertoIndiv(Integer fimPorPorcentAcertoIndiv) {
        this.fimPorPorcentAcertoIndiv = fimPorPorcentAcertoIndiv;
    }

    public Integer getFimPorPorcentAcertoCult() {
        return fimPorPorcentAcertoCult;
    }

    public void setFimPorPorcentAcertoCult(Integer fimPorPorcentAcertoCult) {
        this.fimPorPorcentAcertoCult = fimPorPorcentAcertoCult;
    }

    public Integer getMinimoCiclos() {
        return minimoCiclos;
    }

    public void setMinimoCiclos(Integer minimoCiclos) {
        this.minimoCiclos = minimoCiclos;
    }

    public List<PontuacaoCultural> getListPontCult() {
        return listPontCult;
    }

    public void setListPontCult(List<PontuacaoCultural> listPontCult) {
        this.listPontCult = listPontCult;
    }
    
    public String getNivelComplexidade() {
        return nivelComplexidade;
    }

    public void setNivelComplexidade(String nivelComplexidade) {
        this.nivelComplexidade = nivelComplexidade;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public String getNomesExperimentos() {
        return nomesExperimentos;
    }

    public void setNomesExperimentos(String nomesExperimentos) {
        this.nomesExperimentos = nomesExperimentos;
    }

    public boolean isEmUso() {
        return emUso;
    }

    public void setEmUso(boolean emUso) {
        this.emUso = emUso;
    }
    
     
    public String getLimites(){
        if(fimPorQuantCiclo!=0)
            return "Por "+(fimPorQuantCiclo+1)+" Ciclos";
        else if(fimPorPorcentAcertoIndiv!=0)
            return "Por "+fimPorPorcentAcertoIndiv+" % acertos individuais";
        else if(fimPorPorcentAcertoCult!=0)
            return "Por "+fimPorPorcentAcertoCult+" % acertos em grupo";
        else return "-";
    }
    
    
    

}
