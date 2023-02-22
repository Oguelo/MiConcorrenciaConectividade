package server;

public class Count {

    private String data;
    private String hour;
    private double consumption;
    private String measureState;
    private double valour;
    


    public Count(String data, String hour, double consumption, String measureState, Double valour) {
        this.data = data;
        this.hour = hour;
        this.consumption = consumption;
        this.valour = valour;
        this.measureState = measureState;

    }
    
    public String getData() {
        return data;
    }


    public void setData(String data) {
        this.data = data;
    }


    public String getHour() {
        return hour;
    }


    public void setHour(String hour) {
        this.hour = hour;
    }


    public double getConsumption() {
        return consumption;
    }


    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }


    public String getMeasureState() {
        return measureState;
    }


    public void setMeasureState(String measureState) {
        this.measureState = measureState;
    }


    public double getValour() {
        return valour;
    }


    public void setValour(double valour) {
        this.valour = valour;
    }


}