package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Measure extends User implements Serializable {

	private double bandeira = 1;// valor que sera definido pelo adm mas inicialmente é 100
	private double summedConsumption = 0; // consumo total, sera somado junto com a implementação da lista
		private boolean overconsumption = false;

	public Measure(String registration, String name, double bandeira, double summedConsumption,  boolean overconsumption) {
		super(registration, name);

		this.bandeira = bandeira;
		this.summedConsumption = summedConsumption;
		this.overconsumption = overconsumption;
	}

	public double getBandeira() {
		return bandeira;
	}

	public void setBandeira(double bandeira) {
		this.bandeira = bandeira;
	}

	public double getSummedConsumption() {
		return summedConsumption;
	}

	public void setSummedConsumption(double summedConsumption) {
		this.summedConsumption = summedConsumption * bandeira;
	}

}
