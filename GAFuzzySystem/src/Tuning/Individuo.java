/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tuning;

import gafuzzysystem.Params;
import java.util.ArrayList;

/**
 * Representa a un individuo de la población del algoritmo genético, es decir, 
 * una posible solución al problema. (Global Tuning)
 * @author ana
 */
public class Individuo {
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
    
    
}
