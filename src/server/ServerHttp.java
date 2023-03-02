package server;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import gauge.UserEnergyGaugeThread;

public class ServerHttp {
	static int port = 8922;
	static ServerSocket servidorConsumo;
	boolean change = true;

	public static void main(String[] args) throws IOException {
		System.out.println("Porta 8922 aberta!");
		boolean change = true;
		boolean abertura = true;
		ServerHttp server = new ServerHttp();
		HttpServer httpServer = HttpServer.create(new InetSocketAddress(3254), 0);
		httpServer.createContext("/", server.new MyHandler());
		httpServer.setExecutor(null);
		httpServer.start();

		// Criar o servidor de consumo
		servidorConsumo = new ServerSocket(port);
		try {

			System.out.println("Porta 8922 aberta!");
			while (change == true) {
				Socket monitor = servidorConsumo.accept();
				UserEnergyGaugeThread thread = new UserEnergyGaugeThread();
				new Thread(thread).start();
				System.out.println("Nova conexão com o monitore " + monitor.getInetAddress().getHostAddress());

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class MyHandler implements HttpHandler {
		public void handle(HttpExchange exchange) throws IOException {
            String requestMethod = exchange.getRequestMethod();
            
            String endpoint = exchange.getRequestURI().getPath();
            if (requestMethod.equalsIgnoreCase("POST")) {
                // Ler o corpo da solicitação
                if (endpoint.equals("/createuser")) {
                    try {
                        String requestBody = requestBodyRead(exchange);
                        createUser(exchange, requestBody);
                        // Analisar o corpo da solicitação JSON
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (endpoint.equals("/conectsystem")) {
                    // TODO: Implementar lógica para endpoint "/conectsystem"
                }
            } else if (requestMethod.equalsIgnoreCase("GET")) {
                if (endpoint.equals("/generateinvoice")) {
                    try {
                        String requestBody = requestBodyRead(exchange);
                        String resposta = generateInvoice(exchange, requestBody);
                        if (resposta != null) {
                            returnCode(exchange, 200, resposta);
                        } else {
                            returnCode(exchange, 400, "ID não encontrado");
                        }
                    } catch (IOException e){
                    	
                    }
 } 


		}if(requestMethod.equalsIgnoreCase("PUT"))

		{
			if (endpoint.equals("/changeconsumption")) {
				try {
					String requestBody = requestBodyRead(exchange);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}if(requestMethod.equalsIgnoreCase("PATCH"))
		{
			try {
				String body = requestBodyRead(exchange);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

		public static String requestBodyRead(HttpExchange exchange) throws IOException {
			InputStream in = exchange.getRequestBody();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] bufferPost = new byte[1024];
			int length;
			while ((length = in.read(bufferPost)) != -1) {
				out.write(bufferPost, 0, length);
			}
			return out.toString("UTF-8");
		}

		public static void returnCode(HttpExchange exchange, int status, String message) throws IOException {
			exchange.sendResponseHeaders(status, message.length());
			OutputStream responseBody = exchange.getResponseBody();
			responseBody.write(message.getBytes());
			responseBody.close();
		}

		public static void createUser(HttpExchange exchange, String requestBody) throws IOException {
			// Analisar o corpo da solicitação JSON
			JSONObject jsonObject = new JSONObject(requestBody);
			String type = jsonObject.getString("type");
			String name = jsonObject.getString("name");

			
			String message = ServerSoc.createUser(name, type);

					if (message == null) {
				returnCode(exchange, Status.BAD_REQUEST, "Dados incorretos");
			} else {
				returnCode(exchange, Status.CREATED, "Este é o id do Usuario:" + message);
			}

		}

		public static String generateInvoice(HttpExchange exchange, String requestBody)
				throws UnknownHostException, IOException {
			JSONObject jsonObject = new JSONObject(requestBody);
			String id = jsonObject.getString("id");
			String consumption = jsonObject.getString("consumption");
			if (DaoUser.search(id) != null) {
				
				
			} else {
				return null;

			}
			return consumption;
		}

	}
}

/*
 * // Criar a resposta com base no corpo da solicitação JSONObject
 * responseObject = new JSONObject(); // responseObject.put("message", "Olá " +
 * name + "!");
 * 
 * // Enviar a resposta String response = responseObject.toString();
 * exchange.sendResponseHeaders(200, response.length()); OutputStream os =
 * exchange.getResponseBody(); os.write(response.getBytes()); os.close();
 */