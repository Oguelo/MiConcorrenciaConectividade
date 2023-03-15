package server;

import java.util.ArrayList;

public class DaoUser {
	private static ArrayList<User> listUserClients;
	private static ArrayList<Measure> listUserContas;
	public static double valorFatura = 0;
	public static int flag = 1;
	public static int mesPassado = 0;

	public DaoUser() {
		listUserClients = new ArrayList<>();
		listUserContas = new ArrayList<>();

		listUserClients.add(new User("U40028922", "Alex"));
		listUserClients.add(new User("U3322424", "jorge"));
		listUserContas.add(new Measure("U40028923", "Alex", 0, false, 0, new ArrayList<>(), new ArrayList<>()));
		listUserContas.add(new Measure("U40028924", "Diego", 0, false, 0, new ArrayList<>(), new ArrayList<>()));
		listUserContas.add(new Measure("U40028925", "Rodrigo", 0, false, 0, new ArrayList<>(), new ArrayList<>()));
		listUserContas.add(new Measure("U40028926", "Kaio", 0, false, 0, new ArrayList<>(), new ArrayList<>()));
		listUserContas.add(new Measure("U40028927", "Robson", 0, false, 0, new ArrayList<>(), new ArrayList<>()));
		listUserContas.add(new Measure("U40028928", "Lucas", 0, false, 0, new ArrayList<>(), new ArrayList<>()));
		listUserContas.add(new Measure("U40028929", "Bruno", 0, false, 0, new ArrayList<>(), new ArrayList<>()));

	}

	public static synchronized ArrayList<Measure> getListUserContas() {
		return listUserContas;
	}

	public static synchronized void setListUserContas(ArrayList<Measure> listUserContas) {
		DaoUser.listUserContas = listUserContas;
	}

	public static synchronized ArrayList<User> getListUserClients() {
		return listUserClients;
	}

	public static synchronized void setListUserClients(ArrayList<User> listUserClients) {
		DaoUser.listUserClients = listUserClients;
	}

	public static synchronized Measure searchMeasure(String registration) {
		for (Measure measure : listUserContas) {
			if (measure.getRegistration().equals(registration)) {
				return measure;
			}
		}
		return null;
	}

	public static synchronized int addMeasure(String userId, double initialValue, String dataHour, String data) {
		Measure measure = searchMeasure(userId);

		if (measure != null) {
			double newSummedConsumption = measure.getSummedConsumption() + initialValue;
			measure.setSummedConsumption(newSummedConsumption);

			int numHistoricalValues = measure.getHistoricListData().size();
			if (numHistoricalValues > 0) {
				double avgConsumption = newSummedConsumption / numHistoricalValues;
				if (avgConsumption + 10 < initialValue) {
					measure.setoverconsumption(true);
				} else {
					measure.setoverconsumption(false);
				}
			}
			String valorMes = calendarioMed(data, initialValue);
			double value = initialValue * 0.10;
			if (valorMes != "0") {
				ArrayList<String> listFatura = measure.getHistoriclList();
				listFatura.add(String.format("-Mes:" + flag + " " + "Fatura(R$):" + valorMes));
				measure.setHistoricList(listFatura);
				flag += 1;
				if (flag == 12) {
					flag = 1;
				}
			}

			// caso eu precise so do valor separado
			ArrayList<String> newList = measure.getHistoricListData();
			newList.add(String.format("- Mes:%s - Data/Hora: %s - Medição em kwH: %s - valor unitario(R$): %s", data,
					dataHour, initialValue, value));
			measure.setHistoricListData(newList);

			if (listUserContas.contains(measure)) {
				listUserContas.remove(measure);
			}
			listUserContas.add(measure);
			return 200;
		}
		return 404;
	}

	private static synchronized String calendarioMed(String data, double initialValue) {
		int numero = Integer.parseInt(data);
		String retorno = null;
		switch (data) {

		case "1":
			mesPassado = 1;
			retorno = changeData(numero, initialValue);
			break;
		case "2":
			retorno = changeData(numero, initialValue);
			break;
		case "3":
			retorno = changeData(numero, initialValue);
			break;
		case "4":
			retorno = changeData(numero, initialValue);
			break;
		case "5":
			retorno = changeData(numero, initialValue);
			break;
		case "6":
			retorno = changeData(numero, initialValue);
			break;
		case "7":
			retorno = changeData(numero, initialValue);
			break;
		case "8":
			retorno = changeData(numero, initialValue);
			break;
		case "9":
			retorno = changeData(numero, initialValue);
			break;
		case "10":
			retorno = changeData(numero, initialValue);
			break;
		case "11":
			retorno = changeData(numero, initialValue);
			break;
		case "12":
			retorno = changeData(numero, initialValue);
			break;
		default:
			System.out.println("Mes invalido");
			break;

		}
		return retorno;

	}

	private static String changeData(int mesAtual, double initialValue) {

		if (mesPassado < mesAtual) {
			mesPassado += 1;
			String formatado = String.format("%.2f", valorFatura);
			String valorPassado = String.valueOf(formatado);
			valorFatura = 0.0;
			return String.valueOf(valorPassado);

		} else {
			valorFatura += initialValue * 0.05;
			return "0";
		}

	}

}

/*
 * public static Measure generateHistoric(String userId) { Measure historic =
 * searchMeasure(userId); if (historic == null) { return null; }else {
 * 
 * 
 * StringBuilder stringList = new StringBuilder(); for(String num :
 * historic.getHistoricListData()) { stringList.append(num).append("\n"); }
 * stringList.append("Soma Total de Consumo:" +historic.getSummedConsumption());
 * String retornoLista = stringList.toString();
 * 
 * return historic; }
 * 
 * }
 */

/*
 * public static synchronized String addClient(User user) { if
 * (searchUser(user.getRegistration()) != null) { user.setRegistration("U" +
 * Double.toString((Math.random() * 1000000) + 1)); } listUserClients.add(user);
 * listUserContas.add(new Measure(user.getRegistration(), user.getName(), 0,
 * false, 0, new ArrayList<>(), new ArrayList<>())); return
 * user.getRegistration(); }
 */

/*
 * public static synchronized User searchUser(String registration) { for (User
 * user : listUserClients) { if (user.getRegistration().equals(registration)) {
 * return user; } } return null; }
 */