/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDeDatos;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Define los conjuntos difusos.
 * @author ana
 */
public class DataBase implements Cloneable{
    
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
    
    public void tuningDataBase(double[] cromosoma){
        
        for(int i=1; i<=baseDatos.size(); i++){
            Triangulo t = baseDatos.get(i);   //recuperar el triángulo asociado a la clave
            // desplazamos el triángulo
            t.setX0(t.getX0() + cromosoma[i-1]);
            t.setX1(t.getX1() + cromosoma[i-1]);
            t.setX2(t.getX2() + cromosoma[i-1]);
            t.calcularPuntoMedio();
            baseDatos.put(i, t);            
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Object clone = null;
        clone = super.clone();        
        ((DataBase)clone).setBaseDatos((Hashtable)baseDatos.clone());
        return clone;
    }
    
    
    
}
