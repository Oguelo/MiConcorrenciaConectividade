package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import gauge.UserEnergyGaugeThread;

public class ServerSoc {
	private static DaoUser usuarios = new DaoUser();
	private static DaoAdm adm = new DaoAdm();
	private static boolean change = true;
	private static int porta = 8922;


	public static void main(String[] args) throws IOException {
	
		ServerSocket server = new ServerSocket(porta);
		
		try {
		    System.out.println("Porta " + porta + " aberta!");

		    while (change) {
		        Socket socket = server.accept();
		        System.out.println("Nova conexão com " + socket.getInetAddress().getHostAddress());

		        // Cria um ObjectInputStream para receber os dados do socket
		       String id = (String) SendReceiveServer.receive(socket);
		       double valorConsumido = (double) SendReceiveServer.receive(socket);
		       String retorno = DaoUser.addMeasure(id, valorConsumido);
		       

		        // Loop infinito para receber dados do socket
		        
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
			User objeto = new User(id, name);
			String checkOrAdd = DaoUser.addClient(objeto);
			return checkOrAdd;
		}
		return null;
		
	}
	
		
		
	
}
