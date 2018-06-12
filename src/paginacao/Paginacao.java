/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paginacao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author marti
 */
public class Paginacao {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Arquivo arquivo = new Arquivo();
        ArrayList <Pagina> paginas = new ArrayList <Pagina> ();
        Memoria memoria;
        //Paginador paginador = new Paginador();
        String tela = "";
        
        //algoritmos que serao usados
        AlgoritmoFIFO fifo;
        AlgoritmoOtimo otimo;
        AlgoritmoLRU lru;
        
        //local padrao do arquivo de leitura
        String path = new File ("").getAbsolutePath();
        String filePath = path.concat("\\src\\paginacao\\paginas1.txt");
        
        /**
         * Preparacao para os procedimentos que irao ser usados
         * Algoritmos de substituicao de paginas usados:
         *  - Algoritmo de Substiruicao FIFO
         *  - Algoritmo de Substiruicao Otimo
         *  - Algoritmo de Substiruicao LRU
         * 
         * O array paginas deve guardar todas as informacoes que estao no arquivo
         * A memoria deve ser instanciada para ser usada dentro do Paginador
         * cada algoritmo vai ser instanciado atraves do Paginador.
         * 
         * Como os algoritmos sao interfaces eles terao apenas acesso aos meotodos
         * de suas proprias classes, ficando o Paginador responsavel por chamar
         * os demais metodos necessario, os quais os algoritmos nao teem acesso.
         */
        paginas = arquivo.carregarProcessos(filePath);
        memoria = new Memoria(paginas);
        fifo = new Paginador(paginas, memoria);
        
        paginas = arquivo.carregarProcessos(filePath);
        memoria = new Memoria(paginas);
        otimo = new Paginador(paginas, memoria);
        
        paginas = arquivo.carregarProcessos(filePath);
        memoria = new Memoria(paginas);
        lru = new Paginador(paginas, memoria);
        
        //chamando os procedimentos dos algoritmos de substituicao
        System.out.println("\nINICIO DO ALGORITMO DE SUBSTITUICAO FIFO");
        fifo.substituicaoFIFO();
        tela += "\nFIFO " + fifo.getPageFault();
        
        System.out.println("\nINICIO DO ALGORITMO DE SUBSTITUICAO OTIMO");
        otimo.substituicaoOtimo();
        tela += "\nOTM " + otimo.getPageFault();
        
        System.out.println("\nINICIO DO ALGORITMO DE SUBSTITUICAO LRU");
        lru.substituicaoLRU();
        tela += "\nLRU " + lru.getPageFault();
        
        
        System.out.println("\nResultado Final:" + tela);
        
        
    }
    
}
