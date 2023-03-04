package gauge;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class UserEnergyGaugeThread implements Runnable {
    private Socket socket;
    private static double gauge = 10.0;
    public static void main(String[] args) {
    	boolean on = true;
    	
    	Scanner scanner = new Scanner(System.in);
    	
    	while(on) {
    		System.out.println("O valor atual do medidor é:" + gauge);
    		System.out.print("Digite o novo valor que deseja(ou n para sair) :");
    		String input = scanner.nextLine();
    		if(input.equalsIgnoreCase("n")) {
    			on = false;
    			
    			
    		}else {
    			try {
    				
    				double newData = Double.parseDouble(input);
    				gauge = newData;
    				System.out.println("O valor do medidor foi alterado para:" + gauge);
    				
    				
    			}catch(NumberFormatException e){
    				System.out.println("Entrada invalida, Digite novamente:");
    				
    			}
    		}
    		
    		
    		
    	}scanner.close();
    	
    	
    	
    }
    @Override
    public void run() {
        String userId = "U40028922";
        double bandeira = 0;
       
        boolean onMed = true;
        while (onMed) {
            try {
                if (socket == null || socket.isClosed() || !socket.isConnected()) {
                    socket = new Socket("127.0.0.1", 8922);
                }
                
               
                MedidaConsumo medida = new MedidaConsumo(userId, gauge);
                SendReceiveGauge.send(medida, socket);
                socket.close();
                Thread.sleep(60000);
            } catch (IOException e) {
                e.printStackTrace();
                // Caso haja uma exceção, tente se reconectar em 5 minutos
                try {
                    Thread.sleep(300000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
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