
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="tabs.css" rel="stylesheet">
<link href="bootstrap.css" rel="stylesheet">
<script type="text/javascript" src="jquery-1.11.1.min.js">
</script>


<script type="text/javascript">
$(document).ready(function(){
	$('ul.tab li').click(function(e) 
		    { 
		     alert($(this).find("span.t").text());
		     var activediv=$(this).find("span.t").text();
		     if(activediv=="Add Course"){
		     $('html,body').animate({
		         scrollTop: $(".create").offset().top},
		         'slow');
		     }
		    
		   });
	
	
});

</script>
</head>
<body>
	<center>
		<div id="tabs" style="margin: 30px">
			<ul class="tab">
				<li><a href="#"><span class="t">Select Course</span></a></li>
				<li><a href="#"><span class="t">Add Course</span></a></li>
				<li><a href="<%=request.getContextPath()%>/logout">Logout</a>
			</ul>

		</div>

		<% Boolean notFlag=(Boolean)session.getAttribute("notifyFlag");
if (notFlag) { %>
		<div>
			<a href="<%=request.getContextPath()%>/viewNotifications">${param.mess}</a>
		</div>
		<% } else { %>
		<div>m nt printing</div>
		<% } %>


		Select DIV

		<div class="selectCourse">
			<table border="1">
				<form name="selectCourse" method="get" action="/DBMS/selectCourse">

					<tr>
						<td><label><h4>Select Course:</h4></td>
						<td><select id="select" name="course"
							placeholder="Select course from here" required
							title="Do specify your course">
								<c:forEach var="item" items="${cses}">
									<option value="${item.key}">${item.value}</option>
								</c:forEach>
						</select> </label></td>
					</tr>

					<tr>

						<td align="center" colspan="2"><label><input
								type="submit" id="submit" name="view" value="SUBMIT"required"></label></td>

					</tr>

				</form>
			</table>
		</div>
		---------------------------------------------------- <br /> <br /> <br />
		<br /> <br /> <br /> <br /> <br /> <br /> <br /> Add Course DIV
		<div class="create">
			<table border="1">
				<form name="addCourse" method="post" action="/DBMS/selectCourse">
					<tr>
						<td><label><h4>Course Token:</h4></td>
						<td><input type="text" id="ctoken" name="ctoken"
							placeholder="" required title="Enter Course Token."> </label></td>
					</tr>

					<tr>
						<td align="center" colspan="2"><label><input
								type="submit" id="submit" name="submit" value="REGISTER"
								required></label></td>
					</tr>


				</form>
			</table>
			<p>${param.message }</p>

		</div>
		--------------------- Exit
	</center>


</body>
</html>