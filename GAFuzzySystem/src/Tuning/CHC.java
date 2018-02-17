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
    
    private final int STOP = 100;
    private final int POPULATION_SIZE = 50;
    private final double MAX_TUNING = 0.5;
    private final double MIN_TUNING = -0.5;
    private final double ALPHA = 0.5;
    
    private ArrayList<Individuo> population;
    
    private Random rnd;
    
    private ReadTraining rt;
    
    public CHC(ReadTraining rt){
        population = new ArrayList<>(POPULATION_SIZE);
        rnd = new Random(123456789);
        this.rt = rt;
    }
    
    public void chc(){
        int iterations = 0;
        int L = Params.NUM_ETQ; // L = longitud del cromosoma
        int umbral_cruce = L/4;
        
        // inicializa la población P(t) y evaluar los individuos de la misma
        inicializar();
        
        // mientras no se cumpla la condición de parada
        while(iterations < STOP){
            // t++
            // copiar los miembros de P(t-1) en C(t) en un orden random
            ArrayList<Individuo> current_population = new ArrayList<>(POPULATION_SIZE);
            for (int i = 0; i < POPULATION_SIZE; i++) {
                int random = rnd.nextInt(POPULATION_SIZE);
                Individuo aux = population.get(random);
                
                while(current_population.contains(aux)){
                    random = rnd.nextInt(POPULATION_SIZE);
                    aux = population.get(random);
                }
                
                current_population.add(aux);    // C(t)
            }   
            
            // recombinar estructuras en C(t) formando C'(t) y evaluar a los nuevos individuos
            blx_cross(current_population);
            
            /* seleccionar los individuos que formarán la nueva población P(t) 
            a partir de C'(t) y P(t-1)*/
            Collections.sort(current_population, Collections.reverseOrder());   // ordena la población en orden ascendente en función de ev, es decir, el individuio con ev=1 estará antes que el que tenga ev=2
            ArrayList<Individuo> elite = (ArrayList<Individuo>) current_population.subList(0, POPULATION_SIZE); // cogemos a la élite de la población
            
            // multiarranque
            if(elite.equals(population)){    // si(P(t) equals P(t-1))
                umbral_cruce--;  
            }            
            population.clear();
            population = new ArrayList<>(elite);    // P(t)
            if(umbral_cruce < 0){
                diverge(elite.get(0));
                umbral_cruce = L/4;
            }            
        }
    }

    /**
     * Crea una población de individuos aleatorios. Un individuo contendrá la 
     * solución actual (no modificar las etiquetas) y, por tanto, sus genes 
     * estarán todos a cero.
     */
    private void inicializar() {
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
                ind.getCromosoma().add(random);
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
        
        ArrayList<Individuo> hijos = new ArrayList<>();
        
        for(int i=0; i<POPULATION_SIZE/2; i++){
            Individuo madre = current_population.get(i);
            Individuo padre = current_population.get(i+1);
            
            Individuo hijo1 = new Individuo(rt);
            Individuo hijo2 = new Individuo(rt);
            
            for(int j=0; j<madre.GENES; j++){
                double px = madre.getCromosoma().get(j);
                double py = padre.getCromosoma().get(j);
                
                double I = Math.abs(px-py);
                double cmin = Double.min(px, py);
                double cmax = Double.max(px, py);
                
                // double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
                double min = cmin - I * ALPHA;
                double max = cmax + I * ALPHA;
                double genValue = min + (max - min) * rnd.nextDouble();
                hijo1.getCromosoma().add(j, genValue);
                hijo1.evaluar();
                genValue = min + (max - min) * rnd.nextDouble();
                hijo2.getCromosoma().add(j,genValue);
                hijo2.evaluar();
                
                hijos.add(hijo1);
                hijos.add(hijo2);
            }
        }
        
        current_population.addAll(hijos);   // C'(t)
    }

    /**
     * Mantiene al mejor cromosoma y los restantes se generan aleatoriamente. Este 
     * método trata evitar caer en óptimos locales.
     * @param theBest Mejor individuo de la población que permanecerá en la nueva
     */
    private void diverge(Individuo theBest) {
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
                ind.getCromosoma().add(random);
            }
            ind.evaluar();
           
            if(!population.contains(ind)){
                population.add(ind);
                contador++;
            }
        } 
    }
    
}
