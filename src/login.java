

import java.io.IOException;
import java.sql.*;
import java.io.PrintWriter;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
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
        Statement stat1=c.createStatement();
        Statement stat2=c.createStatement();
        
        String user=request.getParameter("username");
        String passwo=request.getParameter("pass");
        String role=request.getParameter("role");
        
        ResultSet rs=stat.executeQuery("select * from users where USER_ID='"+user+"'and PASSWORD='"+passwo+"'");
        
        out.println("<html>");
        out.println("<head>");
        out.println("</head>");
        out.println("<body>");
       // out.println("USername");
       //out.println("Passowrd");
        
        if(rs.next())
        {
        	String uname=rs.getString("USER_ID");
        	String pass=rs.getString("PASSWORD");
        	out.println("<br/>UserName is "+uname);
            //out.println(pass);
            out.println("<br/>Role is "+role);
            String tablename=role;
            out.println("<br/>Tablename is "+tablename);
            out.println("<br/>Checking in role");
            if(role.equalsIgnoreCase("ta"))
            {
            	out.println("<br/>Role is TA");
            	ResultSet rs2=stat2.executeQuery("select USER_ID from ta where USER_ID='"+uname+"'");
            	if(rs2.next())
            	{
            		ResultSet rs1=stat1.executeQuery("select NAME from students where USER_ID='"+uname+"'");
            		if(rs1.next())
            		{
            			String fullName=rs1.getString("NAME");
            			out.println("<br/>"+fullName);
            			out.println("has Logged in");
            		}
            	}
            	else
            		out.println("<br/>You are not a TA.Please check your Role");
            }
            else
            {
            	ResultSet rs1=stat1.executeQuery("select NAME from "+tablename+" where USER_ID='"+uname+"'");
            	if(rs1.next())
            	{
            		String fullName=rs1.getString("NAME");
            		out.println("<br/>"+fullName);
            		out.println("has Logged in");
            	}
            	else
            		out.println("<br/>No data found.Please check your Role");
            }
        }
        else
        	out.println("<br/>Cannot Logg in.Please check your login credentials. ");
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

}
