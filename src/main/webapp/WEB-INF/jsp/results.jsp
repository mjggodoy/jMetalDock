<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css" />

</head>

<c:set var="results" value='${it}' />
<body>

	<h2>RESULT</h2>
	<table>
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
		<c:forEach items="${results.resultList}" var="result">
			<c:forEach items="${result.solutions}" var="solution">
				<tr>
					<td>${result.id}</td>
					<td>${result.taskId}</td>
					<td>${result.run}</td>
					<td><c:if test="${ssolution.finalBindingEnergy != null}">${solution.finalBindingEnergy}</c:if></td>
					<c:forEach items="${solution.objectives}" var="objective">
						<td><div>
								<c:out value="${objective}" />
							</div></td>
					</c:forEach>
					<td><c:if test="${solution.intermolecularEnergy != null}">${solution.intermolecularEnergy} kcal/mol</c:if></td>
					<td><c:if test="${solution.intramolecularEnergy != null}">${solution.intramolecularEnergy} kcal/mol</c:if></td>
					<td><c:if test="${solution.rmsd != null}">${solution.rmsd}</c:if></td>
				</tr>
			</c:forEach>
		</c:forEach>
	</table>
</body>
</html>