

import gradiance.MyConnectionManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class viewCourse
 */
@WebServlet(name = "viewCourse", urlPatterns = { "/viewCourse" } )
public class viewCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public HashMap<Integer,String> hwList=new HashMap<Integer,String>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public viewCourse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		// TODO Auto-generated method stub
		hwList.clear();
		HttpSession cur=request.getSession(true);
		String Use=(String) cur.getAttribute("username");
		
        String cid=(String)cur.getAttribute("CourseID");
		
		System.out.println("In get of View Course");
		PrintWriter out=response.getWriter();
		out.println("<html>");
		out.println("<head>");
        out.println("</head>");
        out.println("<body>");
        String coName=request.getParameter("course");
        String cdetail=(String) request.getAttribute("courseDetail");
        
        MyConnectionManager createConnection = new MyConnectionManager();
		Connection conn = createConnection.getConnection();
        
        
        
        System.out.println("Connected to database");
        System.out.println("*********************course is "+coName+" cid is "+cid);
        
        Statement stat = null;
        Statement stat1 = null;
        try {
            stat = conn.createStatement();
            stat1 = conn.createStatement();
            //ResultSet rs=stat.executeQuery("select HOMEWORK_ID from homework where sysdate between start_date and end_date and course_id='"+coName+"'");
           // ResultSet rs=stat.executeQuery("select a.hw_id,(h.no_of_retries-count(*)) Att from attempts a,homework h where h.homework_id=a.hw_id and sysdate between h.start_date and h.end_date and student_id='"+Use+"' group by a.hw_id,h.no_of_retries");
            ResultSet rs=stat.executeQuery("select homework_id,NO_OF_RETRIES from homework h where sysdate between h.start_date and h.end_date and course_id='"+coName+"'");

            System.out.println("Populating data");
            while(rs.next())
            {
                int hi=rs.getInt("homework_id");
                int rtries=rs.getInt("NO_OF_RETRIES");
                String tries;
                if(rtries==-1)
                {
                	System.out.println("In else since attmpts is defined");
                	tries="Unlimited";
                }
                else
                {
                	System.out.println("In else since attmpts is defined");
                	System.out.println(">>>>>retries"+rtries);
                	ResultSet rs1=stat1.executeQuery("select count(*) Att from attempts h where student_id='"+Use+"' and hw_id='"+hi+"'");
                	int attmpsLeft;
                	if(rs1.next())
                		attmpsLeft=rs1.getInt("Att");
                	else
                		attmpsLeft=0;
                	
                System.out.println(">>>>> aatmps"+attmpsLeft);
                
                
                if((rtries-attmpsLeft)<=0)
                	tries="0";
                else 
                	tries=""+(rtries-attmpsLeft);
                //String hname=hi+""+attmpsLeft;
                
                }
                hwList.put(hi, tries);
                //System.out.println(hname);
            }
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        //String tp="DBMS";
        System.out.println("count"+hwList.size());
        request.setAttribute("hw", hwList);
       // RequestDispatcher rd=getServletContext().getRequestDispatcher("/viewTa.jsp");
        //rd.forward(request, response);
        
        
        
        
        
        
        
        
       RequestDispatcher rd=getServletContext().getRequestDispatcher("/StudViewHw.jsp");
       // response.sendRedirect("/DBMS/StudViewHw.jsp");
        rd.forward(request, response);
        out.println(cdetail);
        out.println("</body>");
        out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("In view");
	}

}
