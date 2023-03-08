package server;

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
		try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
			String inputLine;
			StringBuilder request = new StringBuilder();
			while ((inputLine = in.readLine()) != null && !inputLine.isEmpty()) {
				request.append(inputLine).append("\r\n");
			}
			System.out.println("Received request:\n" + request.toString());
			String[] endpoint = request.toString().split(" ");
			String path = endpoint[1];
			String[] parts = path.split("/");

			if (request.toString().startsWith("GET")) {
				System.out.println("passou por aqui 1");
				if (parts.length == 3 && parts[1].equals("generateinvoice")) {
					String id = parts[2];
					generateInvoice(in, out,id);					
					

				} else if (parts.length == 3 && parts[1].equals("viewhistory")) {
					String id = parts[2];
					viewHistory(in, out, id);

				}
			} else {
				 String medicao = in.readLine();
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

	private static void viewHistory(BufferedReader in, PrintWriter out, String id) throws IOException {
		Measure historic = DaoUser.searchMeasure(id);
		if (historic == null) {
			codeReturn(out, 404, Status.getMessage(404));
		} else {
			String answer = ("ID:"+ id +"/n");
			answer += ("Historico:" + historic.getHistoricListData()+ "/n");
			answer += ("Consumo Total" + historic.getSummedConsumption() +"/n");
			out.print("HTTP/1.1 200 OK\r\n");
			out.print("Content-Type: text/plain \r\n");
			out.print("Content-Length: " + answer.length() + "\r\n");
			out.print("\r\n");
			out.print(answer);
			out.flush();

		}

	}

	private static void addMeasure(BufferedReader in, PrintWriter out, String matricula, double gaugeValue,
			String dataHora) throws IOException {
		int response = DaoUser.addMeasure(matricula, gaugeValue, dataHora);
		String status = Status.getMessage(response);
		codeReturn(out, response, status);
	}

	private static void generateInvoice(BufferedReader in, PrintWriter out, String id) throws IOException {
		Measure invoice = DaoUser.searchMeasure(id);
		if(invoice == null) {
			codeReturn(out, 404, Status.getMessage(404));
			
		}else {
			String answer = ("ID:"+ id +"/n");
		answer += ("Historico:" + invoice.getHistoricListData()+ "/n");
		answer += ("Consumo Total" + invoice.getSummedConsumption()+ "/n");
		answer +=("Valor da Fatura:" + invoice.getValorFatura());
			
			out.print("HTTP/1.1 200 OK\r\n");
			out.print("Content-Type: application/json\r\n");
			out.print("Content-Length: " + answer.toString().length() + "\r\n");
			out.print("\r\n");
			out.print(answer.toString());
			out.flush();
			invoice.setValorFatura(0);
		}

	}

	private static void codeReturn(PrintWriter out, int i, String status) {
		out.println("HTTP/1.1 " + i + " " + status);
		out.println("Content-Type: application/json");
		out.println();
		out.println("{ \"message\": \"" + status + "\" }");
	}

}
