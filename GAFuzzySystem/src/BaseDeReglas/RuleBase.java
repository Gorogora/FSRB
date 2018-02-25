/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDeReglas;

import BaseDeDatos.DataBase;
import BaseDeDatos.Triangulo;
import gafuzzysystem.Params;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Almacena las reglas que definen el comportamiento del sistema.
 * @author ana
 */
public class RuleBase {
    /**
     * Guarda el key del triángulo que representa el valor del antecedente. Cada 
     * elemento del array representa una regla y contiene un vector de tamaño 
     * INPUTS + OUTPUTS
     */
    private ArrayList<int[]> baseReglas;        
    
    public RuleBase(){
        baseReglas = new ArrayList<>();   
    }

    public ArrayList<int[]> getBaseReglas() {
        return baseReglas;
    }

    public void setBaseReglas(ArrayList<int[]> baseReglas) {
        this.baseReglas = baseReglas;
    }
    
    /**
     * Escribe la base de reglas de acuerdo al formato del fichero wm.
     */
    public void write(DataBase db){
        try {
            FileWriter fichero = new FileWriter(new File(Params.WM_TUNING_PATH));
            
            
            fichero.write("Numero de reglas: " + Params.R + "\n");
            fichero.write("\n");
            
            for(int[] keys : baseReglas){
                for(int i=0; i<keys.length; i++){
                    Triangulo t = db.getBaseDatos().get(keys[i]);
                    fichero.write(t.getX0() + "\t" + t.getX1() + "\t" + t.getX2() + "\n");                    
                }
                fichero.write("\n");
            }

            fichero.write("Salida por defecto:   3877.500000\n");
            fichero.write("\n");
            fichero.write("\n");
            fichero.write("ECMtra: 218644.015625. ECMtst: 196974.687500");
            
            fichero.close();
        } catch (IOException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
