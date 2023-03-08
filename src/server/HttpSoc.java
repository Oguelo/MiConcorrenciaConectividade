package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import org.json.JSONObject;

public class HttpSoc {
	public static void main(String[] args) throws IOException {
		DaoUser dao = new DaoUser();
		int port = 8922;
		boolean on = true;
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("Server listening on port " + port);

		while (on) {
			Socket clientSocket = serverSocket.accept();
			System.out.println("Client connected: " + clientSocket.getInetAddress());
			Thread thread = new Thread(new ClientConnection(clientSocket));
			thread.start();

		}
	}
}