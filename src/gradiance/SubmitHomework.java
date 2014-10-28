package gradiance;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SubmitHomework
 */
@WebServlet("/SubmitHomework")
public class SubmitHomework extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private Connection connection = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitHomework() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		submitHomeworkDo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		submitHomeworkDo(request, response);
	}

	private void submitHomeworkDo(HttpServletRequest request,
			HttpServletResponse response) {
		
		MyConnectionManager connectionManager = new MyConnectionManager();
		connection = connectionManager.getConnection();
		
		
		
		
	}

}
