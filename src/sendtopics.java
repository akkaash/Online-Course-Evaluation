

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
import gradiance.Topics;
/**
 * Servlet implementation class sendtopics
 */
@WebServlet("/sendtopics")
public class sendtopics extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sendtopics() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Code to send topics associated with course");
		String cid=(String)request.getSession().getAttribute("cid");
		System.out.println("Course id : "+cid);
		List<Topics> res=new ArrayList<Topics>();
		Topics t;
		try {
			CreateConnection createConnection = new CreateConnection();
			Connection c = createConnection.getConnection();
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Statement stat=c.createStatement();
        ResultSet rs=stat.executeQuery("select c.chapter_id as tid,c.chapter_title as text,t.textbook_id as tbid,ct.course_id as cid from chapters c,textbook t,course_textbook ct where ct.course_id='"+cid+"' and ct.textbook_id=t.textbook_id and t.textbook_id=c.textbook_id");
       
        if(rs.next()==false)
        	out.println("<h4>Currently, there are no topics for this course.</h4>");
        else
        {
        	
        	String tid,text;
        do
        { 
        	tid=rs.getString("tid");
        	text=rs.getString("text");
        	System.out.println("QID :"+tid);
        	System.out.println("text :"+text);
        	t=new Topics();
        	t.setTid(tid);
        	t.setText(text);
        	res.add(t);
        	/*hid=rs.getString("homework_id");
        	System.out.println("hid"+hid);
        	out.print("<a href=\"edithomework.jsp?hid="+hid+"\">HW"+hid);out.print("</a>");
        	out.println("<br>");*/
        	//res.add(hid);
        } while(rs.next());
        
        request.setAttribute("res", res);
        request.getRequestDispatcher("addhomework.jsp").forward(request,response);
        
        }       
        out.println("<a href=\"courseoptions.jsp\">Back</a>");
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
