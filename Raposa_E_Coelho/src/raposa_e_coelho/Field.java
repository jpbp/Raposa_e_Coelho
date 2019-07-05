package raposa_e_coelho;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Representa um grid retangular de posições no campo.
 * Cada posição guarda apenas um Ator
 * 
 * @author João Paulo Pena, Luiz felipe calvo, Raphael Reis roriz
 */
public class Field
{
    // Um gerador de números aleatórios para se obter posições aleatórias
   private static final Random rand = new Random();
    
    // comprimento e largura do campo
    priArmazena os Atores
    private Actor[][] field;

    /**
     * representa o Campo com as dadas dimensões
     * @param depth A largura do campo
     * @param width O comprimento do campo
     */
    public Field(int depth, int width)
    {
        this.depth = depth;
        this.width = width;
        field = new Actor[depth][width];
    }
    
    /**
     * Esvazia o campo
     */
    public void clear()
    {
        for(int row = 0; row < depth; row++) {
            for(int col = 0; col < width; col++) {
                field[row][col] = null;
            }
        }
    }
    
    /**
     * Esvazia uma localização específica
     * @param location A localização à ser esvaziada
     */
    public void clear(Location location)
    {
        field[location.getRow()][location.getCol()] = null;
    }
    
    /**
     * Posiciona um ator na localização especificada
     * Caso haja algum animal em tal posição, ele será perdido.
     * @param animal O Ator à ser posicionado
     * @param row A coordenada de linha da localização
     * @param col A coordenada de coluna da localização
     */
    public void place(Actor animal, int row, int col)
    {
        place(animal, new Location(row, col));
    }
    
    /**
     * Posiciona um ator na localização especificada
     * Caso haja algum animal em tal posição, ele será perdido.
     * @param animal O Ator à ser posicionado
     * @param location Onde o Ator será posicionado
     */
    public void place(Actor animal, Location location)
    {
        field[location.getRow()][location.getCol()] = animal;
    }
    
    /**
     * Obtém o Ator em uma posição, caso haja algum
     * @param location A posição no field
     * @return O Ator na posição, ou null se não houver nenhum
     */
    public Actor getObjectAt(Location location)
    {
        return getObjectAt(location.getRow(), location.getCol());
    }
    
    /**
     * Obtém o Ator em uma posição, caso haja algum
     * @param row A linha desejada
     * @param col A coluna desejada
     * @return O Ator na posição, ou null se não houver nenhum
     */
    public Actor getObjectAt(int row, int col)
    {
        return field[row][col];
    }
    
    /**
     * Gera uma localização aleatória que seja adjacente à
     * localização fornecida. A localização retornada
     * estará dentro dos limites do Field
     * @param location A localização na qual será gerada a adjacência
     * @return Umas localização válida no grid
     */
    public Location randomAdjacentLocation(Location location)
    {
        List<Location> adjacent = adjacentLocations(location);
        return adjacent.get(0);
    }
    
    /**
     * Obtém uma lista embaralhada de localizações
     * @param location Obtém posições adjacentes à esta
     * @return Uma lista com posições adjacentes livres
     */
    public List<Location> getFreeAdjacentLocations(Location location)
    {
        List<Location> free = new LinkedList<Location>();
        List<Location> adjacent = adjacentLocations(location);
        for(Location next : adjacent) {
            if(getObjectAt(next) == null) {
                free.add(next);
            }
        }
        return free;
    }
    
    /**
     * tenta encontrar uma pósição livre adjacente à posição fornecida.
     * A posição fornecida estará nos limites do Field.
     * @param location A localização na qual será gerada a adjacência
     * @return Umas localização válida no grid
     */
    public Location freeAdjacentLocation(Location location)
    {
        // As posições livres disponíveis
        List<Location> free = getFreeAdjacentLocations(location);
        if(free.size() > 0) {
            return free.get(0);
        }
        else {
            return null;
        }
    }

    /**
     * Obtém uma lista embaralhada de posições
     * A lista não incluirá a própria posição
     * todas as posições devem estar dentro do grid
     * @param location A localização na qual será gerada a adjacência
     * @return Uma lista de posições adjacentes à fornecida
     */
    public List<Location> adjacentLocations(Location location)
    {
        assert location != null : "Null location passed to adjacentLocations";
        // A lista de posições à ser retornada
        List<Location> locations = new LinkedList<Location>();
        if(location != null) {
            int row = location.getRow();
            int col = location.getCol();
            for(int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = row + roffset;
                if(nextRow >= 0 && nextRow < depth) {
                    for(int coffset = -1; coffset <= 1; coffset++) {
                        int nextCol = col + coffset;
                        // Exclui posições inválidas e a própria posição
                        if(nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }
            
            // Embaralha a lista. Muitos dos métopdos que dependem da lista
            // se iniciam em posições aleatórias
            Collections.shuffle(locations, rand);
        }
        return locations;
    }

    /**
     * Obtém a largura do field
     * @return A largura do Field
     */
    public int getDepth()
    {
        return depth;
    }
    
    /**
     * Obtém o comprimento do field
     * @return O comprimento do field
     */
    public int getWidth()
    {
        return width;
    }
}
