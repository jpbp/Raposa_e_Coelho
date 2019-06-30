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
public interface Actor {
    
    public void act(ArrayList<Actor> NewActors);
    public boolean isActive();
    public void setInactive();
    public void setLocation(Location newLocation);
   // public void move();
    //public Location checkSurroundings(Location currentLocation);
    
}
