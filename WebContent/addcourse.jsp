<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div id="addcourse" align="center">
<h4>Add Course</h4>
<% session.setAttribute("course_id", request.getParameter("course_id")); %>
<table border="1">
    <form name="addcourse" method="post" action="addc">
	<tr>
		<td><label><h4>Course ID:</h4></td>
		<td>${course_id}
		<input type="hidden" value="${course_id}" name="course_id">
		</label></td>
	</tr>
	<tr>
		<td><label><h4>Course Token:</h4></td>
		<td>${course_token}</label></td>
	</tr>
	<tr>
		<td><label><h4>Course Name:</h4></td>
		<td>${course_name}</label></td>
	</tr>
	<tr>
		<td><label><h4>Start Date:</h4></td>
		<td>${start_date}</label></td>
	</tr>
	<tr>
		<td><label><h4>End Date:</h4></td>
		<td>${end_date}</label></td>
	</tr>
	<tr>
		<td><label><h4>Course Level:</h4></td>
		<td>${course_level}</label></td>
	</tr>
	<tr>
		<td><label><h4>Students Enrolled:</h4></td>
		<td>${students_enrolled}</label></td>
	</tr>
	<tr>
		<td><label><h4>Maximum Enrollment:</h4></td>
		<td>${maximum_enrollment}</label></td>
	</tr>
	
	<tr>
		<td align="center" colspan="2"><label><input type="submit" id="submit" name="submit" value="ADD" required></label></td>
	</tr>
    </form>
</table>	


<br>
	<a href="<%=request.getContextPath()%>/addcourseprof">Back</a>
</div>
</body>
</html>