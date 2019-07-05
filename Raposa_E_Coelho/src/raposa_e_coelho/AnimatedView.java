package raposa_e_coelho;


import javax.swing.JFrame;
import raposa_e_coelho.SimulatorViewFull;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;
import javax.swing.text.FieldView;
import raposa_e_coelho.Field;
import raposa_e_coelho.FieldStats;

/**
 * Uma interface com métodos comuns aos atores
 * da simulação.
 * @author João Paulo Pena, Luiz Felipe Calvo, Raphael Fernandes Roriz
 */
public class AnimatedView extends JFrame implements SimulatorViewFull
{
    // Colors used for empty locations.
    private static final Color EMPTY_COLOR = Color.white;

    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.gray;

    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    private JLabel stepLabel, population;
    private FieldView fieldView;
    
    // A map for storing colors for participants in the simulation
    private HashMap colors;
    // A statistics object computing and storing simulation information
    private FieldStats stats;

    /**
     * Create a view of the given width and height.
     */
    public AnimatedView(int height, int width)
    {
        stats = new FieldStats();
        colors = new HashMap();

        setTitle("Fox and Rabbit Simulation");
        stepLabel = new JLabel(STEP_PREFIX, JLabel.CENTER);
        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);
        
        setLocation(height,width );
        
        fieldView = new FieldView(height, width);

        Container contents = getContentPane();
        contents.add(stepLabel,BorderLayout.NORTH);
        contents.add(fieldView, BorderLayout.CENTER);
        contents.add(population, BorderLayout.SOUTH);
        pack();
        
        setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Define a color to be used for a given class of animal.
     * @param animalClass classe do animal 
     * @param Cor do Animal
     */
    @Override
    public void setColor(Class animalClass, Color color)
    {
        colors.put(animalClass, color);
    }

    /**
     * Define a color to be used for a given class of animal.
     * @param animalClass obter a cor do animal
     * @return col a cor do animal
     */
    private Color getColor(Class animalClass)
    {
        Color col = (Color)colors.get(animalClass);
        if(col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        }
        else {
            return col;
        }
    }

 /**
      * Mostrar o status atual do campo.
      * @param step Qual é a etapa de iteração?
      * campo @param
      * @param stats Status do campo a ser representado.
      */
    
    @Override
    public void showStatus(int step,Field field)
    {
        if(!isVisible())
            setVisible(true);

        stepLabel.setText(STEP_PREFIX + step);

        stats.reset();
        fieldView.preparePaint();
            
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object animal = field.getObjectAt(row, col);
                if(animal != null) {
                    stats.incrementCount(animal.getClass());
                    fieldView.drawMark(col, row, getColor(animal.getClass()));
                }
                else {
                    fieldView.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        stats.countFinished();

        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
        fieldView.repaint();
    }

   /**
      * Determine se a simulação deve continuar a ser executada.
      * @return true Se houver mais de uma espécie viva.
      */
    @Override
    public boolean isViable(Field field)
    {
        return stats.isViable(field);
    }
    
    /**
      * Fornecer uma visão gráfica de um campo retangular. Isto é
      * uma classe aninhada (uma classe definida dentro de uma classe) que
      * define um componente personalizado para a interface do usuário. este
      * componente exibe o campo.
      * Isso é bastante avançado GUI stuff - você pode ignorar isso
      * para o seu projeto, se quiser.
      */
    private class FieldView extends JPanel
    {
        private final int GRID_VIEW_SCALING_FACTOR = 6;

        private int gridWidth, gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image fieldImage;

        /**
         * Create a new FieldView component.
         * @param height comprimento do campo
         * @param width largura do campo
         */
        public FieldView(int height, int width)
        {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

      /**
          * Diga ao gerente da GUI o tamanho que gostaríamos de ser.
          */
        public Dimension getPreferredSize()
        {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                                 gridHeight * GRID_VIEW_SCALING_FACTOR);
        }
        
        /**
          * Prepare-se para uma nova rodada de pintura. Desde o componente
          * pode ser redimensionado, calcule o fator de escala novamente.
          */
        public void preparePaint()
        {
            if(! size.equals(getSize())) {  // if the size has changed...
                size = getSize();
                fieldImage = fieldView.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / gridWidth;
                if(xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if(yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }
        
        /**
         * Paint on grid location on this field in a given color.
         *@param x 
         *@param y
         *@param color
         */
        public void drawMark(int x, int y, Color color)
        {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
        }

        /**
         * The field view component needs to be redisplayed. Copy the
         * internal image to screen.
         *@param g 
         */
        public void paintComponent(Graphics g)
        {
            if(fieldImage != null) {
                g.drawImage(fieldImage, 0, 0, null);
            }
        }
    }
}
    

