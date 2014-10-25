<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div id="hwoptions" align="center">
Displaying Homeworks : Add Questions
<%if(request.getParameterMap().containsKey("hid"))
{
session.setAttribute("hid", request.getParameter("hid"));
}%>
<%-- <%session.setAttribute("hid", request.getParameter("hid")); %> --%>
<h4>Edit for HW<%=session.getAttribute("hid") %></h4>
<br>
<a href="<%=request.getContextPath()%>/searchaddqtn">Search and Add Questions</a>
<!-- <a href="searchaddqtn.jsp">Search and Add Questions</a> -->
	<br>
	<a href="<%=request.getContextPath()%>/removeqtn">Remove Questions</a>
	<br>
	<a href="courseoptions.jsp">Back</a>
	<br>
</div>
</body>
</html>