

import gradiance.MyConnectionManager;

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

/**
 * Servlet implementation class previewhomework
 */
@WebServlet("/previewhomework")
public class previewhomework extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public previewhomework() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			MyConnectionManager createConnection = new MyConnectionManager();
			Connection c = createConnection.getConnection();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
        Statement stat=c.createStatement();
        String cid=(String)request.getSession().getAttribute("cid");
        //String cid="CSC540";
        
        System.out.println("Course from session :"+cid);
        out.println("<html>");
        out.println("<head>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div id=\"pviewhomework\" align=\"center\"");
       
        out.println("<h4>Displaying Homeworks</h4>");
        
   //     ResultSet rs=stat.executeQuery("select homework_id from homework where chapter_id in(select chapter_id from chapters where textbook_id in (select textbook_id from course_textbook where course_id='"+cid+"'))");
        ResultSet rs=stat.executeQuery("select homework_id from homework where course_id='"+cid+"' order by homework_id");
        List<String> res=new ArrayList<String>();
       
        String hid=null;
        String cname=null;
        if(rs.next()==false)
        	out.println("<h4>No homeworks to display.</h4>");
        else
        {
        do
        {        	
        	hid=rs.getString("homework_id");
        	System.out.println("hid"+hid);
        	out.print("<a href=\"viewhomework?hid="+hid+"\">HW"+hid);out.print("</a>");
        	out.println("<br>");
        	//res.add(hid);
        } while(rs.next());
        }
       // request.setAttribute("res", res);
       // request.getRequestDispatcher("addquestions.jsp").forward(request,response);
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
