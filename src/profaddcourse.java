

import gradiance.MyConnectionManager;

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
 * Servlet implementation class profaddcourse
 */
@WebServlet("/profaddcourse")
public class profaddcourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public profaddcourse() {
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
		System.out.println("In add course");
		
		 try {
		MyConnectionManager createConnection = new MyConnectionManager();
		Connection c = createConnection.getConnection();
		PrintWriter out = response.getWriter();
		Statement stat=c.createStatement();
		Statement stat1=c.createStatement();
		String[] names = request.getParameterValues("names");
		for(int i=0;i<names.length;i++)
			System.out.println("Value of textbook ids : "+names[i]);
		
		String cid=request.getParameter("cid");
        String ctoken=request.getParameter("ctoken");
        String cname=request.getParameter("cname");
        String dd1=request.getParameter("dd1");
		String mon1=request.getParameter("mon1");
		String year1=request.getParameter("year1");
		String stdate=dd1+" "+mon1+" "+year1;
		String dd2=request.getParameter("dd2");
		String mon2=request.getParameter("mon2");
		String year2=request.getParameter("year2");
		String enddate=dd2+" "+mon2+" "+year2;
        String clevel=request.getParameter("clevel");
        String menroll=request.getParameter("menroll");
      
        String username=(String)request.getSession().getAttribute("username");
        System.out.println("Username :"+username);
        
        String sql="insert into courses(COURSE_ID,COURSE_TOKEN,COURSE_NAME,START_DATE,END_DATE,COURSELEVEL,MAXIMUM_ENROLLMENT,STUDENTS_ENROLLED,PROFESSOR) values('" + cid + "','" + ctoken + "','" + cname + "','" + stdate + "','" + enddate + "','" + clevel + "','" + menroll + "',0,'" + username + "')";
		int status=stat.executeUpdate(sql);
		String msg;
			//if(i==1)
		if(status==1)
		{
			msg="Course added successfully";
			for(int i=0;i<names.length;i++)
			{
			String sql1="insert into course_textbook(COURSE_ID,TEXTBOOK_ID) values('" + cid + "','" + names[i] + "')";
			stat1.executeUpdate(sql1);
			}
		}
		else
			msg="Course not added";
		
				System.out.println("Course Insertion success");
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("postcourseadd.jsp").forward(request,response);
				//request.setAttribute("course", "Course Added Successfully");
		//	else
			//	System.out.println("Insertion failure");
		//request.getRequestDispatcher("profhome.jsp").forward(request,response);
		c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
