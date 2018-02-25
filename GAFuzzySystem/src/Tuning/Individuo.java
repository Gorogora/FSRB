/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tuning;

import gafuzzysystem.Params;
import BaseDeDatos.DataBase;
import BaseDeReglas.RuleBase;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import read.ReadPWM;
import read.ReadRB;
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
    private double[] cromosoma;
    
    /**
     * Evaluación del cromosoma. La evaluación se realizará con el cálculo del 
     * ECM (error cuadrático medio)
     */
    private double ev;
    
    private ReadTraining rt;
    
    public final int GENES = Params.NUM_ETQ;
    
    public Individuo(ReadTraining rt){
        cromosoma = new double[GENES];      
        this.rt = rt;
    }

    public double[] getCromosoma() {
        return cromosoma;
    }

    public void setCromosoma(double[] solucion) {
        this.cromosoma = solucion;
    }

    public double getEv() {
        return ev;
    }

    public void setEv(double ev) {
        this.ev = ev;
    }
    
    /**
     * Evalua el cromosoma mediante el ECM. 
     */
    public void evaluar(){
        DataBase db_original = new DataBase(rt.getDb());
        DataBase db_copia = new DataBase(rt.getDb());
        db_copia.tuningDataBase(cromosoma);  
        rt.setDb(db_copia);
        ev = rt.getECM();   //lo evalua con la base de datos tuneada
        rt.setDb(db_original);  //vuelvo a dejarle su base de datos original para no modificar el objeto         
        
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Arrays.hashCode(this.cromosoma);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Individuo other = (Individuo) obj;
        if (!Arrays.equals(this.cromosoma, other.cromosoma)) {
            return false;
        }
        return true;
    }   
}
