/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gafuzzysystem;

import BaseDeConocimiento.KnowledgeBase;
import BaseDeDatos.DataBase;
import BaseDeReglas.RuleBase;
import InterfazDeDefuzzificacion.Defuzzification;
import InterfazDeFuzzificacion.Fuzzification;
import SistemaDeInferencia.InferenceSystem;
import Tuning.CHC;
import java.io.FileNotFoundException;
import read.ReadPWM;
import read.ReadRB;
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
        int numArgs = args.length;
        DataBase db = new DataBase();
        RuleBase rb = new RuleBase();
        
        
        ReadPWM rpwm = new ReadPWM(db);
        rpwm.read("src/Files/ELE1.pwm");
        System.out.println(db.getBaseDatos().size());

        /*ReadRB rrb = new ReadRB(db, rb);
        rrb.read(args[0]);
        
        KnowledgeBase kb = new KnowledgeBase(rb, db); 
        */
        
        switch (numArgs) {
            case 1: 
                /*ReadTraining rt2 = new ReadTraining(db, rb, args[1]);
                rt2.read();
                CHC genetico = new CHC(rt2);
                genetico.chc();*/
                break;
            case 2:
                /**
                * args[0]: ruta fichero de reglas = "src/Files/ELE1.wm"
                * args[1]: ruta fichero entrenamiento = "src/Files/ELE1.tra"
                */
                /*ReadTraining rt = new ReadTraining(db, rb, args[1]);
                rt.read();
                
                System.out.println(rt.getECM());*/
                
                ReadTraining rt2 = new ReadTraining(db, rb, args[1]);
                rt2.read();
                CHC genetico = new CHC(rt2);
                genetico.chc();
                
                break;
            case 3:
                /**
                * args[0]: ruta fichero de reglas = "src/Files/ELE1.wm"
                * args[1]: primera variable de entrada
                * args[i]: i-ésima variable de entrada
                */
                

                String[] args2 = new String[args.length-1];
                for(int i=0; i<args.length; i++){
                    args2[i] = args[i+1];
                }
        
                /*String[] args2 = new String[2];
                args2[0] = "10.000000";
                args2[1] = "648.330017";*/


                Fuzzification fuzzy = new Fuzzification(db, args2);
                fuzzy.fuzzificar();


                InferenceSystem is = new InferenceSystem(fuzzy, db, rb);
                is.inferir();
        
                Defuzzification defuzzy = new Defuzzification(is);
                defuzzy.defuzzificar();

                System.out.println("Salida: " + defuzzy.getSalida());
                break;
                
            
            default:
                throw new AssertionError();
        }
    }
    
}
