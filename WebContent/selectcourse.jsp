<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Select Course</title>
</head>
<body>

	<center>
		<h3>Select Course</h3>
		<div id="selectcourse">
			<%-- <table>
<c:forEach items="${res}" var="ee">  
    <form action="course.jsp" method="get">  
    <label id="courseid" value="${ee.cid}" name="courseid" ></label>
      <label id="coursename" value="${ee.cname}" name="coursename" ></label>
            <tr>  
             <td><c:out value="${ee.cid},"${ee.cname}""/>  
             <input type="submit" value="Go" name="action"></td>  
         </tr>  
    </form>   
  </c:forEach>
  </table> --%>
			<form action="courseoptions" method="post">

				<label for="cid">${cid}</label> <input type="hidden" value="${cid}"
					name="cid"> <label for="cname">${cname}</label> <input
					type="hidden" value="${cname}" name="cname"> <input
					type="submit" value="Go" name="action">
			</form>

			<%-- <a href="course.html">${cid}-${cname}</a> --%>

			<br> <a href="profhome.jsp">Back</a>
		</div>
	</center>
</body>
</html>

