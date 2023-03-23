package server;


import java.io.Serializable;
import java.util.ArrayList;
/**
 * Classe para criação e edição de um usuario 
 *  @author Alexjr
 * @version 0.0.1
 */
public class User implements Serializable{

    private String registration;
    private String name;
  
    

    
    public User(String registration, String name) {
        this.registration = registration; // matricula do usuario
        this.name = name; // nome do usuario, talvez eu tire
         // id do medidor de energia pertecente ao usuario

    }
    /**
     * Retorna o registro do cliente
     *
     * @return registration  o registro do usuario
     */
    public String getRegistration() {
        return registration;
    }
    
    
    /**
     * Define o id do usuario
     *
     * @param  registration - o id do usuario
     */
    public void setRegistration(String registration) {
        this.registration = registration;
    }
    /**
     * retorna o nome do user
     *
     * @return o valor atual do medidor de energia
     */
    public String getName() {
        return name;
    }
    /**
     * Define o nome do usuario
     *
     * @param name - o id do usuario
     */
    public void setName(String name) {
        this.name = name;
    }


    public static  String checkDataClient(String data){
        return data;

    }
}