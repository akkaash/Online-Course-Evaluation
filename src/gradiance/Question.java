package gradiance;

public class Question {
	
	private int quesionID;
	private int chapterID;
	private String text;
	private String hint;
	private String detailedExplanation;
	private int difficulty;
	private int flag;
	public Question(int quesionID, int chapterID, String text, String hint,
			String detailedExplanation, int difficulty, int flag) {
		super();
		this.quesionID = quesionID;
		this.chapterID = chapterID;
		this.text = text;
		this.hint = hint;
		this.detailedExplanation = detailedExplanation;
		this.difficulty = difficulty;
		this.flag = flag;
	}
	public int getQuesionID() {
		return quesionID;
	}
	public void setQuesionID(int quesionID) {
		this.quesionID = quesionID;
	}
	public int getChapterID() {
		return chapterID;
	}
	public void setChapterID(int chapterID) {
		this.chapterID = chapterID;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	public String getDetailedExplanation() {
		return detailedExplanation;
	}
	public void setDetailedExplanation(String detailedExplanation) {
		this.detailedExplanation = detailedExplanation;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
	

}
