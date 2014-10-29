<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>Within Due Date</h1>
<table border="1">
	<tr>
	<th>Homwework</th>
	<th>Submit Date</th>
	<th>Score</th>
	
	</tr>
<c:forEach items="${withinDueDateList}" var="attempt">

	<tr>
		<td>${attempt.getHwID() }</td>
		<td>${attempt.getSubmitDate() }</td>
		<td>${attempt.getPointsScored() }</td>
		<td><a href="/DBMS/ViewAttemptDetails?attemptID=${attempt.getAttemptID() }&hwID=${attempt.getHwID()}">View</a></td>
	</tr>
</c:forEach>
</table>

<h1>Past Due Date</h1>
<table border="1">
<c:forEach items="${pastDueDateList}" var="attempt">
	<tr>
		<td>${attempt.getAttemptID() }</td>
	</tr>
</c:forEach>
</table>

<a href="${backLink}">Back</a>

</body>
</html>