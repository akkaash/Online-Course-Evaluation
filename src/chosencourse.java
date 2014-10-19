

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;

/**
 * Servlet implementation class chosescourse
 */
@WebServlet("/chosescourse")
public class chosencourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public chosencourse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Inside chosen course");
		String course_id=request.getParameter("course_id");
		System.out.println(course_id);
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
       
        
        ResultSet rs=stat.executeQuery("select * from courses where course_id='"+course_id+"'");
        if(rs.next())
        {
        	
        	//System.out.println("Course token :"+token);
        	request.setAttribute("course_id", course_id);
        	String token=rs.getString("COURSE_TOKEN");
        	System.out.println("Course Token :"+token);
        	request.setAttribute("course_token",token);
        	String name=rs.getString("COURSE_NAME");
        	request.setAttribute("course_name", name);
        	String sdate= rs.getString("START_DATE");
        	request.setAttribute("start_date",sdate);
        	String edate=rs.getString("END_DATE");
        	request.setAttribute("end_date", edate);
        	String level=rs.getString("COURSELEVEL");
        	request.setAttribute("course_level",level );
        	String senr=rs.getString("STUDENTS_ENROLLED");
        	request.setAttribute("students_enrolled",senr );
        	String maxe=rs.getString("MAXIMUM_ENROLLMENT");
        	request.setAttribute("maximum_enrollment",maxe );
        	request.getRequestDispatcher("addcourse.jsp").forward(request,response);
        }
      
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
