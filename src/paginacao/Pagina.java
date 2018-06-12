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
public class Pagina {
    private int pagina;
    private int ultimoAcesso;
    private int proximoAcesso;

    public Pagina () {
        
    }
    
    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(int ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    public int getProximoAcesso() {
        return proximoAcesso;
    }

    public void setProximoAcesso(int proximoAcesso) {
        this.proximoAcesso = proximoAcesso;
    }
    
    
}
