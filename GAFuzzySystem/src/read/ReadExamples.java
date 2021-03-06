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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ana
 */
public abstract class ReadExamples {
    
    protected DataBase db;
    protected RuleBase rb;
    /**
     * Almacena las salidas tras la ejecución del controlador difuso.
     */
    protected double[] outputs;
    
    /**
     * Almacena las salidas que se deberían obtener de la ejecución del controlador.
     */
    protected double[] trueOutputs;
    
    /**
     * Almacena las entradas del fichero.
     */
    protected double[][] inputs;
    
    /**
     * Número de ejemplos que contiene el fichero.
     */
    protected int exampleNumber;
    
    protected FileReader fr;
    protected BufferedReader bf;
    
    public ReadExamples(DataBase db, RuleBase rb, String fileName){
        this.db = db;
        this.rb = rb;        
        
        try {            
            fr = new FileReader(fileName);
            bf = new BufferedReader(fr);
            String linea = null;
            
            // leer el número de ejemplos que tiene el fichero
            exampleNumber = Integer.parseInt(bf.readLine());
            outputs = new double[exampleNumber];
            trueOutputs = new double[exampleNumber];
            // entradas + salidas
            int inout = Integer.parseInt(bf.readLine());
            //Params.setINPUTS(inout-1);
            inputs = new double[exampleNumber][Params.INPUTS];
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadTraining.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadTraining.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    /**
     * Lee el fichero fileName.
     */
    public abstract void read();
    
    /**
     * Calcula la salida del sistema.
     */
    private void calculateOutputs(){
        for(int i=0; i<exampleNumber; i++){
            String[] args2 = new String[Params.INPUTS];
            for(int j=0; j<Params.INPUTS; j++){
                args2[j] = String.valueOf(inputs[i][j]);
            }
            
            Fuzzification fuzzy = new Fuzzification(db, args2);
            fuzzy.fuzzificar();


            InferenceSystem is = new InferenceSystem(fuzzy, db, rb);
            is.inferir();

            Defuzzification defuzzy = new Defuzzification(is);
            defuzzy.defuzzificar();

            outputs[i] = defuzzy.getSalida(); 
        }
                  
    }
    
    /**
     * Calcula el error cuadrático medio.
     * @return Error cuadrático medio del sistema.
     */
    public double getECM(){
        calculateOutputs();
        double sum=0;
        for(int i=0; i<exampleNumber; i++){
            sum += (outputs[i] - trueOutputs[i]) * (outputs[i] - trueOutputs[i]);
        }

        double op1 = (double)1.0/(2.0 * exampleNumber);
        double op2 = sum;
        double result = op1 * op2;
        
        return result;
    }
    
    /**
     * Escribe en un fichero la salida de ejecutar el sistema fuzzy para un archivo .tra o .tst.
     * @param fileName Nombre del fichero donde se quiere escribir la salida.
     */
    public void writeOutputs(String fileName){
        try {
            double ecm = getECM();
            FileWriter fichero = new FileWriter(new File(fileName));
            
            // escribir número de ejemplos
            fichero.write(exampleNumber + "\n");
            
            // escribir el número de variables
            fichero.write((Params.INPUTS+1) + "\n");            
            
            // escribimos las entradas y la salida obtenida para ellas
            for(int i=0; i<exampleNumber; i++){
                for(int j=0; j<Params.INPUTS; j=j+2){
                    fichero.write(inputs[i][j] + "\t" + inputs[i][j+1] + "\t" + outputs[i] + "\n");
                }
            }
            
            // escribir el ECM
            fichero.write("ECM: " + ecm);
            
            fichero.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ReadExamples.class.getName()).log(Level.SEVERE, null, ex);
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
     * @return Las salidas que debería haber obtenido el controlador difuso. 
     */
    public double[] getTrueOutputs() {
        return trueOutputs;
    }

    public double[][] getInputs() {
        return inputs;
    }

    public void setInputs(double[][] inputs) {
        this.inputs = inputs;
    }

    public DataBase getDb() {
        return db;
    }

    public void setDb(DataBase db) {
        this.db = db;
    }
    
}
