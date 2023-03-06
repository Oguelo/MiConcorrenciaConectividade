package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import org.json.JSONObject;

public class HttpSoc {
	public static void main(String[] args) throws IOException {
		int port = 8922;
		boolean on = true;
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("Server listening on port " + port);

		while (on) {
			Socket clientSocket = serverSocket.accept();
			System.out.println("Client connected: " + clientSocket.getInetAddress());

			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

			String inputLine;
			StringBuilder request = new StringBuilder();
			while ((inputLine = in.readLine()) != null && !inputLine.isEmpty()) {
				request.append(inputLine).append("\r\n");
			}
			System.out.println("Received request:\n" + request.toString());
			String[] endpoint = request.toString().split(" ");
			String path = endpoint[1];

			if (request.toString().startsWith("GET")) {
				if (path.equals("/generateinvoice")) {
					generateInvoice(in, out);

				} else if (path.equals("/viewhistory")) {
					viewHistory(in, out);

				}
			} else if (request.toString().startsWith("POST")) {
				if (path.equals("/gauge")) {
					addMeasure(in, out);
				}
			} else {

			}
		}
	}

	private static void viewHistory(BufferedReader in, PrintWriter out) throws IOException {
		String bodyJson = "";
		while (in.ready()) {
			bodyJson += (char) in.read();
		}
		JSONObject jsonBody = new JSONObject(bodyJson);
		String id = jsonBody.getString("id");
		Measure historic = DaoUser.searchMeasure(id);
		if(historic == null) {
			codeReturn(out, 404, Status.getMessage(404));
		}else {
			JSONObject answer = new JSONObject();
			answer.put("Id", id);
			answer.put("HistoricList", historic.getHistoricListData());
			answer.put("ConsumoTotal", historic.getSummedConsumption());
			out.print("HTTP/1.1 200 OK\r\n");
			out.print("Content-Type: application/json\r\n");
			out.print("Content-Length: " + answer.toString().length() + "\r\n");
			out.print("\r\n");
			out.print(answer.toString());
			out.flush();

		}
		
	}

	private static void addMeasure(BufferedReader in, PrintWriter out) throws IOException {
		String bodyJson = "";
		while (in.ready()) {
			bodyJson += (char) in.read();
		}
		JSONObject jsonBody = new JSONObject(bodyJson);
		String id = jsonBody.getString("id");
		double measure = jsonBody.getDouble("gauge");
		String dataHora = jsonBody.getString("DataHora");
		int response = DaoUser.addMeasure(id, measure, dataHora);
		String status = Status.getMessage(response);
		codeReturn(out, response, status);
	}

	

	private static void generateInvoice(BufferedReader in, PrintWriter out) throws IOException {
		String bodyJson = "";
		while (in.ready()) {
			bodyJson += (char) in.read();
		}
		JSONObject jsonBody = new JSONObject(bodyJson);
		String id = jsonBody.getString("id");
		
		

	}
	private static void codeReturn(PrintWriter out, int i, String status) {
		out.println("HTTP/1.1 " + i + " " + status);
		out.println("Content-Type: application/json");
		out.println();
		out.println("{ \"message\": \"" + status + "\" }");
	}
}
