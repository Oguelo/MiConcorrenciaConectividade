package gauge;
//Esta classe foi criada para enviar/receber dados usando o protocolo UDP
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SendReceiveMed {
    // Classe SendReceiveMed: Esta classe foi criada para enviar/receber dados usando o protocolo UDP
    // A propriedade socket é do tipo DatagramSocket
    private DatagramSocket clientSocket;
    // O endereço IPv4 é 127.0.0.1, e a porta de comunicação é 8923
    private InetAddress serverAddress;
    // Classe SendReceiveMed: Constructor
    public SendReceiveMed() throws UnknownHostException, SocketException {
        // Inicia o socket
        clientSocket = new DatagramSocket();
        // Define o endereço: 127.0.0.1, onde 127.0.0.1 é o endereço IPv4 do computador
        serverAddress = InetAddress.getByName("127.0.0.1");
    }
    
    
    
	/*
	 * O método sendMessage é usado para enviar uma mensagem de String para o
	 * endereço IP "127.0.0.1" na porta 8923. Ele também converte a String para um
	 * formato de dados binário, que é enviado para o endereço IP "127.0.0.1" na
	 * porta 8923 usando DatagramPacket, que implementa o protocolo UDP. O método
	 * pode lançar uma exceção de IOException se alguma falha de rede ocorre durante
	 * a transferência.
	 */
    public void sendMessage(String message) throws IOException {
    	
        byte[] sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 8923);
        clientSocket.send(sendPacket);
        
    }

	/*
	 * O método receiveMessage é usado para receber uma mensagem UDP na porta 8923
	 * do endereço IP "127.0.0.1". Ele converte os dados UDP recebidos para um
	 * formato de String usando o objeto DatagramPacket, que implementa o protocolo
	 * UDP. O método pode lançar uma exceção de IOException se alguma falha de rede
	 * ocorre durante a recepção da mensagem.
	 */
    public String receiveMessage() throws IOException {
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
        return message;
        
    }

    public void close() {
        clientSocket.close();
    }
}
