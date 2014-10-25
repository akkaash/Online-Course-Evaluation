

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.io.PrintWriter;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class selectcourseprof
 */
@WebServlet("/selectcourseprof")
public class selectcourseprof extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public selectcourseprof() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("In get");
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
        String user="kogan";
        
        ResultSet rs=stat.executeQuery("select course_id,course_name from courses where professor='"+user+"'");
       ArrayList<Course> res=new ArrayList<Course>();
       
       out.println("<html>");
       out.println("<head>");
       out.println("</head>");
       out.println("<body>");
       out.println("<div id=\"selectcourse\" align=\"center\"");
      
       out.println("<h4>Displaying Courses</h4>");
        String cid=null;
        String cname=null;
        if(rs.next()==false)
        	out.println("<h4>No courses to display</h4>");
        else
        {
        do
        {
        	//Course course=new Course();
        	cid=rs.getString("course_id");
        	cname=rs.getString("course_name");
        	System.out.println("cid"+cid);
        	System.out.println("cname"+cname);
        	out.print("<a href=\"courseoptions.jsp?cid="+cid+"\">"+cid+"-"+cname);out.print("</a>");
        	out.println("<br>");
        	/*course.setCid(cid);
        	course.setCname(cname);
        	res.add(course);*/
        }while(rs.next());
		}
        
        out.println("<a href=\"profhome.jsp\">Back</a>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
       /* request.setAttribute("res", res);
        request.setAttribute("cid", cid);
        request.setAttribute("cname", cname);
        request.getRequestDispatcher("selectcourse.jsp").forward(request,response);*/
        
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
		System.out.println("In post");
	}

}
