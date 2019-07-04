package raposa_e_coelho;
/**
 * A classe Principal, que contém o método Main.
 * @author João Paulo Pena, Luiz Felipe Calvo, Raphael Fernandes Roriz.
 */
public class Principal{
  
  /**
 * O método main da simulação, responsável por iniciar a simulação propriamente dita,e
 * definir quantos Steps serão rodados
 * @param argumentos da linha de comando
 */
  public static void main(String[] args) throws InterruptedException{
    //Roda a simulação.
    Simulator simulator = new Simulator();
       simulator.runLongSimulation();
      }
  }

