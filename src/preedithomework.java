

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
 * Servlet implementation class preedithomework
 */
@WebServlet("/preedithomework")
public class preedithomework extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public preedithomework() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Displaying homeworks");
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
        String cid=(String)request.getSession().getAttribute("cid");
        //String cid="CSC540";
        
        System.out.println("Course from session :"+cid);
        out.println("<html>");
        out.println("<head>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div id=\"addquestions\" align=\"center\"");
       
        out.println("<h4>Displaying Homeworks</h4>");
        
        ResultSet rs=stat.executeQuery("select homework_id from homework where chapter_id in(select chapter_id from chapters where textbook_id in (select textbook_id from course_textbook where course_id='"+cid+"'))");
       List<String> res=new ArrayList<String>();
       
        String hid=null;
        String cname=null;
        if(rs.next()==false)
        	out.println("<h4>No homeworks to display.Please add homework.</h4>");
        else
        {
        do
        {        	
        	hid=rs.getString("homework_id");
        	System.out.println("hid"+hid);
        	out.print("<a href=\"edithomework.jsp?hid="+hid+"\">HW"+hid);out.print("</a>");
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
		} catch (ClassNotFoundException e) {
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
