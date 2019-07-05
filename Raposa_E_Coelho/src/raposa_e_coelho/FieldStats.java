package raposa_e_coelho;
import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;

/**
  * Esta classe coleta e fornece alguns dados estatísticos sobre o estado
  * de um campo. É flexível: vai criar e manter um contador
  * para qualquer classe de objeto encontrada no campo.
  *
  * @author João Paulo Pena, Luiz Felipe Calvo, Raphael Fernandes Roriz 
  * 
  */
public class FieldStats
{
    // Counters for each type of entity (fox, rabbit, etc.) in the simulation.
    private HashMap counters;
    // Whether the counters are currently up to date.
    private boolean countsValid;

    /**
     * Construct a field-statistics object.
     */
    public FieldStats()
    {
        // Set up a collection for counters for each type of animal that
        // we might find
        counters = new HashMap();
        countsValid = true;
    }

    /**
     * @param  field campo sobre os detalhes das populacao
     * @return  buffer.toString() Uma string descrevendo o que os animais estão no campo.
     */
    public String getPopulationDetails(Field field)
    {
        StringBuffer buffer = new StringBuffer();
        if(!countsValid) {
            generateCounts(field);
        }
        Iterator keys = counters.keySet().iterator();
        while(keys.hasNext()) {
            Counter info = (Counter) counters.get(keys.next());
            buffer.append(info.getName());
            buffer.append(": ");
            buffer.append(info.getCount());
            buffer.append(' ');
        }
        return buffer.toString();
    }
    
   /**
      * Invalidar o conjunto atual de estatísticas; reiniciar tudo
      * conta para zero.
      */
    public void reset()
    {
        countsValid = false;
        Iterator keys = counters.keySet().iterator();
        while(keys.hasNext()) {
            Counter cnt = (Counter) counters.get(keys.next());
            cnt.reset();
        }
    }

    /**
      * Incrementar a contagem para uma classe de animal.
      */
    public void incrementCount(Class animalClass)
    {
        Counter cnt = (Counter) counters.get(animalClass);
        if(cnt == null) {
            // we do not have a counter for this species yet - create one
            cnt = new Counter(animalClass.getName());
            counters.put(animalClass, cnt);
        }
        cnt.increment();
    }

   /**
      * Indique que uma contagem de animais foi completada.
      */
    public void countFinished()
    {
        countsValid = true;
    }

    /**
      * @param field do campo se é viavel
      * Determine se a simulação ainda é viável.
      * Ou seja, deve continuar a ser executado.
      * @return true Se houver mais de uma espécie viva.
      */
    public boolean isViable(Field field)
    {
        // Quantas contagens são diferentes de zero.
        int nonZero = 0;
        if(!countsValid) {
            generateCounts(field);
        }
        Iterator keys = counters.keySet().iterator();
        while(keys.hasNext()) {
            Counter info = (Counter) counters.get(keys.next());
            if(info.getCount() > 0) {
                nonZero++;
            }
        }
        return nonZero > 1;
    }
    
 /**
      * @param field do campo onde sera gerado a contagem
      * Gerar contagens do número de raposas e coelhos.
      * Estes não são mantidos atualizados como raposas e coelhos
      * são colocados no campo, mas somente quando um pedido
      * é feito para a informação.
      */
    private void generateCounts(Field field)
    {
        reset();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object animal = field.getObjectAt(row, col);
                if(animal != null) {
                    incrementCount(animal.getClass());
                }
            }
        }
        countsValid = true;
    }
}
