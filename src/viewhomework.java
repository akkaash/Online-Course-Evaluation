

import gradiance.MyConnectionManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class viewhomework
 */
@WebServlet("/viewhomework")
public class viewhomework extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public viewhomework() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("View homework get");
		String hid=request.getParameter("hid");
		System.out.println("Homework id :"+hid);
		request.getSession().setAttribute("hid", hid);
		try {
			MyConnectionManager createConnection = new MyConnectionManager();
			Connection c = createConnection.getConnection();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
        Statement stat=c.createStatement();
        Statement stat1=c.createStatement();
      //  String courseid=(String)request.getSession().getAttribute("cid");
        ResultSet rs=stat.executeQuery("select c.chapter_id as cid,c.chapter_title as ctitle,h.homework_id as hid,h.START_DATE as stdate,h.END_DATE as enddate,h.NO_OF_RETRIES as attempts,h.POINTS_CORRECT as cap,h.POINTS_INCORRECT as iap,h.SCORE_SELECTION as scoresel,h.DIFFICULTY_LEVEL_START as fromdiff,h.DIFFICULTY_LEVEL_END as todiff,h.NO_OF_QUESTIONS as questions from chapters c,homework h where c.chapter_id=h.chapter_id and h.homework_id='"+hid+"'");
        
        if(rs.next())
        {
        	String cid=rs.getString("cid");
        	String ctitle=rs.getString("ctitle");
        	String stdate=rs.getString("stdate");
        	String enddate=rs.getString("enddate");
        	String attempts=rs.getString("attempts");
        	String cap=rs.getString("cap");
        	String iap=rs.getString("iap");
        	String scoresel=rs.getString("scoresel");
        	String fromdiff=rs.getString("fromdiff");
        	String todiff=rs.getString("todiff");
        	String questions=rs.getString("questions");
        	request.setAttribute("hid", hid);
        	request.setAttribute("cid", cid);
        	request.setAttribute("ctitle", ctitle);
        	request.setAttribute("stdate", stdate);
        	request.setAttribute("enddate", enddate);
        	request.setAttribute("attempts", attempts);
        	request.setAttribute("cap", cap);
        	request.setAttribute("iap", iap);
        	request.setAttribute("scoresel", scoresel);
        	request.setAttribute("fromdiff", fromdiff);
        	request.setAttribute("todiff", todiff);
        	request.setAttribute("questions", questions);
        }
    List<String> qtns=new ArrayList<String>();
    ResultSet rs1=stat1.executeQuery("select q.text as text,q.question_id as qid,h.hw_id from qtn_hw h,questions q where h.qtn_id=q.question_id and h.hw_id='"+hid+"'");
    String message="non empty";
    if(rs1.next()==false)
    {
    	System.out.println("Result set qtns is empty");
    	message="No questions associated with this homework.";
    }
    else
    {
    	String text;
    		do
    		{ 
    		text=rs1.getString("text");
    		System.out.println("Text : "+text);
    		qtns.add(text);
    		}while(rs1.next());
    	request.setAttribute("qtns", qtns);
    }
    request.setAttribute("message", message);
    request.getRequestDispatcher("viewhomework.jsp").forward(request,response);
   
    c.close();

	} catch (SQLException e) {
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

}
