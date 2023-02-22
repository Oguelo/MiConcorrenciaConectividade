package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerSoc {
	private ArrayList<User> userList;
	private ArrayList<Adm> admList;
	private ArrayList<Measure> measureList;

	private static boolean change = true;
	private static int porta = 8922;

	public static void main(String[] args) throws IOException {

		ServerSocket server = null;
		try {
			server = new ServerSocket(porta);
			System.out.println("Porta 8922 aberta!");

			while (change == true) {
				Socket client = server.accept();
				MultiThread thread = new MultiThread(client);
				new Thread(thread).start();
				System.out.println("Nova conexão com o cliente " + client.getInetAddress().getHostAddress());

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			server.close();
		}
	}

	public static String checkId(Object checkData, Socket socket) {
		String registration = (String) checkData;
		String check;
		if (registration.charAt(0) == 'A') {
			check = (String) DaoAdm.search(registration);
			return check;
		}
		if (registration.charAt(0) == 'U') {
			 check = (String) DaoUser.search(registration);
			 return check;
		} else {
			SendReceiveServer.send("Usuario não iniciado com 'a' ou 'u'", socket);
		}

		return null;
	
	}
	public static void createUser(Socket socket) {
	}
}
