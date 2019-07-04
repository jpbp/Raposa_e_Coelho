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
 * @author João Paulo Pena, Luiz Felipe Calvo Raphael Fernadnes Roriz
 */
public class Simulator
{

    // As variáveis private static final representam
    
    // informações da configuração da simulação.
    // Valor padrão para a largura do grid.
    private static final int DEFAULT_WIDTH = 100;
    // Valor padrão para a altura do grid.
    private static final int DEFAULT_DEPTH = 100;
   
    //A lista de animais no campo.
    private ArrayList<Actor> actors;
    
    //O estado atual do campo.
    private Field field;
    
    // O passo atual da simulacao.
    private int step;
    // Visualização da simulação.
    private AnimatedView view;
    //Preenche o Field com uma população
    private PopulationGenerator populationGenerator;
   
   
    
    
    
     /**
     * Cria uma simulação nas dimensões padrão
     */
     public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Cria uma simulação com dimensões especificadas
     * @param Largura do Field; deve ser maior que zero.
     * @param Altura do Fieled; deve ser maior que zero.
     */
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
        // Definição das cores de cada ator
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
     * Roda a simulação por um longo período de tempo
     * por exemplo, 500 steps.
     */
    public void runLongSimulation()throws InterruptedException
    {
        simulate(500);
    }
    
        
    /**
     * Roda a simulação por um número específico de steps
     * para antes do número definido de steps caso a simulação não seja viável.
     * @param a quantidade desejada de steps.
     * @throws InterruptedException
     */
    public void simulate(int numSteps) throws InterruptedException
    {
        
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            Thread.currentThread().sleep(0); // 1 segundo
        }
    }
    
    /**
     * Roda um step da simulação à partir do seu step atual.
     * Itera por todo o Field atualizando o estado de cara Raposa e Coelho
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
     
      //Adiciona os novos atores à simulação
      actors.addAll(newActors);
      //Mostra o estado do Field após o step.
      view.showStatus(step, field);
    }
        
    /**
     * Reseta asimulação para o estado inicial.
     */
    public void reset()
    {
        step = 0;
        actors.clear();
        field.clear();
        populationGenerator.populate(field,actors);
        
        // Mostra a situação inicial do field
        view.showStatus(step, field);
    }
    
}
