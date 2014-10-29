

import gradiance.Attempt;
import gradiance.MyConnectionManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class viewScores
 */
@WebServlet("/viewScores")
public class viewScores extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public viewScores() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("In get of vewscores");
		ArrayList<Attempt>attList=new ArrayList<Attempt>();
		
		MyConnectionManager createConnection = new MyConnectionManager();
		Connection conn = createConnection.getConnection();
        PrintWriter out = response.getWriter();
        System.out.println("Connected to database in score HW");
        Statement stat = null;
        try {
            stat = conn.createStatement();
            ResultSet rs=stat.executeQuery("select * from attempts");
            
            System.out.println("Populating data in score HW");
            while(rs.next())
            {
            	
                int hid=rs.getInt("HW_ID");
                Date dat=rs.getDate("SUBMIT_DATE");
                String studid=rs.getString("STUDENT_ID");
                int atid=rs.getInt("ATTEMPT_ID");
                int pts=rs.getInt("POINTS_SCORED");
                
                Attempt a=new Attempt(atid, studid, hid, dat, pts);
                attList.add(a);
                
                //System.out.println(hname);
            }
            
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println("count"+attList.size());
        request.setAttribute("attDetails", attList);
        RequestDispatcher rd=getServletContext().getRequestDispatcher("/viewScor.jsp");
        rd.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
