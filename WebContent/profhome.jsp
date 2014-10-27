<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Professor Home Page</title>
<script language="javascript">
   function displayObject(){
	   if(request.getParameterMap().containsKey("course"))
	   {
		   var javascriptVar="${course}";
     	alert(javascriptVar);
	   }
   }
</script>
</head>

<body onload="displayObject()">
<center>
<%if(request.getParameterMap().containsKey("username"))
{
session.setAttribute("username", request.getParameter("username"));
}%>
<%-- <%session.setAttribute("hid", request.getParameter("hid")); %> --%>
<h4>Welcome Professor, <%=session.getAttribute("username") %></h4>
	<div id="profoptions">
	<a href="profhome.jsp">Home</a>
	<br>
	<a href="<%=request.getContextPath()%>/selectcourseprof">Select Course</a>
		
		<br>
		<a href="addcourseprof.jsp">Add Course</a>
		<br>
		
		<br>
		<a href="<%=request.getContextPath()%>/logout">Logout</a>
		<br>
		
	</div>
</center>
</body>
</html>