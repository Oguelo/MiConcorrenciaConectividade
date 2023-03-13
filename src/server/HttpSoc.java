package server;

import java.io.*;
import java.net.*;

public class HttpSoc {
	static int count = 0;
	static int portUDP = 8923;

	public static void main(String[] args) throws IOException {
		DaoUser dao = new DaoUser();
		int portTCP = 8922;

		boolean on = true;

		try (ServerSocket serverSocket = new ServerSocket(portTCP)) {

			System.out.println("Server listening on port " + portTCP);

			new Thread(() -> {
				
				DatagramSocket measureSocket = null;
				try {
					measureSocket = new DatagramSocket(portUDP);
				} catch (SocketException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				

				System.out.println("passei aqui");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while (on) {

					Thread threadTwo = new Thread(new UdpSocketServer(measureSocket));
					threadTwo.start();

				}
			}).start();

			while (on) {

				Socket clientSocket = serverSocket.accept();

				System.out.println("Client connected: " + clientSocket.getInetAddress());

				Thread thread = new Thread(new ClientConnection(clientSocket));
				thread.start();

			}
		}
	}
}