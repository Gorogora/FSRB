/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gafuzzysystem;

import BaseDeConocimiento.KnowledgeBase;
import BaseDeDatos.DataBase;
import BaseDeReglas.RuleBase;
import Tuning.CHC;
import java.io.FileNotFoundException;
import read.ReadPWM;
import read.ReadRB;
import read.ReadTest;
import read.ReadTraining;

/**
 *
 * @author ana
 */
public class GAFuzzySystem {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        /**
         * args[0]: "-tra" 
         * args[1]: "src/Files/ELE1.tra" o "src/Files/ELE1.tst"
         * args[2]: "src/Files/BaseDatos.pwm"
         * args[3]: "src/Files/BaseReglas.wm"
         */
        
        // cargar la base de datos
        DataBase db = new DataBase();
        ReadPWM rpwm = new ReadPWM(db);
        rpwm.read(args[2]);
        System.out.println(db.getBaseDatos().size());
        
        // cargar la base de reglas
        RuleBase rb = new RuleBase();        
        ReadRB rrb = new ReadRB(db, rb);
        rrb.read(args[3]);
        System.out.println(rb.getBaseReglas().size());
        
        // crear la base de conocimiento
        KnowledgeBase kb = new KnowledgeBase(rb, db); 
        
        switch(args[0]){
            case "-tra":
                /**
                 * args[1]: ruta fichero de entrenamiento
                 */
                ReadTraining rt = new ReadTraining(db, rb, args[1]);
                rt.read();
                CHC genetico = new CHC(rt);
                genetico.chc();
                db.tuningDataBase(genetico.getPopulation().get(0).getCromosoma());
                rt.setDb(db);
                rt.writeOutputs(Params.OUTPUT_TRA_PATH);                
                db.write();
                rb.write(db);
                break;
                
            case "-tst":  
                ReadTest rtst = new ReadTest(db, rb, args[1]);
                rtst.read();
                
                rtst.writeOutputs(Params.OUTPUT_TST_PATH);
                System.out.println("Salida guardada en: " + Params.OUTPUT_TST_PATH);
                
                break;
        } 
    }
    
}
