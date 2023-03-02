package server;

import java.io.Serializable;
import java.util.ArrayList;

public class Measure extends User implements Serializable {

	private double currentConsumption = 100;// valor que sera definido pelo adm mas inicialmente é 100
	private double summedConsumption = 0; // consumo total, sera somado junto com a implementação da lista
	private ArrayList<String> consumptionHistoric;// historico que pegara o consumo atual e salvara periodicamente
	private boolean overconsumption = false;

	public Measure(String registration, String name, double currentConsumption, double summedConsumption,
			ArrayList<String> consumptionHistoric,  boolean overconsumption) {
		super(registration, name);

		this.currentConsumption = currentConsumption;
		this.summedConsumption = summedConsumption;
		this.consumptionHistoric = consumptionHistoric;
		this.overconsumption = overconsumption;
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

	public ArrayList<String> getConsumptionHistoric() {
		return consumptionHistoric;
	}

	public void setConsumptionHistoric(ArrayList<String> consumptionHistoric) {
		this.consumptionHistoric = consumptionHistoric;
	}

	public boolean isOverconsumption() {
		return overconsumption;
	}

	public void setOverconsumption(boolean overconsumption) { // esta class servira para medir se o consumo esta exagerado 
		if((summedConsumption > 0) && (summedConsumption <= currentConsumption)){
			this.overconsumption = true;
		}else {
			this.overconsumption = false;
		}
		
	}
}
