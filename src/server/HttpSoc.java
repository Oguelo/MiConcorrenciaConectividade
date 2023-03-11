package server;

import java.io.*;
import java.net.*;

public class HttpSoc {
	static int count= 0;
	static int portUDP = 8923;
	public static void main(String[] args) throws IOException {

		int portTCP = 8922;
		
		boolean on = true;
	
		try (ServerSocket serverSocket = new ServerSocket(portTCP)) {
			System.out.println("Server listening on port " + portTCP);
			new Thread(() -> {
				while(on) {
					DatagramSocket measureSocket = null;
					try {
						measureSocket = new DatagramSocket(portUDP);
					} catch (SocketException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Thread threadTwo = new Thread(new UdpSocketServer(measureSocket));
					threadTwo.start();
					
					try {
							Thread.sleep(1000000000);
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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