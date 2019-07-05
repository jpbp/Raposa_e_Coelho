
package raposa_e_coelho;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * Um modelo simples de Armadilha.
 * Armadilhas são plantadas por caçadores, e não se movem; apenas aguardam algum animal (Raposa ou coelho)
 * se aproximar de seus arredores, então dispara, matando o animal e se desativando no processo.
 * @author João Paulo Pena, Luiz Felipe Calvo, Raphael Fernandes Roriz 
 */
public class Trap implements Actor{
    //A localização da Armadilha no gAtributosrid do Field(Campo)
    private Location location;
    //O Field(Campo) onde a armadilha está localizada
    private Field field;
    // Informa se a armadilha está ativa ou não
    private boolean active;
    
    /**
      * Instancia uma nova armadilha  na simulação.
      * @param field O campo ond a Armadilha será simulada
      * @param location a posição da armadilha no grid do Field
      */
    public Trap(Field field, Location location){
        this.field = field;
        setLocation(location);
        active = true;
    }
    
    /**
     * Obtém o field onde a Armadilha está sendo simulada
     * @return O Field da armadilha
     */
    public Field getField(){
        return field;
    }
        
    /**
     * Define a localização da Armadilha no grido do Field
     * Sobrescreve o método setLocation de Actor
     * @param newLocation A localização no grid de Field onde a armadilha será plantada
     */
    @Override
    public void setLocation(Location newLocation){
        //Se a armadilha já ocupa algum lugar, ela o libera
        if(location != null){
            field.clear(location);
        }
        //Ocupa a nova localização
        location = newLocation;
        field.place(this,newLocation);
    }
    
    /**
     * Informa se a Armadilha ainda está ativa
     * Sobrescreve o método isActive de Actor
     * @return A confirmação ou negação da Armadilha como viva
     */
    @Override
    public boolean isActive(){
        return active;
    }
    
    /**
     * Verifica as posições adjacentes da armadilha por animais para matá-los
     * @param currentLocation A localização atual da armadilha
     * @return A posição do primeiro animal encontrado, ou null se nenhum foi encontrado
     */
    public Location checkSurroundings(Location currentLocation){
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(location);
        Iterator<Location> iter = adjacent.iterator();
        //Busca as posições adjacentes
        while(iter.hasNext()){
            Location place = iter.next();
            Actor anAnimal = field.getObjectAt(place);
            //Se encontrou um Ator, precisa ter certeza que é um animal
            if (anAnimal != null && anAnimal instanceof Animal){
                return place;
            }//fim if
        }//fim while
        return null;
    }
    
    /**
     * Ativa a Armadilha, matando o animal na posição indicada
     * e desativando a Armadilha no processo.
     * @param location A Localização do animal à ser morto pela Armadilha.
     */
    public void engage(Location location){
        Actor prey = field.getObjectAt(location);
        //Verifica se o anima
        if(prey != null && prey instanceof Animal){
            if(prey.isActive()){
                Animal animal = (Animal)prey;
                animal.setInactive();
            }
        }
    }
    
    /**
     * Atualiza o estado da armadilha para desativado(Inativo)
     * Sobrescrita de setInactive de Actor
     */
    @Override
    public void setInactive(){
        active = false;
        //Desocupa a posição no grid de Field
        field.clear(location);
        location = null;
        field = null;
    }
    
    /**
     * As ações que uma Armadilha realiza em cada Step.
     * Ela checa suas posições adjacentes buscando por animais
     * caso encontre algum, a Armadilha dispara, matando o animal
     * e se desativando no processo.
     * Sobrescreve o método act de Actor
     * @param NewActors o ArrayList contendo os Atores da simulação, para indicar quais deles serão mortos.
     */
    @Override
    public void act(ArrayList<Actor> NewActors){
        Location surrounding = checkSurroundings(location);
        if(surrounding != null){
            engage(surrounding);
            this.setInactive();
        }
    }
}
