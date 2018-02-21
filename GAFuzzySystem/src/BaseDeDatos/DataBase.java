/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDeDatos;

import gafuzzysystem.Params;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            t.calcularPuntoMaxValue();
            baseDatos.put(i, t);  
        }
    }
    
    /**
     * Escribe la base de datos de acuerdo al formato del fichero pwm.
     */
    public void write(){
        try {
            FileWriter fichero = new FileWriter(new File(Params.PWM_TUNING_PATH));
            
            fichero.write("Parametros de Entrada aceptados :\n");
            fichero.write("\n");
            fichero.write("Fichero con datos de entrenamiento = ELE1.tra \n" );
            fichero.write("Fichero con datos de prueba = ELE1.tst \n");
            fichero.write("Variable de estado = 1. Numero de etiquetas = 7.\n");
            fichero.write("Universo de discurso = [1.000000.320.000000]\n");
            fichero.write("Variable de estado = 2. Numero de etiquetas = 7.\n");
            fichero.write("Universo de discurso = [60.000000.1673.000000]\n");
            fichero.write("Variable de control = 1. Numero de etiquetas = 7.\n");
            fichero.write("Universo de discurso = [80.000000.7675.000000]\n");
            fichero.write("\n");
            fichero.write("\n");
            fichero.write("Base de Datos inicial: \n");
            fichero.write("\n");
            int it=1;
            while(it<=Params.INPUTS+1){
                fichero.write("  Variable " + it + ":\n");
                int etiqueta = 1;
                for(Triangulo t : baseDatos.values()){
                    if(t.getId() == it){
                        fichero.write("    Etiqueta " + etiqueta + ": (" + t.getX0() + "," + t.getX1() + "," + t.getX2() + ")");
                        etiqueta++;
                    }
                }
                it++;
                fichero.write("\n");
            }

            fichero.close();
        } catch (IOException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
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
