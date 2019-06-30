/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raposa_e_coelho;

import java.awt.Color;

/**
 *
 * @author Jp
 */
public interface SimulatorViewFull {
    
    void setColor(Class cl,Color color);
    boolean isViable(Field field);
    void showStatus(int step,Field field);
}
