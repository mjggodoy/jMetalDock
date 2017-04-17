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


<%@ include file="WEB-INF/jsp/header.jsp"%>

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
			
		
			<div class="form-group">
			
			
			<label for="input">Choose a file in your directory to be processed or an instance from the benchmark </label> 
			
				<select class="form-control" id="optInput">
					<optgroup label="Choose an input option">
						<option value="file">Input File</option>
						<option value="instance">Instance File</option>
					</optgroup>
				</select>
			
			<br/>	
			
			<div id="inputFile" style="display: none;">

			<label for="exampleInputFile">File Input: </label> 
			<input type="file" id="exampleInputFile">
			<p class="help-block">Please, select a .zip in which all your
				macromolecule and ligand files are prepared (.pdbqt) and also the
				output files from AutoGrid software (.map).</p>
				
			</div>
				
			
			<div id="inputInstance" style="display: none;">
			
				<label for="instance" class="control-label">Instance:<br /></label>
				<select class="form-control" name="instance">
						
					<optgroup label="Small Inhibitors">
						<option value="1a9m">1a9m</option>
						<option value="1aaq">1aaq</option>
						<option value="1b6l">1b6l</option>
						<option value="1b6m">1b6m</option>
						<option value="1bdl">1bdl</option>
						<option value="1bdq">1bdq</option>
						<option value="1bdr">1bdr</option>
						<option value="1gnm">1gnm</option>
						<option value="1heg">1heg</option>
						<option value="1hih">1hih</option>
						<option value="1hpv">1hpv</option>
						<option value="1hte">1hte</option>
						<option value="1htf">1htf</option>
						<option value="1kzk">1kzk</option>
						<option value="1sgb">1sgb</option>
						<option value="1tcx">1tcx</option>
						
					</optgroup>
					
					<optgroup label="Medium Inhibitors">
						<option value="1b6k">1b6k</option>
						<option value="1b6j">1b6j</option>
						<option value="1b6p">1b6p</option>
						<option value="1d4k">1d4k</option>
						<option value="1d4l">1d4l</option>
						<option value="1gnn">1gnn</option>
						<option value="1gno">1gno</option>
						<option value="1hvb">1hvb</option>
						<option value="1hef">1hef</option>
						<option value="1hps">1hps</option>
						<option value="1hef">1hsg</option>
						<option value="1hef">1hxw</option>
						<option value="1izh">1izh</option>
						<option value="1izi">1izi</option>
						<option value="1jld">1jld</option>
						<option value="1k6c">1k6c</option>
						<option value="1k6p">1k6p</option>
						<option value="1k6t">1k6t</option>
						<option value="1k6v">1k6v</option>
						<option value="1mtr">1mtr</option>
						<option value="1mui">1mui</option>
						<option value="2bpv">2bpv</option>
						<option value="2bpx">2bpx</option>
						<option value="2upj">3aid</option>
						<option value="4hvp">4hvp</option>
						<option value="4phv">4phv</option>
						<option value="5hvp">5hvp</option>
						<option value="7hvp">7hvp</option>
						<option value="7upj">7upj</option>
						<option value="8hvp">8hvp</option>
						<option value="9hvp">9hvp</option>
					</optgroup>
					
					<optgroup label="Large Inhibitors">
						<option value="1a94">1a94</option>
						<option value="1hiv">1hiv</option>
						<option value="1hos">1hos</option>
						<option value="1hos">1hpx</option>
						<option value="1htg">1htg</option>
						<option value="1hvi">1hvi</option>
						<option value="1hvj">1hvj</option>
						<option value="1hvk">1hvk</option>
						<option value="1hvl">1hvl</option>
						<option value="1hvs">1hvs</option>
						<option value="1hvs">1hwr</option>
						<option value="1ody">1ody</option>
						<option value="1vij">1vij</option>
						<option value="1vik">1vik</option>
						<option value="3tlh">3tlh</option>
						
					</optgroup>
					
					<optgroup label="Cyclic Urea Inhibitors">
						<option value="1ajv">1ajv</option>
						<option value="1bv7">1bv7</option>
						<option value="1bv9">1bv9</option>
						<option value="1bv9">1bwa</option>
						<option value="1bwb">1bwb</option>
						<option value="1dmp">1dmp</option>
						<option value="1g2k">1g2k</option>
						<option value="1g35">1g35</option>
						<option value="1hpo">1hpo</option>
						<option value="1hvh">1hvh</option>
						<option value="1mes">1mes</option>
						<option value="1meu">1meu</option>
						<option value="1pro">1pro</option>
						<option value="1qbr">1qbr</option>
						<option value="1qbt">1qbt</option>
						<option value="1qbu">1qbu</option>
						<option value="1bv9">1bv9</option>
						<option value="2upj">2upj</option>
						
					</optgroup>
						
				</select>
				</div>
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
						<option value=true>Binding Energy and RMSD score</option>
					</select>
				</div>

			</div>

			<label for="runs" class="control-label">Number of Runs:<br /></label>
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

			<br /> 
			
			<label for="population_size" class="control-label">Population
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

			<br /> <label for="evaluations" class="control-label">Number of
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
			<input type="submit" value="Submit" class="btn btn-default">
		</form>
	</div>
	<br />
	
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	
	<br />
</body>

<script src="resources/js/taskJavaScript.js"></script>

</html>
