package server;

import java.util.ArrayList;

public class DaoAdm {
		private static ArrayList<Adm> listAdmClients;
		
		
		
		
		public DaoAdm() {
			this.listAdmClients = new ArrayList<Adm>();
			
			Adm adm1 = new Adm("a40028922", "Alex");
			Adm adm2 = new Adm("a89223141", "Claudio");
			this.listAdmClients.add(adm2);
			this.listAdmClients.add(adm1);
			
		}
		public static boolean  addClient(Adm object) {
			Adm confirmation = (Adm) search(object.getRegistration());
			if(confirmation == null) {
				return false;
			}else {
				listAdmClients.add(object);
			}
			return true;
			
			
		}
		public static Object search(String registration) {
			
			try {
				for(Adm object: listAdmClients) {
					if(object.getRegistration() == registration) {
						return object;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		
		}
		
	}
