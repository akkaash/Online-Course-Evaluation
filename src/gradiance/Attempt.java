package gradiance;

import java.util.Date;

public class Attempt {
	
	private int attemptID;
	private String studentID;
	private int hwID;
	private Date submitDate;
	private int pointsScored;
	public Attempt(int attemptID, String studentID, int hwID, Date submitDate,
			int pointsScored) {
		super();
		this.attemptID = attemptID;
		this.studentID = studentID;
		this.hwID = hwID;
		this.submitDate = submitDate;
		this.pointsScored = pointsScored;
	}
	public int getAttemptID() {
		return attemptID;
	}
	public void setAttemptID(int attemptID) {
		this.attemptID = attemptID;
	}
	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	public int getHwID() {
		return hwID;
	}
	public void setHwID(int hwID) {
		this.hwID = hwID;
	}
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	public int getPointsScored() {
		return pointsScored;
	}
	public void setPointsScored(int pointsScored) {
		this.pointsScored = pointsScored;
	}
	

}
