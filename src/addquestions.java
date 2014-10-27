

import gradiance.MyConnectionManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class addquestions
 */
@WebServlet("/addquestions")
public class addquestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addquestions() {
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
		System.out.println("In add questions");
		String hid=(String)request.getSession().getAttribute("hid");
		System.out.println("Homework id : "+hid);
		String[] names = request.getParameterValues("names");
		for(int i=0;i<names.length;i++)
			System.out.println("Value of question ids : "+names[i]);
		try {
			MyConnectionManager createConnection = new MyConnectionManager();
			Connection c = createConnection.getConnection();
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Statement stat=c.createStatement();
        String msg=null;	
        for(int i=0;i<names.length;i++)
        {
        	String sql="insert into qtn_hw(HW_ID,QTN_ID) values('" + hid + "','" + names[i] + "')";
    		int status=stat.executeUpdate(sql);
    		System.out.println("Status of qtn additions :"+status);
    		//if(i==1)
    		if(status==1)
    		{
    				System.out.println("Question Addition success");
    				msg="Questions added successfully";
    		}
    		else
    		{
    			msg="Questions not added";
    		}
    		//	else
    			//	System.out.println("Insertion failure");
        }
        
        request.setAttribute("msg", msg);
        request.getRequestDispatcher("postqtnadd.jsp").forward(request,response);
        
        //request.getRequestDispatcher("hwoptions.jsp").forward(request,response);
        
        c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
