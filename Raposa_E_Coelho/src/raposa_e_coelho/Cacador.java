
package raposa_e_coelho;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
/**
 * Um modelio básico de caçador. Caçadores se movem, plantam armadilhas 
 * e matam animais próximos.
 * Implementa a interface Actor
 * @author luizc
 */
public class Cacador implements Actor{
    //A posição do caçador no Grid de Field
    private Location location;
    //O field no qual a simulação ocorre
    private Field field;
    //A chance do caçador montar armadilhas
    private static final int SET_TRAP_CHANCE = 5;
    //Informa se o caçador está ativo ou não
    private boolean active;
    
    
    /**
      * Cria uma instância de Caçador para a simulação
      * @param field O Campo onde a simulação ocorre
      * @location a posição onde o caçador será gerado.
      */
    public Cacador(Field field, Location location){
        this.field = field;
        setLocation(location);
        active = true;
    }
    
    /**
     * Obtém o field do caçador
     * @return o field do caçador
     */
    public Field getField(){
        return field;
    }
    
   /**
     * Obtém a posição do caçador no grid de Field
     * @return A posição do caçador
     */
    public Location getLocation(){
        return location;
    }
        
    /**
     * Define a localização do Cacador no grido do Field
     * Sobrescreve o método setLocation de Actor
     * @param newLocation A nova localização de Caçador no Grid de Field
     */
    @Override
    public void setLocation(Location newLocation){
        if(location != null){
            field.clear(location);
        }//fim if
        location = newLocation;
        field.place(this,newLocation);
    }
    
    /**
     * Atualiza o estado do Caçador para desativado(Inativo)
     * Sobrescrita de setInactive de Actor
     */
    @Override
    public void setInactive(){
        active = false;
        field.clear(location);
        field= null;
        location = null;
    }
    
   /**
     * Informa se o Cacador ainda está ativo
     * Sobrescreve o método isActive de Actor
     * @return A confirmação ou negação do Caçador como Ativo
     */
    @Override
    public boolean isActive(){
        return active;
    }
    
    /**
     * Move o caçador para alguma posição adjacente válida
     */
    public void move(){
        Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation !=null){
                setLocation(newLocation);
            }
    }
    
    /**
     * Verifica as posições adjacentes do caçador por animais para matá-los
     * @param currentLocation A localização atual do caçador
     * @return A posição do primeiro animal encontrado, ou null se nenhum foi encontrado
     */
    public Location checkSurroundings(Location currentLocation){
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(location);
        Iterator<Location> iter = adjacent.iterator();
        while(iter.hasNext()){
            Location place = iter.next();
            Actor anAnimal = field.getObjectAt(place);
            if (anAnimal != null && anAnimal instanceof Animal){
                return place;
            }//fim if
        }//fim while
        return null;
    }
    
    /**
     * Tenta plantar uma armadilha no Field
     * @return A nova armadilha plantada no field
     */
    public Trap plantTrap(){
        Location trapLocation = getField().freeAdjacentLocation(getLocation());
        if(trapLocation != null){
            int setTrap = Randomizer.getRandomInt(100);
            if(setTrap < SET_TRAP_CHANCE){
                Trap newTrap = new Trap(getField(),trapLocation);
                return newTrap;
            }
        }
        return null;
    }
    
    /**
     * Mata animais na localização especificada
     * @param location A localização do animal à ser morto
     */
    public void hunt(Location location){
        Actor prey = field.getObjectAt(location);
        if(prey != null && prey instanceof Animal){
            if(prey.isActive()){
                Animal animal = (Animal)prey;
                animal.setInactive();
            }
        }
    }
    
    /**
     * As ações que um cacador realiza em cada Step.
     * Ele se move para uma posição adjacente
     * Ela checa suas posições adjacentes buscando por animais
     * caso encontre algum, ele o mata.
     * Caso não encontre nenum, tenta plantar uma armadilha 
     * em uma posição adjacente aleatória
     * Sobrescreve o método act de Actor
     * @param NewActors o ArrayList contendo os Atores da simulação, para indicar quais deles serão mortos e para inserir armadilhas plantadas.
     */
    @Override
    public void act(ArrayList<Actor> NewActors){
        move();
        Location surrounding = checkSurroundings(location);
        if(surrounding != null){
            hunt(surrounding);
        }
        else{
            Trap newTrap = plantTrap();
            if(newTrap != null){
                NewActors.add(newTrap);
            }
        }
    }
           
}
