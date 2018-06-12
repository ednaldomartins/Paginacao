/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paginacao;

/**
 *
 * @author marti
 */
public interface AlgoritmoOtimo {
    public void substituicaoOtimo();
    public int getPageFault();
    public String getResultadoFinal();
}
