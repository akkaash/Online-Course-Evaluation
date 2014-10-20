

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GenerateHomework
 */
@WebServlet(description = "Generate questions for a given homework", urlPatterns = { "/GenerateHomework" })
public class GenerateHomework extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GenerateHomework() {
        super();
        // TODO Auto-generated constructor stub
    }

    private void generateHomeworkDo(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
    	Map<String, String[]> requestMap = request.getParameterMap();
    	
    	int hwNumber = Integer.parseInt(requestMap.get("hwId")[0]);
    	
    	RandomizedHomeworkGenerator generator = new RandomizedHomeworkGenerator(hwNumber);
    	generator.getHomework();
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		generateHomeworkDo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		generateHomeworkDo(request, response);
	}

}
