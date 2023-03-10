package gauge;

//a classe CounterUpdater é responsável por atualizar o valor do medidor de energia e exibir o valor atualizado a cada segundo.
public class CounterUpdater implements Runnable {
    private volatile double gauge = 0;
	private volatile double edition = 2;
    private volatile boolean running;

    public double getGauge() {
		return gauge;
	}

	public void setGauge(double gauge) {
		this.gauge = gauge;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public double getEdition() {
		return edition;
	}

    public void setEdition(double edition) {
        this.edition = edition;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            gauge += edition;
            System.out.println("Gauge: " + gauge);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

	public void stop() {
		System.out.println("fim");
		
	}
}