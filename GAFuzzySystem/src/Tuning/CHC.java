/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tuning;

import gafuzzysystem.Params;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import read.ReadTraining;

/**
 * Codificación del algoritmo genético CHC acorde a las consideraciones del artículo 
 * Genetic Tuning on Fuzzy Systems Based on the linguistic 2-Tuples Representation.
 * @author ana
 */
public class CHC {
    
    private final int STOP = 200;
    private final int POPULATION_SIZE = 50;
    private final double MAX_TUNING = 0.5;
    private final double MIN_TUNING = -0.5;
    private final double ALPHA = 0.5;
    private final int L = Params.NUM_ETQ; // L = longitud del cromosoma
    
    private ArrayList<Individuo> population;
    
    private Random rnd;
    
    private ReadTraining rt;
    
    private int umbral_cruce;
    
    public CHC(ReadTraining rt){
        population = new ArrayList<>(POPULATION_SIZE);
        rnd = new Random(1000000);
        this.rt = rt;        
        umbral_cruce = L/4;
    }
    
    public void chc(){
        int iterations = 0;
        
        // inicializa la población P(t) y evaluar los individuos de la misma
        inicializar();
        
        // mientras no se cumpla la condición de parada
        while(iterations < STOP){    //iterations < STOP
            // t++
            // copiar los miembros de P(t-1) en C(t) en un orden random            
            System.out.println("Desordenar población");
            Collections.shuffle(population, rnd);
            ArrayList<Individuo> current_population = (ArrayList<Individuo>) population.clone();
            System.out.println("Población desordenada");
            
            // recombinar estructuras en C(t) formando C'(t) y evaluar a los nuevos individuos
            blx_cross(current_population);    // C'(t)
            
            /* seleccionar los individuos que formarán la nueva población P(t) 
            a partir de C'(t) y P(t-1)*/
            Collections.sort(current_population, Collections.reverseOrder());   // ordena la población en orden ascendente en función de ev, es decir, el individuio con ev=1 estará antes que el que tenga ev=2
            ArrayList<Individuo> elite = new ArrayList<>(current_population.subList(0, POPULATION_SIZE));   //P(t)
            
            // multiarranque
            Collections.sort(population, Collections.reverseOrder());   //ordenamos a la población para poder compararlas
            if(elite.equals(population)){    // si(P(t) equals P(t-1))
                umbral_cruce--; 
                System.out.println("Disminuir umbral de cruce");
            }
            population = (ArrayList<Individuo>) elite.clone(); // P(t)

            if(umbral_cruce < 0){
                diverge(elite.get(0));
                umbral_cruce = L/4;
            }  
            
            Collections.sort(population, Collections.reverseOrder());
            System.out.println("Iteración: " + iterations + " --> " + population.get(0).getEv() + " " + population.get(POPULATION_SIZE-1).getEv());
            iterations++;
        }
    }

    /**
     * Crea una población de individuos aleatorios. Un individuo contendrá la 
     * solución actual (no modificar las etiquetas) y, por tanto, sus genes 
     * estarán todos a cero.
     */
    private void inicializar() {
        System.out.println("Inicializando población");
        int contador = 1;
        Individuo ind;
         
        /* El primer individuo de la población contendrá la solución actual (no 
        modificar las etiquetas) y, por tanto, sus genes estarán todos a cero */
        ind = new Individuo(rt);
        ind.evaluar();
        population.add(ind);
        
        while(contador < POPULATION_SIZE) {
            ind = new Individuo(rt);
            for(int i=0; i<ind.GENES; i++){
                // double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
                double random = MIN_TUNING + (MAX_TUNING - MIN_TUNING) * rnd.nextDouble();
                ind.getCromosoma()[i] = random;
            }
            ind.evaluar();
            
            if(!population.contains(ind)){
                population.add(ind);
                contador++;
            }
        } 
    }

    /**
     * Operador de cruce BLX-alpha.
     * @param current_population 
     */
    private void blx_cross(ArrayList<Individuo> current_population) {
        System.out.println("Cruzando población");
        ArrayList<Individuo> hijos = new ArrayList<>();
        
        for(int i=0; i<POPULATION_SIZE/2; i++){
            Individuo madre = current_population.get(2*i);
            Individuo padre = current_population.get(2*i+1);
            
            if((HammingDistance(padre, madre)/2) > umbral_cruce){
                Individuo hijo1 = new Individuo(rt);
                Individuo hijo2 = new Individuo(rt);

                for(int j=0; j<madre.GENES; j++){
                    double px = madre.getCromosoma()[j];
                    double py = padre.getCromosoma()[j];
                    
                    double cmin = Double.min(px, py);
                    double cmax = Double.max(px, py);
                    double I = cmax - cmin;
                                        
                    double min = cmin - I * ALPHA;
                    double max = cmax + I * ALPHA;
 
                    // double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
                    double genValue = min + (max - min) * rnd.nextDouble();
                    while(genValue < -0.5 || genValue > 0.5){
                        genValue = min + (max - min) * rnd.nextDouble();
                    }                    
                    hijo1.getCromosoma()[j] = genValue;
                    hijo1.evaluar();
                    genValue = min + (max - min) * rnd.nextDouble();
                    while(genValue < -0.5 || genValue > 0.5){
                        genValue = min + (max - min) * rnd.nextDouble();
                    }   
                    hijo2.getCromosoma()[j] = genValue;
                    hijo2.evaluar();  
                }
                
                hijos.add(hijo1);
                hijos.add(hijo2);
            }            
        }
        
        current_population.addAll(hijos);   // C'(t)        
    }
    
    /**
     * Calcula el número de genes en los que difieren dos individuos.
     * @param padre Individuo a comparar.
     * @param madre Individuo a comparar.
     * @return Número de genes en los que difieren dos individuos.
     */
    private int HammingDistance(Individuo padre, Individuo madre) {
        int distance = 0;
        
        for(int i=0; i<padre.GENES; i++){
            if(padre.getCromosoma()[i] != madre.getCromosoma()[i]){
                distance++;
            }
        }
        
        return distance;
    }

    /**
     * Mantiene al mejor cromosoma y los restantes se generan aleatoriamente. Este 
     * método trata evitar caer en óptimos locales.
     * @param theBest Mejor individuo de la población que permanecerá en la nueva
     */
    private void diverge(Individuo theBest) {
        System.out.println("Multiarranque");
        // creamos una nueva población vacía
        population.clear();
        population = new ArrayList<>(POPULATION_SIZE);
        // añadimos el mejor elemento de la antigua población
        population.add(theBest);
        
        int contador = 1;
        // generamos el resto de la población con individuos aleatorios
        while(contador < POPULATION_SIZE) {
            Individuo ind = new Individuo(rt);
            for(int i=0; i<ind.GENES; i++){
                // double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
                double random = MIN_TUNING + (MAX_TUNING - MIN_TUNING) * rnd.nextDouble();
                ind.getCromosoma()[i] = random;
            }
            ind.evaluar();
           
            if(!population.contains(ind)){
                population.add(ind);
                contador++;
            }
        } 
    }

    public ArrayList<Individuo> getPopulation() {
        return population;
    }
    
}
