package user;

import java.net.*;
import java.util.Scanner;

import java.io.*;

public class ClientSocket {
	private static boolean serverOn = true;
	private static int porta = 8922;
	private static String path;
	private static Socket socket;
	private static InputStream in;
	private static OutputStream out;

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		socket = new Socket("127.0.0.1", porta); // conecta ao servidor
		in = socket.getInputStream();
		out = socket.getOutputStream();
		while (serverOn) {
			telaLogin();
			serverOn = false;
		}
	}

	public static void telaLogin() throws ClassNotFoundException, IOException, InterruptedException {
		/*
		 * Scanner choiceMScanner = new Scanner(System.in); Scanner opcaoScanner = new
		 * Scanner(System.in);
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("Conexão estabelecida com o servidor.");
			String matriculScanner;
			String loginNumber;

			do {
				System.out.println(
						"--------------------------Bem vindo ao Sistema de Monitoramento de Energia--------------------------");
				System.out.println("Digite sua matricula:"); // a matricula devera ser iniciada pela letra inicial
																// referente ao tipo de usuario 'A'- adm, 'U' - usuario
				matriculScanner = reader.readLine();
				loginNumber = matriculScanner.toUpperCase();
				path = "/login/" + matriculScanner;
				
			} while (loginNumber == null);
			String resposta = sendRequest(path, "GET", socket);
			if(resposta.contains("OK")) {
				
				boolean logado = true;
				while (logado) {
					socket = new Socket("127.0.0.1", porta); // conecta ao servidor
					in = socket.getInputStream();
					out = socket.getOutputStream();
					if (loginNumber.charAt(0) == 'U') {
						
						System.out.println("\n");
						System.out.println("Bem vindo ao menu do usuario");
						System.out.println("(1) - Gerar Fatura");
						System.out.println("(2) - Observar Consumo");
						System.out.println("(3) - logoff do sistema");
						System.out.println("Digite a opcao desejada:");
						matriculScanner = reader.readLine();
						switch (matriculScanner) {

						case "1":
							System.out.println("\n");
							System.out.println("Gerando Fatura");
							path = "/generateinvoice/" + loginNumber;
							sendRequest(path, "GET", socket);
							Thread.sleep(60000);
							break;
						case "2":
							System.out.println("\n");
							System.out.println("Gerando Historico");
							
							path = "/viewhistory/" + loginNumber;
							
							sendRequest(path, "GET", socket);
							Thread.sleep(60000);
							break;

						case "3":
							loginNumber = null;
							break;

						default:
							System.out.println("Opcao nao encontrada do cliente");
							break;

						}
					} else {
						System.out.println("Digite um login Valido");
					}
				}socket.close();
				
				} 
			}
			


	private static String sendRequest(String path, String method, Socket socket) throws IOException {
		String host = "http://localhost:8922";
		String request = method + " " + path + " HTTP/1.1\r\n" + "Host: " + host + "\r\n"
				+ "Connection:keep-alive\r\n\r\n";

		out.write(request.getBytes());
		byte[] buffer = new byte[4096];
		int bytesRead = in.read(buffer);

		String response = new String(buffer, 0, bytesRead);
		String palavra = "ID";
		int posicao = response.indexOf(palavra);
		if (posicao >= 0) {
		    System.out.println(response.substring(posicao));
		}
		out.flush();
	
		return response;

	}

}

/*
 * public static String checkDataLogin(String number, Socket socket) throws
 * IOException { SendReceive.send("/checkData", socket);
 * System.out.println(SendReceive.receive(socket)); SendReceive.send(number,
 * socket); Object response = SendReceive.receive(socket); if
 * (response.equals("Usuario não encontrado")) {
 * System.out.println("Usuario não encontrado"); return null;
 * 
 * } else { System.out.println("Usuario conectado"); return (String) response; }
 * 
 * }
 * 
 * private static void exitSystem(String loginNumber, Socket socket) throws
 * IOException {
 * 
 * SendReceive.send("/exitSystem", socket); SendReceive.send(loginNumber,
 * socket); System.out.println(SendReceive.receive(socket));
 * 
 * }
 * 
 * private static void generateInvoice(String loginNumber, Socket socket) throws
 * ClassNotFoundException, IOException { SendReceive.send("/generateInvoice",
 * socket); SendReceive.send(loginNumber, socket);
 * System.out.println(SendReceive.receive(socket)); }
 * 
 * public static void editKWHUser(String number, Socket socket) throws
 * IOException { Scanner edit = new Scanner(System.in);
 * SendReceive.send("/editAdm", socket);
 * System.out.println(SendReceive.receive(socket)); String matricula =
 * edit.nextLine(); SendReceive.send(matricula, socket);
 * System.out.println(SendReceive.receive(socket)); double valor =
 * edit.nextDouble(); SendReceive.send(valor, socket); edit.close(); }
 * 
 * public static void seeConsumption(String loginNumber, Socket socket) {
 * SendReceive.send("/seeConsumption", socket);
 * System.out.println("Este é o seu consumo:");
 * System.out.println(SendReceive.receive(socket));
 * 
 * } public static void createUser(Socket socket) { Scanner scan = new
 * Scanner(System.in); String name; String function;
 * SendReceive.send("/createUser", socket);
 * System.out.println(SendReceive.receive(socket)); SendReceive.send(name =
 * scan.nextLine(), socket); do {
 * System.out.println(SendReceive.receive(socket)); function = scan.nextLine();
 * }while((function != "1")|| (function != "2")) ; SendReceive.send(function,
 * socket); System.out.println(SendReceive.receive(socket));
 * 
 * 
 * }
 * 
 * }
 */