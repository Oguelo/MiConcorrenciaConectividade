	package server;
	
	import java.util.ArrayList;
	
	public class DaoUser {
	    private static ArrayList<User> listUserClients;
	    private static ArrayList<Measure> listUserContas;
	    
	    public DaoUser() {
	        listUserClients = new ArrayList<>();
	        listUserContas = new ArrayList<>();
	      
	        listUserClients.add(new User("U40028922", "Alex"));
	        listUserClients.add(new User("U3322424", "jorge"));
	        listUserContas.add(new Measure("U40028922", "Alex", 0, false, 0, new ArrayList<>(), new ArrayList<>()));
	        listUserContas.add(new Measure("U40028923", "Diego", 0, false, 0, new ArrayList<>(), new ArrayList<>()));
	        listUserContas.add(new Measure("U40028924", "Rodrigo", 0, false, 0, new ArrayList<>(), new ArrayList<>()));
	        listUserContas.add(new Measure("U40028925", "Kaio", 0, false, 0, new ArrayList<>(), new ArrayList<>()));
	        listUserContas.add(new Measure("U40028926", "Robson", 0, false, 0, new ArrayList<>(), new ArrayList<>()));
	        listUserContas.add(new Measure("U33224246", "Lucas", 0, false, 0, new ArrayList<>(), new ArrayList<>()));
	        listUserContas.add(new Measure("U33224247", "Bruno", 0, false, 0, new ArrayList<>(), new ArrayList<>()));
	        
	    }
	
	    public static  synchronized ArrayList<Measure> getListUserContas() {
	        return listUserContas;
	    }
	
	    public static  synchronized void setListUserContas(ArrayList<Measure> listUserContas) {
	        DaoUser.listUserContas = listUserContas;
	    }
	
	    public static synchronized  ArrayList<User> getListUserClients() {
	        return listUserClients;
	    }
	
	    public static  synchronized void setListUserClients(ArrayList<User> listUserClients) {
	        DaoUser.listUserClients = listUserClients;
	    }
	
	
	
	    public static synchronized  Measure searchMeasure(String registration) {
	        for (Measure measure : listUserContas) {
	            if (measure.getRegistration().equals(registration)) {
	                return measure;
	            }
	        }
	        return null;
	    }
	
	  
	    
	    
	    
	    public static synchronized int addMeasure(String userId, double initialValue, String dataHour) {
	        Measure measure = searchMeasure(userId);
	        
	        if (measure != null) {
	            double newSummedConsumption = measure.getSummedConsumption() + initialValue;
	            measure.setSummedConsumption(newSummedConsumption);

	            int numHistoricalValues = measure.getHistoriclList().size();
	            if (numHistoricalValues > 0) {
	                double avgConsumption = newSummedConsumption / numHistoricalValues;
	                if (avgConsumption + 100 < initialValue) {
	                    measure.setoverconsumption(true);
	                } else {
	                    measure.setoverconsumption(false);
	                }
	            }

	            
	            measure.getHistoriclList().add(initialValue); //caso eu precise so do valor separado 
	            ArrayList<String> newList = measure.getHistoricListData();  
	            newList.add(String.format("Data/Hora: %s - Medição em kwH: %s - valor unitario(R$): %s", dataHour, initialValue));
	            measure.setHistoricListData(newList);

	            if (listUserContas.contains(measure)) {
	                listUserContas.remove(measure);
	            }
	            listUserContas.add(measure);
	            return 200;
	        }
	        return 404;
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