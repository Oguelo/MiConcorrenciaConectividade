package server;

import java.util.ArrayList;
import java.util.List;

public class DaoUser {
	private static ArrayList<User> listUserClients;
	private static ArrayList<Measure> listUserContas;

	public DaoUser() {
		DaoUser.listUserClients = new ArrayList<User>();
		DaoUser.listUserContas = new ArrayList<Measure>();
		User usuarioUm = new User("U40028922", "Alex");
		User usuarioDois = new User("U3322424", "jorge");
		Measure usuarioUmConta = new Measure("U40028922", "Alex", 0, false, null, null);
		Measure usuarioDoisConta = new Measure("U3322424", "jorge", 0, false, null, null);
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
		User confirmation = (User) searchUser(object.getRegistration());
		if (confirmation != null) {
			do {
				object.setRegistration("U" + Double.toString((Math.random() * 1000000) + 1));
			} while (searchUser("U" + Double.toString((Math.random() * 1000000) + 1)) != null);

			listUserClients.add(object);
			listUserContas.add((Measure) object);
		} else {
			listUserClients.add(object);
			listUserContas.add((Measure) object);
		}
		return object.getRegistration();

	}

	public static Object searchUser(String registration) {
		for (User object : listUserClients) {
			if (object.getRegistration().equals(registration)) {
				return object;
			}
		}
		return null;
	}

	public static Object searchMeasure(String registration) {
		for (Measure object : listUserContas) {
			if (object.getRegistration().equals(registration)) {
				return object;
			}
		}
		return null;
	}

	public static int addMeasure(String userId, double initialValue, String dataHour) {
		double soma = 0;
		for (Measure object : listUserContas) {
			if (object.getRegistration().equals(userId)) {
					
				if (object.getSummedConsumption()/object.getHistoriclList().size() < initialValue) { // definir se o consumo é exagerado
					object.setoverconsumption(true);

				} else {
					object.setoverconsumption(false);
				}
				object.setSummedConsumption(object.getSummedConsumption() + initialValue); // setar o valor total
				ArrayList<Double> listaNova = object.getHistoriclList();// pegar a lista de valores em double para usar caso necessario e salvar novamente com os novos valores 
				ArrayList<String> listaNovaHistoric = object.getHistoricListData();
				listaNova.add(initialValue);
				object.setHistoricList(listaNova);
				listaNovaHistoric.add("Data/Hora:" + dataHour + "-" +Double.toString(initialValue));
				object.setHistoricListData(listaNovaHistoric);
				return 200;
			}
		}
		return 404;
	}

	public static String generateHistoric(String userId) {
		Measure historic = (Measure) searchMeasure(userId);
		if (historic == null) {
			return "404";
		}else {
			StringBuilder stringList = new StringBuilder();
			for(String num : historic.getHistoricListData()) {
				stringList.append(num).append("\n");
			}
			stringList.append("Soma Total de Consumo:" +historic.getSummedConsumption());
			String retornoLista = stringList.toString();
			return retornoLista;
		}

	}
}
