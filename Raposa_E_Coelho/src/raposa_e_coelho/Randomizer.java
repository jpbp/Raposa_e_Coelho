/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raposa_e_coelho;

import java.util.Random;

/**
 *
 * @author raphael
 */
public class Randomizer {
    
    private static final Random rand = new Random();
    
    /**
     *
     * @param max
     * @return
     */
    public static int getRandomInt(int max){
        return rand.nextInt(max);
    }
    
    /**
     *@return
     */
    public static double getRandomDouble(){
        return rand.nextDouble() ;
    }
    
}
