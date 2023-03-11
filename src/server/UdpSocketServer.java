package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UdpSocketServer implements Runnable {
    private DatagramSocket measureSocket; // socket UDP para a comunicação
    
    DaoUser dao = new DaoUser();
    // objeto DAO para gerenciamento de usuários
    byte[] receive = new byte[1024]; // array de bytes para receber dados do cliente
    byte[] send = new byte[1024]; // array de bytes para enviar dados para o cliente

    public UdpSocketServer(DatagramSocket measureSocket) {
        this.measureSocket = measureSocket;
    }

    @Override
    public void run() {

        try {
            while (true) { // loop infinito para receber dados do cliente
                // cria um pacote para receber dados do cliente
                DatagramPacket dataReceive = new DatagramPacket(receive, receive.length);
            
                measureSocket.receive(dataReceive); // recebe os dados do cliente
                // converte os dados recebidos em uma string
          
                String message = new String(dataReceive.getData(), 0, dataReceive.getLength()).trim();
                InetAddress enderecoClient = dataReceive.getAddress(); // obtém o endereço IP do cliente
                int port = dataReceive.getPort(); // obtém o número da porta do cliente
                Pattern pattern = Pattern.compile("U\\d+,\\d+\\.\\d+,\\d{2}/\\d{2}/\\d{4}\\s\\d{2}:\\d{2}:\\d{2}");
                
                Matcher match = pattern.matcher(message);
                System.out.println(match.find());
                System.out.println(message);
                if (match.find()) { // se a mensagem recebida tiver apenas 1 caractere
                	  System.out.print("Adicionando Medição");
                	  String[] partes = message.split(",");
                      String matricula = partes[0];
                      double gaugeValue = Double.parseDouble(partes[1]);
                      String dataHora = partes[2];
                      // adiciona uma nova medição para o usuário correspondente
                      DaoUser.addMeasure(matricula, gaugeValue, dataHora);


                } else { // se a mensagem recebida não tiver apenas 1 caractere
                    // divide a string recebida em suas partes
           
                	 Measure confirmation = DaoUser.searchMeasure(message); // busca a medição do usuário correspondente
                     if (confirmation != null) { // se a medição foi encontrada
                     	send = "ok".getBytes(); // cria uma mensagem de resposta "ok"
                         sendData(send, send.length, enderecoClient, port); // envia a resposta para o cliente
                     } else {
                        send = "not ok".getBytes(); // cria uma mensagem de resposta "usuário não encontrado"
                         sendData(send, send.length, enderecoClient, port); // envia a resposta para o cliente
                     }
                  
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
