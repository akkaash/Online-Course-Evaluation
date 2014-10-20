

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;

import com.sun.tools.internal.ws.processor.model.Request;

/**
 * Servlet implementation class selectCourse
 */
@WebServlet(name = "selectCourse", urlPatterns = { "/selectCourse" } )
public class selectCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public ArrayList<String>courseList=new ArrayList<String>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public selectCourse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("view")!=null){
			System.out.println("**************");
			String coName=request.getParameter("course");
			request.setAttribute("courseDetail", coName);
			RequestDispatcher rd=getServletContext().getRequestDispatcher("/viewCourse");
			rd.forward(request, response);
		}
		else
		{
		System.out.println("Here");
		System.out.println(request.getRequestURL());
		String id=request.getParameter("course");
		String conn="jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";
		//String conn="jdbc:oracle:thin:@//remote.eos.ncsu.edu:1521/orcl";
		OracleDriver driver=new OracleDriver();
		PrintWriter out = response.getWriter();
		driver=null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection c=null;
		try {
			c=(OracleConnection)DriverManager.getConnection(conn,"semhatr2","200021589");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Connected to database");
		Statement stat = null;
		try {
			stat = c.createStatement();
			ResultSet rs=stat.executeQuery("select course_name from courses");
			
			System.out.println("Populating data");
			while(rs.next())
	        {
				String cname=rs.getString("course_name");
				courseList.add(cname);
				System.out.println(cname);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//String tp="DBMS";
		System.out.println("count"+courseList.size());
		request.setAttribute("cses", courseList);
		RequestDispatcher rd=getServletContext().getRequestDispatcher("/stud.jsp");
		rd.forward(request, response);
		
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Here");
		System.out.println(request.getRequestURL());
		String courseToken=request.getParameter("ctoken");
		String conn="jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";
		//String conn="jdbc:oracle:thin:@//remote.eos.ncsu.edu:1521/orcl";
		OracleDriver driver=new OracleDriver();
		PrintWriter out = response.getWriter();
		driver=null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection c=null;
		try {
			c=(OracleConnection)DriverManager.getConnection(conn,"semhatr2","200021589");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Connected to database");
		Statement stat = null;
		Statement stat1=null;
		String today = null;
		
		try {
			stat = c.createStatement();
			stat1 = c.createStatement();
			
			ResultSet rs1=stat1.executeQuery("select * from courses where COURSE_TOKEN='"+courseToken+"'");
			if(rs1.next()){
			
				ResultSet rs=stat.executeQuery("select * from courses where COURSE_TOKEN='"+courseToken+"'and END_DATE>sysdate");
				System.out.println("Populating data");
				if(rs.next())
				{
					String cname=rs.getString("course_name");
					String courseStart=rs.getString("START_DATE");
					String courseEnd=rs.getString("END_DATE");
					int studentsEnroll=rs.getInt("STUDENTS_ENROLLED");
					int maxEnroll=rs.getInt("MAXIMUM_ENROLLMENT");
					System.out.println(cname);
					System.out.println(courseStart);
					System.out.println(courseEnd);
					System.out.println(studentsEnroll);
					System.out.println(maxEnroll);
					
					//validateByDate(cname,courseEnd,today);
					validateByEnroll(cname,studentsEnroll,maxEnroll);
				}
				else
					System.out.println("Deadline passed");
			}
			else
				System.out.println("Invalid ID Token");
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//String tp="DBMS";
		System.out.println("count"+courseList.size());
		request.setAttribute("cses", courseList);
		RequestDispatcher rd=getServletContext().getRequestDispatcher("/stud.jsp");
		rd.forward(request, response);
		
			
	}

	public void validateByDate(String name,String dat,String todat)
	{
		System.out.println("Date retriwved "+dat+" and today is "+todat);
		
		
	}
	
	public void validateByEnroll(String name,int curr,int max)
	{
		if(max<=curr)
			System.out.println("Max enrollment reached");
	}
	
	
}
