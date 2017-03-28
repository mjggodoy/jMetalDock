<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>

<!-- <link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/3.1.1/css/bootstrap-combined.min.css" rel="stylesheet"> -->

<link
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.0/themes/ui-lightness/jquery-ui.css"></link>
<script src="http://code.jquery.com/jquery.js"></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src=resources/js/bootstrap-spinner.js></script>
<script src=resources/js/mousehold.js></script>


<%@ include file="/WEB-INF/jsp/header.jsp"%>

</head>

<body>
	<div class="container">
		<div class="page-header">
			<h3>
				<strong>NEW TASK:<br /></strong>
			</h3>
		</div>

		<form id="newTaskForm" name="newTaskForm" action="rest/task"
			method="post" , enctype="multipart/form-data">

			<label for="exampleInputFile">File Input: </label> <input type="file"
				id="exampleInputFile">
			<p class="help-block">Please, select a .zip in which all your
				macromolecule and ligand files are prepared (.pdbqt) and also the
				output files from AutoGrid software (.map).</p>

			<div class="form-group">

				<label for="instance" class="control-label">Instance:<br /></label>

				<select class="form-control" name="instance" id="instance">
					<optgroup label="Instances">
						<option value="1a94">1a94</option>
						<option value="1a9m">1a9m</option>
						<option value="1aaq">1aaq</option>
						<option value="1ajv">1ajv</option>
						<option value="1ajx">1ajx</option>
					</optgroup>
				</select>
			</div>

			<br />

			<div class="form-group">
				<label for="algorithm" class="control-label">Algorithm:*<br /></label>
				<select class="form-control" name="algorithm" id="algorithm">
					<optgroup label="MonoObjective algorithm">
						<option value="PSO">PSO</option>
						<option value="DE">DE</option>
						<option value="ssGA">ssGA</option>
						<option value="gGA">gGA</option>
					</optgroup>
					<optgroup label="MultiObjective algorithm">
						<option value="NSGAII">NSGA-II</option>
						<option value="GDE3">GDE3</option>
						<option value="MOEAD">MOEA/D</option>
						<option value="SMSEMOA">SMS-EMOA</option>
					</optgroup>
				</select> <br />


				<div id="monoobjective" style="display: none;">
					<label for="objective">Objective to Optimize:</label> <select
						class="form-control" name="algorithm" id="algorithm">
						<option>Final Binding Energy</option>
					</select>
				</div>


				<div id="multiobjective" style="display: none;">
					<label for="objective">Objectives to Optimize:</label> <select
						class="form-control" name="algorithm" id="algorithm">
						<option>Intermolecular and Intramolecular Energy</option>
						<option>Binding energy and RMSD score</option>
					</select>
				</div>

			</div>

			<label for="instance" class="control-label">Number of Runs:<br /></label>
			<div class="input-group">
				<span class="input-group-btn">
					<button type="button" class="btn btn-default" data-value="-1"
						data-target="#spinner1" data-toggle="spinner" data-on="mousehold">
						<span class="glyphicon glyphicon-minus"></span>
					</button>
				</span> <input type="text" data-ride="spinner" id="spinner1"
					class="form-control input-number" value="25" data-min="1"
					data-max="50"> <span class="input-group-btn">
					<button type="button" class="btn btn-default" data-value="2"
						data-target="#spinner1" data-toggle="spinner" data-on="mousehold">
						<span class="glyphicon glyphicon-plus"></span>
					</button>
				</span>
			</div>

			<br /> <label for="instance" class="control-label">Population
				size:<br />
			</label>


			<div class="input-group">
				<span class="input-group-btn">
					<button type="button" class="btn btn-default" data-value="-1"
						data-target="#spinner2" data-toggle="spinner" data-on="mousehold">
						<span class="glyphicon glyphicon-minus"></span>
					</button>
				</span> <input type="text" data-ride="spinner" id="spinner2"
					class="form-control input-number" value="250" data-min="20"
					data-max="500"> <span class="input-group-btn">
					<button type="button" class="btn btn-default" data-value="2"
						data-target="#spinner2" data-toggle="spinner" data-on="mousehold">
						<span class="glyphicon glyphicon-plus"></span>
					</button>
				</span>
			</div>

			<br /> <label for="instance" class="control-label">Number of
				Evaluations:<br />
			</label>

			<div class="input-group">
				<span class="input-group-btn">
					<button type="button" class="btn btn-default" data-value="-1000"
						data-target="#spinner3" data-toggle="spinner" data-on="mousehold">
						<span class="glyphicon glyphicon-minus"></span>
					</button>
				</span> <input type="text" data-ride="spinner" id="spinner3"
					class="form-control input-number" value="1500000" data-min="1000"
					data-max="5000000"> <span class="input-group-btn">
					<button type="button" class="btn btn-default" data-value="1000"
						data-target="#spinner3" data-toggle="spinner" data-on="mousehold">
						<span class="glyphicon glyphicon-plus"></span>
					</button>
				</span>
			</div>
			<br />

			<%@ include file="/WEB-INF/jsp/footer.jsp"%>

		</form>

	</div>
	<br />
</body>

<script src="resources/js/taskJavaScript.js"></script>

</html>
