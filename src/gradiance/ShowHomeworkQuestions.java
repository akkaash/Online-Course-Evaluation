package gradiance;

import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ShowHomeworkQuestions
 */
@WebServlet("/ShowHomeworkQuestions")
public class ShowHomeworkQuestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private int homeworkNumber = -1;
	private Homework homework = null;
		
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowHomeworkQuestions() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		myDo(request, response);
	}

	private void myDo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String[]> requestMap = request.getParameterMap();
    	
    	//int hwNumber = Integer.parseInt(requestMap.get("hwId")[0]);
    	homeworkNumber = 1;
		
    	MyConnectionManager connectionManager = new MyConnectionManager();
		connection = connectionManager.getConnection();
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		myDo(request, response);
	}

}
