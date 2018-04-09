<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/jsp/headerHtml.jsp"%>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
</head>
<c:set var="result" value='${it}' />
<body>

	<jsp:include page="/WEB-INF/jsp/header.jsp">
		<jsp:param name="page" value="none" />
	</jsp:include>

	<div class="container">

		<ol class="breadcrumb">
			<li><a href='<c:url value="/." />'>Home</a></li>
			<li><a href='<c:url value="/task.jsp" />'>Task</a></li>
			<li>
				<a href='<c:url value="../../${result.taskId}?token=${param.token}" />'>${result.taskId}</a>
			</li>
			<li>
				<a href='<c:url value="../../${result.taskId}/result?token=${param.token}" />'>Results</a>
			</li>
			<li class="active">Run ${result.run}</li>
		</ol>

		<div class="panel panel-default">
			<div class="panel-body">

				<div class="page-header">
					<h2>Run</h2>
				</div>

				<table class="table table-striped table-condensed">
					<tr>
						<td class="col-md-6"><strong>Run:</strong></td>
						<td class="col-md-6">${result.run}</td>
					</tr>
					<tr>
						<td class="col-md-6"><strong>Objectives:</strong></td>
						<td class="col-md-6">
							<c:out value="${result.solutions[0].objectives[0]}" />
							<br/>
							<c:if test="${result.solutions[0].objectives[1]!=null}">
								<c:out value="${result.solutions[0].objectives[1]}" />
							</c:if>
						</td>
					</tr>
				</table>

				<ul class="nav nav-tabs" role="tablist">
					<li role="presentation" class="active">
						<a href="#table-tab-container" aria-controls="table-tab-container" role="tab" data-toggle="tab">Table</a>
					</li>
					<c:if test="${result.solutions[0].objectives[1]!=null}">
						<li role="presentation" class="">
							<a href="#graph-tab-container" aria-controls="graph-tab-container" role="tab" data-toggle="tab">Plot</a>
						</li>
					</c:if>
				</ul>

				<div class="tab-content">

					<div role="tabpanel" class="tab-pane active" id="table-tab-container">

						<br />

						<table  class="table table-striped table-condensed">
							<thead>
								<tr>

									<th>ID</th>
									<th>
										<span class="hidden-xs">Final Binding Energy</span>
										<span class="visible-xs">
										E<sub>final</sub>
										<small>(kcal/mol)</small>
									</span>
									<th>
										<span class="hidden-xs">Intermolecular Energy</span>
										<span class="visible-xs">
										E<sub>intra</sub>
										<small>(kcal/mol)</small>
									</span>
									</th>
									<th>
										<span class="hidden-xs">Intramolecular Energy</span>
										<span class="visible-xs">
										E<sub>intra</sub>
										<small>(kcal/mol)</small>
									</span>
									</th>
									<th>
										RMSD
										<span class="visible-xs">
										<small>(&Acirc;)</small>
									</span>
									</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${result.solutions}" var="solution">
									<tr>
										<td>
											<a href='<c:url value="../../${result.taskId}/result/${result.run}/${solution.id}?token=${param.token}" />'>
													${solution.id}
											</a>
										</td>
										<td>
											<c:if test="${solution.finalBindingEnergy != null}">
												${solution.finalBindingEnergy}
												<span class="hidden-xs"> kcal/mol</span>
											</c:if>
										</td>
										<td>
											<c:if test="${solution.intermolecularEnergy != null}">
												${solution.intermolecularEnergy}
												<span class="hidden-xs"> kcal/mol</span>
											</c:if>
										</td>
										<td>
											<c:if test="${solution.intramolecularEnergy != null}">
												${solution.intramolecularEnergy}
												<span class="hidden-xs"> kcal/mol</span>
											</c:if>
										</td>
										<td>
											<c:if test="${solution.rmsd != null}">
												${solution.rmsd}
												<span class="hidden-xs"> &Aring;</span>
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>

					<div role="tabpanel" class="tab-pane" id="graph-tab-container">
						<div class="row" id="plot-loading">
							<div class="col-sm-12">Loading...</div>
						</div>
						<div class="row">
							<div id="filter_div0" class="col-sm-6"></div>
							<div id="filter_div1" class="col-sm-6"></div>
							<div id="chart_div" class="col-sm-12" style="height: 600px;"></div>
						</div>
					</div>

				</div>

			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>


<script type="text/javascript">

var url = '<c:url value="/rest/task/${result.taskId}/result/${result.run}?token=${param.token}" />';

function formatResponse(data) {
	var objectives = data.solutions[0].objectives;
	var dataTable = new google.visualization.DataTable();
	dataTable.addColumn('number', objectives[0]);
	dataTable.addColumn('number', objectives[1]);
	dataTable.addColumn({type: 'string', role: 'tooltip'});

	if (objectives[0]=="Total Binding Energy") {
		obj1 = "finalBindingEnergy";
		obj2 = "rmsd";
	} else {
		obj1 = "intermolecularEnergy";
		obj2 = "intramolecularEnergy";
	}

	for (var i in data.solutions) {
		var x = data.solutions[i][obj1];
		var y = data.solutions[i][obj2];
		var tooltip = x + ", " + y;
		dataTable.addRow([x,y, tooltip]);
	}

	return dataTable;
}

function getSolution(data, row) {
	var solution = data.solutions[row];
	window.location.href =
		'<c:url value="/rest/task/${result.taskId}/result/${result.id}/" />'
		+ solution.id + '?token=${param.token}';
}

</script>
<script src='<c:url value="/resources/js/chart.js" />'></script>
</html>