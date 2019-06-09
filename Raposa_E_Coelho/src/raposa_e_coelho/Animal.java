/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raposa_e_coelho;

/**
 *
 * @author Jp
 */
public class Animal {
     // se o animal esta vivo ou morto
    private boolean alive;
    // posicao da animal
    private Location location;
    //o campo ocupado pelo animal
    private Field field;

    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
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
         
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
}
    
    
    

