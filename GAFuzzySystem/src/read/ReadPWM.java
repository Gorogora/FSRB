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
 * Lee un fichero de extensión .pwm que contiene la base de datos.
 * @author ana
 */
public class ReadPWM {
    private DataBase db;

    public ReadPWM(DataBase db) {
        this.db = db;
    }
    
    /**
     * Lee el fichero pasado por parámetro y guarda los datos leídos en la 
     * estructura correspondiente.
     * @param fileName Nombre del fichero que se quiere leer.
     * @throws FileNotFoundException 
     */
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
            bf.readLine();  //
                       
            int id = 1; //id del antecendente o consecuente al que pertenece
            int key = 1;
            while((linea = bf.readLine()) != null){                
                if(linea.contains("Variable")){
                    linea = bf.readLine();
                    while(linea != null && !linea.equals("")){
                        StringTokenizer st = new StringTokenizer(linea);    //Etiqueta 1: (-52.166668,1.000000,54.166668)
                        st.nextToken(); //Etiqueta
                        st.nextToken(); //1:
                        String dato = st.nextToken();   //(-52.166668,1.000000,54.166668)
                        String[] datos = dato.substring(1, dato.length()-1).split(",");  //-52.166668 1.000000 54.166668  
                        Triangulo t = new Triangulo(Double.valueOf(datos[0]), Double.valueOf(datos[1]), Double.valueOf(datos[2]), id);
                        if(!db.getBaseDatos().containsValue(t)){
                            db.getBaseDatos().put(key, t);
                            key++;
                        }
                        linea = bf.readLine();
                    }
                    id++;                    
                }                
            }
            
            Params.setNUMETQ(db.getBaseDatos().size());
            
        } catch (IOException ex) {
            Logger.getLogger(ReadRB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            
}
