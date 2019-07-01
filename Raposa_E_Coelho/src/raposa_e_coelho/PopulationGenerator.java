/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raposa_e_coelho;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Jp
 */
public class PopulationGenerator {
    

     // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.02; //0.02
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;
    // The probability that a hunter will be created in any given grid position.
    private static final double HUNTER_CREATION_PROBABILITY = 0.005;
    // The probability that a trap will be created in any given grid position.
    private static final double TRAP_CREATION_PROBABILITY = 0.0005;
    // The probability that grass will be created in any given grid position.
    private static final double GRASS_CREATION_PROBABILITY = 0.09;
    
    
    

    public PopulationGenerator() {
      
    }
    
    public void adicionarTrap(Field field, ArrayList<Actor> actors){
        
    }
    
    
     public void populate(Field field,ArrayList<Actor> actors)
    {
       
        
       
        //field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(Randomizer.getRandomDouble() <= TRAP_CREATION_PROBABILITY){
                    Location location = new Location(row,col);
                    Trap trap = new Trap(field,location);
                    actors.add(trap);
                    
                    field.place(trap,row,col);
                }
                
                else if(Randomizer.getRandomDouble() <= HUNTER_CREATION_PROBABILITY){
                    Location location = new Location(row,col);
                    Cacador cacador = new Cacador(field,location);
                    actors.add(cacador);
                    
                    field.place(cacador,row,col);
                }
                
                else if(Randomizer.getRandomDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location=new Location(row,col);
                    Fox fox = new Fox(true,field,location);
                    actors.add(fox);
                    field.place(fox, row, col);
                }
                

                else if(Randomizer.getRandomDouble() <= RABBIT_CREATION_PROBABILITY) {
                     Location location=new Location(row,col);
                    Rabbit rabbit = new Rabbit(true,field,location);
                    actors.add(rabbit);
                    
                    field.place(rabbit, row, col);
                    
                }
                
                else if(Randomizer.getRandomDouble() <= GRASS_CREATION_PROBABILITY) {
                    Location location=new Location(row,col);
                    Grass grass = new Grass(true,field,location);
                    actors.add(grass);
                    
                    field.place(grass, row, col);
                    
                }
                
                
                // else leave the location empty.
            }
        }
        Collections.shuffle(actors);
      
    }
}
