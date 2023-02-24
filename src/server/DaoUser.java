package server;

import java.util.ArrayList;

public class DaoUser {
	private static ArrayList<User> listUserClients;

	public DaoUser() {
		DaoUser.listUserClients = new ArrayList<User>();

		User usuarioUm = new User("U40028922", "Alex", 0, null);
		User usuarioDois = new User("U3322424", "jorge", 0, null);
		DaoUser.listUserClients.add(usuarioUm);
		DaoUser.listUserClients.add(usuarioDois);

	}

	public static ArrayList<User> getListUserClients() {
		return listUserClients;
	}

	public static void setListUserClients(ArrayList<User> listUserClients) {
		DaoUser.listUserClients = listUserClients;
	}

	public static String addClient(User object) {
		User confirmation = (User) search(object.getRegistration());
		if (confirmation != null) {
			do {
				object.setRegistration("U" + Double.toString((Math.random() * 10000) + 1));
			} while (search("U" + Double.toString((Math.random() * 10000) + 1)) != null);

			listUserClients.add(object);
		} else {
			listUserClients.add(object);
		}
		return "O id do cliente é:" + object.getRegistration();

	}

	public static Object search(String registration) {

		for (User object : listUserClients) {
			if (object.getRegistration().equals(registration) ) {
				return object;
			} else {
				return null;
			}
		}
		return null;

	}

}
