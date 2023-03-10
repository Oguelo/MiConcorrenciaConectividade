package server;

import java.io.*;
import java.net.Socket;

public class ClientConnection implements Runnable {
    private Socket clientSocket;
    private DaoUser dao = new DaoUser();

    public ClientConnection(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedInputStream bin = new BufferedInputStream(clientSocket.getInputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(bin));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            StringBuilder request = new StringBuilder();
            String inputLine;

            // Lê a requisição do cliente e a armazena em uma String
            while ((inputLine = in.readLine()) != null && !inputLine.isEmpty()) {
                request.append(inputLine).append("\r\n");
            }
            System.out.println("Received request:\n" + request.toString());

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

                }else if(parts.length == 3 && parts[1].equals("login")) {
                	String id = parts[2];
                	authenticator(out, id);
                }
                else {
                    // Retorna código de erro 400 se o endpoint não é válido
                    codeReturn(out, 400, Status.getMessage(400),Status.getMessage(400));
                }
            } else {
                // Retorna código de erro 400 se a requisição não é do tipo GET
                codeReturn(out, 400, Status.getMessage(400), Status.getMessage(400));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void authenticator(PrintWriter out, String id) {
		Measure idValid = DaoUser.searchMeasure(id);
		 if (idValid == null) {
	            // Retorna código de erro 404 se não há histórico para o usuário com o ID fornecido
	            codeReturn(out, 404, Status.getMessage(404), Status.getMessage(404));
	        } else {
	        	String answer = ("ok");
	        	codeReturn(out, 200, Status.getMessage(200), answer);
	        }
		
	}

	// Método que retorna o histórico de consumo do usuário com o ID fornecido
    private static void viewHistory(PrintWriter out, String id) throws IOException {

        Measure historic = DaoUser.searchMeasure(id);
        if (historic == null) {
            // Retorna código de erro 404 se não há histórico para o usuário com o ID fornecido
            codeReturn(out, 404, Status.getMessage(404), Status.getMessage(404));
        } else {
            // Monta a resposta com o histórico de consumo e o consumo total do usuário com o ID fornecido
            String answer = ("ID:" + id + "\n");
            answer += ("Historico:" + historic.getHistoricListData() + "\n");
            answer += ("Consumo Total" + historic.getSummedConsumption() + "\n");
            // Retorna a resposta com código de sucesso 200
            codeReturn(out, 200, Status.getMessage(200), answer);
        }

    }

    // Método que gera uma fatura para ser paga 


	

	private static void generateInvoice(PrintWriter out, String id) throws IOException {
		Measure invoice = DaoUser.searchMeasure(id);
		if (invoice == null) {
			codeReturn(out, 404, Status.getMessage(404), Status.getMessage(404));

		} else {
			String answer = ("ID:" + id + "/n");
			answer += ("Historico:" + invoice.getHistoricListData() + "\n");
			answer += ("Consumo Total" + invoice.getSummedConsumption() + "\n");
			answer += ("Valor da Fatura:" + invoice.getValorFatura() + "\n");
			codeReturn(out, 200, Status.getMessage(200), answer);
			invoice.setValorFatura(0);
		}

	}

	private static void codeReturn(PrintWriter out, int i, String status, String answer) {
		out.println("HTTP/1.1 " + i + " " + status + "\r\n");
		out.println("Content-Type:text/plain");
		out.print("Content-Length: " + answer.length() + "\r\n");
		out.print("\r\n");
		out.print(answer);
		out.flush();
	}

}
