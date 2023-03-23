package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *Essa classe pode ser usada para armazenar e recuperar informações relacionadas a medidas de uso de energia, 
 * e pode ser usada em aplicações que lidam com medição de uso de energia, por exemplo, 
 * em sistema de faturamento de energia ou monitoramento de consumo de energia.
 *  @author Alexjr
 * @version 0.0.1
 */
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
	 /**
     * Retorna a soma das medições do medidor 
     *
     * @return a soma das medições
     */
	public double getSummedConsumption() {
		return summedConsumption;
	}
	/**
     * define a soma do consumo do usuario
     *
     * @param  summedConsumption - a lista que tera as faturas
     */
	
	public void setSummedConsumption(double summedConsumption) {
		this.summedConsumption = summedConsumption;
	}
	 /**
     * Retorna o consumo execessivo ou normal 
     *
     * @return o booleano que ira dizer se o consumo foi alto ou normal
     */
	public boolean getOverConsumption(){
		return overConsumption;
		
	}
	/**
     * Define se o consumo do usuario esta alto ou normal
     *
     * @param  overConsumption - a variavel que ira salvar se o consumo esta alto ou normal 
     */
	public void setoverconsumption(boolean overConsumption) {
		this.overConsumption = overConsumption;
	}
	 /**
     * Retorna a lista de faturas do cliente
     *
     * @return o valor da fatura
     */
	public ArrayList<String> getHistoricList() {
		return historicList;
	}
	 /**
     * Define os valores da fatura do cliente
     *
     * @param   valorMes - a lista que tera as faturas
     */
	public void setHistoricList(ArrayList<String> valorMes) {
		this.historicList = valorMes;
	}
	
	 /**
     * Retorna a lista de medições feitas em todos os meses
     *
     * @return a lista que contem todas as medições do medidor
     */

	public ArrayList<String> getHistoricListData() {
		return historicListData;
	}
	
	
	/**
     *	Define os valores de consumo 
     *
     * @param  historicListData -  guarda os consumos unitarios de cada mes numa lista
     */
	public void setHistoricListData(ArrayList<String> historicListData) {
		this.historicListData = historicListData;
	}
	 /**
     * Retorna o valor da fatura
     *
     * @return valor da fatura somado
     */
	public double getValorFatura() {
		return valorFatura;
	}
	 /**
     * Define o valor da fatura somada
     *
     * @param  valorFatura - define o valor somado da fatura
     */
	public void setValorFatura(double valorFatura) {
		this.valorFatura = valorFatura;
	}
	 /**
     * Retorna o valor que é definido como o mes de medição do medidor.
     *
     * @return o valor do mes da medição do medidor
     */
	public int getFlag() {
		return flag;
	}
	 /**
     * Define o valor  do mês da medição do medidor
     *
     * @param  flag - bandeira para definir o mes de medição do medidor
     */
	public void setFlag(int flag) {
		this.flag = flag;
	}
}
