/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabelaxml;

import java.util.ArrayList;


/**
 *
 * @author rwspa
 */
public class Classe {
    private String id;
    private String nome;
    private ArrayList<Ligacao> ligacao = new ArrayList<>();
    private int fi;
    private int fit;

    public Classe(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the ligacao
     */
    public ArrayList<Ligacao> getLigacao() {
        return ligacao;
    }

    /**
     * @param ligacao the ligacao to set
     */
    public void setLigacao(ArrayList<Ligacao> ligacao) {
        this.ligacao = ligacao;
    }

    /**
     * @return the fi
     */
    public int getFi() {
        return fi;
    }

    /**
     * @param fi the fi to set
     */
    public void setFi(int fi) {
        this.fi = fi;
    }

    /**
     * @return the fit
     */
    public int getFit() {
        return fit;
    }

    /**
     * @param fit the fit to set
     */
    public void setFit(int fit) {
        this.fit = fit;
    }
    
    public void somaFIT(int fi){
        this.fit+=fi;
    }

    
}
