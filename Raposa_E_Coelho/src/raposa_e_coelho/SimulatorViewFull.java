package raposa_e_coelho;

import java.awt.Color;

/**
 *
 * @author Jp
 */

/**
* Define os atributos principais a serem 
* exibidos na vizualiçao da simulaçao
*/
public interface SimulatorViewFull {
    //Define as cores dos atores;     
    void setColor(Class cl,Color color);
    
    //Verica se o Field esta disponivel
    boolean isViable(Field field);
    
    //Exibe o estado atual do Field;
    void showStatus(int step,Field field);
}
