package gauge;

public class MedidaConsumo {
	private String userId;
	private double initialValue;


	public MedidaConsumo(String userId, double initialValue) {
		this.userId = userId;
		this.initialValue = initialValue;

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(double initialValue) {
		this.initialValue = initialValue;
	}

}
