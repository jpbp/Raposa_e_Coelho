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
    
    public Field getField(){
        return field;
    }
        
    //Métodos
    @Override
    public void setLocation(Location newLocation){
        if(location != null){
            field.clear(location);
        }
        location = newLocation;
        field.place(this,newLocation);
    }
    
    @Override
    public boolean isActive(){
        return active;
    }
    
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
    
    public void engage(Location location){
        Actor prey = field.getObjectAt(location);
        if(prey != null && prey instanceof Animal){
            if(prey.isActive()){
                Animal animal = (Animal)prey;
                animal.setInactive();
            }
        }
    }
    @Override
    public void setInactive(){
        active = false;
        field.clear(location);
        location = null;
        field = null;
    }
    
    @Override
    public void act(ArrayList<Actor> NewActors){
        Location surrounding = checkSurroundings(location);
        if(surrounding != null){
            engage(surrounding);
            this.setInactive();
        }
    }
}
