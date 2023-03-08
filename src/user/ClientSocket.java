package user;

import java.net.*;
import java.util.Scanner;

import org.json.JSONObject;

import java.io.*;

public class ClientSocket {
	private static final boolean ServerOn = true;
	private static int porta = 8922;

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		while(ServerOn){
			telaLogin();
		}
			
	}

	public static void telaLogin() throws ClassNotFoundException {
		Scanner choiceMScanner = new Scanner(System.in);
		try {
			
			Socket socket = new Socket("127.0.0.1", porta); // conecta ao servidor
			System.out.println("Conexão estabelecida com o servidor.");
			String matriculScanner;
			String loginNumber;

			do {
				System.out.println(
						"--------------------------Bem vindo ao Sistema de Monitoramento de Energia--------------------------");
				System.out.println("Digite sua matricula:"); // a matricula devera ser iniciada pela letra inicial
																// referente ao tipo de usuario 'A'- adm, 'U' - usuario
				matriculScanner = choiceMScanner.nextLine();
				loginNumber = matriculScanner.toUpperCase();
				

			} while (loginNumber == null);

			if(loginNumber.charAt(0) == 'U') {
				System.out.println("\n");
				System.out.println("Bem vindo ao menu do usuario");
				System.out.println("(1) - Gerar Fatura");
				System.out.println("(2) - Observar Consumo");
				System.out.println("(3) - logoff do sistema");
				System.out.println("Digite a opcao desejada:");
				String opcao = choiceMScanner.nextLine();
				switch (opcao) {

				case "1":
					generateInvoice(loginNumber, socket);
					break;
				case "2":
					viewHistory(loginNumber,socket);
					break;

				case "3":
					exitSystem(loginNumber, socket);
					loginNumber = null;
					break;

				default:
					System.out.println("Opcao nao encontrada do cliente");
					break;

				}

			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void viewHistory(String loginNumber, Socket socket) throws IOException {
		System.out.println("\n");
		System.out.println("Gerando Historico");
		String host = "http://localhost:8922";
		String path = 	"/viewhistory/"+ loginNumber;
		 OutputStream out = socket.getOutputStream();
	        InputStream in = socket.getInputStream();
		String request = "GET " + path + " HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "Connection: close\r\n\r\n";
		out.write(request.getBytes());
		byte[] buffer = new byte[4096];
        int bytesRead = in.read(buffer);
        String response = new String(buffer, 0, bytesRead);

     
        System.out.println(response);

        out.close();
        in.close();
	}

	private static void generateInvoice(String loginNumber, Socket socket) throws IOException {
		System.out.println("\n");
		System.out.println("Gerando Historico");
		String host = "http://localhost:8922";
		String path = 	"/generateinvoice/"+ loginNumber;
		 OutputStream out = socket.getOutputStream();
	        InputStream in = socket.getInputStream();
		String request = "GET " + path + " HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "Connection: close\r\n\r\n";
		out.write(request.getBytes());
		byte[] buffer = new byte[4096];
        int bytesRead = in.read(buffer);
        String response = new String(buffer, 0, bytesRead);

     
        System.out.println(response);

        out.close();
        in.close();
	}

	private static void exitSystem(String loginNumber, Socket socket) throws IOException {
		System.out.println("\n");
		System.out.println("Logoff do sistema");
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println("GET /logoff/" + loginNumber + " HTTP/1.1");
		out.println();
		out.flush();
	}
}


		
/*
	public static String checkDataLogin(String number, Socket socket) throws IOException {
		SendReceive.send("/checkData", socket);	
		System.out.println(SendReceive.receive(socket));
		SendReceive.send(number, socket);
		Object response = SendReceive.receive(socket);
		if (response.equals("Usuario não encontrado")) {
			System.out.println("Usuario não encontrado");
			return null;

		} else {
			System.out.println("Usuario conectado");
			return (String) response;
		}

	}

	private static void exitSystem(String loginNumber, Socket socket) throws IOException {
		
		SendReceive.send("/exitSystem", socket);
		SendReceive.send(loginNumber, socket);
		System.out.println(SendReceive.receive(socket));

	}

	private static void generateInvoice(String loginNumber, Socket socket) throws ClassNotFoundException, IOException {
		SendReceive.send("/generateInvoice", socket);
		SendReceive.send(loginNumber, socket);
		System.out.println(SendReceive.receive(socket));
	}

	public static void editKWHUser(String number, Socket socket) throws IOException {
		Scanner edit = new Scanner(System.in);
		SendReceive.send("/editAdm", socket);
		System.out.println(SendReceive.receive(socket));
		String matricula = edit.nextLine();
		SendReceive.send(matricula, socket);
		System.out.println(SendReceive.receive(socket));
		double valor = edit.nextDouble();
		SendReceive.send(valor, socket);
		edit.close();
	}

	public static void seeConsumption(String loginNumber, Socket socket) {
		SendReceive.send("/seeConsumption", socket);
		System.out.println("Este é o seu consumo:");
		System.out.println(SendReceive.receive(socket));

	}
	public static void createUser(Socket socket) {
		Scanner scan = new Scanner(System.in);
		String name;
		String function;
		SendReceive.send("/createUser", socket);
		System.out.println(SendReceive.receive(socket));
		SendReceive.send(name = scan.nextLine(), socket);
		do {
			System.out.println(SendReceive.receive(socket));
			function = scan.nextLine();			
		}while((function != "1")|| (function != "2")) ;
		SendReceive.send(function, socket);
		System.out.println(SendReceive.receive(socket));
		
		
	}	

}*/