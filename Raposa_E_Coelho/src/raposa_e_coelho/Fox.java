package raposa_e_coelho;
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
public class Fox
{
    // Characteristics shared by all foxes (static fields).
    
    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a fox can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.09;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 7;
    // A shared random number generator to control breeding.
    private static final Random rand = new Random();
    
    // Individual characteristics (instance fields).

    // idade da raposa
    private int age;
    // se a raposa esta vida ou morta
    private boolean alive;
    // posicao da raposa
    private Location location;
    //o campo ocupado
    private Field field;
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
        age = 0;
        alive = true;
        this.field=field;
        this.location=location;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
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
    public void hunt(List newFoxes)
    {
        incrementAge();
        incrementHunger();
        if(alive) {
                giveBirth(newFoxes);
                //mova-se para a fonte de alimento se encontrada.
                Location newLocation=findFood(location);
                if(newLocation == null){
                //nenhum alineto enconrado tenta ,mover para uma localizacao livre
                newLocation= field.freeAdjacentLocation(location);
                }
                //verifica se foi possivel mover-se
                if(newLocation != null){
                    setLocation(newLocation);
                }
                else{
                    //superLotacao
                    setDead();
                }
        }
    }
        
    
     //isso indica que a raposa não esta mais vivo
    //ele é removido do campo
    
    
    public void setDead(){
        alive=false;
        if(location!=null){
            field.clear();
            location=null;
            field=null;
        }
    }
    
    
    
    
     //verifica se o coelho deve ou não procriar nesse passo
    //novos nascimentos serao criados en localizaocao adjacentes livres
    //newRabbits uma lista a qual adiconar os coelhos recem nascidos
    private void giveBirth(List<Fox> newFoxes){
        //novas raposas nascem em locais adjacentes
        //obtem uma lista das localizacoes livres
        List<Location> free=field.getFreeAdjacentLocations(location);
        int births=breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Fox young = new Fox(false,field,loc);
            newFoxes.add(young);
        }
    }
    
    /**instrui a raposa procurar coelhos adjacentes ao seu local ataul
     * só o primeiro coelho é comido @param location onde no campo esta localizado
     * retun onde o alimento foi encontardo ou null caso contrario
     * 
     */
    private Location findFood(Location location){
    
    List<Location> adjacent = field.adjacentLocations(location);
    Iterator<Location> it=adjacent.iterator();
    while(it.hasNext()){
        Location where=it.next();
        Object animal=field.getObjectAt(where);
        if(animal instanceof Rabbit){
            Rabbit rabbit=(Rabbit)animal;
            if(rabbit.isAlive()){
                rabbit.setDead();
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
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            alive = false;
        }
    }
    
    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            alive = false;
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
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A fox can breed if it has reached the breeding age.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
    
    /**
     * Check whether the fox is alive or not.
     * @return True if the fox is still alive.
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * Set the animal's location.
     * @param row The vertical coordinate of the location.
     * @param col The horizontal coordinate of the location.
     */
    public void setLocation(int row, int col)
    {
        this.location = new Location(row, col);
    }

    /**
     * Set the fox's location.
     * @param location The fox's location.
     */
    public void setLocation(Location location)
    {
        this.location = location;
    }
}
