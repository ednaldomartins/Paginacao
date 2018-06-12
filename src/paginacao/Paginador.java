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
public class Paginador implements AlgoritmoFIFO, AlgoritmoOtimo, AlgoritmoLRU {
    
    private ArrayList <Pagina> paginas = new ArrayList <Pagina> ();
    private Memoria memoria;
    
    private int paginaMaisAntiga;
    private int paginaMaiorPeriodo;
    private int cont;
    private int pageFault;
    private String resultadoFinal;
    
    public Paginador (ArrayList <Pagina> paginas, Memoria memoria) {
        this.paginas.addAll(paginas);                   //copiar array
        this.memoria = memoria;
        this.paginaMaisAntiga = -1;                      
        this.paginaMaiorPeriodo = -1;
        this.cont = 0;
        this.pageFault = 0;
        this.resultadoFinal = "";
    }
    
    @Override
    public void substituicaoFIFO() {
        int contadorCircular = 0, posicaoPagina = 0;
        do {
            //atualizar os acessos para ver quem sao os mais antigos e mais ociosos
            //atualizarAcessos(memoria);
            //o procedimento procurarPagina retorna a posicao da pagina na
            //memoria, caso nao esteja na memoria, serah -1.
            posicaoPagina = procurarPagina(paginas.get(cont).getPagina());
            //se o retorno for diferente de -1, entao a pagina estah na memoria
            if( posicaoPagina != -1 ) {
                //executar tarefa da pagina que estah na memoria
                acessarMemoria(posicaoPagina, cont);
            }
            //se nao ta na memoria vai ter que fazer swapIn da pagina
            else {
                //se a memoria estiver cheia, fazer swapOut antes do IN
                if (memoria.cheia()) {
                    //retirar da memoria a pagina mais antiga
                    swapOut(contadorCircular);
                }
                //mandar paginaMaisAntiga para o meotodo para que a nova pagina
                //seja colocada na posicao da pagina que saiu
                swapIn(contadorCircular);
                //mandar pagina mais antiga, pq a novaPagina foi colocado nessa
                //posicao, e a atualizacao de acessos ainda nao foi feita
                acessarMemoria(contadorCircular, cont);
                //incrementar o contador para que a proxima posicao seja o alvo
                
                contadorCircular++;
                contadorCircular = contadorCircular % memoria.getTamanho();
            }
            this.cont++;
            System.out.println(memoria.toStringQuadro());
        }while(this.cont < paginas.size());
        resultadoFinal += "\nFIFO " + pageFault;
        System.out.print("pageFault Otimo: " + pageFault);
    }

    
    @Override
    public void substituicaoOtimo() {
        int posicaoPagina;
        do {
            //atualizar os acessos para ver quem sao os mais antigos e mais ociosos
            atualizarAcessos(memoria);
            //o procedimento procurarPagina retorna a posicao da pagina na
            //memoria, caso nao esteja na memoria, serah -1.
            posicaoPagina = procurarPagina(paginas.get(cont).getPagina());
            //se o retorno for diferente de -1, entao a pagina estah na memoria
            if( posicaoPagina != -1 ) {
                //executar tarefa da pagina que estah na memoria
                acessarMemoria(posicaoPagina, cont);
            }
            //se nao ta na memoria vai ter que fazer swapIn da pagina
            else {
                //se a memoria estiver cheia, fazer swapOut antes do IN
                if (memoria.cheia()) {
                    //retirar da memoria a pagina mais antiga
                    swapOut(getPaginaMaiorPeriodo());
                }
                //mandar paginaMaisAntiga para o meotodo para que a nova pagina
                //seja colocada na posicao da pagina que saiu
                swapIn(getPaginaMaiorPeriodo());
                //mandar pagina mais antiga, pq a novaPagina foi colocado nessa
                //posicao, e a atualizacao de acessos ainda nao foi feita
                acessarMemoria(getPaginaMaiorPeriodo(), cont);
            }
            this.cont++;
            System.out.println(memoria.toStringQuadro());
        } while(this.cont < getPaginas().size());
        resultadoFinal += "\nOTM " + pageFault;
        System.out.print("pageFault Otimo: " + pageFault);
    }

    
    @Override
    public void substituicaoLRU() {
        int posicaoPagina;
        do {
            //atualizar os acessos para ver quem sao os mais antigos e mais ociosos
            atualizarAcessos(memoria);
            //o procedimento procurarPagina retorna a posicao da pagina na
            //memoria, caso nao esteja na memoria, serah -1.
            posicaoPagina = procurarPagina(paginas.get(cont).getPagina());
            //se o retorno for diferente de -1, entao a pagina estah na memoria
            if( posicaoPagina != -1 ) {
                //executar tarefa da pagina que estah na memoria
                acessarMemoria(posicaoPagina, cont);
            }
            //se nao ta na memoria vai ter que fazer swapIn da pagina
            else {
                //se a memoria estiver cheia, fazer swapOut antes do IN
                if (memoria.cheia()) {
                    //retirar da memoria a pagina mais antiga
                    swapOut(getPaginaMaisAntiga());
                }
                //mandar paginaMaisAntiga para o meotodo para que a nova pagina
                //seja colocada na posicao da pagina que saiu
                swapIn(getPaginaMaisAntiga());
                //mandar pagina mais antiga, pq a novaPagina foi colocado nessa
                //posicao, e a atualizacao de acessos ainda nao foi feita
                acessarMemoria(getPaginaMaisAntiga(), cont);
            }
            this.cont++;
            System.out.println(memoria.toStringQuadro());
        } while(this.cont < getPaginas().size());
        resultadoFinal += "\nLRU " + pageFault;
        System.out.print("pageFault LRU: " + pageFault);
    }
    
    
    public int procurarPagina(int pagina) {
        //se ao final permanecer -1 a pagina nao foi encontrada
        int posicaoPagina = -1;
        //procurando se a pagina ja estah na memoria
        for(int i = 0; i < memoria.getTamanho(); i++) {
            //se estiver na memoria, entao captura a posicao
            if (memoria.getQuadro(i).getPagina() == pagina) {
                posicaoPagina = i;
                //posicaoPagina = memoria.getQuadro(i).getPagina();
                break;
            }
        }
        
        //se nao foi encontrado, entao houve falta de pagina
        if(posicaoPagina == -1) {
            pageFault++;
        }
        
        return posicaoPagina;
    }
    
    
    //criar meotodo de acesso a memoria 
    public void acessarMemoria(int posicao, int ultimoAcesso) {
        //ir na memoria, pegar o quadro atual que estah sendo acessado, e setar o ultimo acesso
        memoria.getQuadro(posicao).setUltimoAcesso(ultimoAcesso);
        //guardar proximo acesso, se nao mudar entao nao sera mais acessada
        int verificarAlteracao = memoria.getQuadro(posicao).getProximoAcesso();
        
        for(int i = cont + 1; i < paginas.size(); i++) {
            if(memoria.getQuadro(posicao).getPagina() == paginas.get(i).getPagina() ) {
                //se a pagina q estah na memoria for igual a pagina que 
                //estamos percorrendo dentro do array, entao esse i sera 
                //seu proximo acesso
                memoria.getQuadro(posicao).setProximoAcesso(i);
                break;
            }
        }
        
        //se ainda for igual, nao sera mais acessada
        if(verificarAlteracao == memoria.getQuadro(posicao).getProximoAcesso()) {
            //entao seta -1 para ser uma pagina alvo na retirada
            memoria.getQuadro(posicao).setProximoAcesso(-1);
        }
        
    }
    
    
    public void swapIn(int posicaoPagina){
        //colocando na tabela da memoria
        memoria.setQuadro(posicaoPagina, paginas.get(cont));
    }
    
