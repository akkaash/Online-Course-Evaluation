<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    <%@ page import="gradiance.QuestionAdd" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div id="removeqtn" align="center">
Displaying Questions for Homework<%=session.getAttribute("hid") %>
<form action="removequestions" method="post">
	<h1>Question List</h1>
	<table>
		<%
			List<QuestionAdd> q  = (List<QuestionAdd>)request.getAttribute("res");
			for(QuestionAdd e: q){
		%>
		<tr>
			<td><input type="checkbox" name="names" value="<%=e.getQid()%>"/><%=e.getText()%></td>
		</tr>
		<%
			} 
		%>
	</table>
	<input type="submit" value="REMOVE"/>	
	</form>

<br>
<a href="hwoptions.jsp">Back</a>
</div>
</body>
</html>