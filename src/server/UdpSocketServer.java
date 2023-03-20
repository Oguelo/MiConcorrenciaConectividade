package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UdpSocketServer implements Runnable {
	private DatagramSocket measureSocket; // socket UDP para a comunicação

	// objeto DAO para gerenciamento de usuários
	byte[] receive = new byte[1024]; // array de bytes para receber dados do cliente
	byte[] send = new byte[1024]; // array de bytes para enviar dados para o cliente

	private DatagramPacket packet;

	private byte[] buffer;

	public UdpSocketServer(DatagramSocket measureSocket, DatagramPacket packet, byte[] buffer) {
		this.measureSocket = measureSocket;
		this.packet = packet;
		this.buffer = buffer;
	}
	

	@Override
	public void run() {

		try {

			String message = new String(packet.getData(), 0, packet.getLength()).trim();
			InetAddress enderecoClient = packet.getAddress(); // obtém o endereço IP do cliente
			int port = packet.getPort(); // obtém o número da porta do cliente
			Pattern pattern = Pattern.compile("U\\d+,\\d+\\.\\d+,\\d{2}/\\d{2}/\\d{4}\\s\\d{2}:\\d{2}:\\d{2},\\d+");

			Matcher match = pattern.matcher(message);
			if (match.find()) {
				// se a mensagem recebida não tiver apenas 1 caractere
				
				String[] partes = message.split(",");
				String matricula = partes[0];
				double gaugeValue = Double.parseDouble(partes[1]);
				String dataHora = partes[2];
				String flag = partes[3];

				DaoUser.addMeasure(matricula, gaugeValue, dataHora, flag); // adiciona uma nova medição para o user
																			// correspondente

			} else if (!match.find()) { // se a mensagem recebida não tiver apenas 1 caractere

				Measure confirmation = DaoUser.searchMeasure(message); // busca a medição do usuário correspondente
				if (confirmation != null) { // se a medição foi encontrada
					send = "ok".getBytes(); // cria uma mensagem de resposta "ok"
					sendData(send, send.length, enderecoClient, port); // envia a resposta para o cliente
				} else {
					send = "not ok".getBytes(); // cria uma mensagem de resposta "usuário não encontrado"
					sendData(send, send.length, enderecoClient, port); // envia a resposta para o cliente
				}

			}
		} catch (Exception e) { // trata qualquer exceção que ocorra durante a execução
			e.printStackTrace();
		}
	}

	// método para enviar dados para o cliente
	private void sendData(byte[] resposta, int length, InetAddress enderecoClient, int port) throws IOException {
		DatagramPacket responsePacket = new DatagramPacket(resposta, resposta.length, enderecoClient, port);
		measureSocket.send(responsePacket); // envia os dados para o cliente
	}
}
