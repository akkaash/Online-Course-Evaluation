

import gradiance.MyConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class viewNotifications
 */
@WebServlet("/viewNotifications")
public class viewNotifications extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public viewNotifications() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		proc(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		proc(request,response);
	}
	
	public void proc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("View Notifications");
		HttpSession cur=request.getSession(true);
		String cid = null;
		
		Map<Integer,String> notilst=new HashMap<Integer,String>();
		
        String cuser=(String)cur.getAttribute("username");
        String userRole=(String)cur.getAttribute("role");
        
        //Connctn
        MyConnectionManager createConnection = new MyConnectionManager();
        Connection conn = createConnection.getConnection();
        
        Statement st;
        String coName = null;
		try {
			st = conn.createStatement();
			ResultSet rs1=st.executeQuery("select * from notifications where user_id='"+cuser+"'");
			while(rs1.next()){
				coName=rs1.getString("message");
				int nd=rs1.getInt("NOTIFICATION_ID");
				System.out.println("Curr user is "+cuser+" msg "+coName+" Role "+userRole);
				notilst.put(nd,coName);
		    //Check Student notification
		    
			//notifyFlag=notifyIfneeded(cuser, coName,userRole);
		   // checkNotifyFlag(cuser);
			}
			System.out.println("NOTILISTCOUNT"+notilst.size());
			request.setAttribute("NotDetails", notilst);
			RequestDispatcher rd=getServletContext().getRequestDispatcher("/viewNot.jsp");
			rd.forward(request, response);
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
       
        
		
	}
	

}
