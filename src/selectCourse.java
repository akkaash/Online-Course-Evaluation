

import gradiance.MyConnectionManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	public Boolean nflag=false;
    
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
                	//String coid=rs.getString("course_id");
                    String cname=rs.getString("course_name");
                    String clevel=rs.getString("courselevel");
                    
                    cid=rs.getString("course_id");
                    String courseDet=cid+" - "+cname;
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
		System.out.println("DO Post of SELECTCOURSE");
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
						
						notifyFlag=notifyIfneeded(cuser,cid,"enroll");
						notifyFlag=notifyIfneeded(cuser,cid,"due");
						HttpSession session=request.getSession(true);//creating session
                        session.setAttribute("notifyFlag",notifyFlag);//setting attribute
						
			        	conn.commit();
			        	ps.close();
					}
					else{
						response.sendRedirect("/DBMS/selectCourse?message="+URLEncoder.encode(notifyText,"UTF-8"));
						return;
					}
				}
				else{
                    message="Sorry the Deadline to enroll for the course has passed";
					//request.setAttribute("msg", message);
					System.out.println("Deadline passed");
					response.sendRedirect("/DBMS/selectCourse?mess="+URLEncoder.encode("","UTF-8")+"&message="+URLEncoder.encode(message,"UTF-8"));
					return;
					//RequestDispatcher rd=getServletContext().getRequestDispatcher("/stud.jsp");
					//rd.forward(request, response);
				}
			}
			else
			{	
				System.out.println("Invalid ID Token");
				message="Invalid ID Token";
				//response.sendRedirect("/DBMS/selectCourse?message="+URLEncoder.encode(message,"UTF-8"));
			}
			conn.close();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//String tp="DBMS";
		System.out.println("count"+courseList.size());
		request.setAttribute("cses", courseList);
		//RequestDispatcher rd=getServletContext().getRequestDispatcher("/stud.jsp");
		RequestDispatcher rd=getServletContext().getRequestDispatcher("/wtfgetpost?message="+URLEncoder.encode(notifyText,"UTF-8")+"&valid="+URLEncoder.encode(message,"UTF-8"));
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
		
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		
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
	public Boolean notifyIfneeded(String us,String cd,String URole)
	{
        MyConnectionManager createConnection = new MyConnectionManager();
		Connection conn = createConnection.getConnection();
		System.out.println("Connected to database in notifyIfneed");
		Map<String, List<String>>noti=new HashMap<String, List<String>>();
		
		noti.clear();
		String notText = null;
		if(URole.equalsIgnoreCase("enroll"))
		{
		
			List<String> nolist=new ArrayList<String>();
		
		Statement stat = null;
		String notProfofTa = null;
		String notProfofEn = null;
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
					notProfofTa=rs1.getString("professor");
                    courseofTa=rs1.getString("course_id");
                    notText=us+" has enrolled for "+cd+" whose topics overlap the "+courseofTa+"for which he is TA";//TExt
					nolist.add(notText);
                    
				}
				noti.put(notProfofTa, nolist);
				ResultSet rs2=stat2.executeQuery(enquery);
				if(rs2.next()){
					notProfofEn=rs2.getString("professor");
					//String courseofEn=rs2.getString("course_id");
					notText=us+" has enrolled for "+cd+" whose topics overlap the "+courseofTa+"for which he is TA";//TExt
					nolist.add(notText);
					
				}
				noti.put(notProfofEn,nolist);
				
				System.out.println("*********"+notText+"******");
				System.out.println(noti.size());
				//inserting in notification table
				
				for(Map.Entry<String, List<String>> entry : noti.entrySet())
				{
					String key=entry.getKey();
					List<String> values=entry.getValue();
					for(String s :values){
						System.out.println(s);
						PreparedStatement ps=conn.prepareStatement("insert into notifications(user_id,message) values(?,?)");
						ps.setString(1,key);
						ps.setString(2,s);
						ps.executeUpdate();
						conn.commit();
						ps.close();
					}
					
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
		
		}
		else if(URole.equalsIgnoreCase("due"))
		{
			List<String> nolist=new ArrayList<String>();
			System.out.println("Check for assgnment due date");
			Statement stat;
			notText = null;
			
			try {
				stat=conn.createStatement();
				ResultSet rs1=stat.executeQuery("select course_id,homework_id,extract( day from diff ) days,extract( hour from diff ) hours,extract( minute from diff ) minutes,extract( second from diff ) seconds from (select course_id,homework_id,(end_date-(systimestamp)) diff from homework where course_id in (select course_id from enrollment where user_id='"+us+"' and course_id='"+cd+"'))");
				while(rs1.next()){
					int day=rs1.getInt("days");
					String cours=rs1.getString("course_id");
					String hwrk=rs1.getString("homework_id");
					if(day == 1){
						
						System.out.println("One Day Left for hw"+hwrk+" of "+cours);
						notText="One Day Left for hw"+hwrk+" of "+cours;
						nolist.add(notText);
						
					}
					noti.put(us, nolist);	
				}
				System.out.println("Size of noti"+noti.size());
				//inserting in notification table
				for(Map.Entry<String, List<String>> entry : noti.entrySet())
				{
					String key=entry.getKey();
					List<String> values=entry.getValue();
					for(String s :values){
						System.out.println(s);
						PreparedStatement ps=conn.prepareStatement("insert into notifications(user_id,message) values(?,?)");
						ps.setString(1,key);
						ps.setString(2,s);
						ps.executeUpdate();
						conn.commit();
						ps.close();
					}
					
				}
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		checkNotifyFlag(us);
		return false;
		
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
