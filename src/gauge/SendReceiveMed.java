package gauge;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Esta classe foi criada para enviar/receber dados usando o protocolo UDP.
 * 
 * @author Alexjr
 * @version 0.0.1
 */
 
public class SendReceiveMed {
	
	/** Propriedade socket é do tipo DatagramSocket. */
	private DatagramSocket clientSocket;
	
	/** Endereço IPv4 é 127.0.0.1, e a porta de comunicação é 8923. */
	private InetAddress serverAddress;
	
	/**
	 * Construtor da classe SendReceiveMed.
	 * 
	 * @throws UnknownHostException Caso o endereço do servidor seja desconhecido.
	 * @throws SocketException      Caso ocorra uma exceção ao criar o socket.
	 */
	public SendReceiveMed() throws UnknownHostException, SocketException {
		// Inicia o socket.
		clientSocket = new DatagramSocket();
		// Define o endereço do servidor.
		serverAddress = InetAddress.getByName("172.16.103.3");
	}
	
	/**
	 * O método sendMessage é usado para enviar uma mensagem de String para o
	 * endereço IP "127.0.0.1" na porta 8923. Ele também converte a String para um
	 * formato de dados binário, que é enviado para o endereço IP "127.0.0.1" na
	 * porta 8923 usando DatagramPacket, que implementa o protocolo UDP. O método
	 * pode lançar uma exceção de IOException se alguma falha de rede ocorre durante
	 * a transferência.
	 * 
	 * @param message A mensagem a ser enviada.
	 * 
	 * @throws IOException Caso ocorra uma exceção durante a transferência.
	 */
	public void sendMessage(String message) throws IOException {
		// Converte a mensagem em bytes.
		byte[] sendData = message.getBytes();
		// Cria um DatagramPacket com os dados a serem enviados.
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 8923);
		// Envia o pacote pelo socket.
		clientSocket.send(sendPacket);
	}

	/**
	 * O método receiveMessage é usado para receber uma mensagem UDP na porta 8923
	 * do endereço IP "127.0.0.1". Ele converte os dados UDP recebidos para um
	 * formato de String usando o objeto DatagramPacket, que implementa o protocolo
	 * UDP. O método pode lançar uma exceção de IOException se alguma falha de rede
	 * ocorre durante a recepção da mensagem.
	 * 
	 * @return message retorna a mensagem recebida.
	 * 
	 * @throws IOException Caso ocorra uma exceção durante a recepção.
	 */
	public String receiveMessage() throws IOException {
		// Cria um array de bytes para receber os dados.
		byte[] receiveData = new byte[1024];
		// Cria um DatagramPacket para receber os dados.
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		// Recebe o pacote pelo socket.
		clientSocket.receive(receivePacket);
		// Converte os dados recebidos em uma String.
		String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
		return message;
	}

	/**
	 * Método para fechar 
	 */
	
    public void close() {
        clientSocket.close();
    }
}
