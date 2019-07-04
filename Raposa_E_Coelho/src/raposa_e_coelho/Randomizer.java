package raposa_e_coelho;

import java.util.Random;

/**
 * A classe Randomizer é responsável pela geração de números aleatórios
 * durante a simulação.
 * @author João Paulo Pena, Luiz Felipe Calvo, Raphael Fernandes Roriz
 */
public class Randomizer {
    
    //Atributo que acessa as funções da classe Random.
    private static final Random rand = new Random();
    
    /**
     * Retorna um número INTEIRO aleatório
     * @param Valor máximo que pode ser gerado aleatóriamente
     * @return o número inteiro gerado entre 0 e o valor especificado
     */
    public static int getRandomInt(int max){
        return rand.nextInt(max);
    }
    
    /**
     * Retorna um número REAL aleatório.
     */
    public static double getRandomDouble(){
        return rand.nextDouble() ;
    }
    
}
