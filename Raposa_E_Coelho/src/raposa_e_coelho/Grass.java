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
    
    /**
     * This is what the grass does , it increment it's age or scatter new seeds.
     * 
     */
    public void act(ArrayList<Actor> NewActors){
        incrementAge();
        if(isActive()){
            scatterSeeds(NewActors);
        }
        else{
            setInactive();
        }
    }
    
    /**
     * Get the grasse's max age.
     * 
     */
    private int getMAX_AGE(){
        return MAX_AGE;
    }
    
    /**
     * Get the grasse's breeding age.
     * 
     */
    private int getBREEDING_AGE(){
        return BREEDING_AGE;
    }
    
    /**
     * Get the grasse's current field.
     * 
     */
    private Field getField(){
        return field;
    }
    
    /**
     * Increment the grasse's age.
     * 
     */
    protected void incrementAge(){
        age++;
        if(age > getMAX_AGE()){
            setInactive();
        }
    }
    
    /**
     * Get the grasse's growing probability.
     * 
     */
    private double getGROWING_PROBABILITY(){
        return GROWING_PROBABILITY;
    }
    
    /**
     * Get the grasse's max growth size.
     * 
     */
    private int getMAX_GROWTH_SIZE(){
        return MAX_GROWTH_SIZE;
    }
    
    /**
     * Set the grasse's location for a given Location class.
     * 
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
     * Get the grasse's current location.
     * 
     */
    public Location getLocation(){
        return location;
    }
    
    /**
     * Set the grasse's to inactive, then clean it location on the field.
     * 
     */
    @Override
    public void setInactive(){
        this.alive = false;
        if(location != null){
            field.clear(location);
            location = null;
            field = null;
        }
    }
    
    /**
     * Return true if the grass is alive.
     * 
     */
    @Override
    public boolean isActive(){
        return alive;
    }
    
    /**
     * It make's the grass grow.
     * 
     */
    private int grow(){
        int growths = 0;
        if(canGrow() && Randomizer.getRandomDouble() <= getGROWING_PROBABILITY()){
            growths = Randomizer.getRandomInt(getMAX_GROWTH_SIZE()) + 1;
        }
        return growths;
    }
    
    /**
     * Check if the grass can grow, true if it can.
     * 
     */
    private boolean canGrow(){
        return (age >= getBREEDING_AGE());
    }
    
    
    /**
     * Scatter new seeds in the adjacent free locations.
     * 
     */
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
