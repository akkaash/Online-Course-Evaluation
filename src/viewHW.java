

import gradiance.MyConnectionManager;

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
import javax.servlet.http.HttpSession;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;

/**
 * Servlet implementation class viewHW
 */
@WebServlet(name = "viewHW", urlPatterns = { "/viewHW" } )
public class viewHW extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public ArrayList<homeWork>hwList=new ArrayList<homeWork>();
    
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
        HttpSession cur=request.getSession(true);
        String cuser=(String)cur.getAttribute("username");
        MyConnectionManager createConnection = new MyConnectionManager();
		Connection conn = createConnection.getConnection();
		System.out.println("Connected to database");
		Statement stat = null;
		Statement stat2=null;
		Statement stat3 =null;
		try {
			stat = conn.createStatement();
			ResultSet rs=stat.executeQuery("select * from homework where HOMEWORK_ID='"+hwdetail+"'");
			stat2 = conn.createStatement();
			ResultSet rs2=stat.executeQuery("select * from qtn_hw where HOMEWORK_ID='"+hwdetail+"'");
			System.out.println("Populating HW data");
			while(rs.next())
	        {
				homeWork hwrk=new homeWork();
				hwrk.setHomework_id(rs.getString("HOMEWORK_ID"));
				hwrk.setChapter_id(rs.getString("CHAPTER_ID"));
				hwrk.setStart_date(rs.getString("START_DATE"));
				hwrk.setEnd_date(rs.getString("END_DATE"));
				hwrk.setNo_of_retries(rs.getString("NO_OF_RETRIES"));
				hwrk.setPoints_correct(rs.getString("POINTS_CORRECT"));
				hwrk.setPoints_incorrect(rs.getString("POINTS_INCORRECT"));
				hwrk.setScore_selection(rs.getString("SCORE_SELECTION"));
				hwrk.setDifficulty_level_start(rs.getString("DIFFICULTY_LEVEL_START"));
				hwrk.setDifficulty_level_end(rs.getString("DIFFICULTY_LEVEL_END"));
				
				hwList.add(hwrk);
				
				//courseList.add(cname);
				//System.out.println(cname);
	        }
			while(rs2.next())
			{
				String qid=rs2.getString("QTN_ID");
				stat3=conn.createStatement();
				ResultSet rs3=stat3.executeQuery("select text from questions where QUESTION_ID='"+qid+"'");
				while(rs3.next())
				{
					System.out.println("Qtext is"+rs3.getString("text"));
				}
			}
			conn.close();
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
