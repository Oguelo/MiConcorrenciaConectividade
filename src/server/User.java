package server;


import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{

    private String registration;
    private String name;
  
    

    
    public User(String registration, String name) {
        this.registration = registration; // matricula do usuario
        this.name = name; // nome do usuario, talvez eu tire
         // id do medidor de energia pertecente ao usuario

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


    public static  String checkDataClient(String data){
        return data;

    }
}