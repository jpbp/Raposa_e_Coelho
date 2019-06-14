package raposa_e_coelho;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.Random;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-11
 */
public class Rabbit extends Animal
{
    // Characteristics shared by all rabbits (static fields).

    // idade em que o coelho pode procriar
    private static final int BREEDING_AGE = 5;
    // o limite de idade de um coelho
    private static final int MAX_AGE = 50;
    //a probabilidade de procriação
    private static final double BREEDING_PROBABILITY = 0.15;
    // o numero maximo de nascimento
    private static final int MAX_LITTER_SIZE = 4;
    //um gerador aleatorio compatilhado para controlar a procriação
    private static final Random rand = new Random();
    
    // Individual characteristics (instance fields).
    

    // se o coleho esta vivou ou morto
 
   
    //contrutor arrumado!!!!
   public Rabbit(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }
    
    /** isso é o que o coelho faz a maior parte do tempo, ele corre livre. as vezes ele procria ou morre velho
     * param newRabbits uma lista a qual adiconar os colehos recem-nascidos
     * 
     *
     */
   @Override
    public void act(ArrayList <Animal> newAnimals)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newAnimals);
            //tenta mover-se para uma localizacao livre
            Location newLocation= getField().freeAdjacentLocation(getLocation());
            if(newLocation !=null){
                setLocation(newLocation);
            }
            else {
                //superlotacao
                setDead();
            }
        }
    }
    
    
    /**
     *aumenta a idade do coelho
     *isso poderia resultar na morte de um coelho
     */
    
    //verifica se o coelho deve ou não procriar nesse passo
    //novos nascimentos serao criados en localizaocao adjacentes livres
    //newRabbits uma lista a qual adiconar os coelhos recem nascidos
     private void giveBirth(ArrayList<Animal> newRabbits)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Rabbit young = new Rabbit(false, field, loc);
            newRabbits.add(young);
        }
    }
    
    /**
     * gera um numero que representa o numero de nascimento se ele poder procriar
     * 
     * @return o numero de nascimento pode ser zero
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
     * Um coelho pode se reproduzir se atingir a idade de reprodução.
     */
    
    
    @Override
    public int getBreedingAge(){
        return BREEDING_AGE;
    }
    @Override
    public int getMAX_AGE(){
        return MAX_AGE;
    }
    
}
    
   
    
   

    
  

   
