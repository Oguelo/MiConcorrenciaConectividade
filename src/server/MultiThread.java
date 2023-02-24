package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MultiThread implements Runnable {

	private Socket socket;
	private Boolean serverConnect = true;

	public MultiThread(Socket socket) {

		this.socket = socket;

	}

	@Override
	public void run() {
			boolean connection = true;
			while (connection == true) {
				String requisition = (String) SendReceiveServer.receive(socket); // recebe o registro do cliente
				if(requisition.equals("/checkData")) {
					SendReceiveServer.send("esperando id", socket);
					Object checkData = SendReceiveServer.receive(socket);
					System.out.println("senha adquirida");
					
					
					if (ServerSoc.checkId(checkData, socket) != null) {
						System.out.println("Cliente Conectado");
						SendReceiveServer.send(checkData, socket);
						System.out.println("Esperando requisição");
						requisition = (String) SendReceiveServer.receive(socket);
						if (requisition.equals("/editAdm")) {
							SendReceiveServer.send("Digite a matricula do medidor que deseja editar:", socket);
							String measure = (String) SendReceiveServer.receive(socket);
							if (ServerSoc.checkId(measure, socket) == measure) {
								SendReceiveServer.send("Digite qual deverá ser o novo valor:", socket);
								Double data = (Double) SendReceiveServer.receive(socket);

							}

						}
						if (requisition.equals("/generateInvoice")) {

						}
						if (requisition.equals("/seeConsumption")) {

						}
						if (requisition.equals("/createUser")) {
							SendReceiveServer.send("Digite o nome do usuario:", socket);
							String name = (String) SendReceiveServer.receive(socket);
							SendReceiveServer.send("Qual a sua função: 1 - Adm 2 - Usuario ", socket);
							String function = (String) SendReceiveServer.receive(socket);
							ServerSoc.createUser(name, function, socket);
							
							
						}if(requisition.equals("/exitSystem")) {
							String idString = (String) SendReceiveServer.receive(socket);
							SendReceiveServer.send("Usuario:" +  idString  +  "foi deslogado com sucesso", socket);
							connection = false;
						}
						
						else {
							System.out.println("Erro aqui");
							SendReceiveServer.send("opção nao encontrada", socket);
							try {
								socket.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}else {
						SendReceiveServer.send("Usuario não encontrado", socket);
					}
				}
				
				
				
				
		}

	}
}

