package gauge;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendReceiveGauge {

	public static void send(Object data, Socket socket) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			out.writeObject(data);

		} catch (IOException e) {
			e.getStackTrace();
		}
	}

	public static Object receive(Socket socket) {
		Object data;
		try {

			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			data = input.readObject();
			return data;
		} catch (IOException e) {

			e.printStackTrace();

		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		}
		return null;

	}

}
