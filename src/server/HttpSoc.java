package server;

import java.io.*;
import java.net.*;
/**
 * Essa classe é usada para iniciar um programa Java que monitora o consumo de energia de um aparelho.
 * O programa usa duas classes, a primeira é a CounterUpdater, que é responsável por atualizar o contador
 * de consumo de energia, e a segunda é a UserEnergyGaugeThread, que é responsável por monitorar e mostrar
 * o consumo de energia ao usuário.
 * No método main, a classe StartMedidor cria uma instância da classe CounterUpdater e depois cria uma
 * instância da classe UserEnergyGaugeThread, que é responsável pelo monitoramento do consumo de energia
 * e pela visualização dos dados ao usuário.
 * Essas duas instâncias são implementadas em threads diferentes, permitindo que o programa funcione em paralelo.
 * Depois, o programa inicia a thread UserEnergyGaugeThread, que é responsável pelo monitoramento do consumo
 * de energia.
 *  @author Alexjr
 * @version 0.0.1
 */
public class HttpSoc {
	static int count = 0;
	static int portUDP = 8923;
	
	/*
	 * 
	 * Cria um objeto do tipo DatagramSocket, que é utilizado para enviar
	 * e receber mensagens UDP na porta 8923, e um objeto do tipo ServerSocket, que
	 * permite a criação de uma conexão TCP na porta 8922.
	 * 
	 * 
	 * No loop principal, a classe cria uma thread que espera por várias conexões
	 * UDP na porta 8923. Quando uma conexão UDP é recebida na porta 8923, a class
	 * recebe a conexão e cria uma thread que trata essa conexão.
	 * 
	 * 
	 * Depois disso, a classe cria uma segunda thread que espera por conexões TCP na
	 * porta 8922. Quando uma conexão TCP é recebida, a classe aceita a conexão,
	 * imprime o endereço IP do cliente que estabeleceu a conexão e cria uma thread
	 * para tratar essa conexão.
	 * 
	 * 
	 * Esse loop principal continua executando enquanto a variável 'on' tiver o
	 * valor 'true', indicando que o servidor ainda está ativo aceitando conexões
	 * UDP e TCP.
	 */
	/**
	 * Método principal da classe HttpSoc. Cria uma instância de comunicação UDP e outra de TCP
	 * 
	 * @param args argumentos de linha de comando (não utilizados)
	 * 
	 * @throws IOException se ocorrer um erro de entrada/saída
	 */
	
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