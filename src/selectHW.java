

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

/**
 * Servlet implementation class selectHW
 */
@WebServlet(name = "selectHW", urlPatterns = { "/selectHW" } )
public class selectHW extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public ArrayList<String>hwList=new ArrayList<String>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public selectHW() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("viewHW")!=null){
			System.out.println("**************");
			String hwName=request.getParameter("hw");
			request.setAttribute("hwDetail", hwName);
			RequestDispatcher rd=getServletContext().getRequestDispatcher("/viewHW");
			rd.forward(request, response);
		}
		else
		{
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
			ResultSet rs=stat.executeQuery("select HOMEWORK_ID from homework");
			
			System.out.println("Populating data");
			while(rs.next())
	        {
				String hname=rs.getString("HOMEWORK_ID");
				hwList.add(hname);
				System.out.println(hname);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//String tp="DBMS";
		System.out.println("count"+hwList.size());
		request.setAttribute("hw", hwList);
		RequestDispatcher rd=getServletContext().getRequestDispatcher("/viewTa.jsp");
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
