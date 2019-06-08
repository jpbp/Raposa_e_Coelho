package raposa_e_coelho;
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
public class Rabbit
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
    
    // idade do coelho
    private int age;
    // se o coleho esta vivou ou morto
    private boolean alive;
    //posicao do coelho
    private Location location;
    //o campo ocupado
    private Field field; 
    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     */
    //contrutor arrumado!!!!
    public Rabbit(boolean randomAge,Field field,Location location)
    {
        age = 0;
        alive = true;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
        this.field=field;
        this.location=location;
        
    }
    
    /** isso é o que o coelho faz a maior parte do tempo, ele corre livre. as vezes ele procria ou morre velho
     * param newRabbits uma lista a qual adiconar os colehos recem-nascidos
     * 
     *
     */
    public void run(List newRabbits)
    {
        incrementAge();
        if(alive) {
            giveBirth(newRabbits);
            //tenta mover-se para uma localizacao livre
            Location newLocation= field.freeAdjacentLocation(location);
            if(newLocation !=null){
                setLocation(newLocation);
            }
            else {
                //superlotacao
                setDead();
            }
        }
    }
    
    //isso indica que o coleho não esta mais vivo
    //ele é removido do campo
    
    
    public void setDead(){
        alive=false;
        if(location!=null){
            field.clear(location);
            location=null;
            field=null;
        }
    }
    /**
     *aumenta a idade do coelho
     *isso poderia resultar na morte de um coelho
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    //verifica se o coelho deve ou não procriar nesse passo
    //novos nascimentos serao criados en localizaocao adjacentes livres
    //newRabbits uma lista a qual adiconar os coelhos recem nascidos
    private void giveBirth(List<Rabbit> newRabbits){
        //novas raposas nascem em locais adjacentes
        //obtem uma lista das localizacoes livres
        List<Location> free=field.getFreeAdjacentLocations(location);
        int births=breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Rabbit young = new Rabbit(false,field,loc);
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
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
    
    /**
     * verifica se o coelho ta vivo ou morto
     * @return True if the rabbit is still alive.
     */
    public boolean isAlive()
    {
        return alive;
    }
    
   

    /**
     * Diga ao coelho que está morto agora :( kkkkkkkkkk
     */
    public void setEaten()
    {
        alive = false;
    }
    
    /**
        Defina a localização do animal.
      * @param row A coordenada vertical do local.
      * @param col A coordenada horizontal do local.
     */
    public void setLocation(int row, int col)
    {
        this.location = new Location(row, col);
    }

    /**
     * Set the rabbit's location.
     * @param location The rabbit's location.
     */
    public void setLocation(Location location)
    {
        this.location = location;
    }
}
