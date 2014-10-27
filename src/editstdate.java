

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
 * Servlet implementation class editstdate
 */
@WebServlet("/editstdate")
public class editstdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public editstdate() {
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
		String dd=request.getParameter("dd");
		String mon=request.getParameter("mon");
		String year=request.getParameter("year");
		String stdate=dd+" "+mon+" "+year;
		String hid=(String)request.getSession().getAttribute("hid");
		System.out.println("Homework :"+hid+"\t stdate :"+stdate);
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
        String sql="update homework set START_DATE='" + stdate + "'where homework_id='" + hid + "'";
	//	stat.executeUpdate(sql);
        int status=stat.executeUpdate(sql);
		String msg;
		if(status==1)
			msg="Homework start date updated";
		else
			msg="Homework start date not updated";
		System.out.println("Homework stdate updated");
		
		request.setAttribute("msg", msg);
		request.getRequestDispatcher("posthwedit.jsp").forward(request,response);
		
		//System.out.println("Homework stdate updated");
		//request.getRequestDispatcher("edithomework.jsp").forward(request,response);
		
		c.close();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
