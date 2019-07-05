
package raposa_e_coelho;

import java.util.ArrayList;
import java.util.List;
/**
 * Um modelo simples de Grama(Vegetação).
 * Vegetações se reproduzem e/ou expandem, e morrem.
 * Para efeito de praticidade, crescimento e reprodução 
 * produzem o mesmo efeito na simulação, ocupando uma posição
 * adjacente no grid do Field.
 * Implementa a interface Actor
 * @author João Paulo Pena, Luiz Felipe Calvo, Raphael Fernandes Roriz
 */
public class Grass implements Actor {
    //A Localização da Grama
    private Location location;
    //O campo onde a Grama será simulada
    private Field field;
    //A idade da Grama
    private int age;
    //Se a Grama está viva ou não.
    private boolean alive;
    //Idade Mínima para reprodução 
    private static final int BREEDING_AGE = 1;
    //Idade Máxima que a Grama vive
    private static final int MAX_AGE = 10;
    //Probabilidade da Grama crescer ou se Reproduzir
    private static final double GROWING_PROBABILITY = 0.075;
    //Quantidade máxima de Crescimentos e Reproduções.
    private static final int MAX_GROWTH_SIZE = 2;
    
    /**
      * Cria uma instância de Grama na simulação
      * @param randomAge determina se a grama será gerada aleatóriamente no povoamento da simulação, ou através de reprodução
      * @param field O Field onde a simulação está ocorrendo
      * @param location A posição ocupada pela Instância da Grama no field
      */
    public Grass(boolean randomAge, Field field, Location location){
        this.field = field;
        setLocation(location);
        this.alive = true;
        //Caso a Grama tenha sido gerada durante o povoamento
        if(randomAge){
            age = Randomizer.getRandomInt(MAX_AGE);
        }
        //Caso a Grama seja gerada por reprodução
        else{
            age = 0;
        }
    }
    
    /** 
     * Agir controla o comportamento da Grama presente em cada posição no grido do Field. 
     * Em cada step, um bloco contendo Grama envelhece e tenta procriar/crescer.
     * Sobrescreve o método act da interface Ator
     * @param NewActors O ArrayList contendo todos os atores presentes na simulação, para adicionar mais atores por procriação
     * 
     */
    public void act(ArrayList<Actor> NewActors){
        incrementAge();
        if(isActive()){
            scatterSeeds(NewActors);
        }
        else{
            setInactive();
        }
    }
    
    /**
     * Obtem o atributo MAX_AGE (Idade Máxima)
     * @return o atributo MAX_AGE
     */
    private int getMAX_AGE(){
        return MAX_AGE;
    }
    
    /**
     * Obtem o atributo BREEDING_AGE (Idade minima para reprodução)
     * @return o atributo BREEDING_AGE
     */
    private int getBREEDING_AGE(){
        return BREEDING_AGE;
    }
    
    /**
     * Obtem o atributo field (O campo onde a simulação ocorre)
     * @return o atributo field
     */
    private Field getField(){
        return field;
    }
    
    /**
     * Incrementa a idade da Grama
     */
    protected void incrementAge(){
        age++;/** 
     * Agir controla o comportamento de cada Coelho. Em cada step, um coelho envelhece, fica com mais fome,
     * tenta procriar(caso seja fêmea), procura comida, se move, e/ou morre de fome ou velhice.
     * Encontrar comida causa a morte de um Ator do tipo Grama.
     * Herda da classe Animal
     * Sobrescreve o método act da interface Ator(implementada na classe Animal)
     * @param NewActors O ArrayList contendo todos os atores presentes na simulação, para adicionar mais atores pela procriação
     * 
     */
        //Verifica se a grama não está mais velha que o permitido.
        if(age > getMAX_AGE()){
            setInactive();
        }
    }
    
    /**
     * Obtem o atributo GROWING_PROBABILITY (Probabilidade de crescimento, a mesma para reprodução)
     * @return o atributo GROWING_PROBABILITY
     */
    private double getGROWING_PROBABILITY(){
        return GROWING_PROBABILITY;
    }
    
    /**
     * Obtem o atributo MAX_GROWTH_SIZE (Quantidade máxima de crescimentos e reproduções)
     * @return o atributo MAX_GROWTH_SIZE
     */
    private int getMAX_GROWTH_SIZE(){
        return MAX_GROWTH_SIZE;
    }
    
    /**
     * Define a localização da Grama no grid do Field
     * Sobrescreve o método setLocation de Actor
     */
    @Override
    public void setLocation(Location newLocation){
        //Se já ocupava algum lugar antes, ele é desocupa
        if(location != null){
            field.clear(location);
        }
        //Posiciona a Grama na nova localização
        location = newLocation;
        field.place(this,newLocation);
    }
    
    /**
     * Obtem a localização atual da Grama no grid do Field
     * @return a localização da Grama
     */
    public Location getLocation(){
        return location;
    }
    
    /**
     * Define a grama como Inativa (ou seja, morta),a remove do Field caso esteja ocupando alguma localização
     * Sobrescreve o método setInactive de Actor
     */
    @Override
    public void setInactive(){
        this.alive = false;
        if(location != null){
            field.clear(location);
            location = null;
            field = null;
        }
    }
    
    /**
     * Informa se a Grama está ativa(viva)
     * Sobrescreve o método isActive de Actor
     * @return o parâmetro alive, informando se a grama está viva.
     */
    @Override
    public boolean isActive(){
        return alive;
    }
    
    /**
     * A Grama tenta crescer/se reproduzir. Caso consiga, incrementa o total de crescimentos realizados.
     * @return o numero de crescimentos totais
     */
    private int grow(){
        int growths = 0;
        //Tenta se reproduzir/crescer
        if(canGrow() && Randomizer.getRandomDouble() <= getGROWING_PROBABILITY()){
            //Se conseguir, reproduz/cresce
            growths = Randomizer.getRandomInt(getMAX_GROWTH_SIZE()) + 1;
        }
        return growths;
    }
    
    /**
     * Verifica se a Grama tem idade para se reproduzir
     * @return o resultado da verificação.
     */
    private boolean canGrow(){
        return (age >= getBREEDING_AGE());
    }
    
    
    /**
     * Procria, gerando uma nova instância de Grama em uma posição adjacente
     * @param NewActors ArrayList contendo todos os atores presentes na simulação, utilizado para adicionar novos atores à simulação.
     */
    private void scatterSeeds(ArrayList<Actor> NewActors){
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int totalGrowth = grow();
        //Verifica se há alguma posição adjacente disponível, e se a grama pode se reproduzir/crescer
        for(int g = 0; g < totalGrowth && free.size() > 0; g++){
            Location loc = free.remove(0);
            Grass sprout = new Grass(false,field,loc);
            //Adiciona novas Gramas à simulação.
            NewActors.add(sprout);
        }
        
    }
    
    
}//fim classe
