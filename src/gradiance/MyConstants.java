package gradiance;

public final class MyConstants {
	
	public final static String connectionUrl="jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";
	
	public final static String oracleDriverName = "oracle.jdbc.driver.OracleDriver";
	
	public final static String oracleUsername = "semhatr2";
	
	public final static String oraclePassword = "200021589";
	
	public final static String dbName = "semhatr2";
	
	public final static String ATTEMPTS_TABLE_NAME 			= "ATTEMPTS";
	public final static String ANSWERS_TABLE_NAME			= "ANSWERS";
	public final static String CHAPTERS_TABLE_NAME 			= "CHAPTERS";
	public final static String COURSE_TEXTBOOK_TABLE_NAME 	= "COURSE_TEXTBOOT";
	public final static String COURSES_TABLE_NAME 			= "COURSES";
	public final static String ENROLLMENT_TABLE_NAME		= "ENROLLMENT";
	public final static String HOMEWORK_TABLE_NAME			= "HOMEWORK";
	public final static String PROFESORS_TABLE_NAME			= "PROFESSORS";
	public final static String QTN_HW_TABLE_NAME			= "QTN_HW";
	public final static String QUESTIONS_TABLE_NAME			= "QUESTIONS";
	public final static String SECTION_TABLE_NAME			= "SECTION";
	public final static String STUDENTS_TABLE_NAME			= "STUDENTS";
	public final static String TA_TABLE_NAME				= "TA";
	public final static String TEXTBOOT_TABLE_NAME			= "TEXTBOOK";
	public final static String USERS_TABLE_NAME				= "USERS";
	
	public final static String[] ATTEMPTS_COLS = {
		"ATTEMPT_ID",
		"STUDENT_ID",
		"HW_ID",
		"SUBMISSION_DATE",
		"POINTS_SCORED",
	};
	public final static String[] ANSWERS_COLS = {
		"ANSWER_ID",
		"QTN_ID",
		"ANSWER",
		"FLAG",
		"SHORT_EXP",
		"PARAMETER_ID"
	};
	public final static String[] CHAPTERS_COLS = {
		"CHAPTER_ID",
		"CHAPTER_TITLE",
		"TEXTBOOK_ID"
	};
	public final static String[] COURSE_TEXTBOOK_COLS = {
		"COURSE_ID",
		"TEXTBOOK_ID"
	};
	public final static String[] COURSES_COLS = {
		"COURSE_ID",
		"COURSE_TOKEN",
		"COURSE_NAME",
		"START_DATE",
		"END_DATE",
		"COURSELEVEL",
		"STUDENTS_ENROLLED",
		"MAXIMUM_ENROLLMENT",
		"PROFESSOR"
	};
	public final static String[] ENROLLMENT_COLS = {
		"COURSE_ID",
		"USER_ID"
	};
	public final static String[] HOMEWORK_COLS = {
		"HOMEWORK_ID",
		"CHAPTER_ID",
		"START_DATE",
		"END_DATE",
		"NO_OF_RETRIES",
		"POINTS_CORRECT",
		"POINTS_INCORRECT",
		"SCORE_SELECTION",
		"DIFFICULTY_LEVEL_START",
		"DIFFICULTY_LEVEL_END",
		"NO_OF_QUESTIONS"
	};
	public final static String[] PROFESSORS_COLS = {
		"USER_ID",
		"NAME"
	};
	public final static String[] QTN_HW_COLS = {
		"HW_ID",
		"QTN_ID"
	};
	public final static String[] QUESTIONS_COLS = {
		"QUESTION_ID",
		"CHAPTER_ID",
		"TEXT",
		"HINT",
		"DET_EXPLANATION",
		"DIFFICULTY",
		"FLAG"
	};
	public final static String[] SECTION_COLS = {
		"SECTION_ID",
		"SECTION_TITLE",
		"SUPERSECTION_ID",
		"CHAPTER_ID"
	};
	public final static String[] STUDENTS_COLS = {
		"USER_ID",
		"NAME",
		"STUDYLEVEL"
	};
	public final static String[] TA_COLS = {
		"USER_ID",
		"COURSE_ID"
	};
	public final static String[] TEXTBOOK_COLS = {
		"TEXTBOOK_ID",
		"TEXTBOOK_NAME",
		"AUTHORS",
		"ISBN"
	};
	public final static String[] USERS_COLS = {
		"USER_ID",
		"PASSWORD"
	};	
	
}
