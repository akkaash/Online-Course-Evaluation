

import gradiance.MyConnectionManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;

/**
 * Servlet implementation class createUser
 */
@WebServlet(name = "createUser", urlPatterns = { "/createUser" } )
public class createUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public ArrayList<User>ulist=new ArrayList<User>();
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public createUser() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Here?");
		
		try {
			MyConnectionManager createConnection = new MyConnectionManager();
			Connection conn = createConnection.getConnection();
			System.out.println("Connected to database");
            
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            Statement stat=conn.createStatement();
            Statement stat1=conn.createStatement();
            Statement stat2=conn.createStatement();
            
            User us=new User();
            Student st=new Student();
            Professor pf=new Professor();
            TA t=new TA();
            us.setUser((String)request.getAttribute("username"));
            us.setPassword((String) request.getAttribute("pass"));
            String role=(String) request.getAttribute("role");
            String firstName=(String) request.getAttribute("fname");
            String lastName=(String) request.getAttribute("lname");
            String fullName=firstName+" "+lastName;
            
            
            
            st.setStudyLevel((String) request.getAttribute("lev"));
            t.setCourseId((String) request.getAttribute("course"));
            
            out.println("name "+us.getUser()+" pass "+us.getPassword()+" role "+role+" fullname "+fullName+" lev "+st.getStudyLevel()+" course "+t.getCourseId());
            
            ResultSet rs=stat.executeQuery("select * from users where USER_ID='"+us.getUser()+"'");
            CallableStatement call=null;
            if(rs.next()){
            	request.setAttribute("errorMessage", "Username already exists");
				request.setAttribute("backLink", request.getHeader("referer"));
				
				RequestDispatcher errorDispacther = request.getRequestDispatcher("/error.jsp");
				errorDispacther.forward(request, response);
                
            }
            else{
                out.println("Okay creating User");
                //Inserting User table Common
                
                /*
                 PreparedStatement ps=conn.prepareStatement("insert into users values(?,?)");
                 ps.setString(1,us.getUser());
                 ps.setString(2,us.getPassword());
                 ps.executeUpdate();
                 conn.commit();
                 ps.close();
                 */
                out.println("Inserted");
                //Insert in individual tables
                if(role.equalsIgnoreCase("students"))
                {
                    st.setUserName(fullName);
                    st.setUserId((String)request.getAttribute("username"));
                    String createSql="{call createStudent(?,?,?,?)}";
                    call=conn.prepareCall(createSql);
                    call.setString(1,us.getUser());
                    call.setString(2,us.getPassword());
                    call.setString(3,st.getUserName());
                    call.setString(4,st.getStudyLevel());
                    call.executeUpdate();
                    /*
                     PreparedStatement ps1=conn.prepareStatement("insert into students values(?,?,?)");
                     ps1.setString(1,us.getUser());
                     ps1.setString(2,fullName);
                     ps1.setString(3,st.getStudyLevel());
                     ps1.executeUpdate();
                     */
                    
                    conn.commit();
                    call.close();
                    HttpSession session=request.getSession();//creating session
                    session.setAttribute("username",us.getUser());//setting attribute
                    session.setAttribute("role", role);
                    response.sendRedirect("/DBMS/selectCourse");
                }
                else if(role.equalsIgnoreCase("professors"))
                {
                    pf.setProfName(fullName);
                    pf.setUserId((String)request.getAttribute("username"));
                    
                    String createSql="{call createProfessor(?,?,?)}";
                    call=conn.prepareCall(createSql);
                    call.setString(1,us.getUser());
                    call.setString(2,us.getPassword());
                    call.setString(3,pf.getProfName());
                    
                    call.executeUpdate();
                    /*PreparedStatement ps1=conn.prepareStatement("insert into professors values(?,?)");
                     ps1.setString(1,us.getUser());
                     ps1.setString(2,fullName);
                     ps1.executeUpdate();
                     conn.commit();
                     ps1.close();
                     */
                    conn.commit();
                    call.close();
                    HttpSession session=request.getSession();//creating session
                    session.setAttribute("username",us.getUser());//setting attribute
                    session.setAttribute("role", role);
                    response.sendRedirect("/DBMS/profhome.jsp");
                    
                }
                else if(role.equalsIgnoreCase("ta"))
                {
                    st.setUserName(fullName);
                    t.setUserId((String)request.getAttribute("username"));
                    
                    validateTa(us.getUser(),t.getCourseId());
                    
                    String createStudSql="{call createTA(?,?,?,?,?)}";
                    call=conn.prepareCall(createStudSql);
                    call.setString(1,us.getUser());
                    call.setString(2,us.getPassword());
                    call.setString(3,st.getUserName());
                    call.setString(4,st.getStudyLevel());
                    call.setString(5,t.getCourseId());
                    call.executeUpdate();
                    
                    /*
                     PreparedStatement ps2=conn.prepareStatement("insert into students values(?,?,?)");
                     ps2.setString(1,us.getUser());
                     ps2.setString(2,fullName);
                     ps2.setString(3,st.getStudyLevel());
                     ps2.executeUpdate();
                     conn.commit();
                     ps2.close();
                     PreparedStatement ps1=conn.prepareStatement("insert into ta values(?,?)");
                     ps1.setString(1,us.getUser());
                     ps1.setString(2,t.getCourseId());
                     ps1.executeUpdate();
                     conn.commit();
                     ps1.close();
                     */
                    conn.commit();
                    call.close();
                    HttpSession session=request.getSession();//creating session
                    session.setAttribute("username",us.getUser());//setting attribute
                    session.setAttribute("role", role);
                    response.sendRedirect(request.getContextPath()+"/selectHW");
                }
                
                
                
            }
            conn.close();
		}
		catch (SQLException e) {
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
	
	public void validateTa(String uname,String cname){
		MyConnectionManager createConnection = new MyConnectionManager();
		Connection conn = createConnection.getConnection();
		System.out.println("Connected to database for validating TA");
		Statement stat=null;
		try {
			stat=conn.createStatement();
			ResultSet rs=stat.executeQuery("select course_id from enrollment where user_id='"+uname+"'");
			while(rs.next()){
				String cnameGot=rs.getString("course_id");
				if(cname.equalsIgnoreCase(cnameGot)){
					System.out.println("Cannot be a Ta as he is enrolled in dt");
				}
				else
					System.out.println("can enroll");
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
    
}
