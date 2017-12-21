<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/headerHtml.jsp"%>
</head>
<c:set var="solution" value='${it}' />
<body>

	<jsp:include page="/WEB-INF/jsp/header.jsp">
		<jsp:param name="page" value="none" />
	</jsp:include>

	<div class="container">

		<div class="page-header">
			<ol class="breadcrumb">
				<li><a href='<c:url value="/." />'>Home</a></li>
				<li><a href='<c:url value="/task.jsp" />'>Task</a></li>
				<li>
					<a href='<c:url value="../../../${solution.taskId}?token=${param.token}" />'>${solution.taskId}</a>
				</li>
				<li>
					<a href='<c:url value="../../../${solution.taskId}/result?token=${param.token}" />'>Results</a>
				</li>
				<li>
					<a href='<c:url value="../../../${solution.taskId}/result/${solution.run}?token=${param.token}" />'>
						Run ${solution.run}
					</a>
				</li>
				<li class="active">Solution ${solution.id}</li>
			</ol>
			<h3>Solution</h3>
		</div>

		<div>
			<table class="table table-striped table-condensed">
				<tr>
					<td class="col-md-6"><strong>ID:</strong></td>
					<td class="col-md-6">${solution.id}</td>
				</tr>
				<tr>
					<td class="col-md-6"><strong>Run:</strong></td>
					<td class="col-md-6">${solution.run}</td>
				</tr>
				<tr>
					<td class="col-md-6"><strong>Objectives:</strong></td>
					<td class="col-md-6">
						<c:out value="${solution.objectives[0]}" />
						<c:if test="${solution.objectives[1]!=null}">
							, <c:out value="${solution.objectives[1]}" />
						</c:if>
					</td>
				</tr>
				<tr>
					<td class="col-md-6"><strong>Final Binding Energy:</strong></td>
					<td class="col-md-6">${solution.finalBindingEnergy} kcal/mol</td>
				</tr>
				<tr>
					<td class="col-md-6"><strong>Intermolecular Energy:</strong></td>
					<td class="col-md-6">${solution.intermolecularEnergy} kcal/mol</td>
				</tr>
				<tr>
					<td class="col-md-6"><strong>Intramolecular Energy:</strong></td>
					<td class="col-md-6">${solution.intramolecularEnergy} kcal/mol</td>
				</tr>
				<tr>
					<td class="col-md-6"><strong>RMSD:</strong></td>
					<td class="col-md-6">${solution.rmsd} &Acirc;</td>
				</tr>
			</table>
		</div>

		<c:if test="${solution.pdbqt!=null}">

			<h4>PDBQT</h4>

			<div class="well">
				<samp style="white-space: pre-line;">${solution.pdbqt}</samp>
			</div>

		</c:if>

		<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	
	</div>	
</body>
</html>