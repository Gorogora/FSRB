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
    public static int R;
    
    /**
     * Número de variables de entradas
     */
    public static int INPUTS;
    
    public static int OPERADOR_CONJUNCION = 2;  //producto escalar
    
    /**
     * Número de variables de salidas
     */
    public static final int OUTPUTS = 1;
    
    /**
     * Número total de etiquetas.
     */
    public static int NUM_ETQ;
    
    /**
     * Error cuadrático medio del fichero de training.
     */
    public static double ECMtra;
    
    /**
     * Error cuadrático medio del fichero de test.
     */
    public static double ECMtst;

    /**
     * Salida por defecto.
     */
    public static double DEFAULT_OUTPUT;
    
    /**
     * Ruta del fichero resumen con la base de datos inicial.
     */
    public static String PWM_PATH = "src/Files/ELE1.pwm";
    
    /**
     * Ruta del fichero resumen con la base de datos tras el tuning.
     */
    public static String PWM_TUNING_PATH = "src/Files/BaseDatos.pwm";
    
    public static String WM_TUNING_PATH = "src/Files/BaseReglas.wm";
    
    /**
     * Ruta del fichero de reglas.
     */
    public static final String RULES_PATH = "src/Files/ELE1.wm";
    
    /**
     * Ruta del fichero que contiene la salida del entrenamiento.
     */
    public static final String OUTPUT_TRA_PATH = "src/Files/Output_tra.txt";
    
    /**
     * Ruta del fichero que contiene la salida del test.
     */
    public static final String OUTPUT_TST_PATH = "src/Files/Output_tst.txt";
    
    /**
     * Ruta del fichero que contiene la salida para una entrada dada.
     */
    public static final String OUTPUT_PATH = "src/Files/Output.txt";
    

    public static void setR(int R) {
        Params.R = R;
    }

    public static void setINPUTS(int INPUTS) {
        Params.INPUTS = INPUTS;
    }
    
    public static void setNUMETQ(int NUM_ETQ){
        Params.NUM_ETQ = NUM_ETQ;
    }

    public static void setECMtra(double ECMtra) {
        Params.ECMtra = ECMtra;
    }

    public static void setECMtst(double ECMtst) {
        Params.ECMtst = ECMtst;
    }

    public static void setDEFAULT_OUTPUT(double DEFAULT_OUTPUT) {
        Params.DEFAULT_OUTPUT = DEFAULT_OUTPUT;
    }
    
}
