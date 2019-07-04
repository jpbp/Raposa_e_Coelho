package raposa_e_coelho;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.Random;

/**
 * Um modelo simples de coelho;
 * Coelhos envelhecem, se movem, procriam, comem grama e morrem;
 * Herdam atributos da classe Animal
 * @author João Paulo Pena, Luiz Felipe Calvo, Raphael Fernandes Roriz.
 */
public class Rabbit extends Animal{
    // Características comuns à todos os coelhos

    // idade em que o coelho pode procriar
    private static final int BREEDING_AGE = 5;
    // o limite de idade de um coelho
    private static final int MAX_AGE = 50;
    // a probabilidade de procriação
    private static final double BREEDING_PROBABILITY = 0.15;
    // o numero maximo de nascimento
    private static final int MAX_LITTER_SIZE = 4;
    // valor de comida obtido ao comer grama
    private static final int GRASS_FOOD_VALUE = 12;
   
    /** 
     * Cria uma instância de um novo coelho para a simulação
     * @param randomAge define se o coelho gerado já estará na simulação ao começar, ou será fruto de procriação
     * @param field o Field(campo) no qual o coelho será simulado
     * @param location a localização do coelho gerado no Field(campo)
     *
     */
    public Rabbit(boolean randomAge, Field field, Location location)
    {
        //Caha construtor
        super(field, location);
        //Para coelhos gerados para popular o Field
        if(randomAge) {
            age = Randomizer.getRandomInt(MAX_AGE);
            setFoodLevel(Randomizer.getRandomInt(getFOOD_VALUE()));
        }
        //Para coelhos gerados por procriação.
        else{
            setFoodLevel(getFOOD_VALUE());
        }
    }
    
    /** 
     * Agir controla o comportamento de cada Coelho. Em cada step, um coelho envelhece, fica com mais fome,
     * tenta procriar(caso seja fêmea), procura comida, se move, e/ou morre de fome ou velhice.
     * Encontrar comida causa a morte de um Ator do tipo Grama.
     * Herda da classe Animal
     * Sobrescreve o método act da interface Ator(implementada na classe Animal)
     * @param NewActors O ArrayList contendo todos os atores presentes na simulação, para adicionar mais atores pela procriação
     * 
     */
   @Override
    public void act(ArrayList<Actor> NewActors)
    {
        //Incremeneta a idade e a fome do coelho
        incrementHunger();
        incrementAge();
        
        //Se estiver vivo, realiza suas ações
        if(isAlive()) {
            //Tenta procriar
            giveBirth(NewActors);
            
            //Procura comida com base na sua localização atual
            Location location = getLocation();
            Location newLocation = findFood(location);
            //Se não encontrar comida, se move para uma posição livre
            if(newLocation == null){
                newLocation= getField().freeAdjacentLocation(getLocation());
            }
            //Se encontrar comida, move para sua localização.
            if(newLocation !=null){
                setLocation(newLocation);
            }
            //Em caso de superlotação, o Coelho é setado como morto
            else {
                setInactive();
            }
        }
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
     * Gera um Coelho na posição especificada. Utilizado durante a procriação de Coelhos
     * Sobrescreve o método da classe Animal
     * @param field o Field(campo) onde a simulação ocorre
     * @param loc a localização do novo coelho
     * @return o novo coelho, inserido na localização e no campo especificados.
     */
    @Override
    public Animal getAnimal (Field field,Location loc){
        Rabbit young = new Rabbit(false,field,loc);
        return young;
    }
    
    /** 
     * Verifica se um ator detectado pode ser consumido para obter comida.
     * No caso de coelhos, o ator encontrado deve ser do tipo Grama.
     * Sobrescreve o método da classe Animal
     * @param prey o ator encontrado
     * @return informa se o ator pode ser comido(true) ou não(false)
     */
    @Override
    protected boolean compatibleFood(Actor prey){
        //Verifica se o ator encontrado é uma instância de Grama
        if(prey instanceof Grass){
            Grass grass = (Grass)prey;
            //Mata a Grama(pois a consumiu)
            if(grass.isActive()){
                grass.setInactive();
            }
            return true;
        }
        return false;
    }
    
    /** 
     * Obtem o atributo FOOD_VALUE (valor de comida)
     * Sobrescreve o método da classe Animal
     * @return o valor de comida de um ator do tipo Grama
     */
    @Override
    protected int getFOOD_VALUE(){
        return GRASS_FOOD_VALUE;
    }
    
}
    
   
    
   

    
  

   
