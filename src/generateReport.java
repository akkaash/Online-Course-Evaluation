

import gradiance.MyConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 * Servlet implementation class generateReport
 */
@WebServlet(name = "generateReport", urlPatterns = { "/generateReport" } )
public class generateReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public ArrayList<String>al=new ArrayList<String>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public generateReport() {
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
		HttpSession cur=request.getSession(true);
        String cuser=(String)cur.getAttribute("username");
		MyConnectionManager createConnection = new MyConnectionManager();
		Connection conn = createConnection.getConnection();
		
		String query =request.getParameter("report");
		System.out.println("Connected to DB");
		Statement stat=null;
		try {
			List<Map<String,Object>>rows=new ArrayList<Map<String,Object>>();
			stat=conn.createStatement();
			ResultSet rs=stat.executeQuery(query);
			ResultSetMetaData metadata=rs.getMetaData();
			int numCols=metadata.getColumnCount();
			while(rs.next())
			{
				Map<String,Object>cols=new LinkedHashMap<String,Object>();
				
				for(int i=1;i<=numCols;i++)
				{
					cols.put(metadata.getColumnLabel(i),rs.getObject(i));
				}
				rows.add(cols);
			}
			conn.close();
			
			//System.out.println("count"+hwList.size());
			request.setAttribute("rows", rows);
			//request.setAttribute("qlist", al);
			RequestDispatcher rd=getServletContext().getRequestDispatcher("/Report.jsp");
			rd.forward(request, response);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}

}
