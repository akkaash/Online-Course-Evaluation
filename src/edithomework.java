

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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
 * Servlet implementation class edithomework
 */
@WebServlet("/edithomework")
public class edithomework extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public edithomework() {
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
		System.out.println("In edit homework");
		
        PrintWriter out = response.getWriter();
       
		String option=request.getParameter("option");
		System.out.println("Option chosen is"+option);
		System.out.println("Session variable homework is :"+request.getSession().getAttribute("hid"));
		  if(option.equals("attempt"))
	        {
			  request.getRequestDispatcher("editattempt.jsp").forward(request,response);
	        }
		  else if(option.equals("cap"))
		  {
			  request.getRequestDispatcher("editcap.jsp").forward(request,response);
		  }
		  else if(option.equals("iap"))
		  {
			  request.getRequestDispatcher("editiap.jsp").forward(request,response);
		  }
		  else if(option.equals("stdate"))
		  {
			  request.getRequestDispatcher("editstdate.jsp").forward(request,response);
		  }
		  else if(option.equals("enddate"))
		  {
			  request.getRequestDispatcher("editenddate.jsp").forward(request,response);
		  }
		  else if(option.equals("diff"))
		  {
			  request.getRequestDispatcher("editdiff.jsp").forward(request,response);
		  }
		  else if(option.equals("topic"))
		  {
			  request.getRequestDispatcher("edittopic.jsp").forward(request,response);
		  }
		  else if(option.equals("scoresel"))
		  {
			  request.getRequestDispatcher("editscoresel.jsp").forward(request,response);
		  }
		  else if(option.equals("questions"))
		  {
			  request.getRequestDispatcher("editquestions.jsp").forward(request,response);
		  }
		/*out.println("<html>");
        out.println("<head>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div id=\"edithomework\" align=\"center\"");
       
        out.println("<h4>Edit "+option+"</h4>");
      
		out.println("<table border=\"1\">");
		out.println("<form name=\"edithomeworknext\" method=\"post\" action=\"edithomeworknext\">");
		out.println("<tr>");
		out.println("<td>");
		out.println("<label>");
		out.println("<h4>"+option);
		
		if(option.equals("attempt") || option.equals("cap") || option.equals("iap")||option.equals("questions"))
		{
			out.println("<input type=\"text\" id=\""+option+"\" name=\""+option+"\" required title=\"You can enter characters.\">");
			
		}
		out.println("</label>");
		out.println("</td>");
		out.println("</tr>");
		out.println("<tr>");
			out.println("<td align=\"center\" colspan=\"2\">");
			out.println("<label>");
			out.println("<input type=\"submit\" id=\"submit\" name=\"submit\" value=\"EDIT\" required>");
			out.println("</label>");
			out.println("</td>");
			out.println("</tr>");
			out.println("</form>");
		 out.println("<a href=\"edithomework.jsp\">Back</a>");
	        out.println("</div>");
	        out.println("</body>");
	        out.println("</html>");*/
			

	}

}
