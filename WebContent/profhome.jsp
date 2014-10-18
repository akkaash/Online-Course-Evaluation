<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Professor Home Page</title>
</head>
<body>

<center>
<h3>Welcome Professor, ${username}</h3>
	<div id="profoptions">
	<a href="profhome.jsp">Home</a>
	<br>
	<a href="<%=request.getContextPath()%>/selectcourseprof">Select Course</a>
		
		<br>
		<a href="addcourse.jsp">Add Course</a>
		<br>
		
	</div>
</center>
</body>
</html>