/*
 * ListaEnlada.java
 *
 * Created on 16 de noviembre de 2007, 8:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gapvrpjava;

import java.util.*;

/**
 *
 * @author Cristhian Parra
 * @author Hugo Meyer
 *
 */

public class Poblacion {
    
    /** 
     * Vector que contiene a todos los individuos de la poblaci�n
     */
    private Cromosoma[] individuos; 
    
    /*
     * hijos de los individuos selectos
     */
    private Cromosoma[] hijos;        
    /*
     * Tama�o de la Poblaci�n, equivalente a individuos.length
     */
    private int tamanho; 


    /*
     * Fitness de los individuos 
     */
    private double[] fitness;

    
    private FuncionesGA operacionesGeneticas;

    /**
     * Mejor cromosoma de toda la historia
     */
    private Cromosoma mejorIndividuo = null;

    /*
     * Cantidad total de aristas 
     */
    private int cantAristas = 0;
    
    
    /** 
     * 
     * Constructor vac�o de la poblaci�n que genera una nueva instancia del 
     * Array de Cromosomas y setea un tama�o inicial de la poblaci�n a 30
     * 
     */
    
    public Poblacion() {
        this.tamanho = 30; 
        this.individuos = new Cromosoma[this.tamanho];
    }
    
    /**
     * Constructor de la poblaci�n a partir del Conocimiento que tenemos del
     * Problema.
     * 
     * @param entrada 
     * Clase que contiene la matriz del grafo y los par�metros globales del 
     * problema
     *                
     */    
    public Poblacion(Conocimiento entrada, int tamanhoPop) {
        
        this.tamanho = tamanhoPop;     
        individuos = new Cromosoma[tamanhoPop];
        hijos = new Cromosoma[tamanhoPop];
        fitness = new double[tamanhoPop];
      
        
        // Leer de la entrada el conocimiento e inicializar la poblaci�n a 
        // partir de ello
        this.inicializarPop(entrada);
    }
    

    /** 
     * Inicializa la poblaci�n generando un Cromosoma y construy�ndolo con 
     * valores aleatorios.
     * 
     * @param entrada
     * @return
     */
    public void inicializarPop(Conocimiento entrada) {
       
        for (int i=0; i < individuos.length; i++) {
                individuos[i] = new Cromosoma(entrada);
                individuos[i].aleatorio();
        }
    }

    public Cromosoma[] getIndividuos() {
        return individuos;
    }

    public void setIndividuos(Cromosoma[] individuos) {
        this.individuos = individuos;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    /**
     * M�todo para Optimizar el Algoritmo. Elimina los duplicados. 
     * PENDIENTE
     * =======================================================================
     * 
     * Compara cada individuo de la poblaci�n con
     * los dem�s y modifica los cromosomas duplicados
     * mutandolos.
     */
    
    /**
     * 
     */
    /*
    public void descartarIguales() {
        //System.out.println("DESCARTARIGUALES...");

        for (int i=0; i<this.getTamanho()-1; i++) {
                for (int j=i+1; j<this.getTamanho(); j++) {
                        if (individuos[i].equals(individuos[j])) {
                                operacionesGeneticas.mutar(individuos[j]);
                        }
                }
        }
    }*/

    /**
     * Selecciona nuevos individuos de esta poblaci�n
     * @return Cromosoma[] nuevos individuos seleccionados
     */
    public Cromosoma[] seleccionar() {
            return FuncionesGA.seleccion(this);
    }

 
}
