<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div id="courseoptions" align="center">
		<%if(request.getParameterMap().containsKey("cid"))
{
session.setAttribute("cid", request.getParameter("cid"));
}%>
		<%-- <%session.setAttribute("cid", request.getParameter("cid")); %> --%>
		<h4>
			Course
			<%=session.getAttribute("cid") %></h4>

		<!-- 	<a href="addhomework.jsp">Add Homework</a> -->
		<a href="<%=request.getContextPath()%>/sendtopics">Add Homework</a> <br>
		<a href="<%=request.getContextPath()%>/preaddquestions">Add
			Questions</a>
		<!-- 	<a href="addquestions.jsp">Add Questions</a> -->
		<br> <a href="<%=request.getContextPath()%>/preedithomework">Edit
			Homework</a>
		<!-- 	<a href="edithomework.jsp">Edit Homework</a> -->
		<br> <a href="<%=request.getContextPath()%>/previewhomework">View
			Homework</a>
		<!-- <a href="viewhomework.jsp">View Homework</a> -->
		<br> <a href="viewnot.jsp">View Notifications</a> <br> <a
			href="reports.jsp">Reports</a> <br> <a
			href="<%=request.getContextPath()%>/selectcourseprof">Back</a>
	</div>
</body>
</html>