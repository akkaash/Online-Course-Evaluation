package gradiance;

import java.util.*;

public class AttemptDetail {
	
	private int attemptID;
	private HashMap<Integer, HashMap<Integer, Integer>> questionOptionSelectMap;
	public AttemptDetail(int attemptID,
			HashMap<Integer, HashMap<Integer, Integer>> questionOptionSelectMap) {
		super();
		this.attemptID = attemptID;
		this.questionOptionSelectMap = questionOptionSelectMap;
	}
	public int getAttemptID() {
		return attemptID;
	}
	public void setAttemptID(int attemptID) {
		this.attemptID = attemptID;
	}
	public HashMap<Integer, HashMap<Integer, Integer>> getQuestionOptionSelectMap() {
		return questionOptionSelectMap;
	}
	public void setQuestionOptionSelectMap(
			HashMap<Integer, HashMap<Integer, Integer>> questionOptionSelectMap) {
		this.questionOptionSelectMap = questionOptionSelectMap;
	}
	
	
	

}