    //o metodo pega a posicao de quem deve sair e seta como -1 (metodo generico)
    public void swapOut(int paginaSair) {
        Pagina paginaNula = new Pagina();
        paginaNula.setPagina(-1);
        paginaNula.setProximoAcesso(-1);
        paginaNula.setUltimoAcesso(-1);
        memoria.setQuadro(paginaSair, paginaNula);
    }
    
    public void atualizarAcessos(Memoria memoria) {
        //se a memoria ta cheio eu tenho que pegar o que estah mais tempo sem acessar
        if(memoria.cheia()) {
            
            setPaginaMaisAntiga(0);
            for (int i = 1; i < memoria.getTamanho(); i++) {   
                //se a Pagina "i" tiver o ultimoAcesso a mais tempo que a Pagina com acesso mais antigo
                if (  memoria.getQuadro(i).getUltimoAcesso() <  memoria.getQuadro(getPaginaMaisAntiga()).getUltimoAcesso()) {
                    //entao essa pagina substituira a anterior
                    setPaginaMaisAntiga(i);
                }
            }
            
            setPaginaMaiorPeriodo(0);
            //procurar na memoria a pagina que vai ficar mais tempo ociosa
            for(int i = 0; i < memoria.getTamanho(); i++) {
                //se o quadro procurado tiver proximoAcesso = -1 entao esse sera o proximo alvo
                if( memoria.getQuadro(i).getProximoAcesso() == -1) {
                    setPaginaMaiorPeriodo(i);
                    //escolhendo esse como proximo alvo nao precisa comparar
                    //o restante, pois esse processo aqui nao sera mais acessado
                    break;
                }
                //se proximoAcesso da pagina atual for mair que aquele que eh
                //temporariamente maior, entao esse sera o alvo por enquanto.
                if( memoria.getQuadro(i).getProximoAcesso() > memoria.getQuadro(getPaginaMaiorPeriodo()).getProximoAcesso() ) {
                    setPaginaMaiorPeriodo(i);
                }
            }    
               
        }
        
        //se a memoria nao estah cheia entao pegar o primeiro quadro vazio
        else {
            for (int i = 0; i < memoria.getTamanho(); i++) {
                //se ultimo acesso for -1, entao nunca foi usado e estah livre
                if (memoria.getQuadro(i).getUltimoAcesso() == -1) { 
                    //entao essa pagina substituira a anterior
                    setPaginaMaisAntiga(i);
                    setPaginaMaiorPeriodo(i);
                    break;
                }
            }
        }
        
    }
    
    
    public void toStringPaginas() {
        for(int i = 0; i < paginas.size(); i++) {
            System.out.println(paginas.get(i).getPagina() + " " + paginas.get(i).getProximoAcesso() + " " + paginas.get(i).getUltimoAcesso());
        }
    }

    public ArrayList<Pagina> getPaginas() {
        return paginas;
    }

    public void setPaginas(ArrayList<Pagina> paginas) {
        this.paginas = paginas;
    }

    public int getPaginaMaisAntiga() {
        return paginaMaisAntiga;
    }

    //vou colocar pagina mas antiga como a posicao do vetor mais antigo
    public void setPaginaMaisAntiga(int paginaMaisAntiga) {
        this.paginaMaisAntiga = paginaMaisAntiga;
    }

    public int getPaginaMaiorPeriodo() {
        return paginaMaiorPeriodo;
    }

    public void setPaginaMaiorPeriodo(int paginaMaiorPeriodo) {
        this.paginaMaiorPeriodo = paginaMaiorPeriodo;
    }
 
    
    
    public int getCont() {
        return cont;
    }

    @Override
    public int getPageFault() {
        return pageFault;
    }

    @Override
    public String getResultadoFinal() {
        return resultadoFinal;
    }
    
}
