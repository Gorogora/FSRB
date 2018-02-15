/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package read;

import BaseDeDatos.DataBase;
import BaseDeReglas.RuleBase;
import InterfazDeDefuzzificacion.Defuzzification;
import InterfazDeFuzzificacion.Fuzzification;
import SistemaDeInferencia.InferenceSystem;
import gafuzzysystem.Params;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Lee un fichero de extensión .tra
 * @author usuario
 */
public class ReadTraining {
    
    private DataBase db;
    private RuleBase rb;
    /**
     * Almacena las salidas tras la ejecución del controlador difuso.
     */
    private double[] outputs;
    /**
     * Almacena las salidas que se deberían obtener de la ejecución del controlador.
     */
    private double[] trueOutputs;
    
    /**
     * Número de ejemplos que contiene el fichero de entrenamiento.
     */
    private int exampleNumber;
    
    private FileReader fr;
    private BufferedReader bf;
    
    public ReadTraining(DataBase db, RuleBase rb, String fileName){
        this.db = db;
        this.rb = rb;
        
        FileReader fr = null;
        try {            
            fr = new FileReader(fileName);
            bf = new BufferedReader(fr);
            String linea = null;
            
            // leer el número de ejemplos que tiene el fichero
            exampleNumber = Integer.parseInt(bf.readLine());
            outputs = new double[exampleNumber];
            trueOutputs = new double[exampleNumber];
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadTraining.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadTraining.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void read(){
        try {
            String linea = null;
            
            // entradas + salidas
            int inout = Integer.parseInt(bf.readLine());
            Params.setINPUTS(inout-1);
            
            // leer datos de entrenamiento
            int i=0;
            while((linea = bf.readLine()) != null){                
                String[] tokens = linea.split("\t");                
                String[] args2 = new String[tokens.length-1];
                
                int t;
                for(t=0; t<args2.length; t++){
                    args2[t] = tokens[t];
                }
                
                                
                Fuzzification fuzzy = new Fuzzification(db, args2);
                fuzzy.fuzzificar();
                
                
                InferenceSystem is = new InferenceSystem(fuzzy, db, rb);
                is.inferir();
        
                Defuzzification defuzzy = new Defuzzification(is);
                defuzzy.defuzzificar();

                outputs[i] = defuzzy.getSalida();
                trueOutputs[i] = Double.parseDouble(tokens[t]);
                i++;               
                
            }
        } catch (IOException ex) {
            Logger.getLogger(ReadTraining.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Devuelve las salidas obtenidas por el controlador difuso.
     * @return Las salidas generadas por el controlador difuso.
     */
    public double[] getOutputs() {
        return outputs;
    }

    /**
     * Devuelve las salidas que debería haber obtenido el controlador difuso.
     * @return 
     */
    public double[] getTrueOutputs() {
        return trueOutputs;
    }

    /**
     * Calcula el error cuadrático medio.
     * @return 
     */
    public double getECM(){
        double sum=0;
        for(int i=0; i<exampleNumber; i++){
            sum += (outputs[i] - trueOutputs[i]) * (outputs[i] - trueOutputs[i]);
        }
        
        return (sum/exampleNumber);
    }
    
    
    
}
