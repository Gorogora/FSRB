/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaDeInferencia;

import BaseDeDatos.DataBase;
import BaseDeDatos.Triangulo;
import BaseDeReglas.RuleBase;
import InterfazDeFuzzificacion.Fuzzification;
import gafuzzysystem.Params;
import java.util.ArrayList;

/**
 *
 * @author ana
 */
public class InferenceSystem {
    
    private Fuzzification fuzzy;
    
    private final DataBase db;
    
    private final RuleBase rb;
    
    private ArrayList<Double> matching;
    
    private ArrayList<Double> pmv;
    
    public InferenceSystem(Fuzzification fuzzy, DataBase db, RuleBase rb){
        this.fuzzy = fuzzy;
        this.db = db;
        this.rb = rb;
        matching = new ArrayList<>(Params.R);
        pmv = new ArrayList<>(Params.R);
    }
    
    public void inferir(){
        for(int[] rule : rb.getBaseReglas()){
            double local_matchings[] = new double[Params.INPUTS];
            int l=0;
            for(int i=0; i< fuzzy.getFuzzyArgs().size(); i++){
                /**
                * Coger el antecedente de la regla y mirar si la variable de entrada 
                * correspondiente pertenece a esa etiqueta
                */
                if(fuzzy.getFuzzyArgs().get(i+1).contains(rule[i])){
                    local_matchings[l] = calcular_emparejamiento_local(rule[i], i);
                }
                else{
                    local_matchings[l] = 0;
                }
                l++;
            }
            
            // Calcular el grado de emparejamiento de la regla
            calcular_emparejamiento(local_matchings, Params.OPERADOR_CONJUNCION);
            
            /**
             * Como sólo vamos a tener un consecuente guardamos directamente el 
             * punto medio del mismo
             */
            Triangulo t = db.getBaseDatos().get(rule[rule.length-1]);
            pmv.add(t.getPunto_medio());            
           
        }
    }

    /**
     * Calcula el grado de pertenencia de un valor con un trapecio.
     * @param indexTrapecio Clave para recuperar el trapecio que representa la 
     * etiqueta lingUística.
     * @param indexArg Índice de la variable de entrada que se está estudiando.
     * @return 
     */
    private double calcular_emparejamiento_local(int indexTrapecio, int indexArg) {
        double ejemplo = Double.valueOf(fuzzy.getArgs()[indexArg]);
        Triangulo t = db.getBaseDatos().get(indexTrapecio);
        double h_ij = 0;
        if(ejemplo < t.getX0()){
            h_ij = 0;
        }
        else if(t.getX0() < ejemplo && ejemplo < t.getX1()){
            h_ij = (ejemplo - t.getX0()) / (t.getX1() - t.getX0());
        }
        else if(t.getX1() < ejemplo && ejemplo < t.getX2()){
            h_ij = 1;
        }
        else if(t.getX2() < ejemplo && ejemplo < t.getX3()){
            h_ij = (ejemplo - t.getX3()) / (t.getX2() - t.getX3());
        }
        else if(t.getX3() < ejemplo){
            h_ij = 0;
        }
        
        return h_ij;
    }
    
    /**
     * Calcula el matching de una regla.
     * @param local_matchings Grado de pertenencia de cada uno de los 
     * antecedentes de una regla.
     * @param operador Operador de conjunción.
     */
    private void calcular_emparejamiento(double[] local_matchings, int operador){
        switch(operador){
            case 1: //minimo
                double min = Double.POSITIVE_INFINITY;
                for(Double local : local_matchings){
                    if(local < min){
                        min = local;
                    }
                }
                matching.add(min);
                break;
            case 2: //producto algebraico
                double acumulador = 1;
                for(Double local : local_matchings){
                    acumulador = acumulador * local;
                }
                matching.add(acumulador);
                break;
        }
    }

    
    public ArrayList<Double> getMatching() {
        return matching;
    }

    public void setMatching(ArrayList<Double> matching) {
        this.matching = matching;
    }

    public ArrayList<Double> getPmv() {
        return pmv;
    }

    public void setPmv(ArrayList<Double> pmv) {
        this.pmv = pmv;
    }
}
