package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerSoc {
	private static DaoUser usuarios = new DaoUser();
	private static DaoAdm adm = new DaoAdm();
	private static boolean change = true;
	private static int porta = 8922;

	public static void main(String[] args) throws IOException {

		ServerSocket server = null;
		try {
			server = new ServerSocket(porta);
			System.out.println("Porta 8922 aberta!");

			while (change == true) {
				Socket monitor = server.accept();
				MultiThread thread = new MultiThread(monitor);
				new Thread(thread).start();
				System.out.println("Nova conexão com o monitore " + monitor.getInetAddress().getHostAddress());

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			server.close();
		}
	}

	public static String checkId(Object checkData, Socket socket) {
		
		String check = (String) checkData;
		if (check.charAt(0) == 'A') {
			DaoAdm.search(check);
			return check;
		}
		if (check.charAt(0) == 'U') {
			 DaoUser.search(check);
			 return check;
		} else {
			SendReceiveServer.send("Usuario não iniciado com 'A' ou 'U'", socket);
		}

		return null;
	
	}
	public static String createUser(String name, String function) {
		if(function.equals("adm") ) {
			String id;
			Double createPass = ((Math.random()* 10000)+ 1);
			id = "A" + Double.toString(createPass);
			Adm objeto = new Adm(id, name);
			String checkOrAdd = DaoAdm.addClient(objeto);
			return checkOrAdd;
		}else if (function.equals("user")){
			String id;
			Double createPass = ((Math.random()* 10000)+ 1);
			id = "U" + Double.toString(createPass);
			User objeto = new User(id, name, 0, null);
			String checkOrAdd = DaoUser.addClient(objeto);
			return checkOrAdd;
		}
		return null;
		
	}
}
