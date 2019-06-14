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
public abstract class Animal  {
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
    
  abstract protected int getBreedingAge();
  abstract protected int getMAX_AGE();
    
    
   abstract void act(ArrayList <Animal> newAnimals);
    
}
    
    
    

