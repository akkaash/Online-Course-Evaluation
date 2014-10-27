<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div id="viewhomework" align="center">
<h4>Details of Homework ${hid}</h4>
<table border="1">
   
	<tr>
		<td><label><h4>Topic:</h4></td>
		<td>${ctitle}
		</td>
	</tr>
	<tr>
		<td><label><h4>Start Date:</h4></td>
		<td>${stdate}
		</td>
	</tr>
	<tr>
		<td><label><h4>End Date:</h4></td>
		<td>${enddate}
		</td>
	</tr>
	<tr>
		<td><label><h4>Attempts:</h4></td>
		<td>${attempts}
		</td>
	</tr>
	<tr>
		<td><label><h4>Difficulty Range:</h4></td>
		<td>${fromdiff} - ${todiff}
		</td>
	</tr>
	<tr>
		<td><label><h4>Score Selection:</h4></td>
		<td>${scoresel}
		</td>
	</tr>
	<tr>
		<td><label><h4>No. of questions:</h4></td>
		<td>${questions}
		</td>
	</tr>
	
	<tr>
		<td><label><h4>Correct Answer Points:</h4></td>
		<td>${cap}
		</td>
	</tr>
	<tr>
		<td><label><h4>Incorrect Answer Points:</h4></td>
		<td>${iap}
		</td>
	</tr>
	</table>
	<table border="1">
	<%
	System.out.println(request.getAttribute("message"));
	if(!request.getAttribute("message").equals("non empty"))
	{
		System.out.println("in if..");
		%>
		<tr>
		<td><label><h4>Homework Questions:</h4></td>
		<td>${message}
		</td>
	</tr>
<%	}
	else
	{
		System.out.println("in else");
%>
	<tr><td><label><h4>Homework Questions:</h4></td></tr>
<%
	List<String> q  = (List<String>)request.getAttribute("qtns");
	for(String e: q)
	{
%>
		<tr><td><label><h4><%= e%></h4></td></tr>
<%} }%>	
	
</table>

<a href="<%=request.getContextPath()%>/previewhomework">Back</a>
</div>
</body>
</html>