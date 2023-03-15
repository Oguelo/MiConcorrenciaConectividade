package gauge;

import java.io.IOException;

public class StartMedidor {
	public static void main(String[] args) throws InterruptedException, IOException {
		CounterUpdater updater = new CounterUpdater();
		UserEnergyGaugeThread medidor = new UserEnergyGaugeThread(updater);
		Thread threadMedidor = new Thread(medidor);
		threadMedidor.start();
		UserEnergyGaugeThread.main(args);
	}
}
