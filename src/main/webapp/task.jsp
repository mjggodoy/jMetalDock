<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<%@ include file="/WEB-INF/jsp/headerHtml.jsp"%>
	<script src=vendors/bootstrap-spinner/bootstrap-spinner.js></script>
	<script src=vendors/mousehold/mousehold.js></script>
</head>

<body>

	<jsp:include page="/WEB-INF/jsp/header.jsp">
		<jsp:param name="page" value="task" />
	</jsp:include>

	<div class="container">

		<div class="panel panel-default">
			<div class="panel-body">

				<div class="page-header">
					<h2>New task</h2>
				</div>

				<form id="newTaskForm" class="form-horizontal" name="newTaskForm" action="rest/task" method="post" enctype="multipart/form-data">

					<div class="form-group">
						<!--<strong>Choose a file in your directory to be processed or an instance from the benchmark:</strong>-->
						<label for="optInput" class="control-label col-sm-4">Input option</label>
						<div class="col-md-5 col-sm-6">
							<select class="form-control" id="optInput">
								<option value="instance">Instance File</option>
								<option value="file">Input File</option>
							</select>
						</div>
					</div>

					<div id="inputFile" class="form-group" style="display:none;">
						<div class="col-sm-offset-4 col-sm-6 col-md-5">
							<div class="form-group-dependant">
								<label for="file" class="control-label label-dependant">File Input:</label>
								<input type="file" id="file" name="file">
								<p class="help-block">
									Please, select a .zip in which all your
									macromolecule and ligand files are prepared (.pdbqt) and also the
									output files from AutoGrid software (.map).
									<br/>
									Read the <a href='<c:url value="/tutorial.jsp" />'>documentation</a> to better
									understand which files should be provided inside your zip file.
								</p>
							</div>
						</div>
					</div>

					<div id="inputInstance" class="form-group" style="display:none;">
						<div class="col-sm-offset-4 col-sm-6 col-md-5">
							<div class="form-group-dependant">
								<label for="instance" class="control-label label-dependant">Instance:</label>
								<select class="form-control" name="instance" id="instance">

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
										<option value="3aid">3aid</option>
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
										<option value="1hpx">1hpx</option>
										<option value="1htg">1htg</option>
										<option value="1hvi">1hvi</option>
										<option value="1hvj">1hvj</option>
										<option value="1hvk">1hvk</option>
										<option value="1hvl">1hvl</option>
										<option value="1hvs">1hvs</option>
										<option value="1hwr">1hwr</option>
										<option value="1ody">1ody</option>
										<option value="1vij">1vij</option>
										<option value="1vik">1vik</option>
										<option value="3tlh">3tlh</option>
									</optgroup>

									<optgroup label="Cyclic Urea Inhibitors">
										<option value="1ajv">1ajv</option>
										<option value="1bv7">1bv7</option>
										<option value="1bv9">1bv9</option>
										<option value="1bwa">1bwa</option>
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
					</div>

					<div class="form-group">
						<label for="algorithm" class="control-label col-sm-4">Algorithm:</label>
						<div class="col-md-5 col-sm-6">
							<select class="form-control" name="algorithm" id="algorithm">
								<optgroup label="MonoObjective algorithm">
									<option value="gGA">gGA</option>
									<option value="ssGA">ssGA</option>
									<option value="DE">DE (!)</option>
									<option value="PSO">PSO</option>
								</optgroup>
								<optgroup label="MultiObjective algorithm">
									<option value="NSGAII">NSGA-II</option>
									<option value="ssNSGAII">ssNSGA-II</option>
									<option value="GDE3">GDE3</option>
									<option value="SMPSO">SMPSO (!)</option>
									<option value="OMOPSO">&nbsp;&nbsp;OMOPSO</option>
									<option value="SMPSOD">&nbsp;&nbsp;SMPSOD</option>
									<option value="SMPSOhv">&nbsp;&nbsp;SMPSO (HV) (!)</option>
									<option value="SMPSO_COS">&nbsp;&nbsp;SMPSO (Cosine)</option>
									<option value="MOEAD">MOEA/D</option>
									<option value="SMSEMOA">SMS-EMOA</option>
								</optgroup>
							</select>
							<p class="help-block">
								Optimization algorithm to be used in your execution. Those which obtained better
								results in our studies are marked with (!).
							</p>
						</div>
					</div>

					<div id="monoobjective" class="form-group">
						<div class="col-sm-offset-4 col-sm-6 col-md-5">
							<div class="form-group-dependant">
								<label for="objective-single-opt" class="control-label label-dependant">Objective to Optimize:</label>
								<select	class="form-control" name="objective-single-opt" id="objective-single-opt">
									<option>Final Binding Energy</option>
								</select>
							</div>
						</div>
					</div>

					<div id="multiobjective" class="form-group">
						<div class="col-sm-offset-4 col-sm-6 col-md-5">
							<div class="form-group-dependant">
								<label for="objective-multi-opt" class="control-label label-dependant">Objectives to Optimize:</label>
								<select	class="form-control" name="use_rmsd_as_obj" id="objective-multi-opt">
									<option value="false">Intermolecular and Intramolecular Energy</option>
									<option value=true>Binding Energy and RMSD score</option>
								</select>
							</div>
						</div>
					</div>

					<div class="form-group">
						<label for="runs" class="control-label col-sm-4">Number of Runs</label>
						<div class="col-md-5 col-sm-6">
							<div class="input-group">
							<span class="input-group-btn">
								<button type="button" class="btn btn-default" data-value="-5" data-target="#runs"
										data-toggle="spinner" data-on="mousehold">
									<span class="glyphicon glyphicon-minus"></span>
								</button>
							</span>
								<input type="text" data-ride="spinner" name="runs" id="runs" class="form-control input-number"
									   value="25" data-min="1" data-max="50" />
								<span class="input-group-btn">
								<button type="button" class="btn btn-default" data-value="5" data-target="#runs"
										data-toggle="spinner" data-on="mousehold">
									<span class="glyphicon glyphicon-plus"></span>
								</button>
							</span>
							</div>
						</div>
					</div>

					<div class="form-group">
						<label for="population_size" class="control-label col-sm-4">Population size</label>
						<div class="col-md-5 col-sm-6">
							<div class="input-group">
								<span class="input-group-btn">
									<button type="button" class="btn btn-default" data-value="-5" data-target="#population_size"
											data-toggle="spinner" data-on="mousehold">
										<span class="glyphicon glyphicon-minus"></span>
									</button>
								</span>
								<input type="text" data-ride="spinner" name="population_size" id="population_size"
									   class="form-control input-number" value="250" data-min="50" data-max="500" />
								<span class="input-group-btn">
									<button type="button" class="btn btn-default" data-value="5" data-target="#population_size"
											data-toggle="spinner" data-on="mousehold">
										<span class="glyphicon glyphicon-plus"></span>
									</button>
								</span>
							</div>
						</div>
					</div>

					<div class="form-group">
						<label for="evaluations" class="control-label col-sm-4">Number of evaluations</label>
						<div class="col-md-5 col-sm-6">
							<div class="input-group">
								<span class="input-group-btn">
									<button type="button" class="btn btn-default" data-value="-1000" data-target="#evaluations"
											data-toggle="spinner" data-on="mousehold">
										<span class="glyphicon glyphicon-minus"></span>
									</button>
								</span>
								<input type="text" data-ride="spinner" name="evaluations" id="evaluations"
									   class="form-control input-number" value="1500000" data-min="1000" data-max="5000000" />
								<span class="input-group-btn">
									<button type="button" class="btn btn-default" data-value="5" data-target="#evaluations"
											data-toggle="spinner" data-on="mousehold">
										<span class="glyphicon glyphicon-plus"></span>
									</button>
								</span>
							</div>
						</div>
					</div>

					<div class="form-group">
						<label for="email" class="control-label col-sm-4">Email:</label>
						<div class="col-md-5 col-sm-6">
							<input type="text" class="form-control" id="email" name="email">
							<p class="help-block">
								Write your email only if you want to receive a notification when your task is finished.
								You will not receive any kind of spam from us.
							</p>
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-4 col-sm-10">
							<button type="submit" class="btn btn-primary">Submit</button>
						</div>
					</div>
				</form>
			</div>
		</div>

	</div>

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>

<script src="resources/js/task.js"></script>

</html>
