/**
 * Poblaci�n.java
 *
 * Created on 16 de noviembre de 2007, 8:37
 *
 * Contiene las funcionalidades principales del Algoritmos gen�tico para resolver
 * PVRP
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
     * Hijos de los individuos selectos y variable para almacenar cantidad 
     * de hijos generados
     */
    private Cromosoma[] hijos;        
    private int CantHijosGenerados;
    
    /**
     * Tama�o de la Poblaci�n, equivalente a individuos.length
     */
    private int tamanho; 

    /**
     * Fitness de los individuos 
     * Tambi�n disponible como informaci�n de cada individuo
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
     * Posici�n en el vector de poblaci�n del mejor individuo. 
     */
    private int mejorIndividuoPos;
    
    private Conocimiento conocimiento;
    
    private int probMutacion;
    
    private int iterador = 0;
    
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
        this.probMutacion = 20; 
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
        
        this.conocimiento = entrada;
        this.tamanho = tamanhoPop;     
        this.individuos = new Cromosoma[tamanhoPop];
        this.hijos = new Cromosoma[tamanhoPop];
        this.fitness = new double[tamanhoPop];
        this.probMutacion = 20; 
        // Leer de la entrada el conocimiento e inicializar la poblaci�n a 
        // partir de ello
        this.inicializarPop();
    }
    
    
    
    public Poblacion(Conocimiento entrada, int tamanhoPop, int probMutacion) {
        
        this.conocimiento = entrada;
        this.tamanho = tamanhoPop;
        this.individuos = new Cromosoma[tamanhoPop];
        this.hijos = new Cromosoma[tamanhoPop];
        this.fitness = new double[tamanhoPop];
        
        if (probMutacion > 100 || probMutacion < 0) {
            this.probMutacion = 20; 
        } else {
            this.probMutacion = probMutacion; 
        }
        
        // Leer de la entrada el conocimiento e inicializar la poblaci�n a 
        // partir de ello
        this.inicializarPop();
    }

    /** 
     * Inicializa la poblaci�n generando un Cromosoma y construy�ndolo con 
     * valores aleatorios.
     * 
     * No hace nada m�s que inicializar poblaci�n. No se hace ninguna evaluaci�n
     * de la poblaci�n en su conjunto.
     * 
     * @param entrada
     * @return
     */
    public void inicializarPop() {
       
        for (int i=0; i < individuos.length; i++) {
                individuos[i] = new Cromosoma(this.getConocimiento());
                individuos[i].construirCromosoma(this.getConocimiento());
        }
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
            return FuncionesGA.seleccionar(this);
    }

    
   /**
    * Cruza los individuos seleccionados y genera la lista de hijos que 
    * son utilizados despu�s para reemplazar parte de la poblaci�n
    * @param selectos
    */
    public void cruce(Cromosoma[] selectos) {
        
        Cromosoma nuevos[];
        for (int i=0; i <= selectos.length-2; i = i+2){
            
            nuevos = FuncionesGA.Cruzar(selectos[i], selectos[i+1],this.getConocimiento());

            this.getHijos()[i] = nuevos[0];
            this.getHijos()[i+1] = nuevos[1];
           
        }
        
        // si la longitud es impar, cruzamos el �ltimo con el primero
        int indiceUltimo = selectos.length - 1;
        
        if ((indiceUltimo+1)%2 != 0) {
            nuevos = FuncionesGA.Cruzar(selectos[indiceUltimo], 
                                        selectos[0],this.getConocimiento());
            this.getHijos()[indiceUltimo] = nuevos[0];
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
            if (rand.nextInt(99) < this.probMutacion)
                FuncionesGA.Mutar(getHijos()[i],this.conocimiento);
        }
    }

    public void inicializarIterador() {
        iterador = 0;
    }
    
    public Cromosoma getNextIndividuo() {
        if (iterador < this.tamanho) {
            return individuos[iterador++];
        } else {
            iterador = 0;
            return null;
        }
        
    }
    /**
     * Realiza el reemplazo de individuos de
     * la poblaci�n.
     */
    public void reemplazar() {
        
        // Revisar esta estrategia reemplazo
        // AHORA REEMPLAZAMOS TODO
        for (int i = 0; i<this.getTamanho(); i++)
            
            individuos[i] = getHijos()[i];
            //individuos[0]=this.getMejorIndividuo(); // Reemplaza el mejor
    }

    /**
     * Realiza el calculo de fitness para todos los individuos
     * 
     * INCOMPLETO: Descomentar la evaluaci�n
     */
    public void evaluar() {

            for (int i=0; i<this.getTamanho();i++) {
                getFitness()[i] = individuos[i].evaluar(this.getConocimiento());
            }
            elegirMejor();
    }

    /**
     * Obtiene el fitness de un individuo
     * @param ind indice de un individuo
     * @return fitness
     */
    public double getFitness(int ind) {
        return getFitness()[ind];
    }

    public Cromosoma getIndividuo(int pos) {
        return individuos[pos];
    }

    /**
     * Elige el mejor cromosoma de 
     * toda la historia.
     */
    private void elegirMejor() {
            /*
             * Si todavia no se seleccion�
             * a ninguno, guardamos al primero.
             */
            if (mejorIndividuo == null) {
                    mejorIndividuo = individuos[0];
                    this.setMejorIndividuoPos(0);
            }

            double mejorFitness = 0;// mejorIndividuo.getFitness();
            for (int i=0; i < this.getTamanho(); i++) {
                    if (getFitness()[i]> mejorFitness) {
                            mejorIndividuo = individuos[i];
                            mejorFitness = mejorIndividuo.getFitness();
                            this.setMejorIndividuoPos(i);
                    }
            }
    }

    /**
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
                    if (getFitness()[i] < 0)
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

   /**
    * Setters y Getters
    * @return
    */
   
    public Cromosoma getMejorIndividuo() {
            return this.mejorIndividuo;
    }

    public void setMejorIndividuo(Cromosoma x) {
            this.mejorIndividuo = x; 
    }

    public double getMejorFitness(){
        return this.mejorIndividuo.getFitness();
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
     * Funci�n qeu convierte la poblaci�n en un string concatenado de todos los
     * individuos.
     * 
     */
    @Override
    public String toString() {
        
        String PopString = individuos[0].toString();
        
        for (int i=1; i<this.getTamanho(); i++){
            String currentIndividual = individuos[i].toString();
            PopString +="$"+currentIndividual;
        }        
        
        return PopString+"$"+this.getMejorIndividuoPos()+","+this.getMejorFitness(); 
    }
    
    public String toStringMultilinea(String toStringLinea) {
        
        StringTokenizer tk = new StringTokenizer(toStringLinea, "$");
        
        String poblacionMultilinea = "Individuo 1: "+tk.nextToken()+"\n";
        poblacionMultilinea +="-----> Fitness = "+individuos[0].getFitness()+"\n";
        int i = 1;
        while (tk.hasMoreTokens()) {
            String current_token = tk.nextToken();
            
            // si se cumple esta condici�n, todav�a no estamos en el �ltimo token
            if (tk.hasMoreTokens()) {
                double currentFitness = individuos[i].getFitness();
            
                String current ="Individuo "+(++i)+": "+ current_token+"\n";
                poblacionMultilinea += current;
                poblacionMultilinea +="-----> Fitness = "+currentFitness+"\n";
            } else {
                poblacionMultilinea +="*** Mejor Individuo, Fitness *** "+current_token+"\n";
            }
        }
           
        return poblacionMultilinea;
    }
    
    public String toStringImprimible(){
        String poblacionMultilinea ="<----------------------------------------------------------------------------------------------->\n";
        poblacionMultilinea += "<--------------------------- | Poblaci�n - Inicio | ------------------------->\n"; 
        
        poblacionMultilinea +=toStringMultilinea(this.toString());
        
        poblacionMultilinea +="<---------------------------- | Poblaci�n - Fin | ----------------------------->\n"; 
        poblacionMultilinea +="<--------------------------------------------------------------------------------------------------->\n\n";
        
        return poblacionMultilinea;
    }

    public String toStringPorCromosoma(String toStringLinea) {
        
        StringTokenizer tk = new StringTokenizer(toStringLinea, "$");
        
        String individuoMultiLinea = this.individuos[0].ImprimirCromo(tk.nextToken());
        
        String poblacionMultilinea ="<--------------------------------------------------------------------------------------------------->\n";
        poblacionMultilinea += "<--------------------------- | Poblaci�n - Inicio | --------------------------->\n"; 
        
        poblacionMultilinea += "Individuo 1: "+individuoMultiLinea+"\n";
        poblacionMultilinea +="-----> Fitness = "+individuos[0].getFitness()+"\n";
        int i = 0;
        while (tk.hasMoreTokens()) {
            String current_token = tk.nextToken();
            
            // no estamos todav�a en el �ltimo...
            if (tk.hasMoreTokens()) {
                double currentFitness = individuos[i].getFitness();

                individuoMultiLinea = this.individuos[++i].ImprimirCromo(current_token);
                String current ="Individuo "+(i+1)+": "+ individuoMultiLinea+"\n";
                poblacionMultilinea += current;
                poblacionMultilinea +="-----> Fitness = "+currentFitness+"\n"; 
            } else {
                poblacionMultilinea +="*** Mejor Individuo, Fitness *** "+current_token+"\n";                
            }
        }
        
        poblacionMultilinea +="<---------------------------- | Poblaci�n - Fin | ----------------------------->\n"; 
        poblacionMultilinea +="<---------------------------------------------------------------------------------->\n\n";
        
        return poblacionMultilinea;
    }

    /**
     * Imprime en salida standard toda la poblaci�n
     */
    public void imprimir(){
        for (int i=0; i<this.getTamanho(); i++){
            System.out.println("Cromosoma: "+i+" ");
            System.out.println("Fitness  : "+getFitness()[i]);
            String LineaCromosoma = individuos[i].toString();            
            String CromosomaMultilinea = individuos[i].ImprimirCromo(LineaCromosoma);
            System.out.print(CromosomaMultilinea);
            System.out.println();
        }
    }

    public Cromosoma[] getHijos() {
        return hijos;
    }

    public void setHijos(Cromosoma[] hijos) {
        this.hijos = hijos;
    }

    public double[] getFitness() {
        return fitness;
    }

    public void setFitness(double[] fitness) {
        this.fitness = fitness;
    }

    public int getMejorIndividuoPos() {
        return mejorIndividuoPos;
    }

    public void setMejorIndividuoPos(int mejorIndividuoPos) {
        this.mejorIndividuoPos = mejorIndividuoPos;
    }

    public Conocimiento getConocimiento() {
        return conocimiento;
    }

    public int getCantHijosGenerados() {
        return CantHijosGenerados;
    }

    public void setCantHijosGenerados(int CantHijosGenerados) {
        this.CantHijosGenerados = CantHijosGenerados;
    }

    public int getGeneracion() {
        return generacion;
    }

    public void setGeneracion(int generacion) {
        this.generacion = generacion;
    }
}
