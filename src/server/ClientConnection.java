package server;

import java.io.*;
import java.net.Socket;

public class ClientConnection implements Runnable {
	private Socket clientSocket;

	public ClientConnection(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {

		while (!clientSocket.isClosed()) {

			BufferedInputStream bin = null;
			try {
				bin = new BufferedInputStream(clientSocket.getInputStream());
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(bin));
			PrintWriter out = null;
			try {
				out = new PrintWriter(clientSocket.getOutputStream(), true);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			StringBuilder request = new StringBuilder();
			String inputLine;

			// Lê a requisição do cliente e a armazena em uma String
			try {
				while ((inputLine = in.readLine()) != null && !inputLine.isEmpty()) {
					request.append(inputLine).append("\r\n");
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(
					"===================================================================================================");
			System.out.println("Received request:\n" + request.toString());
			System.out.println(
					"===================================================================================================");
			String[] endpoint = request.toString().split(" ");
			String path = endpoint[1];
			String[] parts = path.split("/");

			// Verifica se a requisição é do tipo GET e se o endpoint é válido
			if (request.toString().startsWith("GET")) {
				if (parts.length == 3 && parts[1].equals("generateinvoice")) {
					String id = parts[2];
					try {
						// Chama o método generateInvoice e passa o id como parâmetro
						generateInvoice(out, id);
					} catch (IOException e) {
						// Retorna código de erro 500 em caso de exceção
						codeReturn(out, 500, Status.getMessage(500), Status.getMessage(500));
					}

				} else if (parts.length == 3 && parts[1].equals("viewhistory")) {
					String id = parts[2];
					try {
						// Chama o método viewHistory e passa o id como parâmetro
						viewHistory(out, id);
					} catch (IOException e) {
						// Retorna código de erro 500 em caso de exceção
						codeReturn(out, 500, Status.getMessage(500), Status.getMessage(500));
					}

				} 
			} else {
				// Retorna código de erro 400 se a requisição não é do tipo GET
				codeReturn(out, 400, Status.getMessage(400), Status.getMessage(400));
			}

		}
	}

	

	 /**
     * Este metodo retorna o historico do usuario caso ele solicite
     *
     *@param out para envio da resposta
     * @param id identificação do usuario a ser buscada
     */
	private static void viewHistory(PrintWriter out, String id) throws IOException {

		Measure historic = DaoUser.searchMeasure(id);
		if (historic == null) {
			// Retorna código de erro 404 se não há histórico para o usuário com o ID
			// fornecido
			codeReturn(out, 404, Status.getMessage(404), Status.getMessage(404));
		} else {
			// Monta a resposta com o histórico de consumo e o consumo total do usuário com
			// o ID fornecido
			String answer = ("ID:" + id + "\n");
			if (historic.getOverConsumption() == true) {
				answer +=("====================================================================================");
				answer += ("- Seu Consumo Atual esta Alto, diminua o consumo para que sua proxima fatura não venha alta\n");
				answer +=("====================================================================================");
			} else {
				answer +=("====================================================================================");
				answer += ("- Seu Consumo Atual esta controlado, continue assim\n");
				answer +=("====================================================================================");
			}
			answer +=("************************************************************************************");
			answer += ("- Consumo Total:" + historic.getSummedConsumption() + "\n");
			answer +=("************************************************************************************");
			StringBuilder sb = new StringBuilder();
			for (String str : historic.getHistoricListData()) {
				sb.append(str + "\n");
			}
			answer += sb;
			
			// Retorna a resposta com código de sucesso 200
			codeReturn(out, 200, Status.getMessage(200), answer);
		}

	}



	 /**
    * Este metodo retorna a fatura do usuario caso ele solicite
    *
    *@param out para envio da resposta
    * @param id identificação do usuario a ser buscada
    */

	private static void generateInvoice(PrintWriter out, String id) throws IOException {
		Measure invoice = DaoUser.searchMeasure(id);
		
		if (invoice == null) {
			codeReturn(out, 404, Status.getMessage(404), Status.getMessage(404));
		} else {
			String answer = ("ID:" + id + "\n");
			
			StringBuilder sb = new StringBuilder();
			for (String str : invoice.getHistoricList()) {
				sb.append(str + "\n");
			}
			answer += sb;
			codeReturn(out, 200, Status.getMessage(200), answer);
		}

	}
	 /**
	    * Este metodo retorna a fatura do usuario caso ele solicite
	    *
	    *@param out para envio da resposta
	    * @param i codigo de identificação de retorno da requisição
	    * @param status messagem de conclusão da requisição
	    * @param answer resposta da requisição
	    */
	private static void codeReturn(PrintWriter out, int i, String status, String answer) {
		out.println("HTTP/1.1 " + i + " " + status + "\r\n");
		out.println("Content-Type:text/plain");
		out.print("Content-Length: " + answer.length() + "\r\n");
		out.print("\r\n");
		out.print(answer);
		out.flush();
		out.close();
	}

}