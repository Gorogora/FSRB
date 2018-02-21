/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfazDeFuzzificacion;

import BaseDeDatos.DataBase;
import BaseDeDatos.Triangulo;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Dado un ejemplo de entrada identifica a que etiquetas pertenece cada una de las entradas.
 * @author ana
 */
public class Fuzzification {
    
    private final DataBase db;
    /**
     * Almacena una lista de las etiquetas lingüísticas a las que pertenece la 
     * variable de entrada guardando su key en la base de datos. La clave de esta 
     * tabla es el número de argumento.
     */
    private Hashtable<Integer,ArrayList<Integer>> fuzzyArgs;
    
    private String[] args;
    
    public Fuzzification(DataBase db, String[] args){
        this.db = db;
        fuzzyArgs = new Hashtable<>();
        this.args = args;
    }
    
    /**
     * Fuzzifica las entradas. 
     */
    public void fuzzificar(){

        int it = 1;
        for(String a : args){
            double arg = Double.valueOf(a);
            ArrayList<Integer> keys = new ArrayList<>();
            for(int i=0; i<db.getBaseDatos().size(); i++){
                int key = i+1;
                Triangulo t = db.getBaseDatos().get(key);
                if(t != null){
                    /** comprobar que el trapecio que se está revisando es de la 
                    * variable correspondiente por ejemplo, si se está revisando la
                    * primera variable de entrada el id debe ser 1                
                    */
                    if(t.getId() == it){
                       /**
                        * Si el valor pertenece a la etiqueta lingüística 
                        */
                        if(t.getX0() <= arg && arg <= t.getX2()){
                           // guardarlo
                           keys.add(key);
                        }
                    } 
                }                
            }
            fuzzyArgs.put(it, keys);
            it++;
        }
    }

    public Hashtable<Integer, ArrayList<Integer>> getFuzzyArgs() {
        return fuzzyArgs;
    }

    public void setFuzzyArgs(Hashtable<Integer, ArrayList<Integer>> fuzzyArgs) {
        this.fuzzyArgs = fuzzyArgs;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
    
}
