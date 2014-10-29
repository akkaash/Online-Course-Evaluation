

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class attmpt
 */
@WebServlet("/attmpt")
public class attmpt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public attmpt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("In post of sttmpt");
		HttpSession cur=request.getSession(true);
		
		Map<String, String[]> requestMap = request.getParameterMap();
		
		int homeworkID = (Integer) cur.getAttribute("currHw");
		System.out.println("+++++"+homeworkID);

		List<String> questionList = new ArrayList<String>();
		questionList = Arrays.asList(requestMap.get("question"));
		
		System.out.println("%%%"+questionList.size());
		
		for(String question: questionList){
			System.out.println("$$$"+question);
			String answerID = requestMap.get(question)[0];
			System.out.println("@@@@"+answerID);
		}
		
		
		/*for(Map.Entry<String, String[]> entry:requestMap.entrySet()){
			String key=entry.getKey();
			String[] val=entry.getValue();
			System.out.println("******KEY*****"+key);
			//System.out.println(k);
			
		}
		*/
		
	
		
        
		
		
	}

}
