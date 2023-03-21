package gauge;

import java.io.IOException;

/**
 * Essa classe é usada para iniciar um programa Java que monitora o consumo de energia de um aparelho.
 * O programa usa duas classes, a primeira é a CounterUpdater, que é responsável por atualizar o contador
 * de consumo de energia, e a segunda é a UserEnergyGaugeThread, que é responsável por monitorar e mostrar
 * o consumo de energia ao usuário.
 * No método main, a classe StartMedidor cria uma instância da classe CounterUpdater e depois cria uma
 * instância da classe UserEnergyGaugeThread, que é responsável pelo monitoramento do consumo de energia
 * e pela visualização dos dados ao usuário.
 * Essas duas instâncias são implementadas em threads diferentes, permitindo que o programa funcione em paralelo.
 * Depois, o programa inicia a thread UserEnergyGaugeThread, que é responsável pelo monitoramento do consumo
 * de energia.
 *  @author Alexjr
 * @version 0.0.1
 */
public class StartMedidor {
	
	/**
	 * Método principal da classe StartMedidor. Cria uma instância de CounterUpdater e UserEnergyGaugeThread,
	 * e inicia a thread UserEnergyGaugeThread para monitorar o consumo de energia.
	 * 
	 * @param args argumentos de linha de comando (não utilizados)
	 * @throws InterruptedException se a thread for interrompida enquanto estiver dormindo
	 * @throws IOException se ocorrer um erro de entrada/saída
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		CounterUpdater updater = new CounterUpdater();
		UserEnergyGaugeThread medidor = new UserEnergyGaugeThread(updater);
		Thread threadMedidor = new Thread(medidor);
		threadMedidor.start();
		UserEnergyGaugeThread.main(args);
	}
}
