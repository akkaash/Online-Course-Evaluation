<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
HW DETAILS DIV
<center>
<div id="vwHW">
<table border="1">
    <form name="vwHW" method="get" action="/DBMS/viewHW">
    <tr>
    <th>HOMEWORK_ID</th>
    <th>CHAPTER_ID</th>
    <th>START_DATE</th>
    <th>END_DATE</th>
    <th>NO_OF_RETRIES</th>
    <th>POINTS_CORRECT</th>
    <th>POINTS_INCORRECT</th>
    <th>SCORE_SELECTION</th>
    <th>DIFFICULTY_LEVEL_START</th>
    <th>DIFFICULTY_LEVEL_END</th>
    </tr>
    
	<c:forEach var="hw" items="${hwk}">
	<tr>
		<td><c:out value="${hw.homework_id}"/></td>
		<td><c:out value="${hw.chapter_id}"/></td>
		<td><c:out value="${hw.start_date}"/></td>
		<td><c:out value="${hw.end_date}"/></td>
		<td><c:out value="${hw.no_of_retries}"/></td>
		<td><c:out value="${hw.points_correct}"/></td>
		<td><c:out value="${hw.points_incorrect}"/></td>
		<td><c:out value="${hw.score_selection}"/></td>
		<td><c:out value="${hw.difficulty_level_start}"/></td>
		<td><c:out value="${hw.difficulty_level_end}"/></td>
		
	</tr>
	</c:forEach>
	<tr>
	
		<td align="center" colspan="10"><label><input type="submit" id="submit" name="view" value="BACK" required"></label></td>
	
	</tr>

    </form>
</table>	
</div>	

</body>
</html>