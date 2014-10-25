

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class removequestions
 */
@WebServlet("/removequestions")
public class removequestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public removequestions() {
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
		System.out.println("In remove questions");
		String hid=(String)request.getSession().getAttribute("hid");
		System.out.println("Homework id : "+hid);
		String[] names = request.getParameterValues("names");
		for(int i=0;i<names.length;i++)
			System.out.println("Value of question ids : "+names[i]);
		try {
			CreateConnection createConnection = new CreateConnection();
			Connection c = createConnection.getConnection();
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Statement stat=c.createStatement();
        for(int i=0;i<names.length;i++)
        {
        	String sql="delete from qtn_hw where hw_id='" + hid + "'and qtn_id='" + names[i] + "'";
        	stat.executeUpdate(sql);
    			//if(i==1)
    		System.out.println("Question Deletion success");
    		//	else
    			//	System.out.println("Insertion failure");
        }
        request.getRequestDispatcher("hwoptions.jsp").forward(request,response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
