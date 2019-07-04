package raposa_e_coelho;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Classe responsável por popular o Field (campo) da simulação.
 * A população do campo é feita de forma aleatória.
 * @author João Paulo Pena, Luiz Felipe Calvo, Raphael Fernandes Roriz
 */
public class PopulationGenerator {
    

    // Probabilidade de uma raposa ser posicionada em uma posição no grid do Field
    private static final double FOX_CREATION_PROBABILITY = 0.02; //0.02
    // Probabilidade de um coelho ser posicionado em uma posição no grid do Field
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;
    // Probabilidade de um caçador ser posicionado em uma posição no grid do Field
    private static final double HUNTER_CREATION_PROBABILITY = 0.005;
   // Probabilidade de uma armadilha ser posicionada em uma posição no grid do Field
    private static final double TRAP_CREATION_PROBABILITY = 0.0005;
    // Probabilidade de grama ser posicionada em uma posição no grid do Field
    private static final double GRASS_CREATION_PROBABILITY = 0.09;
        
   /**
     * Gera uma população aleatória para o Field.
     * Cada posição no grid é verificada, e um Ator pode ser inserido em cada posição.
     * Atores possíveis são: Raposas, Coelhos, Caçadores, Grama e Armadilhas.
     * A população do campo é feita de forma aleatória.
     * @param field O Field(Campo) onde a simulação ocorre
     * @param actors Um ArrayList contendo todos os atores gerados
     */
    public void populate(Field field,ArrayList<Actor> actors)
    {
       
       
        //Percorre cada posição do grid do Field, tentando posicionar um dos vários atores em cada uma delas
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                
                //Tenta gerar uma Armadilha
                if(Randomizer.getRandomDouble() <= TRAP_CREATION_PROBABILITY){
                    Location location = new Location(row,col);
                    Trap trap = new Trap(field,location);
                    actors.add(trap);
                    
                    field.place(trap,row,col);
                }
                
                //Tenta gerar um Caçador
                else if(Randomizer.getRandomDouble() <= HUNTER_CREATION_PROBABILITY){
                    Location location = new Location(row,col);
                    Cacador cacador = new Cacador(field,location);
                    actors.add(cacador);
                    
                    field.place(cacador,row,col);
                }
                
                //Tenta gerar uma Raposa
                else if(Randomizer.getRandomDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location=new Location(row,col);
                    Fox fox = new Fox(true,field,location);
                    actors.add(fox);
                    field.place(fox, row, col);
                }
                
                //Tenta gerar um Coelho
                else if(Randomizer.getRandomDouble() <= RABBIT_CREATION_PROBABILITY) {
                     Location location=new Location(row,col);
                    Rabbit rabbit = new Rabbit(true,field,location);
                    actors.add(rabbit);
                    
                    field.place(rabbit, row, col);
                    
                }
                
                //Tenta gerar Grama
                else if(Randomizer.getRandomDouble() <= GRASS_CREATION_PROBABILITY) {
                    Location location=new Location(row,col);
                    Grass grass = new Grass(true,field,location);
                    actors.add(grass);
                    
                    field.place(grass, row, col);
                    
                }

                //Se nenhum Ator for gerado, a posição permanece vazia.
            }
        }
        //Embaralha os atores presentes
        Collections.shuffle(actors);
      
    }
}
