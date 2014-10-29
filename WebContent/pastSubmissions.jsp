<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:when test="${dueDateFlag ==0 }">
WITHIN DUE DATE
<table border="1">
<tr>
	<th>QID</th>
	<th>QUEST</th>
	
	
</tr>
	<c:forEach var="questions" items="${questionList}">
		<tr>
		<td><c:out value="${questions.getQuestionID()}" /></td>
		<td><c:out value="${questions.getText()}" /></td>
		</tr>
		
		<tr>
			<c:set var="questID" value="{questions.getQuestionID()}"/>
			<c:set var="answerList" value=${questionAnswerMap["${questID}"]}/>
			<c:forEach var="ans" items="${answerList}">
				<tr>
					<td/>
					<td><c:out value="${ans}" /></td>
					<c:set var="AnsFlag" value="${ans}"/>
				</tr>
			
		</tr>
		<tr>
		Answer:
			<td>	
			<c:set var="AnswerFlagVal" value=${answerSelectMap["${AnsFlag}"]}/>
			<c:when anFlag="${AnswerFlagVal ==1 }">
				Marked:<c:out value="${AnsFlag.getAnswer()}" />
			</c:when>
			<c:otherwise>
			Marked:<c:out value="${AnsFlag.getAnswer()}" />
			</c:otherwise>
			</td>
		</tr>
		
		</c:forEach>
		
		
		
	</c:forEach>








</table>
</c:when>

<c:otherwise>
PAST DUE DATE




</c:otherwise>
</body>
</html>