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
    private static final int DEFAULT_WIDTH = 100;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 100;
   

    //aA lista de animais no campo
    private ArrayList<Actor> actors;
    
    
    
    
    // o estado atual do campo
    private Field field;
    
    // o passo atual da simulacao
    private int step;
    // A graphical view of the simulation.
    private AnimatedView view;
    private PopulationGenerator populationGenerator;
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
        actors =new ArrayList<>();
        
        field = new Field(depth, width);
        

        // criação de visualizacao do campo
        view = new AnimatedView(depth, width);
        view.setColor(Fox.class, Color.red);
        view.setColor(Rabbit.class, Color.black);
        view.setColor(Cacador.class,Color.blue);
        view.setColor(Trap.class,Color.orange);
        view.setColor(Grass.class,Color.green);
        populationGenerator = new PopulationGenerator();
        // configura um ponto inicial valido
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * e.g. 500 steps.
     */
    public void runLongSimulation()throws InterruptedException
    {
        simulate(500);
    }
    
    /*public int TamanhoFox(){
        int cont=0;
        for(Animal a: animals){
            if(a instanceof Fox){
                cont++;
            }
        }
        return cont;
    }
    public int TamanhoRabbit(){
        int cont=0;
        for(Animal a: animals){
            if(a instanceof Rabbit){
                cont++;
            }
        }
        return cont;
    }
    */
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     */
    public void simulate(int numSteps) throws InterruptedException
    {
        
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            Thread.currentThread().sleep(0); // 1 segundo
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
      ArrayList<Actor> newActors=new ArrayList<Actor>();
      for (Iterator<Actor> it =actors.iterator(); it.hasNext();){
          Actor actor =  it.next();
          actor.act(newActors);
          
          if(!actor.isActive()){

              it.remove();
          }
      }
     
      
      actors.addAll(newActors);
      view.showStatus(step, field);
        //System.out.print(TamanhoFox()+" ");
        //System.out.println(TamanhoRabbit());
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        actors.clear();
        field.clear();
        populationGenerator.populate(field,actors);
        
        // Show the starting state in the view.
        view.showStatus(step, field);
    }
    
    /**
     * Populate the field with foxes and rabbits.
     */
    
}
