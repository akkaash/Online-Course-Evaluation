package gradiance;


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
 * Servlet implementation class viewHW
 * 
 */
@WebServlet(name = "viewHW", urlPatterns = { "/viewHW" } )
public class viewHW extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public ArrayList hwList=new ArrayList();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public viewHW() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("In get");
		PrintWriter out=response.getWriter();
		String hwdetail=(String) request.getAttribute("hwDetail");
        out.println(hwdetail);
        String conn="jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";
		//String conn="jdbc:oracle:thin:@//remote.eos.ncsu.edu:1521/orcl";
		OracleDriver driver=new OracleDriver();
	
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
			ResultSet rs=stat.executeQuery("select * from homework where HOMEWORK_ID='"+hwdetail+"'");
			
			System.out.println("Populating HW data");
			while(rs.next())
	        {
				Homework hwrk = new Homework(rs.getInt(MyConstants.HOMEWORK_COLS[0]),						 
						rs.getInt(MyConstants.HOMEWORK_COLS[1]), 
						rs.getString(MyConstants.HOMEWORK_COLS[2]), 
						rs.getString(MyConstants.HOMEWORK_COLS[3]), 
						rs.getInt(MyConstants.HOMEWORK_COLS[4]), 
						rs.getInt(MyConstants.HOMEWORK_COLS[5]), 
						rs.getInt(MyConstants.HOMEWORK_COLS[6]), 
						rs.getString(MyConstants.HOMEWORK_COLS[7]), 
						rs.getInt(MyConstants.HOMEWORK_COLS[8]), 
						rs.getInt(MyConstants.HOMEWORK_COLS[9]));
				
				
				
				hwList.add(hwrk);
				
				//courseList.add(cname);
				//System.out.println(cname);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("count"+hwList.size());
		request.setAttribute("hwk", hwList);
		RequestDispatcher rd=getServletContext().getRequestDispatcher("/viewHW.jsp");
		rd.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
