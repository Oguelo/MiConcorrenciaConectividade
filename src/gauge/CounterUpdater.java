package gauge;

//a classe CounterUpdater é responsável por atualizar o valor do medidor de energia e exibir o valor atualizado a cada segundo.
public class CounterUpdater implements Runnable {
    private static volatile double gauge = 0;
    private volatile static double gaugeValue = 0;
	private volatile static  double edition = 2;
    private volatile static boolean running;

    public static double getGauge() {
		return gauge;
	}

	public static void setGauge(double gauge) {
		CounterUpdater.gauge = gauge;
	}

	public static double getGaugeValue() {
		return gaugeValue;
	}

	public static void setGaugeValue(double gaugeValue) {
		CounterUpdater.gaugeValue = gaugeValue;
	}

	public static double getEdition() {
		return edition;
	}

	public static void setEdition(double edition) {
		CounterUpdater.edition = edition;
	}

	public static boolean isRunning() {
		return running;
	}

	public static void setRunning(boolean running) {
		CounterUpdater.running = running;
	}

	@Override
    public void run() {
        running = true;
   
        while (running) {
            gauge += edition;
            gaugeValue = gauge;
            try {
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