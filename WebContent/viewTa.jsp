<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TA view here</title>
</head>
<body>
<center>
<div id="selectHW">
<table border="1">
    <form name="selectHW" method="get" action="/DBMS/selectHW">

	<tr>
		<td><label><h4>Select HW:</h4></td><td><select id="select" name="hw" placeholder="Select HW from here" required title="Do specify your HW">
			<c:forEach var="item" items="${hw}">
				<option value="${item}">${item}</option>
			</c:forEach>
		</select>
		</label>
		</td>
	</tr>

	<tr>
	
		<td align="center" colspan="2"><label><input type="submit" id="submit" name="viewHW" value="VIEW" required"></label></td>
	
	</tr>

    </form>
</table>	
</div>	
</body>
</html>