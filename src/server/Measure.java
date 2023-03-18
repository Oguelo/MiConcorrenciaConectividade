package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Measure extends User implements Serializable {
	private double summedConsumption = 0; // consumo total, sera somado junto com a implementação da lista
	private boolean overConsumption = false;
	private double valorFatura = 0;
	private ArrayList<String> historicList;
	private ArrayList<String> historicListData;
	private int flag = 1;
	

	public Measure(String registration, String name,double summedConsumption,  boolean overConsumption, double valorFatura, ArrayList<String> historicList, ArrayList<String> historicListData) {
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

	

	public void setHistoricList(ArrayList<String> valorMes) {
		this.historicList = valorMes;
	}
	
	

	public ArrayList<String> getHistoricListData() {
		return historicListData;
	}

	public void setHistoricListData(ArrayList<String> historicListData) {
		this.historicListData = historicListData;
	}

	public double getValorFatura() {
		return valorFatura;
	}

	public void setValorFatura(double valorFatura) {
		this.valorFatura = valorFatura;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public ArrayList<String> getHistoricList() {
		return historicList;
	}
}
