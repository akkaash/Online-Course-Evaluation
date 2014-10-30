package gradiance;

public class Params {
	
	@Override
	public String toString() {
		return "Params [parameterID=" + parameterID + ", qtnID=" + qtnID
				+ ", parameter=" + parameter + "]";
	}
	private int parameterID;
	private int qtnID;
	private String parameter;
	public Params(int parameterID, int qtnID, String parameter) {
		super();
		this.parameterID = parameterID;
		this.qtnID = qtnID;
		this.parameter = parameter;
	}
	public int getParameterID() {
		return parameterID;
	}
	public void setParameterID(int parameterID) {
		this.parameterID = parameterID;
	}
	public int getQtnID() {
		return qtnID;
	}
	public void setQtnID(int qtnID) {
		this.qtnID = qtnID;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	

}
