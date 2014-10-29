<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div id="editscoresel" align="center">
		<table border="1">
			<form name="editscoresel" method="post" action="editscoresel">
				<tr>
					<td><label><h4>Score Selection:</h4></td>
					<td><select id="select" name="scoresel"
						placeholder="Select score selection from here" required
						title="Do specify your score selection">
							<option value="latest attempt">Latest Attempt</option>
							<option value="maximum score">Maximum Score</option>
							<option value="average score">Average Score</option>
					</select> </label></td>
				</tr>
				<tr>
					<td align="center" colspan="2"><label><input
							type="submit" id="submit" name="submit" value="EDIT" required></label></td>
				</tr>
			</form>
		</table>
	</div>
</body>
</html>