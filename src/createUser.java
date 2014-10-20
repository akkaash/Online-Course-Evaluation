

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;

/**
 * Servlet implementation class createUser
 */
@WebServlet(name = "createUser", urlPatterns = { "/createUser" } )
public class createUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public ArrayList<User>ulist=new ArrayList<User>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public createUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Here?");
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
        /*
        String user=request.getParameter("username");
        String passwo=request.getParameter("pass");
        String role=request.getParameter("role");
        String firstName=request.getParameter("fname");
        String lastName=request.getParameter("lname");
        String fullName=firstName+" "+lastName;
        String studyLevel=request.getParameter("lev");
        String coName=request.getParameter("course");
        */
        User us=new User();
        Student st=new Student();
        Professor pf=new Professor();
        TA t=new TA();
        us.setUser((String)request.getAttribute("username"));
        us.setPassword((String) request.getAttribute("pass"));
        String role=(String) request.getAttribute("role");
        String firstName=(String) request.getAttribute("fname");
        String lastName=(String) request.getAttribute("lname");
        String fullName=firstName+" "+lastName;
        
        
        st.setStudyLevel((String) request.getAttribute("lev"));
        t.setCourseId((String) request.getAttribute("course"));
        
        out.println("name "+us.getUser()+" pass "+us.getPassword()+" role "+role+" fullname "+fullName+" lev "+st.getStudyLevel()+" course "+t.getCourseId());
        
        ResultSet rs=stat.executeQuery("select * from users where USER_ID='"+us.getUser()+"'and PASSWORD='"+us.getPassword()+"'");
        
        if(rs.next())
        	out.println("Username already exists");
        else{
        	out.println("Okay creating User");
        	//Inserting User table Common
        	PreparedStatement ps=c.prepareStatement("insert into users values(?,?)");
        	ps.setString(1,us.getUser());
        	ps.setString(2,us.getPassword());
        	ps.executeUpdate();
        	c.commit();
        	ps.close();
        	out.println("Inserted");
        	//Insert in individual tables
        	if(role.equalsIgnoreCase("students"))
        	{
        		st.setUserName(fullName);
        		st.setUserId((String)request.getAttribute("username"));
        		PreparedStatement ps1=c.prepareStatement("insert into students values(?,?,?)");
        		ps1.setString(1,us.getUser());
            	ps1.setString(2,fullName);
            	ps1.setString(3,st.getStudyLevel());
            	ps1.executeUpdate();
            	c.commit();
            	ps1.close();
        	}
        	else if(role.equalsIgnoreCase("professors"))
        	{
        		pf.setProfName(fullName);
        		pf.setUserId((String)request.getAttribute("username"));
        		PreparedStatement ps1=c.prepareStatement("insert into professors values(?,?)");
        		ps1.setString(1,us.getUser());
            	ps1.setString(2,fullName);
            	ps1.executeUpdate();
            	c.commit();
            	ps1.close();
        	}
        	else if(role.equalsIgnoreCase("ta"))
        	{
        		t.setUserId((String)request.getAttribute("username"));
        		PreparedStatement ps2=c.prepareStatement("insert into students values(?,?,?)");
        		ps2.setString(1,us.getUser());
            	ps2.setString(2,fullName);
            	ps2.setString(3,st.getStudyLevel());
            	ps2.executeUpdate();
            	c.commit();
            	ps2.close();
        		PreparedStatement ps1=c.prepareStatement("insert into ta values(?,?)");	
        		ps1.setString(1,us.getUser());
            	ps1.setString(2,t.getCourseId());
            	ps1.executeUpdate();
            	c.commit();
            	ps1.close();
        	}
        	
        	
        }
		}
		catch(ClassNotFoundException e){
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
