package paginacao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author marti
 */
public interface AlgoritmoLRU {
    public void substituicaoLRU();
    public int getPageFault();
    public String getResultadoFinal();
}
