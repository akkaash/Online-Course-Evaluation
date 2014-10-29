

import gradiance.MyConnectionManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class wtfgetpost
 */
@WebServlet("/wtfgetpost")
public class wtfgetpost extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public HashMap<String,String>courseList=new HashMap<String,String>();
	public String message;
	public Boolean notifyFlag;
	public String notifyText;
	public Boolean nflag=false;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public wtfgetpost() {
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
		proc(request, response);
	}

	public void proc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
				System.out.println("DO GET of WTF");
				HttpSession cur=request.getSession(true);
				String cid = null;
				courseList.clear();
		        String cuser=(String)cur.getAttribute("username");
		        String userRole=(String)cur.getAttribute("role");
		        
		        //Check for course
		        MyConnectionManager createConnection = new MyConnectionManager();
		        Connection conn = createConnection.getConnection();
		        
		        Statement st;
		        String coName = null;
				try {
					st = conn.createStatement();
					ResultSet rs1=st.executeQuery("select course_id from enrollment where user_id='"+cuser+"'");
					while(rs1.next()){
				    coName=rs1.getString("course_id");
				    System.out.println("Curr user is "+cuser+" Coid is "+coName+" Role "+userRole);
				  //Check Student notification
				    
					//notifyFlag=notifyIfneeded(cuser, coName,userRole);
				    checkNotifyFlag(cuser);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		       
		        
		        
		        
		        
		      
				
		        
				if(request.getParameter("view")!=null){
					System.out.println("**************");
					
					
					
					
					request.setAttribute("courseDetail", coName);
					RequestDispatcher rd=getServletContext().getRequestDispatcher("/viewCourse");
					rd.forward(request, response);
				}
				else
				{
		            System.out.println("Here");
		            System.out.println(request.getRequestURL());
		            String id=request.getParameter("course");
		            
		           // MyConnectionManager createConnection = new MyConnectionManager();
		            //Connection conn = createConnection.getConnection();
		            PrintWriter out = response.getWriter();
		            
		            
		            System.out.println("Connected to database");
		            System.out.println("<<<<<<>>>>>>>Session user"+cuser);
		            Statement stat = null;
		            try {
		                stat = conn.createStatement();
		                ResultSet rs=stat.executeQuery("select course_name,course_id,courselevel from courses where course_id in (select course_id from enrollment where user_id='"+cuser+"')");
		                
		                System.out.println("Populating data");
		                while(rs.next())
		                {
		                	
		                    String cname=rs.getString("course_name");
		                    String clevel=rs.getString("courselevel");
		                    String courseDet=cname+" - "+clevel;
		                    cid=rs.getString("course_id");
		                    courseList.put(cid,courseDet);
		                    System.out.println(cname);
		                }
		                //RequestDispatcher rd=getServletContext().getRequestDispatcher("/StudViewHw.jsp");
		                //response.sendRedirect("/DBMS/StudViewHw.jsp");
		                //rd.forward(request, response);
		                conn.close();
		            } catch (SQLException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }
		            
		            
		            //String tp="DBMS";
		            System.out.println("Seesion id>>>>>"+cuser);
		            System.out.println("count"+courseList.size());
		            request.setAttribute("cses", courseList);
		            HttpSession session=request.getSession(true);//creating session
		            session.setAttribute("CourseID",cid);//setting attribute
		            notifyText=request.getParameter("message");
		            System.out.println("?????????????????"+notifyText);
		            session.setAttribute("notifyFlag", nflag);
		            RequestDispatcher rd;
					try {
						rd = getServletContext().getRequestDispatcher("/stud.jsp?mess="+URLEncoder.encode(notifyText,"UTF-8")+"&message="+URLEncoder.encode(" ","UTF-8"));
						  rd.forward(request, response);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            //response.sendRedirect("/DBMS/selectCourse?message="+URLEncoder.encode(message,"UTF-8"));
 catch (ServletException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		          
		            
				}
	}
	
public void checkNotifyFlag(String uname){
		
		System.out.println("Check for notifications");
		
		MyConnectionManager createConnection = new MyConnectionManager();
		Connection conn = createConnection.getConnection();
		Statement stat=null;
		
		try {
			stat=conn.createStatement();
			ResultSet rs=stat.executeQuery("select * from notifications where user_id='"+uname+"'");
			if(rs.next()){
				String user_id=rs.getString("user_id");
				notifyText=rs.getString("MESSAGE");
				nflag=true;
				System.out.println("Notfiy this text>>"+notifyText);
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
