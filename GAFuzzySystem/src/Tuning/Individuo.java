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
import read.ReadTest;
import read.ReadPWM;
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
    
    /**
     * Base de datos original. 
     */
    private DataBase db_original;   //sobre esta base de datos se puede trabajar sin importar si se modifica
    
    private ReadTraining rt;
    
    public final int GENES = Params.NUM_ETQ;
    
    public Individuo(ReadTraining rt){
        cromosoma = new double[GENES];
        try {
            db_original = (DataBase) rt.getDb().clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Individuo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
    
    public static void main(String[] args) throws FileNotFoundException {
        /*DataBase db = new DataBase();
        RuleBase rb = new RuleBase();

        ReadRB rrb = new ReadRB(db, rb);
        rrb.read("src/Files/ELE1.wm");
        System.out.println(db.getBaseDatos().size());
        ReadTraining rt = new ReadTraining(db, rb, "src/Files/ELE1.tra");
        rt.read();
        System.out.println(rt.getECM());
        
        ReadTest rtst = new ReadTest(db, rb, "src/Files/ELE1.tst");
        rtst.read();
        System.out.println(rtst.getECM());
        
        ArrayList<Individuo> population = new ArrayList<>();
        
        Individuo i1 = new Individuo(rt);
        Individuo i2 = new Individuo(rt);
        
        System.out.println(i1.equals(i2));
        
        population.add(i1);
        System.out.println(population.contains(i2));
        
        i1.evaluar();
        i2.evaluar();
        System.out.println(i1.equals(i2));
        
        i1.getCromosoma()[0] = 0.4;
        i1.evaluar();
        System.out.println(i1.equals(i2));
        
        i2.getCromosoma()[0] = 0.4;
        i2.evaluar();
        System.out.println(i1.equals(i2));
        */
        
        DataBase db = new DataBase();
        ReadPWM rpwm = new ReadPWM(db);
        rpwm.read("src/Files/ELE1.pwm");
        System.out.println(db.getBaseDatos().size());
        
    }
}
