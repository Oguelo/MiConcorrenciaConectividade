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
			String endpoint = exchange.getRequestURI().getPath();
			if (requestMethod.equalsIgnoreCase("POST")) {
				// Ler o corpo da solicitação
				if (endpoint.equals("/createuser")) {
					try {
						String body = requestBodyRead(exchange);

						// Analisar o corpo da solicitação JSON
						JSONObject jsonObject = new JSONObject(body);
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
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

					}
				}else if(endpoint.equals("/conectsystem"))
			}
			if (requestMethod.equalsIgnoreCase("GET")) {
				try {
					String body = requestBodyRead(exchange);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (requestMethod.equalsIgnoreCase("PUT")) {
				try {
					String body = requestBodyRead(exchange);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
}
