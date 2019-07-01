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
    private static final int GRASS_FOOD_VALUE = 12;
    
    // Individual characteristics (instance fields).
    

    // se o coleho esta vivou ou morto
 
   
    //contrutor arrumado!!!!
   public Rabbit(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            age = Randomizer.getRandomInt(MAX_AGE);
            setFoodLevel(Randomizer.getRandomInt(getFOOD_VALUE()));
        }
        else{
            setFoodLevel(getFOOD_VALUE());
        }
    }
    
    /** isso é o que o coelho faz a maior parte do tempo, ele corre livre. as vezes ele procria ou morre velho
     * param newRabbits uma lista a qual adiconar os colehos recem-nascidos
     * 
     *
     */
   @Override
    public void act(ArrayList<Actor> NewActors)
    {
        incrementHunger();
        incrementAge();
        if(isAlive()) {
            giveBirth(NewActors);
            Location location = getLocation();
            Location newLocation = findFood(location);
            if(newLocation == null){
            //tenta mover-se para uma localizacao livre
                newLocation= getField().freeAdjacentLocation(getLocation());
            }
            if(newLocation !=null){
                setLocation(newLocation);
            }
            else {
                //superlotacao
                setInactive();
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
  
    
    /**
     * gera um numero que representa o numero de nascimento se ele poder procriar
     * 
     * @return o numero de nascimento pode ser zero
     */
     
     // colocar esse metdo na classe animal??????
  

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
        Rabbit young = new Rabbit(false,field,loc);
        return young;
    }
    
    
    @Override
    protected boolean compatibleFood(Actor prey){
        if(prey instanceof Grass){
            Grass grass = (Grass)prey;
            if(grass.isActive()){
                grass.setInactive();
            }
            return true;
        }
        return false;
    }
    
    @Override
    protected int getFOOD_VALUE(){
        return GRASS_FOOD_VALUE;
    }
    
}
    
   
    
   

    
  

   
