/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raposa_e_coelho;

import java.util.ArrayList;

/**
 *
 * @author Jp
 */
public abstract class Actor {
    
    abstract public void act (ArrayList<Actor> NewActors);
    abstract public boolean isActive();
    
}
