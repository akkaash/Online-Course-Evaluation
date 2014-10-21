

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
 * Servlet implementation class addhomework
 */
@WebServlet("/addhomework")
public class addhomework extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addhomework() {
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
		System.out.println("Add homework :");
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
      
        
        String topic=request.getParameter("topic");
        String attempt=request.getParameter("attempt");
      //  String stdate=request.getParameter("stdate");
       // String enddate=request.getParameter("enddate");
        String dd1=request.getParameter("dd1");
		String mon1=request.getParameter("mon1");
		String year1=request.getParameter("year1");
		String stdate=dd1+" "+mon1+" "+year1;
		String dd2=request.getParameter("dd2");
		String mon2=request.getParameter("mon2");
		String year2=request.getParameter("year2");
		String enddate=dd2+" "+mon2+" "+year2;
        String fromdiff=request.getParameter("fromdiff");
        String todiff=request.getParameter("todiff");
        String scoresel=request.getParameter("scoresel");
        String questions=request.getParameter("questions");
        String cap=request.getParameter("cap");
        String iap=request.getParameter("iap");
        ResultSet rs=stat.executeQuery("select CHAPTER_ID from chapters where CHAPTER_TITLE ='"+topic+"'");
        if(rs.next())
        {
        String chapid=rs.getString("CHAPTER_ID");
        System.out.println("Chapter id : "+chapid);
        String sql="insert into homework(CHAPTER_ID,START_DATE,END_DATE,NO_OF_RETRIES,POINTS_CORRECT,POINTS_INCORRECT,SCORE_SELECTION,DIFFICULTY_LEVEL_START,DIFFICULTY_LEVEL_END) values('" + chapid + "','" + stdate + "','" + enddate + "','" + attempt + "','" + cap + "','" + iap + "','" + scoresel + "','" + fromdiff + "','" + todiff + "')";
		stat1.executeUpdate(sql);
			//if(i==1)
				System.out.println("Insertion success");
		//	else
			//	System.out.println("Insertion failure");
		request.getRequestDispatcher("courseoptions.jsp").forward(request,response);
		} 
        else
        {
        	System.out.println("Chapter not present");
        	request.getRequestDispatcher("courseoptions.jsp").forward(request,response);
        }
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
	}

}
