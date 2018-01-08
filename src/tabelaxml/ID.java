/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabelaxml;

/**
 *
 * @author rwspa
 */
public class ID {
    private String id;
    private int qtd;

    public ID(String id) {
        this.id = id;
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
     * @return the qtd
     */
    public int getQtd() {
        return qtd;
    }

     /**
     * @param qtd the id to set
     */
    public void setQtd(int qtd) {
        this.qtd = qtd;
    }
    
    public void adicionaUmEmQtd() {
        this.qtd += 1;
    }
}
