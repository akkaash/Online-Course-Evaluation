

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;

/**
 * Servlet implementation class searchaddqtn
 */
@WebServlet("/searchaddqtn")
public class searchaddqtn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public searchaddqtn() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Search and add questions");
		String hid=(String)request.getSession().getAttribute("hid");
		System.out.println("Homework id from session :"+hid);
		List<String> res=new ArrayList<String>();
		try {
			String conn="jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";
			//String conn="jdbc:oracle:thin:@//remote.eos.ncsu.edu:1521/orcl";
			OracleDriver driver=new OracleDriver();
			driver=null;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection c=null;
			c=(OracleConnection)DriverManager.getConnection(conn,"semhatr2","200021589");
			System.out.println("Connected to database");
		
			
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Statement stat=c.createStatement();
        ResultSet rs=stat.executeQuery("select question_id,text from questions where chapter_id in (select chapter_id from homework where homework_id='"+hid+"')");
       
        if(rs.next()==false)
        	out.println("<h4>No questions for this course topic.Please add questions.</h4>");
        else
        {
        	String qid;
        do
        { 
        	qid=rs.getString("question_id");
        	System.out.println("QID :"+qid);
        	res.add(qid);
        	/*hid=rs.getString("homework_id");
        	System.out.println("hid"+hid);
        	out.print("<a href=\"edithomework.jsp?hid="+hid+"\">HW"+hid);out.print("</a>");
        	out.println("<br>");*/
        	//res.add(hid);
        } while(rs.next());
        
        request.setAttribute("res", res);
        request.getRequestDispatcher("addquestions.jsp").forward(request,response);
        
        }       
        out.println("<a href=\"hwoptions.jsp\">Back</a>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
