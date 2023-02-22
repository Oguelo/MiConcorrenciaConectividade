package server;

import java.util.ArrayList;

public class Measure {
    
    private String measureRegistration;
    private double currentConsumption;
    private double summedConsumption = 0; 
    private ArrayList<Count> consumptionHistoric;
    
    
    
    
    public Measure(String measureRegistration, double currentConsumption, double summedConsumption,
            ArrayList<Count> consumptionHistoric) {
        this.measureRegistration = measureRegistration;
        this.currentConsumption = currentConsumption;
        this.summedConsumption = summedConsumption;
        this.consumptionHistoric = consumptionHistoric;
    }
    public String getMeasureRegistration() {
        return measureRegistration;
    }
    public void setMeasureRegistration(String measureRegistration) {
        this.measureRegistration = measureRegistration;
    }
    public double getCurrentConsumption() {
        return currentConsumption;
    }
    public void setCurrentConsumption(double currentConsumption) {
        this.currentConsumption = currentConsumption;
    }
    public double getSummedConsumption() {
        return summedConsumption;
    }
    public void setSummedConsumption(double summedConsumption) {
        this.summedConsumption = summedConsumption;
    }
    public ArrayList<Count> getConsumptionHistoric() {
        return consumptionHistoric;
    }
    public void setConsumptionHistoric(ArrayList<Count> consumptionHistoric) {
        this.consumptionHistoric = consumptionHistoric;
    }

}
