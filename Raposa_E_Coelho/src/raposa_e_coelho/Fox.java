package raposa_e_coelho;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * Um modelo simples de Raposa;
 * Raposas envelhecem, se movem, procriam, comem coelhos e morrem;
 * Herdam atributos da classe Animal
 * @author João Paulo Pena, Luiz Felipe Calvo, Raphael Fernandes Roriz.
 */
public class Fox extends Animal
{
    // Characteristics shared by all foxes (static fields).
    
    // A idade em que uma raposa pode começar a se reproduzir.
    private static final int BREEDING_AGE = 5;
    // O limite de idade de uma Raposa
    private static final int MAX_AGE = 300;
    // A probabilidade de procriação de raposa.
    private static final double BREEDING_PROBABILITY = 0.09;
    // O número máximo de nascimentos.
    private static final int MAX_LITTER_SIZE = 3;
    // O valor alimentar de um único coelho; praticamente, é o número de Steps
    //Que uma raposa vive antes de se alimentar novamente
    private static final int RABBIT_FOOD_VALUE = 20;
    

   /** 
     * Cria uma instância de uma nova Raposa para a simulação
     * @param randomAge define se a Raposa gerada já estará na simulação ao começar, ou será fruto de procriação
     * @param field o Field(campo) no qual a Raposa será simulada
     * @param location a localização da Raposa gerada no Field(campo)
     *
     */
    public Fox(boolean randomAge,Field field,Location location)
    {
        super(field,location);
        if(randomAge) {
            age = Randomizer.getRandomInt(MAX_AGE);
            setFoodLevel(Randomizer.getRandomInt(getFOOD_VALUE()));
        }
        else {
            // leave age at 0
           
            setFoodLevel(getFOOD_VALUE());
        }
    }
    
    /** 
     * Agir controla o comportamento de cada Raposa. Em cada step, uma Raposa envelhece, fica com mais fome,
     * tenta procriar(caso seja fêmea), procura comida, se move, e/ou morre de fome ou velhice.
     * Encontrar comida causa a morte de um Ator do tipo Coelho.
     * Herda da classe Animal
     * Sobrescreve o método act da interface Ator(implementada na classe Animal)
     * @param NewActors O ArrayList contendo todos os atores presentes na simulação, para adicionar mais atores pela procriação
     * 
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
        
   /** 
     * Verifica se um ator detectado pode ser consumido para obter comida.
     * No caso de Raposas, o ator encontrado deve ser do tipo Coelho.
     * Sobrescreve o método da classe Animal
     * @param prey o ator encontrado
     * @return informa se o ator pode ser comido(true) ou não(false)
     */
    @Override
    protected boolean compatibleFood(Actor prey){
        if(prey instanceof Rabbit){
            Rabbit rabbit = (Rabbit)prey;
            if(rabbit.isAlive()){
                rabbit.setInactive();
 
            }
            return true;
        }
        return false;
    }
    
  /** 
    * Obtem o atributo FOOD_VALUE (valor de comida)
    * Sobrescreve o método da classe Animal
    * @return o valor de comida de um ator do tipo Coelho
    */
    @Override
    protected int getFOOD_VALUE(){
        return RABBIT_FOOD_VALUE;
    }
    
    
    /** 
     * Obtem o atributo BREEDING_AGE (Idade mínima para procriacao)
     * Sobrescreve o método da classe Animal
     * @return o atributo BREEDING_AGE
     */
    @Override
    public int getBreedingAge(){
        return BREEDING_AGE;
    }
    
    /** 
     * Obtem o atributo MAX_AGE (Idade máxima)
     * Sobrescreve o método da classe Animal
     * @return o atributo MAX_AGE
     */
    @Override
    public int getMAX_AGE(){
        return MAX_AGE;
    }
    
     /** 
     * Obtem o atributo BREEDING_PROBABILITY (Probabilidade de procriação)
     * Sobrescreve o método da classe Animal
     * @return o atributo BREEDING_PROBABILITY
     */
    @Override
    public double getBREEDING_PROBABILITY(){
        return BREEDING_PROBABILITY;
    }
    
    /** 
     * Obtem o atributo MAX_LITTER_SIZE (Número máximo de filhotes por procriação)
     * Sobrescreve o método da classe Animal
     * @return o atributo MAX_LITTER_SIZE
     */
    @Override
    public int getMAX_LITTER_SIZE(){
        return MAX_LITTER_SIZE;
    }
    
    /** 
     * Gera uma Raposa na posição especificada. Utilizado durante a procriação de Raposas
     * Sobrescreve o método da classe Animal
     * @param field o Field(campo) onde a simulação ocorre
     * @param loc a localização da nova Raposa
     * @return A nova Raposa, inserida na localização e no campo especificados.
     */
    @Override
    public Animal getAnimal (Field field,Location loc){
        Fox young = new Fox(false,field,loc);
        return young;
    }
            
}
