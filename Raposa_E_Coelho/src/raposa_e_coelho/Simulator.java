package raposa_e_coelho;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a field containing
 * rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-09
 */
public class Simulator
{
    // The private static final variables represent 
    // configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 50;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 50;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;    

    //aA lista de animais no campo
    private List<Fox> foxes;
    private List<Rabbit> rabbits;
    
    
    
    // o estado atual do campo
    private Field field;
    
    // o passo atual da simulacao
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    
    /**
     * Construct a simulation field with default size.
     */
   
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
     public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        rabbits =new ArrayList<Rabbit>();
        foxes=new ArrayList<Fox>();
        field = new Field(depth, width);
       

        // criação de visualizacao do campo
        view = new SimulatorView(depth, width);
        view.setColor(Fox.class, Color.red);
        view.setColor(Rabbit.class, Color.black);
        
        // configura um ponto inicial valido
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * e.g. 500 steps.
     */
    public void runLongSimulation()
    {
        simulate(500);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;
      //Fornece espaço para animais recem-nascidos
      List<Rabbit> newRabbits=new ArrayList<>();
      List<Fox> newFoxes=new ArrayList<>();
      for (Iterator<Rabbit> it =rabbits.iterator(); it.hasNext();){
          Rabbit rabbit=it.next();
          rabbit.run(newRabbits);
          if(!rabbit.isAlive()){
              it.remove();
          }
      }
      for (Iterator<Fox> it =foxes.iterator(); it.hasNext();){
          Fox fox=it.next();
          fox.hunt(newFoxes);
          if(!fox.isAlive()){
              it.remove();
          }
      }
      
      rabbits.addAll(newRabbits);
      foxes.addAll(newFoxes);
      view.showStatus(step, field);
        System.out.println("coelhos: "+rabbits.size());
        System.out.println("raposas: "+foxes.size());
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        rabbits.clear();
        field.clear();
        foxes.clear();
        populate();
        
        // Show the starting state in the view.
        view.showStatus(step, field);
    }
    
    /**
     * Populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = new Random();
       
        //field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location=new Location(row,col);
                    Fox fox = new Fox(true,field,location);
                    foxes.add(fox);
                    field.place(fox, row, col);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                     Location location=new Location(row,col);
                    Rabbit rabbit = new Rabbit(true,field,location);
                    rabbits.add(rabbit);
                    
                    field.place(rabbit, row, col);
                    
                }
                // else leave the location empty.
            }
        }
        Collections.shuffle(foxes);
        Collections.shuffle(rabbits);
    }
}
