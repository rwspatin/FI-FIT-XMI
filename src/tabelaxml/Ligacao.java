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
public class Ligacao {
    private String tipo;
    private String destino;
    private Complemento dest;
    private String origem;
    private Complemento ori;

    public Ligacao() {
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the destino
     */
    public String getDestino() {
        return destino;
    }

    /**
     * @param destino the destino to set
     */
    public void setDestino(String destino) {
        this.destino = destino;
    }

    /**
     * @return the dest
     */
    public Complemento getDest() {
        return dest;
    }

    /**
     * @param dest the dest to set
     */
    public void setDest(Complemento dest) {
        this.dest = dest;
    }

    /**
     * @return the origem
     */
    public String getOrigem() {
        return origem;
    }

    /**
     * @param origem the origem to set
     */
    public void setOrigem(String origem) {
        this.origem = origem;
    }

    /**
     * @return the ori
     */
    public Complemento getOri() {
        return ori;
    }

    /**
     * @param ori the ori to set
     */
    public void setOri(Complemento ori) {
        this.ori = ori;
    }
}
