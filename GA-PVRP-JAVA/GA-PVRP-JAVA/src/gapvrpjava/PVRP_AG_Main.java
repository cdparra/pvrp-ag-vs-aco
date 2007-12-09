/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gapvrpjava;

import java.io.IOException;

/**
 *
 * @author Cristhian Parra
 */
public class PVRP_AG_Main {
    private static int CANT_GENERACIONES = 300;
    private static int PROB_MUTACION = 40;
    private static int TAM_POBLACION = 30;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        Poblacion poblacion;
        Conocimiento conocimiento;
        String problemInstanceFile;
        String bestSolutionFile;
              
        if (args.length == 2) {
            problemInstanceFile = args[0];
            bestSolutionFile = args[1];
        } else if (args.length == 1) {
            problemInstanceFile = "C:\\pvrp\\p"+args[0];
            bestSolutionFile = "C:\\pvrp-sols\\p"+args[0];
        } else {
            problemInstanceFile = "C:\\pvrp\\p26";
            bestSolutionFile = "C:\\pvrp-sols\\p26";
        }
        
        
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("-----------------> Periodic Vehicle Routing Problem <------------------\n");
        
        System.out.println("I. CARGANDO EL CONOCIMIENTO DE LA INSTANCIA '"+problemInstanceFile+"'\n");
        
        conocimiento = new Conocimiento();
        conocimiento.CargarConocimiento(problemInstanceFile);
        
        String conocimientoString = conocimiento.toString();
        System.out.println(conocimientoString);
        
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("II. INICALIZANDO POBLACIÓN \n");
        
        poblacion = new Poblacion(conocimiento,TAM_POBLACION,PROB_MUTACION);
        poblacion.evaluar();
        
        System.out.println("  2.1. Población Inicial: \n");     
        String popInicial = poblacion.toStringImprimible();        
        System.out.println(popInicial);
        
        
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("III. GENERACIONES \n");
        
                
        while (poblacion.getGeneracion() <=CANT_GENERACIONES) {
            String generacion = "--> POBGEN ["+poblacion.getGeneracion()+"]::";
            
            // iteraciones del algoritmo genetico
            Cromosoma [] selectos = poblacion.seleccion();
            poblacion.cruce(selectos);
            
            //poblacion.setHijos(poblacion.getIndividuos());
            poblacion.mutar();
            poblacion.reemplazar();
            boolean newBestGlobal = poblacion.evaluar();
            
            System.out.println(" Inválidos en la Población -> "+poblacion.hayInvalidos());
            System.out.println(generacion+"BEST Local  -> "+poblacion.toStringMejorActual());
            System.out.println(generacion+"BEST Global -> "+poblacion.toStringMejorHistorico());
            if (newBestGlobal) {
                System.out.println(generacion+"NUEVO BEST Global!!!!");
            }

            System.out.println(generacion+"POBLACIÓN GENERACIÓN: \n");
            String ultimo = poblacion.toStringImprimible();
            System.out.println(generacion+ultimo);
            System.out.println(generacion+"END ----------------------------------------------------------->");
        }
        
        
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("IV. FIN DEL ALGORITMO \n");
        System.out.println("Mejor Solución Global -> "+poblacion.toStringMejorHistorico());
        System.out.println("Generación de la Mejor Solución Global -> " + poblacion.getMejorGeneracion());
        System.out.println(" Es la mejor solución válida? "+poblacion.getMejorHistorico().isValido(conocimiento));
        System.out.println("-------------------------------------------------------------------------");
        
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("ÚLTIMA GENERACIÓN: \n");
        String ultimo = poblacion.toStringImprimible();
        System.out.println(ultimo);
        System.out.println("-------------------------------------------------------------------------");
        
        
    }

}
