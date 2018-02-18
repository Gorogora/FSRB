/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package read;

import BaseDeDatos.DataBase;
import BaseDeReglas.RuleBase;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Lee un fichero de extensión .tra
 * @author usuario
 */
public class ReadTraining extends ReadExamples{    
     
    public ReadTraining(DataBase db, RuleBase rb, String fileName){
        super(db, rb, fileName);
    }
    
    /**
     * Almacena los datos del fichero de training en su estructura correspondiente.
     */
    @Override
    public void read(){
        try {
            String linea = null;
            
            // leer datos de entrenamiento
            int i=0;
            while((linea = bf.readLine()) != null){ 
                String[] tokens = linea.split("\t");                
                
                int t;
                for(t=0; t<tokens.length-1; t++){
                    inputs[i][t] = Double.parseDouble(tokens[t]);
                }
                
                trueOutputs[i] = Double.parseDouble(tokens[t]);
                i++;  
            }
        } catch (IOException ex) {
            Logger.getLogger(ReadTraining.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
