<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<%@ include file="header.jsp"%>

</head>
<c:set var="results" value='${it}' />
<body>
	
	<div class="container">
	
	<h2>
		RESULT<br>
	</h2>
	
	<div class="table table-bordered">
		<table  class="table table-striped table table-bordered">
			<thead>
				<tr>
					<th>Id</th>
					<th>Task Id</th>
					<th>Runs</th>
					<th>Final Binding Energy</th>
					<th>Objectives</th>
					<th>Inter- Energy</th>
					<th>Intra- Energy</th>
					<th>RMSD</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${results.resultList}" var="result">
					<c:forEach items="${result.solutions}" var="solution">
						<tr>
							<td>${result.id}</td>
							<td>${result.taskId}</td>
							<td>${result.run}</td>
							<td><c:if test="${solution.finalBindingEnergy != null}">${solution.finalBindingEnergy}</c:if>kcal/mol</td>
							<td><c:forEach items="${solution.objectives}" var="objective" varStatus="loop">
									<c:if
										test="${not empty solution.objectives[fn:length(solution.objectives)-1]}">

										<c:if test="${loop.last}">
											<c:out value="${objective}" />
										</c:if>

										<c:if test="${not loop.last}">
											<c:out value="${objective}" />,
									</c:if>
									</c:if>

									<c:if
										test="${empty solution.objectives[fn:length(solution.objectives)-1]}">
										<c:out value="${objective}" />
									</c:if>

								</c:forEach></td>
							<td><c:if test="${solution.intermolecularEnergy != null}">${solution.intermolecularEnergy} kcal/mol</c:if></td>
							<td><c:if test="${solution.intramolecularEnergy != null}">${solution.intramolecularEnergy} kcal/mol</c:if></td>
							<td><c:if test="${solution.rmsd != null}">${solution.rmsd}</c:if></td>
						</tr>
					</c:forEach>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
				<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	
	</div>	
</body>
</html>