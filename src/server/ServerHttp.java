package server;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class ServerHttp {

	public static void main(String[] args) throws IOException {
		int port = 8922;
		HttpServer server = HttpServer.create(new InetSocketAddress(8922), 0);
		server.createContext("/", new MyHandler());
		server.setExecutor(null); //
		server.start();
	}

	static class MyHandler implements HttpHandler {
		public void handle(HttpExchange exchange) throws IOException {
			String requestMethod = exchange.getRequestMethod();
			if (requestMethod.equalsIgnoreCase("POST")) {
				// Ler o corpo da solicitação
				InputStream is = exchange.getRequestBody();
				ByteArrayOutputStream result = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int length;
				while ((length = is.read(buffer)) != -1) {
					result.write(buffer, 0, length);
				}
				String requestBody = result.toString(StandardCharsets.UTF_8);

				// Analisar o corpo da solicitação JSON
				JSONObject jsonObject = new JSONObject(requestBody);
				String name = jsonObject.getString("name");

				// Criar a resposta com base no corpo da solicitação
				JSONObject responseObject = new JSONObject();
				responseObject.put("message", "Olá " + name + "!");

				// Enviar a resposta
				String response = responseObject.toString();
				exchange.sendResponseHeaders(200, response.length());
				OutputStream os = exchange.getResponseBody();
				os.write(response.getBytes());
				os.close();
			}
		}
	}

}
