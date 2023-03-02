package gauge;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import server.DaoAdm;
import server.DaoUser;
//essa classe servira para aplicar as medições e enviar os dados de consumo 

public class UserEnergyGaugeThread implements Runnable {
	private Socket socket;
	

	@Override
	public void run() {
		String userId = "U40028922";
		double initialValue = 0;
		double gauge = 10.0;
		boolean onMed = true;
		Socket socket = null;
		while (onMed) {
			initialValue += gauge;
			try {
				socket = new Socket("127.0.0.1", 8922);
				MedidaConsumo medida = new MedidaConsumo(userId, initialValue);
				SendReceiveGauge.send(medida, socket);

				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
			
			
			
			/*
			 * String idRecebido = (String) SendReceiveGauge.receive(socket);
			 * 
			 * if (idRecebido == userId) { SendReceiveGauge.send("ok", socket); if
			 * (SendReceiveGauge.receive(socket) == "envia") { try { ObjectOutputStream
			 * objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			 * MedidaConsumo medida = new MedidaConsumo(userId, initialValue, gauge);
			 * SendReceiveGauge.send(medida, socket); socket.close(); } catch (IOException
			 * e) { e.printStackTrace(); } }if (SendReceiveGauge.receive(socket)== "muda"){
			 * SendReceiveGauge.send("go", socket); double newGauge = (double)
			 * SendReceiveGauge.receive(socket); gauge = newGauge;
			 * 
			 * 
			 * } }try {
			 * 
			 * Thread.sleep(60000); initialValue += gauge;
			 * 
			 * 
			 * } catch (InterruptedException e) { e.printStackTrace(); }
			 */

		}

	}
}

/*
 * private static DaoUser usuarios = new DaoUser(); private static DaoAdm adm =
 * new DaoAdm(); private static boolean change = true; private static int porta
 * = 8922;
 * 
 * public static void main(String[] args) throws IOException {
 * 
 * ServerSocket server = null; try { server = new ServerSocket(porta);
 * System.out.println("Porta 8922 aberta!");
 * 
 * while (change == true) { Socket monitor = server.accept(); MultiThreadMeasure
 * thread = new MultiThreadMeasure(monitor); new Thread(thread).start();
 * System.out.println("Nova conexão com o monitore " +
 * monitor.getInetAddress().getHostAddress());
 * 
 * } } catch (IOException e) { e.printStackTrace(); } finally { server.close();
 * } } }
 */