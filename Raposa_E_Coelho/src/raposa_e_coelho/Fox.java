package raposa_e_coelho;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-11
 */
public class Fox extends Animal
{
    // Characteristics shared by all foxes (static fields).
    
    // A idade em que uma raposa pode começar a se reproduzir.
    private static final int BREEDING_AGE = 5;
    // A idade em que uma raposa pode viver.
    private static final int MAX_AGE = 300;
    // A probabilidade de uma criação de raposa.
    private static final double BREEDING_PROBABILITY = 0.09;
    // O número máximo de nascimentos.
    private static final int MAX_LITTER_SIZE = 3;
    // O valor alimentar de um único coelho. Com efeito, esta é a
    // number of steps a fox can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 7;
    
    
    // Individual characteristics (instance fields).

   
    // se a raposa esta vida ou morta
   
    // o nivel de alimenots da raposa, que aumenta comendo coelhos
    private int foodLevel;
    
    

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with random age.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     */
    public Fox(boolean randomAge,Field field,Location location)
    {
        super(field,location);
        if(randomAge) {
            age = Randomizer.getRandomInt(MAX_AGE);
            foodLevel = Randomizer.getRandomInt(RABBIT_FOOD_VALUE);
        }
        else {
            // leave age at 0
           
            foodLevel = RABBIT_FOOD_VALUE;
        }
    }
    
    /**
     * This is what the fox does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     */
    public void act(ArrayList<Actor> NewActors)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
                giveBirth(NewActors);
                Location location=getLocation();
                //mova-se para a fonte de alimento se encontrada.
                Location newLocation=findFood(location);
                if(newLocation == null){
                //nenhum alineto enconrado tenta ,mover para uma localizacao livre
                newLocation= getField().freeAdjacentLocation(location);
                }
                //verifica se foi possivel mover-se
                if(newLocation != null){
                    setLocation(newLocation);
                }
                else{
                    //superLotacao
                    setInactive();
                }
        }
    }
        
    
   
    
    
    
     //verifica se o coelho deve ou não procriar nesse passo
    //novos nascimentos serao criados en localizaocao adjacentes livres
    //newRabbits uma lista a qual adiconar os coelhos recem nascidos
   
    
    /**instrui a raposa procurar coelhos adjacentes ao seu local ataul
     * só o primeiro coelho é comido @param location onde no campo esta localizado
     * retun onde o alimento foi encontardo ou null caso contrario
     * 
     */
    private Location findFood(Location location){
    Field field=getField();
    List<Location> adjacent = field.adjacentLocations(location);
    Iterator<Location> it=adjacent.iterator();
    while(it.hasNext()){
        Location where=it.next();
        Object animal=field.getObjectAt(where);
        if(animal instanceof Rabbit){
            Rabbit rabbit=(Rabbit)animal;
            if(rabbit.isAlive()){
                rabbit.setInactive();
                
                foodLevel=RABBIT_FOOD_VALUE;
                return where;
            }
        }
    }
    return null;
    }
    
    /**
     * Increase the age. This could result in the fox's death.
     */
  
    
    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
           setInactive();
        }
    }
    
    /**
     * Tell the fox to look for rabbits adjacent to its current location.
     * @param field The field in which it must look.
     * @param location Where in the field it is located.
     * @return Where food was found, or null if it wasn't.
     */

        
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
   
    /**
     * A fox can breed if it has reached the breeding age.
     */
  
    
    @Override
    public int getBreedingAge(){
        return BREEDING_AGE;
    }
    @Override
    public int getMAX_AGE(){
        return MAX_AGE;
    }
    @Override
    public double getBREEDING_PROBABILITY(){
        return BREEDING_PROBABILITY;
    }
    
    @Override
    public int getMAX_LITTER_SIZE(){
        return MAX_LITTER_SIZE;
    }
    
    @Override
    public Animal getAnimal (Field field,Location loc){
        Fox young = new Fox(false,field,loc);
        return young;
    }
            
            
      
    
  
    /**
     * Set the animal's location.
     * @param row The vertical coordinate of the location.
     * @param col The horizontal coordinate of the location.
     */
   

    
}
