package raposa_e_coelho;
public class Principal{
  public static void main(String[] args) throws InterruptedException{
    Simulator simulator = new Simulator();
      for (int i = 0; i < 10; i++) {
          simulator.simulateOneStep(); 
      }
  }
}
