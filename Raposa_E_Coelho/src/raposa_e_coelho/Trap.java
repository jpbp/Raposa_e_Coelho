/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raposa_e_coelho;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author luizc
 */
public class Trap implements Actor{
    //Atributos
    private Location location;
    
    private Field field;
    
    private boolean active;
    
    //Construtor
    public Trap(Field field, Location location){
        this.field = field;
        setLocation(location);
        active = true;
    }
    
    /**
     * Return the trap's current field.
     */
    public Field getField(){
        return field;
    }
        
    /**
     * Set the trap's locaton for a given Location class.
     */
    @Override
    public void setLocation(Location newLocation){
        if(location != null){
            field.clear(location);
        }
        location = newLocation;
        field.place(this,newLocation);
    }
    
    /**
     * Return the trap's current situation.
     */
    @Override
    public boolean isActive(){
        return active;
    }
    
    /**
     * Return the animals int he tra's surrounding.
     */
    public Location checkSurroundings(Location currentLocation){
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(location);
        Iterator<Location> iter = adjacent.iterator();
        while(iter.hasNext()){
            Location place = iter.next();
            Actor anAnimal = field.getObjectAt(place);
            if (anAnimal != null && anAnimal instanceof Animal){
                return place;
            }//fim if
        }//fim while
        return null;
    }
    
    /**
     * Kill an animal in one adjacent location.
     */
    public void engage(Location location){
        Actor prey = field.getObjectAt(location);
        if(prey != null && prey instanceof Animal){
            if(prey.isActive()){
                Animal animal = (Animal)prey;
                animal.setInactive();
            }
        }
    }
    
    /**
     * Set the trap's situation to inactive.
     */
    @Override
    public void setInactive(){
        active = false;
        field.clear(location);
        location = null;
        field = null;
    }
    
    /**
     * This is what the trap does , it check it surroundings and engage if an animal was found.
     */
    @Override
    public void act(ArrayList<Actor> NewActors){
        Location surrounding = checkSurroundings(location);
        if(surrounding != null){
            engage(surrounding);
            this.setInactive();
        }
    }
}
