<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.ArrayList"%>
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

		<div class="page-header">
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
			<h3>Run</h3>
		</div>

		<ul class="nav nav-tabs nav-justified" role="tablist">
			<li role="presentation" class="active">
				<a href="#table-tab-container" aria-controls="table-tab-container" role="tab" data-toggle="tab">Table</a>
			</li>
			<li role="presentation" class="">
				<a href="#graph-tab-container" aria-controls="graph-tab-container" role="tab" data-toggle="tab">Graph</a>
			</li>
		</ul>

		<div class="tab-content">

			<div role="tabpanel" class="tab-pane active" id="table-tab-container">

				<br />

				<table class="table table-striped table-condensed">
					<tr>
						<td class="col-md-6"><strong>Run:</strong></td>
						<td class="col-md-6">${result.run}</td>
					</tr>
					<tr>
						<td class="col-md-6"><strong>Objectives:</strong></td>
						<td class="col-md-6">
							<c:out value="${result.solutions[0].objectives[0]}" />
							<c:if test="${result.solutions[0].objectives[1]!=null}">
								, <c:out value="${result.solutions[0].objectives[1]}" />
							</c:if>
						</td>
					</tr>
				</table>

				<table  class="table table-striped table-condensed">
					<thead>
						<tr>

							<th>ID</th>
							<!--
							<th>Task ID</th>
							<th>Run</th>
							-->
							<th>
								<span class="hidden-xs">Final Binding Energy</span>
								<span class="visible-xs">E<sub>final</sub> (kcal/mol)</span>
							</th>
							<th>
								<span class="hidden-xs">Intermolecular Energy</span>
								<span class="visible-xs">E<sub>inter</sub> (kcal/mol)</span>
							</th>
							<th>
								<span class="hidden-xs">Intramolecular Energy</span>
								<span class="visible-xs">E<sub>intra</sub> (kcal/mol)</span>
							</th>
							<th>
								RMSD
								<span class="visible-xs"> (&Acirc;)</span>
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
								<!--
								<td>${result.taskId}</td>
								<td>${result.run}</td>
								-->
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
				<div id="filter_div0"></div>
				<div id="filter_div1"></div>
				<div id="chart_div" style="width: 100%; height: 600px;"></div>
			</div>

		</div>

		<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	
	</div>
</body>


<script type="text/javascript">

	$("a[href='#graph-tab-container']").on('shown.bs.tab', function(e) {
		google.charts.load('current', {'packages':['corechart', 'controls']});
		google.charts.setOnLoadCallback(drawChart);
	});

	function drawChart() {

		$.get('<c:url value="/rest/task/${result.taskId}/result/${result.run}?token=${param.token}" />', function( data ) {
			globalData = data;

			var dashboard = new google.visualization.Dashboard(document.getElementById('graph-tab-container'));

			var input = [data.solutions[0].objectives]
			for (var i in data.solutions) {
				var x = data.solutions[i].intermolecularEnergy;
				var y = data.solutions[i].intramolecularEnergy;
				input.push([x,y]);
			}

			var data = google.visualization.arrayToDataTable(input);
			var options = {
				title: 'Front',
				hAxis : {title: input[0][0]},
				vAxis : {title: input[0][1]},
				legend: 'none'
			};
			var chart = new google.visualization.ChartWrapper({
				'chartType': 'ScatterChart',
				'containerId': 'chart_div',
				'options': options
			});

			var slider0 = new google.visualization.ControlWrapper({
				'controlType': 'NumberRangeFilter',
				'containerId': 'filter_div0',
				'options' : {
					'filterColumnIndex':0
				}
			});

			var slider1 = new google.visualization.ControlWrapper({
				'controlType': 'NumberRangeFilter',
				'containerId': 'filter_div1',
				'options' : {
					'filterColumnIndex':1
				}
			});

			// The select handler. Call the chart's getSelection() method
			function selectHandler() {
				var selectedItem = chart.getChart().getSelection()[0];

				if (selectedItem) {
					solution = globalData.solutions[selectedItem.row];
					var value = data.getValue(selectedItem.row, selectedItem.column);
					window.location.href =
						'<c:url value="/rest/task/${result.taskId}/result/${result.id}/" />'
							+ solution.id + '?token=${param.token}';
				}
			}

			function onReady() {
				google.visualization.events.addListener(chart.getChart(), 'select', selectHandler);
			}

			google.visualization.events.addListener(chart, 'ready', onReady);
			dashboard.bind([slider0, slider1], chart);
			dashboard.draw(data);

		});
	}

</script>

</html>