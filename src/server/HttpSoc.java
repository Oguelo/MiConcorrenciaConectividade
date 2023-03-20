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
		  DatagramSocket measureSocket = new DatagramSocket(8923);
		try (ServerSocket serverSocket = new ServerSocket(portTCP)) {

			System.out.println("Server listening on port" + portTCP);
			System.out.println(InetAddress.getLocalHost().getHostAddress());

			new Thread(() -> {
			    while (on) {
			     
			        byte[] buffer = new byte[1024];
			        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			        try {
			        
			            measureSocket.receive(packet);
			           
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
			        Thread threadTwo = new Thread(new UdpSocketServer(measureSocket, packet, buffer));
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