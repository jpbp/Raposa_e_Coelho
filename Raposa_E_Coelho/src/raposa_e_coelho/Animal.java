package raposa_e_coelho;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Generalização dos tipos de animais encontrados durante a simulação
 * Animais podem ser do tipo Raposa ou Coelho;
 * Seus Métodos e Atributos são herdados por Raposas e coelhos
 * Implementa a interface Actor
 * @author Jp
 */
public abstract class Animal implements Actor  {
    // Se o animal esta vivo ou morto
    private boolean alive;
    // posicao da animal no grid de Field
    private Location location;
    //o Field(campo) onde o animal está sendo simulado
    private Field field;
    //A idade do animal
    protected int age;
    //Informa se o animal é Fêmea
    private boolean female;
    //Nível de comida dos animais; ianimais devem buscar comida, senão morrem de fome
    private int foodLevel;
     
     /**
       * Cria uma instância de um animal na siumulação.
       * Como animal é uma classe abstrata, não existirão instâncias de "Animal"
       * apenas de suas subclasses Raposa e Coelho
       * @param field O Field onde o animal será simulado
       * @param location A localização do novo animal no grid de Field
     */
    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        age=0;
         //Define aleatóriamente se o animal será macho ou fêmea
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
     * Informa se um animal ainda está vivo
     * @return O estado do animal, vivo(true) ou morto(false)
     * alive == true
     */
     public boolean isAlive()
    {
        return alive;
    }
     
    /**
     * Verifica se o animal está ativo(vivo)
     * Sobrecarga do método isActive de Actor
     * @return o estado do animal, vivo ou morto.
     */
    @Override
     public boolean isActive(){
         return isAlive();
     }
    
    /**
     * Define o estado do animal como inativo(morto)
     * liberando sua localização no grid de Field no processo.
     */ 
    @Override
    public void setInactive()
    {
        alive = false;
        if(location != null) {
             //Libera a posição em Field
            field.clear(location);
            location = null;
            field = null;
        }
    }
    
    /**
     * Obtém a localização de Animal no grid de field
     * @return A localização de Animal
     */
    public Location getLocation()
    {
        return location;
    }
       
    
    /**
     * Obtém o field onde o animal está sendo simulado
     * @return o field do animal
     */
    public Field getField()
    {
        return field;
    }
    
    /**
      * Define uma nova localização do Animal no grido do Field
      * liberando a localização anterior no processo
      * Sobrescreve o método setLocation de Actor
      * @param newLocation A localização no grid de Field para onde o Animal irá
      */
    @Override
    public void setLocation(Location newLocation)
    {
         //Libera a posição anterior
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Verifica se o Animal está em condições de procriar
     * em função de sua idade e sexo.
     * @return A possibilidade de procriação do animal
     */
    protected boolean canBreed()
    {
        return (age >= getBreedingAge() && female);
        
    }
    
    /**
     * Envelhece o animal em um Step
     */
    protected void incrementAge()
    {
        age++;
        if(age > getMAX_AGE()) {
            setInactive();
        }
    }
    
    /**
     * O animal tenta se reproduzir; Caso consiga,
     * incrementa o total de reproduções realizadas.
     * @return o numero total de reproduções de um Animal.
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
     * Procria, gerando novas instâncias de Animal em uma posições adjacentes
     * @param NewActors ArrayList contendo todos os atores presentes na simulação, utilizado para adicionar novos atores à simulação.
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
     * Verifica as posições adjacentes do Animal buscando por 
     * atores que sejam de tipos compatíveis com o que o animal come.
     * Raposas buscam coelhos, coelhos buscam Grama.
     * @param location a locatização atual do animal
     * @return A posição do primeiro animal encontrado, Null se não encontrar nenhum
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
     * Decrementa o valor de alimento de um animal
     * Valor de alimento muito baixo pode matar o animal
     * Animais devem comer para repor seu nível de alimento
     */
   protected void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
           setInactive();
        }
    }
   
   /**
     * Atualiza o nível de alimento de um animal.
     * @param O valor alimentício à ser utilizado
     */
   protected void setFoodLevel(int value){
       foodLevel = value;
   }
   
   /** 
     * Agir controla o comportamento específico de cada Animal em um Step. 
     * Método Abstrato, então é Utilizado nas subclasses
     * @param NewActors O ArrayList contendo todos os atores presentes na simulação, que pode ser alterado durante o act
     */
  abstract protected boolean compatibleFood(Actor prey);
     
   /** 
     * Obtem o atributo FOOD_VALUE (valor de comida)
     * Método Abstrato, então é Utilizado nas subclasses
     * @return o valor de comida de um ator do tipo Grama
     */
  abstract protected int getFOOD_VALUE();
     
     /** 
     * Obtem o atributo BREEDING_AGE (Idade mínima para procriacao)
     * Método Abstrato, então é Utilizado nas subclasses
     * @return o atributo BREEDING_AGE
     */
  abstract protected int getBreedingAge();
     
     /** 
     * Obtem o atributo MAX_AGE (Idade máxima)
     * Método Abstrato, então é Utilizado nas subclasses
     * @return o atributo MAX_AGE
     */
  abstract protected int getMAX_AGE();
     
     /** 
     * Obtem o atributo BREEDING_PROBABILITY (Probabilidade de procriação)
     * Método Abstrato, então é Utilizado nas subclasses
     * @return o atributo BREEDING_PROBABILITY
     */
  abstract protected double getBREEDING_PROBABILITY();
     
      /** 
     * Obtem o atributo MAX_LITTER_SIZE (Número máximo de filhotes por procriação)
     * Método Abstrato, então é Utilizado nas subclasses
     * @return o atributo MAX_LITTER_SIZE
     */
  abstract protected int getMAX_LITTER_SIZE();
     
      /** 
     * Gera um Animal na posição especificada. Utilizado durante a procriação de Animais
     * Método Abstrato, então é Utilizado nas subclasses
     * @param field o Field(campo) onde a simulação ocorre
     * @param loc a localização do novo Animal
     * @return o novo coelho, inserido na localização e no campo especificados.
     */
  abstract protected Animal getAnimal(Field field,Location loc);
  
    /** 
     * Agir controla o comportamento específico de cada Animal em um Step. 
     * Método Abstrato, então é Utilizado nas subclasses
     * @param NewActors O ArrayList contendo todos os atores presentes na simulação, que pode ser alterado durante o act
     */
  @Override
  public abstract void act(ArrayList<Actor> NewActors);
    
}
    
    
    

