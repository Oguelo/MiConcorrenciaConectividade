package server;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class ServerHttp {
    private static DaoUser usuarios = new DaoUser();
    private static DaoAdm adm = new DaoAdm();
    static int port = 8922;

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        httpServer.createContext("/", new HttpHandle());
        httpServer.setExecutor(null);
        httpServer.start();
        
    }

    static class HttpHandle implements HttpHandler {
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
                } 
            } else if (requestMethod.equalsIgnoreCase("GET")) {
            	System.out.print("Passou por aq");
                if (endpoint.equals("/generateinvoice")) {
                	 String response = "Hello, world!";
                     exchange.sendResponseHeaders(200, response.getBytes().length);
                     OutputStream os = exchange.getResponseBody();
                     os.write(response.getBytes());
                     os.close();
                	
     
                }
            }
            if (requestMethod.equalsIgnoreCase("PUT")) {
                if (endpoint.equals("/changeconsumption")) {
                    try {
                        String requestBody = requestBodyRead(exchange);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            }
            if (requestMethod.equalsIgnoreCase("PATCH")) {
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
            return out.toString(StandardCharsets.UTF_8);
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
            String type = jsonObject.getString(null);
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
 * try {
 * 
 * String requestBody = requestBodyRead(exchange); String resposta =
 * generateInvoice(exchange, requestBody); if (resposta != null) {
 * returnCode(exchange, 200, resposta); } else { returnCode(exchange, 400,
 * "ID não encontrado"); } } catch (IOException e) {
 * 
 * }
 */

/*
 * // Criar a resposta com base no corpo da solicitação JSONObject
 * responseObject = new JSONObject(); // responseObject.put("message", "Olá " +
 * name + "!");
 * 
 * // Enviar a resposta String response = responseObject.toString();
 * exchange.sendResponseHeaders(200, response.length()); OutputStream os =
 * exchange.getResponseBody(); os.write(response.getBytes()); os.close();
 */