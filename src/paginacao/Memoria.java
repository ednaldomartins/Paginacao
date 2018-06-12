/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paginacao;

import java.util.ArrayList;
/**
 *
 * @author marti
 */
public class Memoria {
    private Pagina[] tabela;
    private int tamanho;
    
    Memoria(ArrayList <Pagina> paginas) {
        this.tamanho =  paginas.get(0).getPagina();         //o tamanho esta na posicao 0
        this.tabela = new Pagina[tamanho];                  //quadro de memoria assume seu tamanho
        paginas.remove(0);                                  //remover o tamanho do array
        
        //iniciar a memoria toda em -1(vazio)
        for (int i = 0; i < tabela.length ; i++) {
            Pagina pagina = new Pagina();
            pagina.setPagina(-1);
            pagina.setProximoAcesso(-1);
            pagina.setUltimoAcesso(-1);
            setQuadro(i, pagina);
        }
        
    }

    Memoria() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String toStringQuadro() {
        String mensagem = "QUADROS: | ";
        for(int i = 0; i < this.tamanho; i++) {
            mensagem += tabela[i].getPagina() + " | ";
        }
        return mensagem;
    }
    
   
    public Pagina[] getTabela() {
        return tabela;
    }

    public Pagina getQuadro(int pos) {
        return tabela[pos];
    }
    
    public void setTabela(Pagina[] quadro) {
        this.tabela = quadro;
    }

    public void setQuadro (int pos, Pagina pagina) {
        this.tabela[pos] = pagina;
    }
    
    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

   
    
    public boolean cheia() {
        for (int i = 0; i < tamanho ; i++) {
           if (tabela[i].getPagina() == -1) {
               return false;
           }
        }
        return true;
    }
    
}
