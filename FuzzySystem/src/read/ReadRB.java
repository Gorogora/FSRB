/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package read;

import BaseDeDatos.DataBase;
import BaseDeDatos.Triangulo;
import BaseDeReglas.RuleBase;
import fuzzysystem.Params;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Lee un fichero de extensión .wm  con la base de reglas y rellena con la 
 * información correspondiente tanto la base de reglas como la base de conocimiento.
 * @author ana
 */
public class ReadRB {
    private DataBase db;
    private RuleBase rb;
    
    public ReadRB(DataBase db, RuleBase rb){
        this.db = db;
        this.rb = rb;
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
            ArrayList<int[]> baseReglas = new ArrayList<>();
            
            /**
             * Leer número de reglas
             * Numero de reglas: 22
             */
            int R = 0;
            if((linea = bf.readLine()) != null){
                String[] tokens = linea.split(": ");    //dos puntos espacio
                //coger el segundo token que es el que contiene el nº de reglas
                R = Integer.parseInt(tokens[1]);
                Params.setR(R);
            }
            
            // leer linea en blanco de separación
            bf.readLine();
            
            // lectura de las reglas
            int it = 0;
            while(it < R){   
                int j = 1;  //id del antecendente o consecuente al que pertenece
                int[] keys = new int[Params.INPUTS + Params.OUTPUTS];
                while(!(linea = bf.readLine()).equals("")){
                    // guarda los apuntadores a las etiquetas que definen la regla                   
                    StringTokenizer st = new StringTokenizer(linea);
                    double datos[] =  new double[st.countTokens()];
                    int i = 0;
                    while (st.hasMoreTokens()) {
                        datos[i] = Double.parseDouble(st.nextToken());
                        i++;
                    }                    
                                          
                    Triangulo t = new Triangulo(datos[0], datos[1], datos[2], j);
                    for(int k : db.getBaseDatos().keySet()){
                        if(db.getBaseDatos().get(k).equals(t)){
                            keys[j-1] = k;
                            break;
                        }
                    }                                     
                    j++;                                       
                } 
                
                baseReglas.add(keys);
                it++;                
            } 
            
            rb.setBaseReglas(baseReglas);
            
            /**
             * Lectura salida por defecto
             * Salida por defecto:   3877.500000
             */
            double salida_por_defecto = 0;
            if((linea = bf.readLine()) != null){
                String[] tokens = linea.split(":   ");    //dos puntos tres espacios
                //coger el segundo token que es el que contiene el valor
                salida_por_defecto = Double.parseDouble(tokens[1]);
                Params.setDEFAULT_OUTPUT(salida_por_defecto);
            }
            
            // leer dos lineas en blanco de separación
            bf.readLine();
            bf.readLine();
            
            /**
             * Lectura error cuadrático medio de training y test.
             * ECMtra: 218644.015625. ECMtst: 196974.687500
             */
            double ECMtra = 0, ECMtst = 0;
            if((linea = bf.readLine()) != null){
                String[] tokens = linea.split(" "); // separamospor espacios   
                ECMtra = Double.parseDouble(tokens[1].substring(0, tokens[1].length()-1));  //guardamos el ECMtra sin el punto del final
                ECMtst = Double.parseDouble(tokens[3]);
                
                Params.setECMtra(ECMtra);
                Params.setECMtst(ECMtst);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ReadRB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
