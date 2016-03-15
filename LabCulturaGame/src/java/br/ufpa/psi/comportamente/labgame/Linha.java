package br.ufpa.psi.comportamente.labgame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adailton
 */
public class Linha {

    public Linha(int posicao,String cor, boolean a,boolean b,boolean c,boolean d,boolean e,boolean f,boolean g,boolean h,boolean i,boolean j) {
        this.posicao = posicao;
        this.cor=cor;
        this.A=a;
        this.B=b;
        this.C=c;
        this.D=d;
        this.E=e;
        this.F=f;
        this.G=g;
        this.H=h;
        this.I=i;
        this.J=j;
    }
    

    //posicao da linha na tabela
    private int posicao;
    
    //cor da linha
    private String cor = "#FFFF00";
    
    private boolean A=true;
    private boolean B=false;
    private boolean C=true;
    private boolean D=true;
    private boolean E=false;
    private boolean F=false;
    private boolean G=true;
    private boolean H=false;
    private boolean I=true;
    private boolean J=true;

    
    
    public boolean isA() {
        return A;
    }

    public void setA(boolean A) {
        this.A = A;
    }

    public boolean isB() {
        return B;
    }

    public void setB(boolean B) {
        this.B = B;
    }

    public boolean isC() {
        return C;
    }

    public void setC(boolean C) {
        this.C = C;
    }

    public boolean isD() {
        return D;
    }

    public void setD(boolean D) {
        this.D = D;
    }

    public boolean isE() {
        return E;
    }

    public void setE(boolean E) {
        this.E = E;
    }

    public boolean isF() {
        return F;
    }

    public void setF(boolean F) {
        this.F = F;
    }

    public boolean isG() {
        return G;
    }

    public void setG(boolean G) {
        this.G = G;
    }

    public boolean isH() {
        return H;
    }

    public void setH(boolean H) {
        this.H = H;
    }

    public boolean isI() {
        return I;
    }

    public void setI(boolean I) {
        this.I = I;
    }

    public boolean isJ() {
        return J;
    }

    public void setJ(boolean J) {
        this.J = J;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
    
    
    
    
    
    
}
