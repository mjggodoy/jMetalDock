<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
  <head>
  <title>jMetalDock: A web-service that provides single and multi-objetive approaches to molecular docking</title>
  <meta name="description" content="This web service provides mono- and multi-objective approaches to solve the problem of molecular docking. 
  The molecular docking has as objetive to optimize the ligand's pose to the macromolecule (receptor) with the minimum binding energy. 
  The molecular docking problem can be regarded either as a mono-objective or multi-objective problem." />
  <meta name="keywords" content="molecular docking multiobjective approach intermolecular energy intramolecular energy RMSD" />
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=9" />
  <link rel="stylesheet" type="text/css" href="css/style.css" />
  <script type="text/javascript" src="js/jquery.min.js"></script>
  <script type="text/javascript" src="js/image_slide.js"></script>
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
        		
    		</tr>
   		 
   		 <c:forEach items="${results.resultList}" var="result">
        	<tr>
            	<td>${result.id}</td>
            	<td>${result.taskId}</td>
            	<td>${result.run}</td>
        	</tr>
    	</c:forEach>
		</table>
  		
  		
</body>	
</html>