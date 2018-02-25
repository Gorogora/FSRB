/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfazDeDefuzzificacion;

import SistemaDeInferencia.InferenceSystem;

/**
 * Convierte la salida del sistema de inferencia en un único valor real.
 * @author ana
 */
public class Defuzzification {
    
    /**
     * Salida del sistema.
     */
    private double salida;
    
    private final InferenceSystem is;
    
    public Defuzzification(InferenceSystem is){
        this.is = is;
    }
    
    /**
     * Función de defuzzificación siguiendo el Modo B (“Defuzzificar” Primero y 
     * Agregar Después). Convertir la aportación de cada regla en un número 
     * mediante el PMV y posteriormente, con esos valores, calcula un único valor 
     * final como salida, mediante una suma de cada uno de esos valores, ponderada 
     * por el matching
     */
    public void defuzzificar(){
        double dividendo=0, divisor=0;
        
        for(int i=0; i<is.getPmv().size(); i++){  
            dividendo += is.getMatching().get(i) * is.getPmv().get(i);
            divisor += is.getMatching().get(i);
        }
        
        if(divisor==0){
            salida = Params.DEFAULT_OUTPUT;
        }
        else{
            salida = dividendo / divisor;
        }
        
    }

    public double getSalida() {
        return salida;
    }

    public void setSalida(double salida) {
        this.salida = salida;
    }
}
