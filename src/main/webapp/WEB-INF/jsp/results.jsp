<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/headerHtml.jsp"%>
</head>
<c:set var="results" value='${it}' />
<body>

	<jsp:include page="/WEB-INF/jsp/header.jsp">
		<jsp:param name="page" value="none" />
	</jsp:include>

	<div class="container">

		<div class="page-header">
			<h3>RESULTS <small><c:out value="${task.id}" /></small></h3>
		</div>
	
	<div>
		<table  class="table table-striped table-condensed">
			<thead>
				<tr>
					<th>ID</th>
					<th>Task ID</th>
					<th>Runs</th>
					<th>Final Binding Energy</th>
					<th>Objectives</th>
					<th>Intermolecular Energy</th>
					<th>Intramolecular Energy</th>
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

								</c:forEach>
							</td>
							<td><c:if test="${solution.intermolecularEnergy != null}">${solution.intermolecularEnergy} kcal/mol</c:if></td>
							<td><c:if test="${solution.intramolecularEnergy != null}">${solution.intramolecularEnergy} kcal/mol</c:if></td>
							<td><c:if test="${solution.rmsd != null}">${solution.rmsd} &Acirc;</c:if></td>
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