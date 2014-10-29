<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="bootstrap.css" rel="stylesheet">
<link href="tabs.css" rel="stylesheet">
<script type="text/javascript" src="jquery-1.11.1.min.js">
</script>


<script type="text/javascript">
$(document).ready(function(){
	
	
	$('ul.tab li').click(function(e) 
		    { 
		     alert($(this).find("span.t").text());
		     var activediv=$(this).find("span.t").text();
		     if(activediv=="Back"){
		    	alert("Are you sure you want to exit");
		     }
		    
		   });
	
	
	
	
	$('input:radio[name^="answer_"]').change(
			function(){
				if($(this).is(':checked')){
					//alert($(this).attr('name'));
					var hidQ=$(this).attr('name');
					var QuestIDFinally=hidQ.substring(7);
					//alert(QuestIDFinally);
					//var hdQ=$('.Qclass').val();
					//alert("HDQ"+hdQ);
					var newVal=$(this).val();
		   			//alert("hid "+hidQ+" newVal "+newVal);
		   			$('input[name="' + QuestIDFinally + '"]').val(newVal);
		   			//alert(QuestIDFinally+" "+$('input[name="' + QuestIDFinally + '"]').val());
				}
		});
	
	
});

</script>
</head>
<body>
<center>
<div id="tabs" style="margin: 30px">
<ul class="tab">
	<li><a href="#"><span class="t">Back</span></a></li>
</ul>	

</div>


FORM



<div class="qform">
<table border="1" style="margin: 60px">
    <form id="attForm" name="exer" method="post" action="attmpt">
	<tbody>
		<c:forEach var="questions" items="${questionOptions}">
			<tr>
				<input class="Qclass" type="hidden" id="hid_q" name="question" value="${questions.key.getQuestionID()}"/>
				<input id="hid_id" type="hidden" name="${questions.key.getQuestionID()}" value="" />
				<td id="Data"><c:out value="${questions.key.getQuestionID()}"/></td>
				
				<td><c:out value="${questions.key.getText()}"/></td>
			</tr>
			<tr>	
				<c:forEach items="${questions.value}" var="options">
					<tr>
					<td/>
					<td><input type="radio" name="answer_${questions.key.getQuestionID()}" value="${options.getAnswer()}">
					<c:out value="${options.getAnswer()}"/></td>
					<input id="hoptns_id" type="hidden" name="opt_"+${questions.key.getQuestionID()} value="${options.getAnswer()}" />
					</tr>
				</c:forEach>	
			</tr>
		</c:forEach>
		<tr>
			<td align="center" colspan="2"><label><button class="btn btn-primary" type="submit" id="submit" name="submit">SUBMIT</button></label></td>
		</tr>
		
	</tbody>
    </form>
</table>	
</div>	
</center>	


</body>

</html>