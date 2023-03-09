package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import org.json.JSONObject;

public class HttpSoc {
	public static void main(String[] args) throws IOException {

		int port = 8922;
		int portMeasure = 8923;
		boolean on = true;
		ServerSocket serverSocket = new ServerSocket(port);
		
		System.out.println("Server listening on port " + port);

		while (on) {
			Socket clientSocket = serverSocket.accept();
			DatagramSocket measureSocket = new DatagramSocket(portMeasure);
			System.out.println("Client connected: " + clientSocket.getInetAddress());
			Thread thread = new Thread(new ClientConnection(clientSocket));
			thread.start();
			Thread threadTwo = new Thread(new UdpSocketServer(measureSocket));

		}
	}
}