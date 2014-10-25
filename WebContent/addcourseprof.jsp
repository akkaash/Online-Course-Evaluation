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
<h4>Add Course</h4>
<table border="1">
    <form name="addcourseprof" method="post" action="profaddcourse">
	<tr>
		<td><label><h4>Course ID:</h4></td>
		<td><input type="text" id="cid" name="cid">
		</label></td>
	</tr>
	<tr>
		<td><label><h4>Course Token:</h4></td>
		<td><input type="text" id="ctoken" name="ctoken" placeholder="" required title="You can enter characters.">
		</label></td>
	</tr>
	<tr>
		<td><label><h4>Course Name:</h4></td>
		<td><input type="text" id="cname" name="cname" placeholder="" required title="You can enter characters.">
		</label></td>
	</tr>
	<tr>
		<td><label><h4>Start Date:</h4></td>
		<!-- <td><input type="text" id="stdate" name="stdate" placeholder="DD-MON-YYYY" required title="You can enter characters.">
		</label></td> -->
		<td><select id="dd1" name="dd1" placeholder="Select date" required title="Do specify your range">
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
			<option value="6">6</option>
			<option value="7">7</option>
			<option value="8">8</option>
			<option value="9">9</option>
			<option value="10">10</option>
			<option value="11">11</option>
			<option value="12">12</option>
			<option value="13">13</option>
			<option value="14">14</option>
			<option value="15">15</option>
			<option value="16">16</option>
			<option value="17">17</option>
			<option value="18">18</option>
			<option value="19">19</option>
			<option value="20">20</option>
			<option value="21">21</option>
			<option value="22">22</option>
			<option value="23">23</option>
			<option value="24">24</option>
			<option value="25">25</option>
			<option value="26">26</option>
			<option value="27">27</option>
			<option value="28">28</option>
			<option value="29">29</option>
			<option value="30">30</option>
			<option value="31">31</option>
		</select>
	</td>
	<td><select id="mon1" name="mon1" placeholder="Select month" required title="Do specify your range">
			<option value="January">JAN</option>
			<option value="February">FEB</option>
			<option value="March">Mar</option>
			<option value="April">Apr</option>
			<option value="May">May</option>
			<option value="June">Jun</option>
			<option value="July">Jul</option>
			<option value="August">Aug</option>
			<option value="September">Sep</option>
			<option value="October">Oct</option>
			<option value="November">Nov</option>
			<option value="December">Dec</option>
		</select>
	</td>
	<td><select id="year1" name="year1" placeholder="Select year" required title="Do specify your range">
			<option value="2014">2014</option>
			<option value="2015">2015</option>
		</select>
	</td>	
	</tr>
	<tr>
		<td><label><h4>End Date:</h4></td>
		<!-- <td><input type="text" id="enddate" name="enddate" placeholder="DD-MON-YYYY" required title="You can enter characters.">
		</label></td> -->
		<td><select id="dd2" name="dd2" placeholder="Select date" required title="Do specify your range">
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
			<option value="6">6</option>
			<option value="7">7</option>
			<option value="8">8</option>
			<option value="9">9</option>
			<option value="10">10</option>
			<option value="11">11</option>
			<option value="12">12</option>
			<option value="13">13</option>
			<option value="14">14</option>
			<option value="15">15</option>
			<option value="16">16</option>
			<option value="17">17</option>
			<option value="18">18</option>
			<option value="19">19</option>
			<option value="20">20</option>
			<option value="21">21</option>
			<option value="22">22</option>
			<option value="23">23</option>
			<option value="24">24</option>
			<option value="25">25</option>
			<option value="26">26</option>
			<option value="27">27</option>
			<option value="28">28</option>
			<option value="29">29</option>
			<option value="30">30</option>
			<option value="31">31</option>
		</select>
	</td>
	<td><select id="mon2" name="mon2" placeholder="Select month" required title="Do specify your range">
			<option value="January">JAN</option>
			<option value="February">FEB</option>
			<option value="March">Mar</option>
			<option value="April">Apr</option>
			<option value="May">May</option>
			<option value="June">Jun</option>
			<option value="July">Jul</option>
			<option value="August">Aug</option>
			<option value="September">Sep</option>
			<option value="October">Oct</option>
			<option value="November">Nov</option>
			<option value="December">Dec</option>
		</select>
	</td>
	<td><select id="year2" name="year2" placeholder="Select year" required title="Do specify your range">
			<option value="2014">2014</option>
			<option value="2015">2015</option>
		</select>
	</td>	
	</tr>
	<tr>
		<td><label><h4>Course Level:</h4></td>
		<td><input type="text" id="clevel" name="clevel" placeholder="" required title="You can enter characters.">
		</label></td>
	</tr>
	<tr>
		<td><label><h4>Maximum Enrollment:</h4></td>
		<td><input type="text" id="menroll" name="menroll" placeholder="" required title="You can enter characters.">
		</label></td>
	</tr>
	<tr>
		<td align="center" colspan="2"><label><input type="submit" id="submit" name="submit" value="ADD" required></label></td>
	</tr>
    </form>
</table>	

</div>
<a href="profhome.jsp">Back</a>
</body>
</html>