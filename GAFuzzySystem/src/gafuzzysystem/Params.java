/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gafuzzysystem;

/**
 *
 * @author ana
 */
public class Params {
    /**
     * Número de reglas
     */
    public static int R=0;
    
    /**
     * Número de variables de entradas
     */
    public static int INPUTS = 2;
    
    public static int OPERADOR_CONJUNCION = 2;  //producto escalar
    
    /**
     * Número de variables de salidas
     */
    public static int OUTPUTS = 1;
    
    public static int NUM_ETQ = 0;

    public static void setR(int R) {
        Params.R = R;
    }

    public static void setINPUTS(int INPUTS) {
        Params.INPUTS = INPUTS;
    }

    public static void setOUTPUTS(int OUTPUTS) {
        Params.OUTPUTS = OUTPUTS;
    }
    
    public static void setNUMETQ(int NUM_ETQ){
        Params.NUM_ETQ = NUM_ETQ;
    }
    
}
