

import gradiance.MyConnectionManager;

import java.io.IOException;
import java.sql.*;
import java.io.PrintWriter;
import java.net.URLEncoder;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public String notifyText="";
	public Boolean nflag=false;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
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
		try {
			
			MyConnectionManager createConnection = new MyConnectionManager();
			Connection conn = createConnection.getConnection();
            
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            Statement stat=conn.createStatement();
            Statement stat1=conn.createStatement();
            Statement stat2=conn.createStatement();
            
            String user=request.getParameter("username");
            String passwo=request.getParameter("pass");
            String role=request.getParameter("role");
            
            ResultSet rs=stat.executeQuery("select * from users where USER_ID='"+user+"'and PASSWORD='"+passwo+"'");
            
            out.println("<html>");
            out.println("<head>");
            out.println("</head>");
            out.println("<body>");
            // out.println("USername");
            //out.println("Passowrd");
            
            if(rs.next())
            {
                
                String uname=rs.getString("USER_ID");
                checkNotifyFlag(uname);//For notifications
                String pass=rs.getString("PASSWORD");
                out.println("<br/>UserName is "+uname);
                //out.println(pass);
                out.println("<br/>Role is "+role);
                String tablename=role;
                out.println("<br/>Tablename is "+tablename);
                out.println("<br/>Checking in role");
                if(role.equalsIgnoreCase("ta"))
                {
                    out.println("<br/>Role is TA");
                    ResultSet rs2=stat2.executeQuery("select USER_ID from ta where USER_ID='"+uname+"'");
                    if(rs2.next())
                    {
                        ResultSet rs1=stat1.executeQuery("select NAME from students where USER_ID='"+uname+"'");
                        if(rs1.next())
                        {
                            String fullName=rs1.getString("NAME");
                            out.println("<br/>"+fullName);
                            out.println("has Logged in");
                            HttpSession session=request.getSession();//creating session
                            session.setAttribute("username",uname);//setting attribute
                            session.setAttribute("role", role);
                            session.setAttribute("notifyFlag", false);
                            System.out.println("Before sending to select HW");
                            conn.close();
                            response.sendRedirect(request.getContextPath()+"/selectHW");
                        }
                    }
                    else
                        out.println("<br/>You are not a TA.Please check your Role");
                }
                else if(role.equalsIgnoreCase("students"))
                {
                    ResultSet rs1=stat1.executeQuery("select NAME from "+tablename+" where USER_ID='"+uname+"'");
                    if(rs1.next())
                    {
                        String fullName=rs1.getString("NAME");
                        System.out.println("<br/>"+fullName);
                        System.out.println("has Logged in");
                        HttpSession session=request.getSession();//creating session
                        session.setAttribute("username",uname);//setting attribute
                        session.setAttribute("role", role);
                        session.setAttribute("notifyFlag", nflag);
                        //response.sendRedirect("/DBMS/selectCourse");
                        conn.close();
                        response.sendRedirect("/DBMS/selectCourse?mess="+URLEncoder.encode(notifyText,"UTF-8"));
                    }
                    else
                        out.println("<br/>No data found.Please check your Role");
                }
                else//professors
                {
                    ResultSet rs1=stat1.executeQuery("select NAME from "+tablename+" where USER_ID='"+uname+"'");
                    if(rs1.next())
                    {
                        String fullName=rs1.getString("NAME");
                        out.println("<br/>"+fullName);
                        out.println("has Logged in");
                        HttpSession session=request.getSession();//creating session
                        session.setAttribute("username",uname);//setting attribute
                        session.setAttribute("role", role);
                        session.setAttribute("notifyFlag", false);
                        conn.close();
                        response.sendRedirect("/DBMS/profhome.jsp?message="+URLEncoder.encode(notifyText,"UTF-8"));
                        //response.sendRedirect("/DBMS/selectCourse?message="+URLEncoder.encode(message,"UTF-8"));
                    }
                    else
                        out.println("<br/>No Professor data found.Please check your Role");
                }
                
            }
            else
                out.println("<br/>Cannot Logg in.Please check your login credentials. ");
            out.println("</body>");
            out.println("</html>");
            conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
	}
	
	public void checkNotifyFlag(String uname){
		MyConnectionManager createConnection = new MyConnectionManager();
		Connection conn = createConnection.getConnection();
		Statement stat=null;
		
		try {
			stat=conn.createStatement();
			ResultSet rs=stat.executeQuery("select * from notifications where user_id='"+uname+"'");
			if(rs.next()){
				String user_id=rs.getString("user_id");
				notifyText=rs.getString("text");
				nflag=true;
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
    
	
	
	
    
}
