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
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;   
    
    

    public PopulationGenerator() {
      
    }
    
    
     public void populate(Field field,ArrayList<Actor> actors)
    {
       
        
       
        //field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(Randomizer.getRandomDouble() <= FOX_CREATION_PROBABILITY) {
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
                // else leave the location empty.
            }
        }
        Collections.shuffle(actors);
      
    }
}
