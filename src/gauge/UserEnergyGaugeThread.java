package gauge;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.json.JSONObject;

public class UserEnergyGaugeThread implements Runnable {
	private static double gauge = 10.0;

	public static void main(String[] args) {
		boolean on = true;

		Scanner scanner = new Scanner(System.in);

		while (on) {
			System.out.println("O valor atual do medidor é:" + gauge);
			System.out.print("Digite o novo valor que deseja(ou n para sair) :");
			String input = scanner.nextLine();
			if (input.equalsIgnoreCase("n")) {
				on = false;

			} else {
				try {

					double newData = Double.parseDouble(input);
					gauge = newData;
					System.out.println("O valor do medidor foi alterado para:" + gauge);

				} catch (NumberFormatException e) {
					System.out.println("Entrada invalida, Digite novamente:");

				}
			}

		}
		scanner.close();

	}

	@Override
	public void run() {
		String userId = "U40028922";

		boolean onMed = true;
		while (onMed) {

			try {
				// Cria uma nova conexão HTTP POST
				URL url = new URL("http://localhost:8922/gauge");
				HttpURLConnection requisition = (HttpURLConnection) url.openConnection();
				requisition.setRequestMethod("POST");
				requisition.setRequestProperty("Content-Type", "application/json");
				requisition.setDoOutput(true);
				LocalDateTime now = LocalDateTime.now(); // pega a data e hora atual
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); // define o formato da data e hora
		        String dataHora = now.format(formatter); 
				// Escreve o corpo da requisição JSON no OutputStream da conexão
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", userId);
				jsonObject.put("gauge",gauge);
				jsonObject.put("DataHora", dataHora);
			

				String jsonBody = jsonObject.toString();
				OutputStreamWriter writer = new OutputStreamWriter(requisition.getOutputStream(), "UTF-8");
				writer.write(jsonBody);
				writer.close();

				int codeReturn = requisition.getResponseCode();
				System.out.println("retorno: " + codeReturn);

				requisition.disconnect();
				Thread.sleep(100000);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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