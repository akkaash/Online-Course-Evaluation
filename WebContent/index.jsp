<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="jquery-1.11.1.min.js">
</script>
<script type="text/javascript">
$(document).ready(function(){
	$("#LevelSelect").hide();
	$("#CourseSelect").hide();
	$("#RoleSelect").change(function(){
		var selectedVal = $(this).val();
		if(selectedVal=="students"){
			$("#LevelSelect").show();
			$("#CourseSelect").hide();
		}
		else if(selectedVal=="ta"){
			$("#CourseSelect").show();
			$("#LevelSelect").show();
		}
		else
			$("#LevelSelect").hide();
	});
	
});
</script>
</head>
<body>
LOGIN DIV
<center>
<div id="login">
<table border="1">
    <form name="login" method="post" action="login">
	<tr>
		<td width="100"><label><h4>User Name:</h4></td>
		<td><input type="text" id="username" name="username" placeholder="Enter your name here" required title="You can enter characters as well as numbers.">
		</label></td>
	</tr>

	<tr>
		<td><label><h4>Password:</h4></td><td><input type="password" id="password" name="pass" placeholder="Enter your password here" required title="Your password should be atleast 6 characters long.">
		</label>
		</td>
	</tr>
	
	<tr>
		<td><label><h4>Specify Role:</h4></td><td><select id="select" name="role" placeholder="Select role from here" required title="Do specify your role">
			<option value="professors">PProfessor</option>
			<option value="students">SStudents</option>
			<option value="ta">TA</option>
		</select>
		</label>
		</td>
	</tr>

	<tr>
		<td align="center" colspan="2"><label><input type="submit" id="submit" name="submit" value="LOGIN" required></label></td>
	</tr>

	<tr>
	<td colspan="2"><center><h4><a href="newhome.html">Go back to Home</a></center></h4>
	</tr>
    </form>
</table>	
</div>	
----------------------------------------------------
New User DIV
<div id="create">
<table border="1">
    <form name="newUser" method="get" action="/DBMS/Home">
	<tr>
		<td><label><h4>First Name:</h4></td>
		<td><input type="text" id="fname" name="fname" placeholder="" required title="You can enter characters.">
		</label></td>
	</tr>
	<tr>
		<td><label><h4>Last Name:</h4></td>
		<td><input type="text" id="lname" name="lname" placeholder="" required title="You can enter characters.">
		</label></td>
	</tr>
	
	<tr>
		<td><label><h4>Specify Role:</h4></td><td><select id="RoleSelect" name="role" placeholder="Select role from here" required title="Do specify your role">
			<option value="professors">Professor</option>
			<option value="students">Student</option>
			<option value="ta">TA</option>
		</select>
		</label>
		</td>
	</tr>
	
	<tr>
		<td><label><h4>Study Level:</h4></td><td><select id="LevelSelect" name="lev" placeholder="Select level from here" required title="Do specify your level">
			<option value="Grad" selected="selected">Graduate</option>
			<option value="Undergrad">Under-Graduate</option>
		</select>
		</label>
		</td>
	</tr>
	
	<tr>
		<td><label><h4>Course:</h4></td><td><select id="CourseSelect" name="course" placeholder="Select course from here" required title="Do specify your course">
			<c:forEach var="item" items="${cses}">
				<option value="${item.key}" selected="selected">${item.value}</option>
			</c:forEach>
		</select>
		</label>
		</td>
	</tr>
	
	
	<tr>
		<td width="100"><label><h4>User Name:</h4></td>
		<td><input type="text" id="username" name="username" placeholder="Enter your name here" required title="You can enter characters as well as numbers.">
		</label></td>
	</tr>

	<tr>
		<td><label><h4>Password:</h4></td><td><input type="password" id="password" name="pass" placeholder="Enter your password here" required title="Your password should be atleast 6 characters long.">
		</label>
		</td>
	</tr>
	
	

	<tr>
		<td align="center" colspan="2"><label><input type="submit" id="submit" name="submitDetails" value="REGISTER" required></label></td>
	</tr>


    </form>
</table>	

</div>
---------------------
Exit
</center>	


</body>

</html>