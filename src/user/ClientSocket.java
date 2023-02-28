package user;

import java.net.*;
import java.util.Scanner;
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
				loginNumber = checkDataLogin(matriculScanner, socket);

			} while (loginNumber == null);

			while (loginNumber != null) {
				if (loginNumber.charAt(0) == 'A') {
					System.out.println("\n");
					System.out.println("Bem vindo ao menu do Adm");
					System.out.println("(1) - Cadastrar novo usuario");
					System.out.println("(2) - Editar o Consumo de um medidor");
					System.out.println("(3) - logoff do sistema");
					System.out.println("Digite a opcao desejada:");
					String opcao = choiceMScanner.nextLine();
					switch (opcao) {

					case "1":
						createUser(socket);
						break;
					case "2":
						editKWHUser(loginNumber, socket);
						break;

					case "3":
						exitSystem(loginNumber, socket);
						loginNumber = null;

						break;

					default:
						System.out.println("Opcao nao encontrada");
						break;

					}

				} if(loginNumber.charAt(0) == 'U') {
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
					case "2":

					case "3":
						exitSystem(loginNumber, socket);

						loginNumber = null;

					default:
						System.out.println("Opcao nao encontrada do cliente");
						break;

					}

				}

			}

		} catch (UnknownHostException e) {
			System.err.println("Endereço de servidor inválido: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Erro de I/O na comunicação com o servidor: " + e.getMessage());
		}
	}

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

}