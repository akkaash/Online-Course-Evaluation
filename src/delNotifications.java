

import gradiance.MyConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class delNotifications
 */
@WebServlet("/delNotifications")
public class delNotifications extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public delNotifications() {
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
        
        int not_id=Integer.parseInt(request.getParameter("nid"));
        
        //Connctn
        MyConnectionManager createConnection = new MyConnectionManager();
        Connection conn = createConnection.getConnection();
        
        PreparedStatement ps2;
        String coName = null;
		try {
			ps2=conn.prepareStatement("delete from notifications where NOTIFICATION_ID =?");
            ps2.setInt(1,not_id);
           
            ps2.executeUpdate();
            conn.commit();
            ps2.close();
			
			System.out.println("NOTILISTCOUNT"+notilst.size());
			request.setAttribute("NotDetails", notilst);
			RequestDispatcher rd=getServletContext().getRequestDispatcher("/viewNotifications");
			rd.forward(request, response);
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
       
        
		
	}
	
	

}
