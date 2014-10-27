

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;

/**
 * Servlet implementation class addc
 */
@WebServlet("/addc")
public class addc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addc() {
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
		System.out.println("Course for addition :");
		String course_id=request.getParameter("course_id");
		System.out.println(course_id);
		String userid=(String)request.getSession().getAttribute("username");
		System.out.println("Professor userid : "+userid);
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
        String sql="update courses set professor='" + userid + "'where course_id='" + course_id + "'";
		stat1.executeUpdate(sql);
			//if(i==1)
				System.out.println("Course added successfully");
		//	else
			//	System.out.println("Insertion failure");
		request.getRequestDispatcher("/addcourseprof").forward(request,response);
		
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
		
	}

}
