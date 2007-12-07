/*
 * TestCromosoma.java
 *
 * Created on 31 de octubre de 2007, 19:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pruebas;

import gapvrpjava.*;

/**
 *
 * @author Huguis
 */
public class TestCruce {
    
    /** Creates a new instance of TestCromosoma */
    public TestCruce() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        Conocimiento prueba= new Conocimiento();
        String filename = "C:\\pvrp\\p26";
        
     
        prueba.CargarConocimiento(filename);
        Cromosoma cromo = new Cromosoma(prueba);
        cromo.construirCromosoma(prueba);
                
        System.out.println("1. PRUEBA DE OPERCI�N DE CRUCE");
        System.out.println("-* Poblaci�n Inicial Generada =========================================");
        
        Poblacion p = new Poblacion(prueba, 2, 30);
        p.evaluar(prueba);
        
        String popString = p.toStringImprimible();        
        System.out.print(popString);        
        
        
        // SELECCI�N B�SICA...
        Cromosoma[] selectos = p.getIndividuos();
        p.cruce(selectos);
        p.reemplazar();
        p.evaluar(prueba);
        System.out.println("-* Poblaci�n Cruzada  =========================================");
                
        popString = p.toStringImprimible();
        
        System.out.print(popString);        
        
    }
    
}
