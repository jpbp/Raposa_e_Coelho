package raposa_e_coelho;
import java.awt.Color;

/**
 * Providencia um contador para cada participante da simulação
 * Isto inclui uma string identificadora, e um contador de quantos participantes
 * daquele tipo existem atualmente na simulação
 * Provide a counter for a participant in the simulation.
 * @author João Paulo Pena, Luiz Felipe Calvo, Raphael Fernandes Roriz
 */
public class Counter
{
    // O nome do tipo de participante da simulação
    private String name;
    // Quantos do mesmo tipo estão presentes na simulação
    private int count;

    /**
     * Providencia o nome para um dos tipos simulados
     * @param name  Um nome, por exemplo, "Fox"
     */
    public Counter(String name)
    {
        this.name = name;
        count = 0;
    }
    
    /**
     * Obtem o nome do participante da simulação
     * @return Uma descrição curta do participante
     */
    public String getName()
    {
        return name;
    }

    /**
     * Obtém uma contagem para o participante da simulação
     * @return A contagem do tipo participante.
     */
    public int getCount()
    {
        return count;
    }

    /**
     * Incrementa o contador em um 
     */
    public void increment()
    {
        count++;
    }
    
    /**
     * Reseta o contador para zero
     */
    public void reset()
    {
        count = 0;
    }
}
