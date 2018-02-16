/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tuning;

import gafuzzysystem.Params;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Representa a un individuo de la población del algoritmo genético, es decir, 
 * una posible solución al problema. (Global Tuning)
 * @author ana
 */
public class Individuo implements Comparable<Individuo>{
    /**
     * Cromosoma en el cual cada gen está asociado al valor de tuning 
     * correspondiente a una etiqueta.
     */
    private ArrayList<Double> cromosoma;
    
    /**
     * Evaluación del cromosoma. La evaluación se realizará con el cálculo del 
     * ECM (error cuadrático medio)
     */
    private double ev;
    
    public final int GENES = Params.NUM_ETQ;
    
    public Individuo(){
        cromosoma = new ArrayList<>(GENES);
    }

    public ArrayList<Double> getCromosoma() {
        return cromosoma;
    }

    public void setCromosoma(ArrayList<Double> solucion) {
        this.cromosoma = solucion;
    }

        public double getEv() {
        return ev;
    }

    public void setEv(double ev) {
        this.ev = ev;
    }
    
    /**
     * Evalua el cromosoma mediante el ECM 
     */
    public void evaluar(){
        /**
         * Implementar el cálculo del error cuadrático medio
         * MSE = (1/2*dataSetSize)*sumatorio[(salida_obtenida-salida_esperada)^2]
         */
    }

    
    /**
     * The compareTo method compares the receiving object with the specified object 
     * and returns a negative integer, 0, or a positive integer depending on whether 
     * the receiving object is less than, equal to, or greater than the specified 
     * object. If the specified object cannot be compared to the receiving object, 
     * the method throws a ClassCastException.
     */
    @Override
    public int compareTo(Individuo o) {       
        if(o.getEv() < ev){
            return -1;
        }
        else if(o.getEv() == ev){
            return 0;
        }
        else{
            return 1;
        }
    }
    
    public static void main(String[] args) {
        Individuo i1 = new Individuo();
        i1.setEv(1);
        Individuo i2 = new Individuo();
        i2.setEv(2);
        ArrayList<Individuo> poblacion = new ArrayList<>();
        poblacion.add(i1);
        poblacion.add(i2);
        
        // ordena la colección en orden descendente de ev.
        Collections.sort(poblacion);
        for(Individuo i : poblacion){
            System.out.println("Ev: " + i.getEv());
        }
        
        
    }
    
    
}
