<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div id="edithomework" align="center">
Displaying Homeworks : Edit Homework
<%if(request.getParameterMap().containsKey("hid"))
{
session.setAttribute("hid", request.getParameter("hid"));
}%>
<%-- <%session.setAttribute("hid", request.getParameter("hid")); %> --%>
<h4>Edit for HW<%=session.getAttribute("hid") %></h4>
<br>
<table border="1">
    <form name="edithomework" method="post" action="edithomework">
	<tr>
		<td><label><h4>Difficulty Range:</h4></td>
		<td><select id="select" name="option" placeholder="Select option to edit" required title="Do specify your range">
			<option value="topic">Topic</option>
			<option value="attempt">Attempt</option>
			<option value="stdate">Start Date</option>
			<option value="enddate">End Date</option>
			<option value="diff">Difficulty Level</option>
			<option value="scoresel">Score Selection</option>
			<option value="questions">Question</option>
			<option value="cap">Correct Answer Points</option>
			<option value="iap">Incorrect Answer Points</option>
		</select>
	</td>
	</tr>
	<tr>
		<td align="center" colspan="2"><label><input type="submit" id="submit" name="submit" value="SUBMIT" required></label></td>
	</tr>
    </form>
</table>	
<a href="courseoptions.jsp">Back</a>
</div>
</body>
</html>