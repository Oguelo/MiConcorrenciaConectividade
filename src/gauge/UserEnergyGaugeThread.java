package gauge;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
/**
 * A classe UserEnergyGaugeThread representa um medidor de energia e permite que as medições sejam enviadas para um servidor
 * em intervalos regulares. Esta classe implementa a interface Runnable e é executada como uma thread.
 * 
 * @author Alexjr
 * @version 0.0.1
 */

public class UserEnergyGaugeThread extends Thread {
	private static String matriculScanner;
	private CounterUpdater updater;
	private static int flag = 1;
	private static long tempoDia = 10 * 1000;
	private static long tempoMes = 0;
	public UserEnergyGaugeThread(CounterUpdater updater) {
		this.updater = updater;
	}
	/**
	 * Metodo principal da classe UserEnergyGaugeThread, esta classe ira fazer a conexão do medidor com o sistema e permitira a passagem das medições para
	 * o servidor por meio das threads
	 * 
	 * @param args argumentos de linha de comando (não utilizados)
	 * @throws InterruptedException se a thread for interrompida enquanto estiver dormindo
	 * @throws IOException se ocorrer um erro de entrada/saída
	 */
	public static void main(String[] args) throws InterruptedException, IOException {

		Scanner scanner = new Scanner(System.in);
		boolean validationOne = false;
		// Loop para solicitar a entrada da matrícula do servidor até que ela seja
		// válida
		do {
			matriculScanner = null;
			System.out.println("Digite o numero de matricula deste medidor:");
			matriculScanner = scanner.nextLine();
			if (matriculScanner != null) {
		        validationOne = authenticator(matriculScanner);
		    }
		} while (matriculScanner == null || !validationOne);
		if (validationOne == true) {
			CounterUpdater updater = new CounterUpdater();
			UserEnergyGaugeThread gaugeThread = new UserEnergyGaugeThread(updater);
			Thread updaterThread = new Thread(updater);
			updaterThread.start();

			while (validationOne == true) {
				System.out.println("A atual bandeira de medição:" + CounterUpdater.getEdition());
				System.out.print("Digite o novo valor que deseja(ou n para sair):");
				String input = scanner.nextLine();
				if (input.equalsIgnoreCase("n")) {
					// Quando o usuário digitar "n", o programa é encerrado após um minuto
					validationOne = false;
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
		}else {
			
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
				if(matriculScanner != null) {
					Thread.sleep(tempoDia); // Espera 20seg
					
					double gaugeValue = CounterUpdater.getGauge();
					SendReceiveMed sendReceive = new SendReceiveMed();
					// Constrói uma string com os dados da medição
					LocalDateTime now = LocalDateTime.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
					String dataHora = now.format(formatter);
					String medicao = matriculScanner + "," + gaugeValue + "," + dataHora + "," + flag;
					sendReceive.sendMessage(medicao);
					tempoMes += tempoDia;
					if (tempoMes == 60 * 1000) {// um minuto equivale a um mes
						flag += 1;
						tempoMes = 0;
					}
					if(flag == 12) {
						flag = 1;
					}
				}
				CounterUpdater.setGauge(0); // Reinicia o contador do medidor
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	Verifica se a matrícula é válida e se o servidor pode ser autenticado.
	@param id a matrícula do servidor a ser verificado
	@return true se a matrícula for válida e o servidor puder ser autenticado, false caso contrário
	@throws IOException se ocorrer um erro ao enviar ou receber a mensagem
	*/
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
