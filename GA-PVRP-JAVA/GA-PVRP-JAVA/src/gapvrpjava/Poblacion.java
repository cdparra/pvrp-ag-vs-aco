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
    
    /**
     * Hijos de los individuos selectos
     */
    private Cromosoma[] hijos;        
    /**
     * Tama�o de la Poblaci�n, equivalente a individuos.length
     */
    private int tamanho; 

    /**
     * Fitness de los individuos 
     */
    private double[] fitness;
    
    /** 
     * N�mero de la generaci�n actual. 
     */
    private int generacion;

    /**
     * Contiene todas las operaciones utilizadas para el algoritmo gen�tico
     */
    private FuncionesGA operacionesGeneticas;

    /**
     * Mejor cromosoma de toda la historia
     */
    private Cromosoma mejorIndividuo = null;
    
    /** 
     * 
     * Constructor vac�o de la poblaci�n que genera una nueva instancia del 
     * Array de Cromosomas y setea un tama�o inicial de la poblaci�n a 30
     * 
     */    
    public Poblacion() {
        this.tamanho = 30; 
        this.individuos = new Cromosoma[this.tamanho];
        this.hijos = new Cromosoma[this.tamanho];
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
        this.individuos = new Cromosoma[tamanhoPop];
        this.hijos = new Cromosoma[tamanhoPop];
        this.fitness = new double[tamanhoPop];
        
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

    /**
     * Solo el getter de tamanaho est� implementado, porque esta propiedad solo
     * se establece una vez, al inicializar la poblaci�n
     * 
     * @return El tamanho (cantidad de individuos) de la poblaci�n
     */
    public int getTamanho() {
        return tamanho;
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
     * Selecciona nuevos individuos de esta poblaci�n y realiza el 
     * @return Cromosoma[] nuevos individuos seleccionados
     */
    public Cromosoma[] seleccion() {
            return FuncionesGA.seleccion(this);
    }

    
   /**
    * Cruza los individuos seleccionados y genera la lista de hijos que 
    * son utilizados despu�s para reemplazar parte de la poblaci�n
    * @param selectos
    */
    public void cruzar(Cromosoma[] selectos) {
        
        
        for (int i=0; i <= selectos.length-2; i = i+2){
            Cromosoma nuevos[];
            nuevos = FuncionesGA.Cruzar(selectos[i], selectos[i+1]);

            this.hijos[i] = nuevos[0];
            this.hijos[i+1] = nuevos[1];
        }
    }

    /**
     * Muta cromosomas de la poblaci�n con una 
     * problabilidad de mutar de 20%
     */
    public void mutar() {
        Random rand = new Random();
        rand.nextInt();

        for (int i=0; i < this.getTamanho(); i++){
            if (rand.nextInt(99) < 20)
                FuncionesGA.Mutar(hijos[i]);
        }
    }

    /**
     * Realiza el reemplazo de individuos de
     * la poblaci�n.
     */
    public void reemplazar() {
        
        // Revisar esta estrategia reemplazo
        for (int i =0; i<this.getTamanho(); i++)
            individuos[i] = hijos[i];
            //individuos[0]=this.getMejorIndividuo(); // Reemplaza el mejor
    }

    /**
     * Realiza el calculo de fitness para todos los individuos
     * 
     * INCOMPLETO: Descomentar la evaluaci�n
     */
    public void evaluar(Conocimiento entrada) {

            for (int i=0; i<this.getTamanho();i++) {
                    //fitness[i] = individuos[i].evaluar(entrada);
            }
            elegirMejor();
    }

    /**
     * Obtiene el fitness de un individuo
     * @param ind indice de un individuo
     * @return fitness
     */
    public double getFitness(int ind) {
        return fitness[ind];
    }

    public Cromosoma getIndividuo(int pos) {
        return individuos[pos];
    }

    /**
     * Elige el mejor cromosoma de 
     * toda la historia.
     * 
     * INCOMPLETO --> FALTA DESCOMENTAR Y VERIFICAR ELECCION DEL MEJORFITNESS
     */
    private void elegirMejor() {
            /*
             * Si todavia no se seleccion�
             * a ninguno, guardamos al primero.
             */
            if (mejorIndividuo == null) {
                    mejorIndividuo = individuos[0];
            }

            double mejorFitness = 0;// mejorIndividuo.getFitness();
            for (int i=0; i < this.getTamanho(); i++) {
                    if (fitness[i]> mejorFitness) {
                            mejorIndividuo = individuos[i];
                            //mejorFitness = mejorIndividuo.getFitness();
                    }
            }
    }

    /**
     * REVISAR --------------------------------------
     * 
     * Realiza el control de la poblaci�n, y si la cantidad
     * de cromosomas inv�lidos es mayor al factor, retorna
     * true y en caso contrario, retorna false.
     * @param factor valor entre 0 y 1 que indica el porcentaje permitido.
     * @return true si la cantidad de invalidos supera el factor.
     */
   public boolean reinicializar(double factor){
            int contador = 0; // cuenta los cromosomas invalidos

            for (int i=1; i < this.getTamanho(); i++) {
                    // Contamos si el fitness es inv�lido
                    if (fitness[i] < 0)
                            contador++;
            }

            /*
             * Si el porcentaje de inv�lidos calculado es mayor 
             * al permitido, retornamos true
             */
            if (contador > this.getTamanho()*factor)
                    return true;

            // Si no, retornamos false
            return false;
    }

    public Cromosoma getMejorIndividuo() {
            return this.mejorIndividuo;
    }

    public void setMejorIndividuo(Cromosoma x) {
            this.mejorIndividuo = x; 
    }

    // INCOMPLETO
    public double getMejorFitness(){
        // return this.mejorIndividuo.getFitness();
        return 0;
    }

    // INCOMPLETO
    public double getMejorCosto(){
            // return 1/this.mejorIndividuo.getCosto();
        return 0;
    }

    /**
     * Imprime en salida standard toda la poblaci�n
     */
    public void imprimir(){
            for (int i=0; i<this.getTamanho(); i++){
                    System.out.println("Cromosoma: "+i+" ");
                    System.out.println("Fitness  : "+fitness[i]);
                    //individuos[i].ImprimirCromo(granLinea);
                    System.out.println();
            }
    }

    
 
}
