package server;

import java.util.ArrayList;

public class DaoUser {
	private static ArrayList<User> listUserClients;
	
	
	
	
	public DaoUser() {
		this.listUserClients = new ArrayList<User>();
		
		User usuarioUm = new User("u40028922", "Alex", 0, null);
		User usuarioDois = new User("u3322424", "jorge", 0, null);
		this.listUserClients.add(usuarioUm);
		this.listUserClients.add(usuarioDois);
		
	}
	public static String addClient(User object) {
		User confirmation = (User) search(object.getRegistration());
		if(confirmation == null) {
			return "Usuario já existe" ;
		}else {
			try {
				listUserClients.add(object);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "Usuario cadastrado";
		
		
		
		
	}
	public static Object search(String registration) {
		
		for(User object: listUserClients) {
			if(object.getRegistration() == registration) {
				return object;
			}
		}
		return null;
	
	}
	
	
	
}
