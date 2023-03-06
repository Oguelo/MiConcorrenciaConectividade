package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Measure extends User implements Serializable {
	private double summedConsumption = 0; // consumo total, sera somado junto com a implementação da lista
	private boolean overConsumption = false;
	private ArrayList<Double> historicList;
	private ArrayList<String> historicListData;

	public Measure(String registration, String name,double summedConsumption,  boolean overConsumption, ArrayList<Double> historicList, ArrayList<String> historicListData) {
		super(registration, name);
		this.summedConsumption = summedConsumption;
		this.overConsumption = overConsumption;
		this.historicList = historicList;
		this.historicListData = historicListData;
	}

	public void setOverConsumption(boolean overConsumption) {
		this.overConsumption = overConsumption;
	}

	public double getSummedConsumption() {
		return summedConsumption;
	}

	public void setSummedConsumption(double summedConsumption) {
		this.summedConsumption = summedConsumption;
	}

	public boolean getOverConsumption(){
		return overConsumption;
		
	}
	public void setoverconsumption(boolean overConsumption) {
		this.overConsumption = overConsumption;
	}

	
	
	
	
	public ArrayList<Double> getHistoriclList() {
		return historicList;
	}

	public void setHistoricList(ArrayList<Double> values) {
		this.historicList = values;
	}
	
	

	public ArrayList<String> getHistoricListData() {
		return historicListData;
	}

	public void setHistoricListData(ArrayList<String> historicListData) {
		this.historicListData = historicListData;
	}


}
