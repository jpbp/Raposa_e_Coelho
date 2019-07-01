/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raposa_e_coelho;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author luizc
 */
public class Grass implements Actor {
    
    private Location location;
    
    private Field field;
    
    private int age;
    
    private boolean alive;
    
    private static final int BREEDING_AGE = 1;
    
    private static final int MAX_AGE = 10;
    
    private static final double GROWING_PROBABILITY = 0.075;
    
    private static final int MAX_GROWTH_SIZE = 2;
    
    public Grass(boolean randomAge, Field field, Location location){
        this.field = field;
        setLocation(location);
        this.alive = true;
        if(randomAge){
            age = Randomizer.getRandomInt(MAX_AGE);
        }
        else{
            age = 0;
        }
    }
    
    public void act(ArrayList<Actor> NewActors){
        incrementAge();
        if(isActive()){
            scatterSeeds(NewActors);
        }
        else{
            setInactive();
        }
    }
    
    
    private int getMAX_AGE(){
        return MAX_AGE;
    }
    
    private int getBREEDING_AGE(){
        return BREEDING_AGE;
    }
    
    private Field getField(){
        return field;
    }
    
    protected void incrementAge(){
        age++;
        if(age > getMAX_AGE()){
            setInactive();
        }
    }
    
    private double getGROWING_PROBABILITY(){
        return GROWING_PROBABILITY;
    }
    
    private int getMAX_GROWTH_SIZE(){
        return MAX_GROWTH_SIZE;
    }
    
    @Override
    public void setLocation(Location newLocation){
        if(location != null){
            field.clear(location);
        }
        location = newLocation;
        field.place(this,newLocation);
    }
    
    public Location getLocation(){
        return location;
    }
    
    @Override
    public void setInactive(){
        this.alive = false;
        if(location != null){
            field.clear(location);
            location = null;
            field = null;
        }
    }
    
    @Override
    public boolean isActive(){
        return alive;
    }
    
    private int grow(){
        int growths = 0;
        if(canGrow() && Randomizer.getRandomDouble() <= getGROWING_PROBABILITY()){
            growths = Randomizer.getRandomInt(getMAX_GROWTH_SIZE()) + 1;
        }
        return growths;
    }
    
    private boolean canGrow(){
        return (age >= getBREEDING_AGE());
    }
    
    private void scatterSeeds(ArrayList<Actor> NewActors){
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int totalGrowth = grow();
        for(int g = 0; g < totalGrowth && free.size() > 0; g++){
            Location loc = free.remove(0);
            Grass sprout = new Grass(false,field,loc);
            NewActors.add(sprout);
        }
        
    }
    
    
}//fim classe
