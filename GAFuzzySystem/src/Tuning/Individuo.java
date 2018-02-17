/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tuning;

import BaseDeConocimiento.KnowledgeBase;
import gafuzzysystem.Params;
import java.util.ArrayList;
import java.util.Collections;
import BaseDeDatos.DataBase;
import java.util.logging.Level;
import java.util.logging.Logger;
import read.ReadTraining;

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
    
    /**
     * Base de datos original. 
     */
    private DataBase db_original;   //sobre esta base de datos se puede trabajar sin importar si se modifica
    
    private ReadTraining rt;
    
    public final int GENES = Params.NUM_ETQ;
    
    public Individuo(ReadTraining rt){
        cromosoma = new ArrayList<>(GENES);
        try {
            db_original = (DataBase) rt.getDb().clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Individuo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.rt = rt;
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
        try {
            DataBase db_copia = (DataBase) db_original.clone();
            db_copia.tuningDataBase(cromosoma);   
            rt.setDb(db_copia);
            ev = rt.getECM();
            rt.setDb(db_original);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Individuo.class.getName()).log(Level.SEVERE, null, ex);
        }
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
}
