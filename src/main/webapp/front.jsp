<html>
<head>

	<%@ include file="/WEB-INF/jsp/headerHtml.jsp"%>

	<!--Load the AJAX API-->
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script type="text/javascript">

		google.charts.load('current', {'packages':['corechart']});
		google.charts.setOnLoadCallback(drawChart);

		function drawChart() {

			$.get( "http://localhost:8080/docking-service/rest/task/622/result/1?token=69kip8t7fkku66ek54mstu5j00", function( data ) {
				console.log(data);
				var input = [data.solutions[0].objectives]
				for (var i in data.solutions) {
					var x = data.solutions[i].intermolecularEnergy;
					var y = data.solutions[i].intramolecularEnergy;
					if (x > -10 && x < 5 && y > -2 && y < 15)
						input.push([x,y]);
				}
				console.log(input);
				var data = google.visualization.arrayToDataTable(input);
				var options = {
					title: 'Front',
					hAxis: {title: input[0][0], minValue: -10, maxValue: 5},
					vAxis: {title: input[0][1], minValue: -2, maxValue: 15},
					legend: 'none'
				};
				var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));

				// The select handler. Call the chart's getSelection() method
				function selectHandler() {
					var selectedItem = chart.getSelection()[0];
					console.log("SELECT: ");
					console.log(selectedItem);
					if (selectedItem) {
						var value = data.getValue(selectedItem.row, selectedItem.column);
						//alert('The user selected ' + value);
					}
				}
				google.visualization.events.addListener(chart, 'select', selectHandler);

				chart.draw(data, options);

			});

			/*
			var data = google.visualization.arrayToDataTable([
				['Age', 'Weight'],
				[ 8,      12],
				[ 4,      5.5],
				[ 11,     14],
				[ 4,      5],
				[ 3,      3.5],
				[ 6.5,    7]
			]);

			var options = {
				title: 'Age vs. Weight comparison',
				hAxis: {title: 'Age', minValue: 0, maxValue: 15},
				vAxis: {title: 'Weight', minValue: 0, maxValue: 15},
				legend: 'none'
			};


			var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));

			chart.draw(data, options);
			*/
		}
	</script>
</head>

<body>
<!--Div that will hold the pie chart-->
<div id="chart_div" style="width: 900px; height: 500px;"></div>
</body>
</html>