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
		while (serverConnect) {
			boolean login = true;
			String checkData = (String) SendReceiveServer.receive(socket); // recebe o registro do cliente
			System.out.println("senha adquirida");
			if (ServerSoc.checkId(checkData, socket) != null) {
				SendReceiveServer.send(checkData, socket);
				while (login == true) {
					String requisition = (String) SendReceiveServer.receive(socket);
					if (requisition.equals("/editAdm")) {
						SendReceiveServer.send("Digite a matricula do medidor que desja editar:", socket);
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
						if(function == "1") {
							String id;
							Double createPass = ((Math.random()* 10000)+ 1);
							id = "A" + Double.toString(createPass);
							Adm objeto = new Adm(id, name);
							String checkOrAdd = DaoAdm.addClient(objeto);
							SendReceiveServer.send(checkOrAdd, socket);
						}else {
							String id;
							Double createPass = ((Math.random()* 10000)+ 1);
							id = "U" + Double.toString(createPass);
							User objeto = new User(id, name, 0, null);
							String checkOrAdd = DaoUser.addClient(objeto);
							SendReceiveServer.send(checkOrAdd, socket);
						}
						
					} else {
						String idString = (String) SendReceiveServer.receive(socket);
						SendReceiveServer.send("Usuario" + idString + "foi deslogado com sucesso", socket);
						try {
							socket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			} else {
				SendReceiveServer.send("Usuario não encontrado", socket);
			}
		}

	}

}
