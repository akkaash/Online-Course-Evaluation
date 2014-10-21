package gradiance;

public class Homework {
	private String homework_id;
	private String chapter_id;
	private String start_date;
	private String end_date;
	private String no_of_retries;
	private String points_correct;
	private String points_incorrect;
	private String score_selection;
	private String difficulty_level_start;
	private String difficulty_level_end;
	
	
	public Homework() {
		// TODO Auto-generated constructor stub
	}
	public Homework(String homework_id, String chapter_id, String start_date,
			String end_date, String no_of_retries, String points_correct,
			String points_incorrect, String score_selection,
			String difficulty_level_start, String difficulty_level_end) {
		super();
		this.homework_id = homework_id;
		this.chapter_id = chapter_id;
		this.start_date = start_date;
		this.end_date = end_date;
		this.no_of_retries = no_of_retries;
		this.points_correct = points_correct;
		this.points_incorrect = points_incorrect;
		this.score_selection = score_selection;
		this.difficulty_level_start = difficulty_level_start;
		this.difficulty_level_end = difficulty_level_end;
	}
	public String getHomework_id() {
		return homework_id;
	}
	public void setHomework_id(String homework_id) {
		this.homework_id = homework_id;
	}
	public String getChapter_id() {
		return chapter_id;
	}
	public void setChapter_id(String chapter_id) {
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
	public String getNo_of_retries() {
		return no_of_retries;
	}
	public void setNo_of_retries(String no_of_retries) {
		this.no_of_retries = no_of_retries;
	}
	public String getPoints_correct() {
		return points_correct;
	}
	public void setPoints_correct(String points_correct) {
		this.points_correct = points_correct;
	}
	public String getPoints_incorrect() {
		return points_incorrect;
	}
	public void setPoints_incorrect(String points_incorrect) {
		this.points_incorrect = points_incorrect;
	}
	public String getScore_selection() {
		return score_selection;
	}
	public void setScore_selection(String score_selection) {
		this.score_selection = score_selection;
	}
	public String getDifficulty_level_start() {
		return difficulty_level_start;
	}
	public void setDifficulty_level_start(String difficulty_level_start) {
		this.difficulty_level_start = difficulty_level_start;
	}
	public String getDifficulty_level_end() {
		return difficulty_level_end;
	}
	public void setDifficulty_level_end(String difficulty_level_end) {
		this.difficulty_level_end = difficulty_level_end;
	}
	
}
