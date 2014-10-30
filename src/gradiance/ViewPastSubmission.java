package gradiance;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ViewPastSubmission
 */
@WebServlet("/ViewPastSubmission")
public class ViewPastSubmission extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Connection connection;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewPastSubmission() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		myDo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		myDo(request, response);
	}

	private void myDo(HttpServletRequest request, HttpServletResponse response) {
		String userID = "jmick";

//		String userID = (String) request.getSession(true).getAttribute("user_id");
		
//		int homeworkID = Integer.parseInt((String)request.getAttribute("hwID"));
		
//		int homeworkID = 1;
		
		MyConnectionManager connectionManager = new MyConnectionManager();
		
		try{
			connection = connectionManager.getConnection();
			QueryExecutor queryExecutor = new QueryExecutor(connection);
			
			String attemptQueryString = "select *" + " "
										+ " from " + MyConstants.ATTEMPTS_TABLE_NAME + " t1 " + " join " + MyConstants.HOMEWORK_TABLE_NAME + " t2 " + " "
													+ " on " + "t1." + MyConstants.ATTEMPTS_COLS[2] + " = "
															 + "t2." + MyConstants.HOMEWORK_COLS[0] + " "
										+ " where " + " t1" +"." + MyConstants.ATTEMPTS_COLS[1] + " = ?";
			
			queryExecutor.setQueryString(attemptQueryString);
			
			ResultSet attemptsResultSet = queryExecutor.execute(new String[]{
				userID,
			});
			
			if(!attemptsResultSet.next()){
				request.setAttribute("errorMessage", "No attempts for user " + userID);
				request.setAttribute("backLink", request.getHeader("referer"));
				RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
				
			} else{
				ArrayList<Attempt> withinDueDateList = new ArrayList<Attempt>();
				ArrayList<Attempt> pastDueDateList = new ArrayList<Attempt>();
				
				
				do{
					Attempt attempt = new Attempt(
							attemptsResultSet.getInt(MyConstants.ATTEMPTS_COLS[0]), 
							attemptsResultSet.getString(MyConstants.ATTEMPTS_COLS[1]), 
							attemptsResultSet.getInt(MyConstants.ATTEMPTS_COLS[2]), 
							attemptsResultSet.getTimestamp(MyConstants.ATTEMPTS_COLS[3]), 
							attemptsResultSet.getInt(MyConstants.ATTEMPTS_COLS[4]));
					
					
					Timestamp now = new Timestamp((new Date()).getTime());
					Timestamp endDate = new Timestamp((attemptsResultSet.getTimestamp(MyConstants.HOMEWORK_COLS[3])).getTime());
					
					if(now.after(endDate)){
						pastDueDateList.add(attempt);
					} else{
						withinDueDateList.add(attempt);
					}
					
					
				}while(attemptsResultSet.next());
				
				request.setAttribute("withinDueDateList", withinDueDateList);
				request.setAttribute("pastDueDateList", pastDueDateList);
				request.setAttribute("backLink", request.getHeader("referer"));
				System.out.println(request.getParameter("z"));
				int dflag=Integer.parseInt((String)request.getParameter("z"));
				System.out.println("............................."+request.getHeader("referer"));
				
				if(dflag==1)
				{
					RequestDispatcher rd = request.getRequestDispatcher("/forviewscores.jsp");
					rd.forward(request, response);
				}
				else
				{
					RequestDispatcher rd = request.getRequestDispatcher("/displayAttempts.jsp");
					rd.forward(request, response);
				}
				
					
			}
		} catch(SQLException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			
		}
		
		
		
		
		
	}

}
