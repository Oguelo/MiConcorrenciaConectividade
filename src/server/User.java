package server;


import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{

    private String registration;
    private String name;
    private double consumption;
    private ArrayList<Count> consumptionHistoric;

    
    public User(String registration, String name, double consumption, ArrayList<Count> consumptionHistoric) {
        this.registration = registration; // matricula do usuario
        this.name = name; // nome do usuario, talvez eu tire
        this.consumption = consumption;// consumo atual do usuario
        this.consumptionHistoric = consumptionHistoric; // historico de consumo do usuario
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

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public ArrayList<Count> getConsumptionHistoric() {
        return consumptionHistoric;
    }

    public void setConsumptionHistoric(ArrayList<Count> consumptionHistoric) {
        this.consumptionHistoric = consumptionHistoric;
    }



    public static  String checkDataClient(String data){
        return data;

    }
}