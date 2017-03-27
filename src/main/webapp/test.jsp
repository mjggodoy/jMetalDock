<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>

<!-- <link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/3.1.1/css/bootstrap-combined.min.css" rel="stylesheet"> -->

<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.0/themes/ui-lightness/jquery-ui.css"></link>
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
				<div>
					<input type="text" class="form-control input-restricted-width"
						name="instance" />
				</div>
			</div>

			<div class="form-group">
				<label for="algorithm" class="control-label">Algorithm:*<br />
				</label> <select class="form-control">
					<optgroup label="MonoObjective">
						<option>PSO</option>
						<option>DE</option>
						<option>ssGA</option>
						<option>gGA</option>
					</optgroup>
					<optgroup label="MultiObjective">
						<option>NSGAII</option>
						<option>GDE3</option>
						<option>MOEA/D</option>
						<option>SMS-EMOA</option>
					</optgroup>
				</select>
			</div>
		
		<label for="instance" class="control-label">Number of Runs:<br/></label>
			
		<div class="input-group">
				<span class="input-group-btn">
					<button type="button" class="btn btn-default" data-value="-1"
						data-target="#spinner2" data-toggle="spinner">
						<span class="glyphicon glyphicon-minus"></span>
					</button>
				</span> <input type="text" data-ride="spinner" id="spinner2"
					class="form-control input-number" value="1" data-min="1" data-max="50"> <span class="input-group-btn">
					<button type="button" class="btn btn-default" data-value="2"
						data-target="#spinner2" data-toggle="spinner" data-on="mousehold">
						<span class="glyphicon glyphicon-plus"></span>
					</button>
				</span>
			</div>

				<!-- <label for="instance" class="control-label">Number of Runs:<br/></label>		
   			<div class="input-group spinner">
    			<input type="text" class="form-control" value="25" data-min-value="10" data-max-value="50" name="demo1">
    			<div class="input-group-btn-vertical">
	      			<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>
	      			<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>
	    		</div>
  			</div> -->

				<br />

				<!-- <label for="instance" class="control-label">Number of Population'individuals:<br/></label>		
   			<div class="input-group spinner">
    			<input type="text" class="form-control" value="150" data-min-value="20" data-max-value="500" name="demo2">
    			<div class="input-group-btn-vertical">
	      			<button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>
	      			<button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>
	    		</div>
  			</div>
  			
  			
			</form>
			<br /> -->







				<!-- <div class="col-xs-4">
				<label for="runs" class="control-label">Number of Runs:<br/></label> <input class="form-control"
					type="text" name="runs" />
			</div>
			<div class="col-xs-4">
				<label for="population_size" class="control-label">Population size:<br/></label> <input
					type="text" name="population_size" />
			</div>
			<div class="col-xs-4">
				<label for="evaluations" class="control-label">Evaluations:<br/></label> <input
					type="text" name="evaluations" />
			</div>
			<div class="col-xs-4">
				<label for="use_rmsd_as_obj" class="control-label">RMSD to optimize:<br/></label> <input
					type="text" name="use_rmsd_as_obj" />
			</div> -->



				<%@ include file="/WEB-INF/jsp/footer.jsp"%>

</form>

			</div>
			<br />
</body>

<script src="resources/js/taskJavaScript.js"></script>

</html>
