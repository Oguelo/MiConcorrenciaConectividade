package gauge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserEnergyGaugeThread implements Runnable {
	private static String matriculScanner;

	public static void main(String[] args) throws InterruptedException {
		CounterUpdater updater = new CounterUpdater();
		Thread updaterThread = new Thread(updater);
		updaterThread.start();
		boolean on = true;

		Scanner scanner = new Scanner(System.in);

		System.out.println("Digite o numero de matricula deste Servidor:");
		matriculScanner = scanner.nextLine();

		while (on) {
			System.out.println("O valor atual do medidor é:" + updater.getEdition());
			System.out.print("Digite o novo valor que deseja(ou n para sair) :");
			String input = scanner.nextLine();
			if (input.equalsIgnoreCase("n")) {
				on = false;
				Thread.sleep(60 * 1000);
				updater.stop();
				updaterThread.join();

			} else {
				try {

					updater.setEdition(Double.parseDouble(input));

					System.out.println("O valor do medidor foi alterado para:" + input);

				} catch (NumberFormatException e) {
					System.out.println("Entrada invalida, Digite novamente:");

				}
			}

		}
		scanner.close();

	}

	@Override
	public void run() {
	    boolean onMed = true;
	    CounterUpdater updater = new CounterUpdater();
	    while (onMed) {
	        
	        try {
	            double gaugeValue = updater.getGauge();
	            
	            // Cria um socket cliente e se conecta ao servidor
	            Socket socket = new Socket("localhost", 8922);
	            OutputStream output = socket.getOutputStream();
	            PrintWriter writer = new PrintWriter(output, true);

	            // Constrói uma string com os dados da medição
	            LocalDateTime now = LocalDateTime.now();
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	            String dataHora = now.format(formatter);
	            String medicao = matriculScanner + "," + gaugeValue + "," + dataHora;

	            // Envia a string para o servidor
	            writer.println(medicao);
	            //recebe resposta
	            InputStream inputStream = socket.getInputStream();
	            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	            String response = reader.readLine();
	            System.out.println("Resposta do servidor: " + response);

	     
	            // Fecha o socket
	            writer.close();
	            output.close();
	            socket.close();

	            updater.setGauge(0);
	            Thread.sleep(60000);
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}

}

/*
 * String idRecebido = (String) SendReceiveGauge.receive(socket);
 * 
 * if (idRecebido == userId) { SendReceiveGauge.send("ok", socket); if
 * (SendReceiveGauge.receive(socket) == "envia") { try { ObjectOutputStream
 * objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
 * MedidaConsumo medida = new MedidaConsumo(userId, initialValue, gauge);
 * SendReceiveGauge.send(medida, socket); socket.close(); } catch (IOException
 * e) { e.printStackTrace(); } }if (SendReceiveGauge.receive(socket)== "muda"){
 * SendReceiveGauge.send("go", socket); double newGauge = (double)
 * SendReceiveGauge.receive(socket); gauge = newGauge;
 * 
 * 
 * } }try {
 * 
 * Thread.sleep(60000); initialValue += gauge;
 * 
 * 
 * } catch (InterruptedException e) { e.printStackTrace(); }
 */

/*
 * private static DaoUser usuarios = new DaoUser(); private static DaoAdm adm =
 * new DaoAdm(); private static boolean change = true; private static int porta
 * = 8922;
 * 
 * public static void main(String[] args) throws IOException {
 * 
 * ServerSocket server = null; try { server = new ServerSocket(porta);
 * System.out.println("Porta 8922 aberta!");
 * 
 * while (change == true) { Socket monitor = server.accept(); MultiThreadMeasure
 * thread = new MultiThreadMeasure(monitor); new Thread(thread).start();
 * System.out.println("Nova conexão com o monitore " +
 * monitor.getInetAddress().getHostAddress());
 * 
 * } } catch (IOException e) { e.printStackTrace(); } finally { server.close();
 * } } }
 */