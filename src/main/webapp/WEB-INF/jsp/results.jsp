<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
  <head>
  	<link rel="stylesheet" type="text/css" href="css/style.css" />
  
	</head>
 

	<c:set var="results" value='${it}' />
	<body>
	
		<h2>RESULT</h2>
		
		
		<!--<c:forEach items="${results.resultList}" var="result">
    		<h4>Result ID: </h4><c:out value="${result.id}"/>
    		<h4>Task ID: </h4><c:out value="${result.taskId}"/>
  		</c:forEach>
  		  -->
  		<table>
    		<tr>
        		<th>Id</th>
        		<th>Task Id</th>
        		<th>Runs</th>
        		<th>Final Binding Energy</th>
        		<th>Objectives</th>
        		
    		</tr>
   		 
   		 <c:forEach items="${results.resultList}" var="result">
   		    <c:forEach items="${result.solutions}" var="solution">
        	<tr>
            	<td>${result.id}</td>
            	<td>${result.taskId}</td>
            	<td>${result.run}</td>
            	<td>${solution.finalBindingEnergy}</td>
            	<c:forEach items="${solution.objectives}" var="objective">	
            		<td><div><c:out value="${objective}"/></div></td>
            	</c:forEach> 
        	</tr>
        	</c:forEach>
    	</c:forEach>
		</table>
  		
  		
</body>	
</html>