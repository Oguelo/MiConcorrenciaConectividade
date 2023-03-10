package gauge;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SendReceiveMed {
    private DatagramSocket clientSocket;
    private InetAddress serverAddress;

    public SendReceiveMed() throws UnknownHostException, SocketException {
        clientSocket = new DatagramSocket();
        serverAddress = InetAddress.getByName("127.0.0.1");
    }

    public void sendMessage(String message) throws IOException {
        byte[] sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 8923);
        clientSocket.send(sendPacket);
    }

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
