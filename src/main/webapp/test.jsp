<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.2/css/bootstrap-select.min.css" />
<link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" rel="stylesheet">
<%@ include file="WEB-INF/jsp/header.jsp"%>
</head>

<body>
	<div class="container">
		<h3>

			<strong>NEW TASK:<br /></strong>

		</h3>
			<form id="newTaskForm" name="newTaskForm" action="rest/task"
			method="post" , enctype="multipart/form-data">
			
			<div class="form-group">
				<label for="exampleInputFile">File input</label> <input type="file"
					class="form-control-file" id="exampleInputFile"
					aria-describedby="fileHelp"> <small id="fileHelp"
					class="form-text text-muted">This is some placeholder
					block-level help text for the above input. It's a bit lighter and
					easily wraps to a new line.</small>
			</div>
			</form>
			<br />

			
			
			<div class="form-group">
				<label for="algorithm" class="control-label">Algorithm:<br/> </label>
				<form>
				<select class="selectpicker">
  					<optgroup label="Picnic">
   					<option>Mustard</option>
    				<option>Ketchup</option>
    				<option>Relish</option>
 					</optgroup>
  					<optgroup label="Camping">
   					<option>Tent</option>
    				<option>Flashlight</option>
    				<option>Toilet Paper</option>
  					</optgroup>
				</select>

			</form>
			
					
			</div>
			<div class="form-group">
				<label for="instance" class="control-label">Instance:<br/></label> <input
					type="text" name="instance" />
			</div>
			<div class="form-group">
				<label for="runs" class="control-label">Number of Runs:<br/></label> <input
					type="text" name="runs" />
			</div>
			<div class="form-group">
				<label for="population_size" class="control-label">Population size:<br/></label> <input
					type="text" name="population_size" />
			</div>
			<div class="form-group">
				<label for="evaluations" class="control-label">Evaluations:<br/></label> <input
					type="text" name="evaluations" />
			</div>
			<div class="form-group">
				<label for="use_rmsd_as_obj" class="control-label">RMSD to optimize:<br/></label> <input
					type="text" name="use_rmsd_as_obj" />
			</div>
			
			<input type="submit" />
		</form>

	</div>

</body>
</html>
