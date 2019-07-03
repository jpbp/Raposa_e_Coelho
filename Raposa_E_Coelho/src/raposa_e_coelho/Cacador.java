/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raposa_e_coelho;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
/**
 *
 * @author luizc
 */
public class Cacador implements Actor{
    //Atributos
    
    private Location location;
    
    private Field field;
    
    private static final int SET_TRAP_CHANCE = 5;
    
    private boolean active;
    
    
    //Construtor
    public Cacador(Field field, Location location){
        this.field = field;
        setLocation(location);
        active = true;
    }
    
    /**
     * Return the hunter's field.
     */
    public Field getField(){
        return field;
    }
    
    /**
     * Return the hunter's current location.
     */
    public Location getLocation(){
        return location;
    }
        
    /**
     * Set a location for the hunter for a given class of location.
     */
    @Override
    public void setLocation(Location newLocation){
        if(location != null){
            field.clear(location);
        }//fim if
        location = newLocation;
        field.place(this,newLocation);
    }
    
    /**
     * Set the hunter situation to inactive.
     * also clean the animal's spot in the field
     */ 
    @Override
    public void setInactive(){
        active = false;
        field.clear(location);
        field= null;
        location = null;
    }
    
    /**
     * Show the current situation of the hunter.
     * 
     */
    @Override
    public boolean isActive(){
        return active;
    }
    
    /**
     * Move the hunter , first it checks the free adjacent locations of the hunter.
     * 
     */
    //@Override
    public void move(){
        Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation !=null){
                setLocation(newLocation);
            }
    }
    
    /**
     * Check the surrouindings of the hunter for his current location
     * 
     */
   // @Override
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
     * Make the hunter plant a trap in a free adjacent location.
     * 
     */
    public Trap plantTrap(){
        Location trapLocation = getField().freeAdjacentLocation(getLocation());
        if(trapLocation != null){
            int setTrap = Randomizer.getRandomInt(100);
            if(setTrap < SET_TRAP_CHANCE){
                Trap newTrap = new Trap(getField(),trapLocation);
                return newTrap;
            }
        }
        return null;
    }
    
    /**
     * Make the hunter kill an animal in his surroundings..
     * 
     */
    public void hunt(Location location){
        Actor prey = field.getObjectAt(location);
        if(prey != null && prey instanceof Animal){
            if(prey.isActive()){
                Animal animal = (Animal)prey;
                animal.setInactive();
            }
        }
    }
    
    /**
     * Hunter acting , first he checks the surroundings , if there are an animal he hunts
     * else he set a new trap.
     * 
     */
    @Override
    public void act(ArrayList<Actor> NewActors){
        move();
        Location surrounding = checkSurroundings(location);
        if(surrounding != null){
            hunt(surrounding);
        }
        else{
            Trap newTrap = plantTrap();
            if(newTrap != null){
                NewActors.add(newTrap);
            }
        }
    }
           
}
