/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDeDatos;

import java.util.Hashtable;

/**
 * Define los conjuntos difusos.
 * @author ana
 */
public class DataBase {
    
    /**
     * Almacena las etiquetas lingüísticas de cada una de las variables de entrada.
     */
    private Hashtable<Integer,Triangulo> baseDatos;
    
    public DataBase(){
        baseDatos = new Hashtable<>();
    }

    public Hashtable<Integer, Triangulo> getBaseDatos() {
        return baseDatos;
    }

    public void setBaseDatos(Hashtable<Integer, Triangulo> baseDatos) {
        this.baseDatos = baseDatos;
    }
    
}
