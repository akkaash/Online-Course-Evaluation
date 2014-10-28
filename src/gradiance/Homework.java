package gradiance;

public class Homework {
	private int homework_id;
	private int chapter_id;
	private String start_date;
	private String end_date;
	private int no_of_retries;
	private int points_correct;
	private int points_incorrect;
	private int score_selection;
	private int difficulty_level_start;
	private int difficulty_level_end;
	private int numberOfQuestions;
	private String courseID;
	
	
	public Homework() {
		super();
	}
	public Homework(int homework_id, int chapter_id, String start_date,
			String end_date, int no_of_retries, int points_correct,
			int points_incorrect, String score_selection,
			int difficulty_level_start, int difficulty_level_end, int numberOfQuestions, String courseID) {
		super();
		this.homework_id = homework_id;
		this.chapter_id = chapter_id;
		this.start_date = start_date;
		this.end_date = end_date;
		this.no_of_retries = no_of_retries;
		this.points_correct = points_correct;
		this.points_incorrect = points_incorrect;
		
		String scoreSelectionString = score_selection;
		if(scoreSelectionString.equalsIgnoreCase("latest attempt")){
			this.score_selection = 0;
		} else if(scoreSelectionString.equalsIgnoreCase("average score")){
			this.score_selection = 1;
		} else if(scoreSelectionString.equalsIgnoreCase("maximum score")){
			this.score_selection = 2;
		}
		
		this.difficulty_level_start = difficulty_level_start;
		this.difficulty_level_end = difficulty_level_end;
		this.numberOfQuestions = numberOfQuestions;
	}
	public String getCourseID() {
		return courseID;
	}
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	public int getHomework_id() {
		return homework_id;
	}
	public void setHomework_id(int homework_id) {
		this.homework_id = homework_id;
	}
	public int getChapter_id() {
		return chapter_id;
	}
	public void setChapter_id(int chapter_id) {
		this.chapter_id = chapter_id;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public int getNo_of_retries() {
		return no_of_retries;
	}
	public void setNo_of_retries(int no_of_retries) {
		this.no_of_retries = no_of_retries;
	}
	public int getPoints_correct() {
		return points_correct;
	}
	public void setPoints_correct(int points_correct) {
		this.points_correct = points_correct;
	}
	public int getPoints_incorrect() {
		return points_incorrect;
	}
	public void setPoints_incorrect(int points_incorrect) {
		this.points_incorrect = points_incorrect;
	}
	public int getScore_selection() {
		return score_selection;
	}
	public void setScore_selection(int score_selection) {
		this.score_selection = score_selection;
	}
	public int getDifficulty_level_start() {
		return difficulty_level_start;
	}
	public void setDifficulty_level_start(int difficulty_level_start) {
		this.difficulty_level_start = difficulty_level_start;
	}
	public int getDifficulty_level_end() {
		return difficulty_level_end;
	}
	public void setDifficulty_level_end(int difficulty_level_end) {
		this.difficulty_level_end = difficulty_level_end;
	}
	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}
	public void setNumberOfQuestions(int numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}
	
	
}
