package gradiance;

public class Answer {
	
	private int answerID;
	private int questionID;
	private String answer;
	private int flag;
	private String shortExplanation;
	private int parameterID;
	public int getAnswerID() {
		return answerID;
	}
	public void setAnswerID(int answerID) {
		this.answerID = answerID;
	}
	public int getQuestionID() {
		return questionID;
	}
	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getShortExplanation() {
		return shortExplanation;
	}
	public void setShortExplanation(String shortExplanation) {
		this.shortExplanation = shortExplanation;
	}
	public int getParameterID() {
		return parameterID;
	}
	public void setParameterID(int parameterID) {
		this.parameterID = parameterID;
	}
	public Answer(int answerID, int questionID, String answer, int flag,
			String shortExplanation, int parameterID) {
		super();
		this.answerID = answerID;
		this.questionID = questionID;
		this.answer = answer;
		this.flag = flag;
		this.shortExplanation = shortExplanation;
		this.parameterID = parameterID;
	}
	
	
}
