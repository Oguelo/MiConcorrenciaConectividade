package server;

import java.util.ArrayList;

public class DaoAdm {
		private static ArrayList<Adm> listAdmClients;
		private static String registro;
		
		
		
		public DaoAdm() {
			this.listAdmClients = new ArrayList<Adm>();
			
			Adm adm1 = new Adm("a40028922", "Alex");
			Adm adm2 = new Adm("a89223141", "Claudio");
			this.listAdmClients.add(adm2);
			this.listAdmClients.add(adm1);
			
		}
		public static  String addClient(Adm object) {
			
			String confirmation =  (String) search(object.getRegistration());
			if(confirmation != null) {
				do{
					object.setRegistration("A" + Double.toString((Math.random()* 10000)+ 1));
				}while(search("A" + Double.toString((Math.random()* 10000)+ 1)) != null);
				
				listAdmClients.add(object);
			}else {
				listAdmClients.add(object);
			}
			return "O id do cliente é:" + object.getRegistration();
			
			
		}
		public static Object search(String registration) { // Serve para descobrir se um registro existe
			
			try {
				for(Adm object: listAdmClients) {
					if(object.getRegistration() == registration) {
						return object;
					}else {
						return null;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}return null;
			
		
		}
		
	}
