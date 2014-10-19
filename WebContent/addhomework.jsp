<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<div id="addhomework" align="center">
<h4>Chapter id <%=session.getAttribute("cid") %></h4>
<%-- <%request.setAttribute("cid", request.getParameter("cid")); %> --%>
<table border="1">
    <form name="addhomework" method="post" action="addhomework">
	<tr>
		<td><label><h4>Topic:</h4></td>
		<td><input type="text" id="topic" name="topic">
		</label></td>
	</tr>
	<tr>
		<td><label><h4>Number of Attempts:</h4></td>
		<td><input type="text" id="attempt" name="attempt" placeholder="" required title="You can enter characters.">
		</label></td>
	</tr>
	<tr>
		<td><label><h4>Start Date:</h4></td>
		<td><input type="text" id="stdate" name="stdate" placeholder="DD-MON-YYYY" required title="You can enter characters.">
		</label></td>
	</tr>
	<tr>
		<td><label><h4>End Date:</h4></td>
		<td><input type="text" id="enddate" name="enddate" placeholder="DD-MON-YYYY" required title="You can enter characters.">
		</label></td>
	</tr>
	<tr>
		<td><label><h4>Difficulty Range:</h4></td>
		<td><select id="select" name="fromdiff" placeholder="Select minimum difficulty range from here" required title="Do specify your range">
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
			<option value="6">6</option>
		</select>
	</td>
	<td><select id="select" name="todiff" placeholder="Select maximum difficulty range from here" required title="Do specify your range">
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
			<option value="6">6</option>
		</select>
	</td>	
	</label>
	</tr>
	<tr>
		<td><label><h4>Score Selection:</h4></td><td><select id="select" name="scoresel" placeholder="Select score selection from here" required title="Do specify your score selection">
			<option value="latest attempt">Latest Attempt</option>
			<option value="maximum score">Maximum Score</option>
			<option value="average score">Average Score</option>
		</select>
		</label>
		</td>
	</tr>
	
	<tr>
		<td><label><h4>Number of Questions:</h4></td>
		<td><input type="text" id="questions" name="questions" placeholder="" required title="You can enter characters.">
		</label></td>
	</tr>
	<tr>
		<td><label><h4>Correct Answer Points:</h4></td>
		<td><input type="text" id="cap" name="cap" placeholder="" required title="You can enter characters.">
		</label></td>
	</tr>
	<tr>
		<td><label><h4>Incorrect Answer Points:</h4></td>
		<td><input type="text" id="iap" name="iap" placeholder="" required title="You can enter characters.">
		</label></td>
	</tr>
	<tr>
		<td align="center" colspan="2"><label><input type="submit" id="submit" name="submit" value="ADD" required></label></td>
	</tr>
    </form>
</table>	

</div>
<a href="courseoptions.jsp">Back</a>
</div>
</body>
</html>