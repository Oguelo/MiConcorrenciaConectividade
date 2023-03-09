package server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONObject;

public class ClientConnection implements Runnable {
	private Socket clientSocket;

	public ClientConnection(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		DaoUser dao = new DaoUser();
		try (BufferedInputStream in = new BufferedInputStream(clientSocket.getInputStream());
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
			StringBuilder request = new StringBuilder();
					
			
					
			while(in.available() > 0) {
				
				request.append((char)in.read());
				
			}
			System.out.println("Received request:\n" + request.toString());
			String[] endpoint = request.toString().split(" ");
			String path = endpoint[1];
			String[] parts = path.split("/");

			if (request.toString().startsWith("GET")) {
				System.out.println("passou por aqui 1");
				if (parts.length == 3 && parts[1].equals("generateinvoice")) {
					String id = parts[2];
					generateInvoice(in, out, id);

				} else if (parts.length == 3 && parts[1].equals("viewhistory")) {
					String id = parts[2];
					viewHistory(in, out, id);

				}
			} else {
				// vai deixar de existir quando eu fizer o UDP para o servidor e medidor
				String medicao = in.read();
				// Dividir a string em seus componentes
				String[] partes = medicao.split(",");
				String matricula = partes[0];
				double gaugeValue = Double.parseDouble(partes[1]);
				String dataHora = partes[2];
				addMeasure(in, out, matricula, gaugeValue, dataHora);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void viewHistory(BufferedInputStream in, PrintWriter out, String id) throws IOException {
		Measure historic = DaoUser.searchMeasure(id);
		if (historic == null) {
			codeReturn(out, 404, Status.getMessage(404), Status.getMessage(404));
		} else {
			String answer = ("ID:" + id + "\n");
			answer += ("Historico:" + historic.getHistoricListData() + "\n");
			answer += ("Consumo Total" + historic.getSummedConsumption() + "\n");
			codeReturn(out, 200, Status.getMessage(200), answer);

		}

	}

	private static void addMeasure(BufferedInputStream in, PrintWriter out, String matricula, double gaugeValue,
			String dataHora) throws IOException {
		int response = DaoUser.addMeasure(matricula, gaugeValue, dataHora);
		String status = Status.getMessage(response);
	}

	private static void generateInvoice(BufferedInputStream in, PrintWriter out, String id) throws IOException {
		Measure invoice = DaoUser.searchMeasure(id);
		if (invoice == null) {
			codeReturn(out, 404, Status.getMessage(404), Status.getMessage(404));

		} else {
			String answer = ("ID:" + id + "/n");
			answer += ("Historico:" + invoice.getHistoricListData() + "\n");
			answer += ("Consumo Total" + invoice.getSummedConsumption() + "\n");
			answer += ("Valor da Fatura:" + invoice.getValorFatura() + "\n");
			codeReturn(out, 200, Status.getMessage(200), answer);
			invoice.setValorFatura(0);
		}

	}

	private static void codeReturn(PrintWriter out, int i, String status, String answer) {
		out.println("HTTP/1.1 " + i + " " + status + "\r\n");
		out.println("Content-Type:text/plain");
		out.print("Content-Length: " + answer.length() + "\r\n");
		out.print("\r\n");
		out.print(answer);
		out.flush();
	}

}
