/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzysystem;

import BaseDeConocimiento.KnowledgeBase;
import BaseDeDatos.DataBase;
import BaseDeReglas.RuleBase;
import InterfazDeDefuzzificacion.Defuzzification;
import InterfazDeFuzzificacion.Fuzzification;
import SistemaDeInferencia.InferenceSystem;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import read.ReadPWM;
import read.ReadRB;
import read.ReadTraining;

/**
 *
 * @author ana
 */
public class FuzzySystem {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        int numArgs = args.length;
        
        // cargar la base de datos
        DataBase db = new DataBase();
        ReadPWM rpwm = new ReadPWM(db);
        rpwm.read(Params.PWM_PATH);
        
        // cargar la base de reglas
        RuleBase rb = new RuleBase();        
        ReadRB rrb = new ReadRB(db, rb);
        rrb.read(Params.RULES_PATH);
        
        // crear la base de conocimiento
        KnowledgeBase kb = new KnowledgeBase(rb, db); 
        
        
        switch(args[0]){
            case "-tra":
                ReadTraining rt = new ReadTraining(db, rb, args[1]);
                rt.read();
                rt.writeOutputs();
                System.out.println("Salida guardada en: " + Params.OUTPUT_TRA_PATH);
                break;
            case "-in":
                /**
                * args[1]: primera variable
                * args[i]: i-ésima variable
                */
                
                String[] args2 = new String[args.length-1];
                for(int i=0; i<args2.length; i++){
                    args2[i] = args[i+1];
                }

                /*
                * Ejemplo: entrada: 36.000000 223.330002   salida: 567.000000
                */

                Fuzzification fuzzy = new Fuzzification(db, args2);
                fuzzy.fuzzificar();


                InferenceSystem is = new InferenceSystem(fuzzy, db, rb);
                is.inferir();

                Defuzzification defuzzy = new Defuzzification(is);
                defuzzy.defuzzificar();

                // escribir la salida en el fichero
                try {
                    FileWriter fichero = new FileWriter(new File(Params.OUTPUT_PATH));

                    for(String in : args2){
                        fichero.write(in + "\t");
                    }

                    fichero.write(defuzzy.getSalida() + "");
                    
                    fichero.close();

                } catch (IOException ex) {
                    Logger.getLogger(FuzzySystem.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Salida guardada en: " + Params.OUTPUT_PATH);
                break;
        }   
    }
    
}
