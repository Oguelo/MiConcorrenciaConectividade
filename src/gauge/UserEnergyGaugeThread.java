package gauge;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserEnergyGaugeThread extends Thread {
	private static String matriculScanner;
	private CounterUpdater updater;

	public UserEnergyGaugeThread(CounterUpdater updater) {
		this.updater = updater;
	}

	public static void main(String[] args) throws InterruptedException, IOException {

		Scanner scanner = new Scanner(System.in);
		// Loop para solicitar a entrada da matrícula do servidor até que ela seja
		// válida
		do {
			System.out.println("Digite o numero de matricula deste medidor:");
			matriculScanner = scanner.nextLine();
		} while (matriculScanner == null);
		boolean validation = authenticator(matriculScanner);
		if (validation == true) {
			CounterUpdater updater = new CounterUpdater();
			UserEnergyGaugeThread gaugeThread = new UserEnergyGaugeThread(updater);
			Thread updaterThread = new Thread(updater);
			updaterThread.start();

			while (validation == true) {

				System.out.print("Digite o novo valor que deseja(ou n para sair) :");
				String input = scanner.nextLine();
				if (input.equalsIgnoreCase("n")) {
					// Quando o usuário digitar "n", o programa é encerrado após um minuto
					validation = false;
					Thread.sleep(60 * 1000);
					updater.stop();
					updaterThread.join();

				} else {
					try {
						CounterUpdater.setEdition(Double.parseDouble(input));
						System.out.println("O valor do medidor foi alterado para:" + input);

					} catch (NumberFormatException e) {
						// Caso a entrada não seja um número, o usuário é solicitado a inserir novamente
						System.out.println("Entrada invalida, Digite novamente:");
					}
				}
			}
		}
		scanner.close();
	}

	// Runnable que envia a leitura do medidor para o servidor em intervalos
	// regulares
	@Override
	public void run() {
		boolean onMed = true;

		while (onMed) {

			try {
				
				Thread.sleep(10000); // Espera 1 minuto
				double gaugeValue = CounterUpdater.getGauge();
				SendReceiveMed sendReceive = new SendReceiveMed();
				// Constrói uma string com os dados da medição
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
				String dataHora = now.format(formatter);
				String medicao = matriculScanner + "," + gaugeValue + "," + dataHora;
				sendReceive.sendMessage(medicao);
				CounterUpdater.setGauge(0); // Reinicia o contador do medidor

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Verifica se a matrícula é válida e se o servidor pode ser autenticado
	public static boolean authenticator(String id) throws IOException {
		SendReceiveMed sendReceive = new SendReceiveMed();
		sendReceive.sendMessage(id);
		String resposta = sendReceive.receiveMessage();
		if (resposta.equals("ok")) {
			return true;
		} else {
			return false;
		}
	}
}
