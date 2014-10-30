

import gradiance.MyConnectionManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
            //SP for hashing
            CallableStatement call=null;
            String createSql="{call app_user_security.valid_user(?,?)}";
            call=conn.prepareCall(createSql);
            call.setString(1,user);
            call.setString(2,passwo);
            
            call.executeUpdate();
            conn.commit();
            call.close();
            
            
            
            
            //Simple
           // ResultSet rs=stat.executeQuery("select * from users where USER_ID='"+user+"'and PASSWORD='"+passwo+"'");
            
            out.println("<html>");
            out.println("<head>");
            out.println("</head>");
            out.println("<body>");
            // out.println("USername");
            //out.println("Passowrd");
            
          // if(rs.next())
            //{
                
               // String uname=rs.getString("USER_ID");
                Boolean f=checkNotifyFlag(user);//For notifications
                
                //String pass=rs.getString("PASSWORD");
                out.println("<br/>UserName is "+user);
                //out.println(pass);
                out.println("<br/>Role is "+role);
                String tablename=role;
                out.println("<br/>Tablename is "+tablename);
                out.println("<br/>Checking in role");
                if(role.equalsIgnoreCase("ta"))
                {
                    out.println("<br/>Role is TA");
                    ResultSet rs2=stat2.executeQuery("select USER_ID from ta where USER_ID='"+user+"'");
                    if(rs2.next())
                    {
                        ResultSet rs1=stat1.executeQuery("select NAME from students where USER_ID='"+user+"'");
                        if(rs1.next())
                        {
                            String fullName=rs1.getString("NAME");
                            out.println("<br/>"+fullName);
                            out.println("has Logged in");
                            HttpSession session=request.getSession();//creating session
                            session.setAttribute("username",user);//setting attribute
                            session.setAttribute("role", role);
                            session.setAttribute("notifyFlag", false);
                            System.out.println("Before sending to select HW");
                            conn.close();
                            response.sendRedirect(request.getContextPath()+"/selectHW");
                        }
                    }
                    else{
                        out.println("<br/>You are not a TA.Please check your Role");
                        request.setAttribute("errorMessage", "You are not a TA.Please check your Role");
    					request.setAttribute("backLink", request.getHeader("referer"));
    					
    					RequestDispatcher errorDispacther = request.getRequestDispatcher("/error.jsp");
    					errorDispacther.forward(request, response);
                    }
                }
                else if(role.equalsIgnoreCase("students"))
                {
                    ResultSet rs1=stat1.executeQuery("select NAME from "+tablename+" where USER_ID='"+user+"'");
                    if(rs1.next())
                    {
                        String fullName=rs1.getString("NAME");
                        System.out.println("<br/>"+fullName);
                        System.out.println("has Logged in");
                        HttpSession session=request.getSession();//creating session
                        session.setAttribute("username",user);//setting attribute
                        session.setAttribute("role", role);
                        session.setAttribute("notifyFlag", f);
                        //response.sendRedirect("/DBMS/selectCourse");
                        conn.close();
                        response.sendRedirect("/DBMS/selectCourse?mess="+URLEncoder.encode(notifyText,"UTF-8"));
                    }
                    else{
                        out.println("<br/>No data found.Please check your Role");
                        request.setAttribute("errorMessage", "No data found.Please check your Role");
    					request.setAttribute("backLink", request.getHeader("referer"));
    					
    					RequestDispatcher errorDispacther = request.getRequestDispatcher("/error.jsp");
    					errorDispacther.forward(request, response);
                    }
                }
                else//professors
                {
                    ResultSet rs1=stat1.executeQuery("select NAME from "+tablename+" where USER_ID='"+user+"'");
                    if(rs1.next())
                    {
                        String fullName=rs1.getString("NAME");
                        out.println("<br/>"+fullName);
                        out.println("has Logged in");
                        HttpSession session=request.getSession();//creating session
                        session.setAttribute("username",user);//setting attribute
                        session.setAttribute("role", role);
                        session.setAttribute("notifyFlag", f);
                        conn.close();
                        response.sendRedirect("/DBMS/profhome.jsp?message="+URLEncoder.encode(notifyText,"UTF-8"));
                        //response.sendRedirect("/DBMS/selectCourse?message="+URLEncoder.encode(message,"UTF-8"));
                    }
                    else{
                        out.println("<br/>No Professor data found.Please check your Role");
	                    request.setAttribute("errorMessage", "No Professor data found.Please check your Role");
						request.setAttribute("backLink", request.getHeader("referer"));
						
						RequestDispatcher errorDispacther = request.getRequestDispatcher("/error.jsp");
						errorDispacther.forward(request, response);
                    }
                }
                
          /*  }
            else{
                out.println("<br/>Cannot Logg in.Please check your login credentials. ");
                request.setAttribute("errorMessage", "Cannot Logg in.Please check your login credentials.");
				request.setAttribute("backLink", request.getHeader("referer"));
				
				RequestDispatcher errorDispacther = request.getRequestDispatcher("/error.jsp");
				errorDispacther.forward(request, response);
            }
            out.println("</body>");
            out.println("</html>");
            conn.close();
            */
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("EERRCODE"+e.getErrorCode());
			response.sendRedirect("/DBMS/invalid.jsp");
		}
        
        
	}
	
	public Boolean checkNotifyFlag(String uname){
		
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
				return nflag;
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
    
	
	
	
    
}
