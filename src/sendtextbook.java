

import gradiance.MyConnectionManager;
import gradiance.Topics;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gradiance.Texbook;

/**
 * Servlet implementation class sendtextbook
 */
@WebServlet("/sendtextbook")
public class sendtextbook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sendtextbook() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Texbook> res=new ArrayList<Texbook>();
		Texbook t;
		try {
			MyConnectionManager createConnection = new MyConnectionManager();
			Connection c = createConnection.getConnection();
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Statement stat=c.createStatement();
        ResultSet rs=stat.executeQuery("select textbook_id,textbook_name from textbook");
       
        if(rs.next()==false)
        	out.println("<h4>Currently, there are no texbooks</h4>");
        else
        {
        	
        	String tbid,tbtext;
        do
        { 
        	tbid=rs.getString("textbook_id");
        	tbtext=rs.getString("textbook_name");
        	System.out.println("TBID :"+tbid);
        	System.out.println("TBtext :"+tbtext);
        	t=new Texbook();
        	t.setTbid(tbid);
        	t.setTbtext(tbtext);
        	res.add(t);
        	/*hid=rs.getString("homework_id");
        	System.out.println("hid"+hid);
        	out.print("<a href=\"edithomework.jsp?hid="+hid+"\">HW"+hid);out.print("</a>");
        	out.println("<br>");*/
        	//res.add(hid);
        } while(rs.next());
        
        request.setAttribute("res", res);
        request.getRequestDispatcher("addcourseprof.jsp").forward(request,response);
        
        }       
        out.println("<a href=\"profhome.jsp\">Back</a>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
		c.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
