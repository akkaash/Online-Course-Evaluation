

import gradiance.MyConnectionManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;

/**
 * Servlet implementation class Home
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public HashMap<String,String>courseList=new HashMap<String,String>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("submitDetails")!=null){
			System.out.println("**************");
			//String coName=request.getParameter("course");
			String user=request.getParameter("username");
	        String passwo=request.getParameter("pass");
	        String role=request.getParameter("role");
	        String firstName=request.getParameter("fname");
	        String lastName=request.getParameter("lname");
	        String fullName=firstName+" "+lastName;
	        String studyLevel=request.getParameter("lev");
	        String coName=request.getParameter("course");
			request.setAttribute("username",user );
			request.setAttribute("pass",passwo );
			request.setAttribute("role",role );
			request.setAttribute("fname",firstName );
			request.setAttribute("lname",lastName );
			request.setAttribute("lev",studyLevel );
			request.setAttribute("course",coName );
			System.out.println("name "+user+" pass "+passwo+" role "+role+" fullname "+fullName+" lev "+studyLevel+" course "+coName);
			RequestDispatcher rd=getServletContext().getRequestDispatcher("/createUser");
			rd.forward(request, response);
		}
		else
		{
		System.out.println("Here");
		System.out.println(request.getRequestURL());
		String id=request.getParameter("course");
		
		
		MyConnectionManager createConnection = new MyConnectionManager();
		Connection c = createConnection.getConnection();
		
		//String conn="jdbc:oracle:thin:@//remote.eos.ncsu.edu:1521/orcl";
		
		PrintWriter out = response.getWriter();
	
		System.out.println("Connected to database");
		Statement stat = null;
		try {
			stat = c.createStatement();
			ResultSet rs=stat.executeQuery("select course_id,course_name from courses");
			
			System.out.println("Populating data");
			while(rs.next())
	        {
				String cname=rs.getString("course_name");
				String cid=rs.getString("course_id");
				courseList.put(cid,cname);
				System.out.println(cname);
	        }
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//String tp="DBMS";
		System.out.println("count"+courseList.size());
		request.setAttribute("cses", courseList);
		RequestDispatcher rd=getServletContext().getRequestDispatcher("/index.jsp");
		rd.forward(request, response);
		
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
