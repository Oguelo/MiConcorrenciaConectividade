package server;

import java.util.ArrayList;

public class DaoUser {
	private static ArrayList<User> listUserClients;
	private static ArrayList<Measure> listUserContas;

	public DaoUser() {
		DaoUser.listUserClients = new ArrayList<User>();
		DaoUser.listUserContas = new ArrayList<Measure>();
		User usuarioUm = new User("U40028922", "Alex");
		User usuarioDois = new User("U3322424", "jorge");
		Measure usuarioUmConta = new Measure("U40028922", "Alex", 100, 0, false);
		Measure usuarioDoisConta = new Measure("U3322424", "jorge", 100, 0, false);
		DaoUser.listUserClients.add(usuarioUm);
		DaoUser.listUserClients.add(usuarioDois);
		DaoUser.listUserContas.add(usuarioUmConta);
		DaoUser.listUserContas.add(usuarioDoisConta);

	}

	public static ArrayList<Measure> getListUserContas() {
		return listUserContas;
	}

	public static void setListUserContas(ArrayList<Measure> listUserContas) {
		DaoUser.listUserContas = listUserContas;
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
		return object.getRegistration();

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

	public static String addMeasure(String userId, double initialValue) {
		
		for(Measure object : listUserContas) {
			if(object.getRegistration().equals(userId)) {
				object.setSummedConsumption(object.getSummedConsumption() + initialValue);
				
				
				return"OK";
				
			}else {
				return "ID nao encontrado Measure";
			}
		}
		return null;
		
		
		
	}



}
