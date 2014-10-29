

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
		System.out.println("<<<>>>>>In doget of selectHW");
		hwList.clear();
		HttpSession cur=request.getSession(true);
        String cuser=(String)cur.getAttribute("username");
		if(request.getParameter("viewHW")!=null){
			System.out.println("**************");
			String hwName=request.getParameter("hw");
			request.setAttribute("hwDetail", hwName);
			//RequestDispatcher rd=getServletContext().getRequestDispatcher("/viewHW");
			RequestDispatcher rd=getServletContext().getRequestDispatcher("/ShowHomeworkQuestions?hid="+hwName);
			rd.forward(request, response);
		}
		else
		{
			System.out.println("<<<>>>>>In else of selectHW");
			MyConnectionManager createConnection = new MyConnectionManager();
			Connection conn = createConnection.getConnection();
            PrintWriter out = response.getWriter();
            
            
            System.out.println("Connected to database in select HW");
            Statement stat = null;
            try {
                stat = conn.createStatement();
                ResultSet rs=stat.executeQuery("select HOMEWORK_ID from homework");
                
                System.out.println("Populating data in select HW");
                while(rs.next())
                {
                    String hname=rs.getString("HOMEWORK_ID");
                    hwList.add(hname);
                    System.out.println(hname);
                }
                conn.close();
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
		System.out.println("<<<>>>>>In dopost of selectHW");
	}
    
}
