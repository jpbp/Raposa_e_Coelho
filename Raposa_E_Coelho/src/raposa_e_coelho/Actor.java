
package raposa_e_coelho;

import java.util.ArrayList;

/**
 * Uma interface com métodos comuns aos atores
 * da simulação.
 * @author João Paulo Pena, Luiz Felipe Calvo, Raphael Fernandes Roriz
 */
public interface Actor {
    
    /**
     * Controla o comportamento de cada ator durante a simulação
     * @param NewActors Um ArrayList que contém novos atores gerados pelo métodos
     */
    public void act(ArrayList<Actor> NewActors);
     
    /**
     * Verifica se o ator ainda está vivo(ativo)
     * @return A verificação se o Ator ainda está ativo
     */
    public boolean isActive();
    
    /**
     * Torna o ator Inativo(morto)
     */
    public void setInactive();
    
    /**
     * Altera a localização do ator no grid do Field
     * @param newLocation A localização destino do ator
     */
    public void setLocation(Location newLocation);
    
}
