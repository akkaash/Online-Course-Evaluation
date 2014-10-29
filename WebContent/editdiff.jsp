<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div id="editdiff" align="center">
		<table border="1">
			<form name="editdiff" method="post" action="editdiff">
				<tr>
					<td><label><h4>Difficulty Range:</h4></td>
					<td><select id="select" name="fromdiff"
						placeholder="Select minimum difficulty range from here" required
						title="Do specify your range">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
					</select></td>
					<td><select id="select" name="todiff"
						placeholder="Select maximum difficulty range from here" required
						title="Do specify your range">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
					</select></td>
					</label>
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