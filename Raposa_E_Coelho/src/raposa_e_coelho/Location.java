
package raposa_e_coelho;
/**
 * Representa uma localização no grid de um Field
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-09
 */
public class Location
{
    // Posições de linhas e colunas
    private int row;
    private int col;

    /**
     * Representa uma linha e coluna
     * @param row A linha
     * @param col A coluna
     */
    public Location(int row, int col)
    {
        this.row = row;
        this.col = col;
    }
    
    /**
     * Implementa a igualdade do conteúdo
     */
    public boolean equals(Object obj)
    {
        if(obj instanceof Location) {
            Location other = (Location) obj;
            return row == other.getRow() && col == other.getCol();
        }
        else {
            return false;
        }
    }
    
    /**
     * Retorna uma string no formato linha,coluna
     * @return Uma String representando a localização
     */
    public String toString()
    {
        return row + "," + col;
    }
    
    /**
     * Usa os primeiros 16 bit para o valor da linha, e os últimos para a coluna
     * Exceto para grids muito grandes, isto dá uma hash exclusiva para cada par de (linha,coluna)
     */
    public int hashCode()
    {
        return (row << 16) + col;
    }
    
    /**
     * Obtém a linha
     * @return A linha
     */
    public int getRow()
    {
        return row;
    }
    
    /**
     * Obtém a coluna
     * @return A Coluna
     */
    public int getCol()
    {
        return col;
    }
}
