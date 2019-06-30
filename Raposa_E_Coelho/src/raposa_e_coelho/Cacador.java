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
    
    //Métodos
    public Field getField(){
        return field;
    }
    
    private Location adjacentLocation(){
        return getField().freeAdjacentLocation(location);
    }
    
    @Override
    public void setLocation(Location newLocation){
        if(location != null){
            field.clear(location);
        }//fim if
        location = newLocation;
        field.place(this,newLocation);
    }
    
    @Override
    public void setInactive(){
        active = false;
        field.clear(location);
        field= null;
        location = null;
    }
    
    @Override
    public boolean isActive(){
        return active;
    }
    
    //@Override
    public void move(){
        Location newLocation = adjacentLocation();
        if(newLocation != null){
            setLocation(newLocation);
        }
    }
    
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
    
    public void plantTrap(){
        Location trapLocation = adjacentLocation();
        if(trapLocation != null){
            int setTrap = Randomizer.getRandomInt(100);
            if(setTrap < SET_TRAP_CHANCE){
                Trap newTrap = new Trap(getField(),trapLocation);
            }
        }
    }
    
    public void hunt(Location location){
        Actor prey = field.getObjectAt(location);
        if(prey != null && prey instanceof Animal){
            if(prey.isActive()){
                Animal animal = (Animal)prey;
                animal.setInactive();
            }
        }
    }
    
    @Override
    public void act(ArrayList<Actor> NewActors){
        move();
        Location surrounding = checkSurroundings(location);
        if(surrounding != null){
            hunt(surrounding);
        }
        else{
            plantTrap();
        }
    }
        
        
    
    
}
