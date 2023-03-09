package server;

import java.net.DatagramSocket;
import java.net.Socket;

public class UdpSocketServer implements Runnable {
	private DatagramSocket measureSocket;
	
	
	
	
	
	public UdpSocketServer(DatagramSocket measureSocket) {
		this.measureSocket = measureSocket;
	}

	public static void main(String args[])throws Exception {
		
	}

	@Override
	public void run() {
		DaoUser dao = new DaoUser();
		
		
	}
}
