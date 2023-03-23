package gauge;

/**
 * A classe CounterUpdater é responsável por atualizar o valor do medidor de energia
 * e exibir o valor atualizado a cada segundo.
 *  @author Alexjr
 * @version 0.0.1
 */
public class CounterUpdater implements Runnable {
    
    // Variável estática e volátil para armazenar o valor do medidor de energia
    private static volatile double gauge = 0;
    
    // Variável estática e volátil para armazenar o valor do medidor de energia atualizado
    private volatile static double gaugeValue = 0;
	
    // Variável estática e volátil para armazenar a quantidade de energia a ser adicionada a cada atualização
    private volatile static  double edition = 2;
    
    // Variável estática e volátil para controlar a execução da thread
    private volatile static boolean running;

    /**
     * Retorna o valor atual do medidor de energia.
     *
     * @return o valor atual do medidor de energia
     */
    public static double getGauge() {
		return gauge;
	}

    /**
     * Define o valor do medidor de energia.
     *
     * @param  gauge - o valor a ser definido para o medidor de energia
     */
	public static void setGauge(double gauge) {
		CounterUpdater.gauge = gauge;
	}

    /**
     * Retorna o valor atualizado do medidor de energia.
     *
     * @return o valor atualizado do medidor de energia
     */
	public static double getGaugeValue() {
		return gaugeValue;
	}

    /**
     * Define o valor atualizado do medidor de energia.
     *
     * @param  gaugeValue - o valor atualizado a ser definido para o medidor de energia
     */
	public static void setGaugeValue(double gaugeValue) {
		CounterUpdater.gaugeValue = gaugeValue;
	}

    /**
     * Retorna a quantidade de energia adicionada a cada atualização do medidor de energia.
     *
     * @return a quantidade de energia adicionada a cada atualização do medidor de energia
     */
	public static double getEdition() {
		return edition;
	}

    /**
     * Define a quantidade de energia a ser adicionada a cada atualização do medidor de energia.
     *
     * @param  edition - a quantidade de energia a ser adicionada a cada atualização do medidor de energia
     */
	public static void setEdition(double edition) {
		CounterUpdater.edition = edition;
	}

    /**
     * Retorna se a thread de atualização do medidor de energia está em execução.
     *
     * @return true se a thread está em execução, false caso contrário
     */
	public static boolean isRunning() {
		return running;
	}

    /**
     * Define se a thread de atualização do medidor de energia deve ser executada.
     *
     * @param  running - true se a thread deve ser executada, false caso contrário
     */
	public static void setRunning(boolean running) {
		CounterUpdater.running = running;
	}

	@Override
    public void run() {
        // Define que a thread está em execução
        running = true;
   
        while (running) {
            // Adiciona a quantidade de energia definida ao medidor de energia
            gauge += edition;
            // Atualiza o valor do medidor de energia atualizado
            gaugeValue = gauge;
            try {
                // Espera um segundo antes de realizar a próxima atualização

                Thread.sleep(1000);
  
            } catch (InterruptedException e) {
                //nada
            }
        }
    }

	public void stop() {
		System.out.println("fim");
		
	}
}