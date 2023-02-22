package server;



public class Adm {



    private String registration; 
    private String name;


    
    public Adm(String registration, String name) {
        this.registration = registration;
        this.name = name;
    }



    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	} 
    
    
}
