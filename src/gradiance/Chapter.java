package gradiance;

public class Chapter {
	
	private int chapterID;
	private String chapterTitle;
	private int textBookID;
	public Chapter(int chapterID, String chapterTitle, int textBookID) {
		super();
		this.chapterID = chapterID;
		this.chapterTitle = chapterTitle;
		this.textBookID = textBookID;
	}
	public int getChapterID() {
		return chapterID;
	}
	public void setChapterID(int chapterID) {
		this.chapterID = chapterID;
	}
	public String getChapterTitle() {
		return chapterTitle;
	}
	public void setChapterTitle(String chapterTitle) {
		this.chapterTitle = chapterTitle;
	}
	public int getTextBookID() {
		return textBookID;
	}
	public void setTextBookID(int textBookID) {
		this.textBookID = textBookID;
	}
	
	

}
