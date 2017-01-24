<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList"%>
<%
	ArrayList<String> numList = new ArrayList<String>();
	numList.add("one");
	numList.add("two");
	numList.add("three");
	request.setAttribute("numList", numList);
%>
<!DOCTYPE html>
<html>
	<c:set var="task" value='${requestScope["task"]}' />
	<body>
	<%= "hello" %>
		<%= request.getAttribute("Name") %>
		<c:out value='${requestScope["numList"]}' /><br/>
		<c:out value='${requestScope.numList}'/>
		<h1>HELLO PUA!!!!</h1>
		<h2>VAYA</h2>
		<c:out value='${requestScope["task"].id}'/>
		<c:out value="${task.id}"/>
		<c:out value="${task.hash}"/>
		<c:out value="${task.state}"/>
	</body>
</html>