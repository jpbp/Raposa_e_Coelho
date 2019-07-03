/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raposa_e_coelho;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Jp
 */
public abstract class Animal implements Actor  {
     // se o animal esta vivo ou morto
    private boolean alive;
    // posicao da animal
    private Location location;
    //o campo ocupado pelo animal
    private Field field;
    //idade do animal
    
    
    //duvida é melhor deixar esse metodo protegido ou fazer uma metdo para o acesso????????
    protected int age;
    
    private boolean female;
    
    private int foodLevel;

    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        age=0;
        int number = Randomizer.getRandomInt(10);
        if(number % 2 ==0){
            female = false;
        }
        else{
            female = true;
        }
        foodLevel = getFOOD_VALUE();
        
    }
    
    /**
     * Show the current status of the animal(dead or alive).
     * alive == true
     */
     public boolean isAlive()
    {
        return alive;
    }
     
    /**
     * Show the current situation of the animal.
     * 
     */
    @Override
     public boolean isActive(){
         return isAlive();
     }
    
    /**
     * Set the animal situation to inactive.
     * also clean the animal's spot in the field
     */ 
    @Override
    public void setInactive()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
    
    /**
     * Return the current location of the animal.
     * 
     */
    public Location getLocation()
    {
        return location;
    }
       
    
    /**
     * Return the animal's field.
     */
    public Field getField()
    {
        return field;
    }
    
     //visibilidade protegida pois o metodo setLocation era privado tanta na raposa quanto no coelho
    /**
     * Set a location for the animal for a given class of location.
     */
    @Override
    public void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * return true if the animal can breed.
     */
    protected boolean canBreed()
    {
        return (age >= getBreedingAge() && female);
        
    }
    
    /**
     * Increment the animal's age by one.
     */
    protected void incrementAge()
    {
        age++;
        if(age > getMAX_AGE()) {
            setInactive();
        }
    }
    
    /**
     * nao sei oq escrever aqui.
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && Randomizer.getRandomDouble() <= getBREEDING_PROBABILITY()) {
            births = Randomizer.getRandomInt(getMAX_LITTER_SIZE()) + 1;
        }
        return births;
    }  
  
   /**
     * nem aqui.
     */
   protected void giveBirth(ArrayList<Actor> NewActors){
        //novas raposas nascem em locais adjacentes
        //obtem uma lista das localizacoes livres
        Field field = getField();
        List<Location> free=field.getFreeAdjacentLocations(getLocation());
        int births=breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Animal young = getAnimal(field,loc);
            NewActors.add(young);
        }
    }
   
   /**
     * Check if there are food in the adjacent locations of the animal.
     */
   protected Location findFood(Location location){
       Field field = getField();
       List<Location> adjacent = field.adjacentLocations(location);
       Iterator<Location> it = adjacent.iterator();
       while(it.hasNext()){
           Location where = it.next();
           Actor food = field.getObjectAt(location);
           if(compatibleFood(food)){
               foodLevel = getFOOD_VALUE();
               return where;
           }
       }
       return null;
   }
  
   /**
     * Increment , by one , the animal's level of hunger.
     * if the food level reach 0 then the animal is set to inactive
     */
   protected void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
           setInactive();
        }
    }
   
   /**
     * Set the animal's level of food.
     */
   protected void setFoodLevel(int value){
       foodLevel = value;
   }
   
   
  abstract protected boolean compatibleFood(Actor prey);
  abstract protected int getFOOD_VALUE();
  abstract protected int getBreedingAge();
  abstract protected int getMAX_AGE();
  abstract protected double getBREEDING_PROBABILITY();
  abstract protected int getMAX_LITTER_SIZE();
  abstract protected Animal getAnimal(Field field,Location loc);
    
@Override
public abstract void act(ArrayList<Actor> NewActors);
    
}
    
    
    

