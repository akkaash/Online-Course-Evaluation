

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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

import gradiance.QuestionAdd;
/**
 * Servlet implementation class removeqtn
 */
@WebServlet("/removeqtn")
public class removeqtn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public removeqtn() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("In add questions");
		String hid=(String)request.getSession().getAttribute("hid");
		System.out.println("Homework id : "+hid);
		List<QuestionAdd> res=new ArrayList<QuestionAdd>();
		QuestionAdd q;
		try {
			CreateConnection createConnection = new CreateConnection();
			Connection c = createConnection.getConnection();
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Statement stat=c.createStatement();
        ResultSet rs=stat.executeQuery(" select q.question_id as question_id,q.text as text from questions q,qtn_hw h where h.hw_id='"+hid+"' and q.question_id = h.qtn_id");
       
        if(rs.next()==false)
        	out.println("<h4>No questions for this homework.Please add questions.</h4>");
        else
        {
        	
        	String qid,text;
        do
        { 
        	qid=rs.getString("question_id");
        	text=rs.getString("text");
        	System.out.println("QID :"+qid);
        	System.out.println("text :"+text);
        	q=new QuestionAdd();
        	q.setQid(qid);
        	q.setText(text);
        	res.add(q);
        	/*hid=rs.getString("homework_id");
        	System.out.println("hid"+hid);
        	out.print("<a href=\"edithomework.jsp?hid="+hid+"\">HW"+hid);out.print("</a>");
        	out.println("<br>");*/
        	//res.add(hid);
        } while(rs.next());
        
        request.setAttribute("res", res);
        request.getRequestDispatcher("removeqtn.jsp").forward(request,response);
        
        }       
        out.println("<a href=\"hwoptions.jsp\">Back</a>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
		
        c.close();
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
