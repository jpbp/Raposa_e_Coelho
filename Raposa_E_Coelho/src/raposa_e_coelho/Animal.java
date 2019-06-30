/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raposa_e_coelho;

import java.util.ArrayList;
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
    
    

    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        age=0;
    }
    
     public boolean isAlive()
    {
        return alive;
    }
  
    @Override
     public boolean isActive(){
         return isAlive();
     }
     
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
      
    public Location getLocation()
    {
        return location;
    }
       
    public Field getField()
    {
        return field;
    }
     //visibilidade protegida pois o metodo setLocation era privado tanta na raposa quanto no coelho  
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    protected boolean canBreed()
    {
        return age >= getBreedingAge();
        
    }
    
    protected void incrementAge()
    {
        age++;
        if(age > getMAX_AGE()) {
            setDead();
        }
    }
    
     protected int breed()
    {
        int births = 0;
        if(canBreed() && Randomizer.getRandomDouble() <= getBREEDING_PROBABILITY()) {
            births = Randomizer.getRandomInt(getMAX_LITTER_SIZE()) + 1;
        }
        return births;
    }  
  
     
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
  abstract protected int getBreedingAge();
  abstract protected int getMAX_AGE();
  abstract protected double getBREEDING_PROBABILITY();
  abstract protected int getMAX_LITTER_SIZE();
  abstract protected Animal getAnimal(Field field,Location loc);
    
@Override
public abstract void act(ArrayList<Actor> NewActors);
    
}
    
    
    

