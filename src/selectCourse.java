

import gradiance.MyConnectionManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;

//import com.sun.tools.internal.ws.processor.model.Request;

/**
 * Servlet implementation class selectCourse
 */
@WebServlet(name = "selectCourse", urlPatterns = { "/selectCourse" } )
public class selectCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public HashMap<String,String>courseList=new HashMap<String,String>();
	public String message;
	public Boolean notifyFlag;
	public String notifyText;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public selectCourse() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("DO GET of SELECTCOURSE");
		HttpSession cur=request.getSession(true);
		String cid = null;
		courseList.clear();
        String cuser=(String)cur.getAttribute("username");
        System.out.println("Curr user is "+cuser);
		if(request.getParameter("view")!=null){
			System.out.println("**************");
			String coName=request.getParameter("course");
			request.setAttribute("courseDetail", coName);
			RequestDispatcher rd=getServletContext().getRequestDispatcher("/viewCourse");
			rd.forward(request, response);
		}
		else
		{
            System.out.println("Here");
            System.out.println(request.getRequestURL());
            String id=request.getParameter("course");
            
            MyConnectionManager createConnection = new MyConnectionManager();
            Connection conn = createConnection.getConnection();
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
            notifyText=request.getParameter("mess");
            RequestDispatcher rd=getServletContext().getRequestDispatcher("/stud.jsp?mess"+URLEncoder.encode(notifyText,"UTF-8"));
            //response.sendRedirect("/DBMS/selectCourse?message="+URLEncoder.encode(message,"UTF-8"));
            rd.forward(request, response);
            
		}
	}
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("DO GET of SELECTCOURSE");
		HttpSession cur=request.getSession(true);
        String cuser=(String)cur.getAttribute("username");
        String crole=(String)cur.getAttribute("role");
		System.out.println("Here");
		System.out.println(request.getRequestURL());
		String courseToken=request.getParameter("ctoken");
        MyConnectionManager createConnection = new MyConnectionManager();
		Connection conn = createConnection.getConnection();
		PrintWriter out = response.getWriter();
        
		System.out.println("Connected to database");
		Statement stat = null;
		Statement stat1=null;
		String today = null;
		
		try {
			stat = conn.createStatement();
			stat1 = conn.createStatement();
			
			ResultSet rs1=stat1.executeQuery("select * from courses where COURSE_TOKEN='"+courseToken+"'");
			if(rs1.next()){
                
				ResultSet rs=stat.executeQuery("select * from courses where COURSE_TOKEN='"+courseToken+"'and END_DATE>sysdate");
				System.out.println("Populating data");
				if(rs.next())
				{
					String cname=rs.getString("course_name");
					String cid=rs.getString("course_id");
					String courseStart=rs.getString("START_DATE");
					String courseEnd=rs.getString("END_DATE");
					int studentsEnroll=rs.getInt("STUDENTS_ENROLLED");
					int maxEnroll=rs.getInt("MAXIMUM_ENROLLMENT");
					System.out.println(cname);
					System.out.println(courseStart);
					System.out.println(courseEnd);
					System.out.println(studentsEnroll);
					System.out.println(maxEnroll);
					
					//validateByDate(cname,courseEnd,today);
					Boolean flag=validateByEnroll(cid,studentsEnroll,maxEnroll);
					//if(crole.equalsIgnoreCase("ta"))
					flag=validateIftA(cuser,cid);
					if(flag){
						//Insert into enrollment
						System.out.println("In insert"+cuser+" "+cid);
						PreparedStatement ps=conn.prepareStatement("insert into enrollment values(?,?)");
						ps.setString(2, cuser);
						ps.setString(1, cid);
						ps.executeUpdate();
						
						notifyFlag=notifyIfneeded(cuser,cid);
						HttpSession session=request.getSession(true);//creating session
                        session.setAttribute("notifyFlag",notifyFlag);//setting attribute
						
			        	conn.commit();
			        	ps.close();
					}
					else{
						response.sendRedirect("/DBMS/selectCourse?message="+URLEncoder.encode(message,"UTF-8"));
						return;
					}
				}
				else{
                    message="Sorry the Deadline to enroll for the course has passed";
					//request.setAttribute("msg", message);
					System.out.println("Deadline passed");
					response.sendRedirect("/DBMS/selectCourse?message="+URLEncoder.encode(message,"UTF-8"));
					return;
					//RequestDispatcher rd=getServletContext().getRequestDispatcher("/stud.jsp");
					//rd.forward(request, response);
				}
			}
			else
				System.out.println("Invalid ID Token");
			conn.close();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//String tp="DBMS";
		System.out.println("count"+courseList.size());
		request.setAttribute("cses", courseList);
		RequestDispatcher rd=getServletContext().getRequestDispatcher("/stud.jsp");
		rd.forward(request, response);
		
        
	}
    
	public void validateByDate(String name,String dat,String todat)
	{
		System.out.println("Date retriwved "+dat+" and today is "+todat);
		
		
	}
	public Boolean validateIftA(String cuser,String cid){
        MyConnectionManager createConnection = new MyConnectionManager();
		Connection conn = createConnection.getConnection();
		System.out.println("Connected to database");
		Statement stat = null;
		System.out.println("In validate by ta "+cuser);
		
		
		try {
			stat=conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from ta where user_id='"+cuser+"'");
			System.out.println("Hereee");
            if(rs.next()){
                System.out.println("Or Hereee");
                String getcid=rs.getString("course_id");
                if(cid.equalsIgnoreCase(getcid))
                {
                    message="U r a TA for course so u cant enroll";
                    System.out.println("U r a TA for course so u cant enroll");
                    return false;
                }
                else{
                    System.out.println("U can enroll");
                    return true;
                }
				
            }
            else
                System.out.println("No record found so not a TA");
            conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		
		
	}
	
	public boolean validateByEnroll(String cid,int curr,int max)
	{
		if(max<=curr){
			message="Sorry Max enrollment has reached";
			System.out.println("Max enrollment reached");
			return false;
		}
		else {
			System.out.println("Validated");
			return true;
		}
	}
	public Boolean notifyIfneeded(String us,String cd)
	{
        MyConnectionManager createConnection = new MyConnectionManager();
		Connection conn = createConnection.getConnection();
		System.out.println("Connected to database");
		Statement stat = null;
		HashMap<String, String>noti=new HashMap<String, String>();
		String courseofTa = null;
		String q="select * from chapters cp where cp.TEXTBOOK_ID in (select tx.TEXTBOOK_ID from COURSE_TEXTBOOK tx where tx.course_id in (select t.course_id from enrollment t where t.user_id='"+us+"')) intersect select * from chapters cp where cp.TEXTBOOK_ID in (select tx.TEXTBOOK_ID from COURSE_TEXTBOOK tx where tx.course_id in (select t.course_id from ta t where t.user_id='"+us+"'))";
		try {
			stat=conn.createStatement();
			ResultSet rs = stat.executeQuery(q);
			if(rs.next())
			{
				System.out.println("Records exist.Notification needed");
				String taquery="select professor,course_id from courses where course_id in (select course_id from ta where user_id='"+us+"')";
				String enquery="select professor from courses where course_id ='"+cd+"'";
				Statement stat1=conn.createStatement();
				Statement stat2=conn.createStatement();
				ResultSet rs1=stat1.executeQuery(taquery);
				
				while(rs1.next()){
					String notProfofTa=rs1.getString("professor");
                    courseofTa=rs1.getString("course_id");
					noti.put(courseofTa, notProfofTa);
				}
				ResultSet rs2=stat2.executeQuery(enquery);
				if(rs2.next()){
					String notProfofEn=rs2.getString("professor");
					//String courseofEn=rs2.getString("course_id");
					noti.put(cd, notProfofEn);
				}
				String notText=us+" has enrolled for "+cd+" whose topics overlap the "+courseofTa+"for which he is TA";//TExt
				System.out.println("*********"+notText+"******");
				System.out.println(noti.size());
				//inserting in notification table
				Iterator it =noti.entrySet().iterator();
				while(it.hasNext()){
					PreparedStatement ps=conn.prepareStatement("insert into notifications values(?,?,?)");
					Map.Entry pairs=(Map.Entry)it.next();
					ps.setString(1,(String)pairs.getKey());
					ps.setString(2,(String)pairs.getValue());
					ps.setString(3, notText);
					System.out.println("Key(course)"+pairs.getKey());
					System.out.println("Val(prof)"+pairs.getValue());
					ps.executeUpdate();
					conn.commit();
					ps.close();
					it.remove();
				}
				
				return true;
			}
			else{
				
				System.out.println("Notification not needed");
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	
}
