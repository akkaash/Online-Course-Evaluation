
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
LOGIN DIV
<center>
<div id="selectCourse">
<table border="1">
    <form name="selectCourse" method="get" action="/DBMS/selectCourse">

	<tr>
		<td><label><h4>Select Course:</h4></td><td><select id="select" name="course" placeholder="Select course from here" required title="Do specify your course">
			<c:forEach var="item" items="${cses}">
				<option value="${item}">${item}</option>
			</c:forEach>
		</select>
		</label>
		</td>
	</tr>

	<tr>
	
		<td align="center" colspan="2"><label><input type="submit" id="submit" name="view" value="SUBMIT" required"></label></td>
	
	</tr>

    </form>
</table>	
</div>	
----------------------------------------------------
Add Course DIV
<div id="create">
<table border="1">
    <form name="addCourse" method="post" action="/DBMS/selectCourse">
	<tr>
		<td><label><h4>Course Token:</h4></td>
		<td><input type="text" id="ctoken" name="ctoken" placeholder="" required title="Enter Course Token.">
		</label></td>
	</tr>
	
	<tr>
		<td align="center" colspan="2"><label><input type="submit" id="submit" name="submit" value="REGISTER" required></label></td>
	</tr>


    </form>
</table>	

</div>
---------------------
Exit
</center>	


</body>
</html>