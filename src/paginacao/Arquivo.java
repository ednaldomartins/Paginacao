/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paginacao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author marti
 */
public class Arquivo {
    private Charset utf8 = StandardCharsets.UTF_8;
    /**
     * @param filePath
     * @return Objeto do tipo Pagina
     * @throws IOException
     * 
     * A classe Cria um Array de Objeto do tipo Pagina, que irá ler o arquivo
     * TXT e guardar os valores referente aos parametros que estao contido na
     * classe Pagina.
     */
    public ArrayList<Pagina> carregarProcessos(String filePath) throws IOException {
        
        ArrayList <Pagina> paginas = new ArrayList <Pagina>();        
        Path loadPath = Paths.get(filePath);
        try (BufferedReader reader = Files.newBufferedReader(loadPath, utf8)) {//duvida: eh fechado automaticamento por estar declarado no try?
            String line;
            while((line = reader.readLine()) != null) {
                Pagina pagina = new Pagina();
                pagina.setPagina(Integer.parseInt(line));
                paginas.add(pagina);
            }       
        }
        
        catch(FileNotFoundException ex) {
            System.err.println("Arquivo não encontrado");                
        }
        catch(IOException ex) {
            System.err.println("Erro de leitura");
            ex.printStackTrace();
        }
        catch(NumberFormatException ex) {
            System.err.println("Erro de formato de numeros");
        }
    
        return paginas;
    }
}
