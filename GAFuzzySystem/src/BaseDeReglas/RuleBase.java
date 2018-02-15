/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDeReglas;

import java.util.ArrayList;

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
    
}
