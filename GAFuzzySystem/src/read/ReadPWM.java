/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package read;

import BaseDeDatos.DataBase;
import BaseDeDatos.Triangulo;
import gafuzzysystem.Params;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ana
 */
public class ReadPWM {
    private DataBase db;

    public ReadPWM(DataBase db) {
        this.db = db;
    }
    
    public void read(String fileName) throws FileNotFoundException{
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader bf = new BufferedReader(fr);
            String linea;
            
            // leer lineas no necesarias
            bf.readLine();  //Parametros de Entrada aceptados :
            bf.readLine();  //
            bf.readLine();  //Fichero con datos de entrenamiento = d1-1.tra 
            bf.readLine();  //Fichero con datos de prueba = d1-1.tst 
            
            int inputs=0;
            
            while(!bf.readLine().equals("")){
                inputs++;
            }
            
            // definimos el número de entradas del sistema
            Params.setINPUTS(inputs/2 - 1); //-1 porque también lee la linea de output que siempre es 1
            
            // leer lineas no necesarias
            bf.readLine();  //
            bf.readLine();  //Base de Datos inicial: 
            bf.readLine();
            
            int it = 0;
            int key = 1;
            bf.readLine();  //Variable 1:
            StringTokenizer st = new StringTokenizer(bf.readLine());
            while(it < (inputs/2)+2){
                while(st.hasMoreTokens() && st.nextToken().equals("Etiqueta")){
                    //st.nextToken(); //Etiqueta
                    st.nextToken(); //1:  
                    String dato = st.nextToken();   //(-52.166668,1.000000,54.166668)
                    String[] datos = dato.substring(1, dato.length()-1).split(",");  //-52.166668 1.000000 54.166668  
                    Triangulo t = new Triangulo(Double.valueOf(datos[0]), Double.valueOf(datos[1]), Double.valueOf(datos[2]), it);
                    if(!db.getBaseDatos().containsValue(t)){
                            db.getBaseDatos().put(key, t);
                            key++;
                    }
                    st = new StringTokenizer(bf.readLine());
                }
                it++;                
                st = new StringTokenizer(bf.readLine());
                
            }
            
            Params.setNUMETQ(db.getBaseDatos().size());
            
        } catch (IOException ex) {
            Logger.getLogger(ReadRB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            
}
