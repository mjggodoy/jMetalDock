<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">

<head>
	<%@ include file="/WEB-INF/jsp/headerHtml.jsp"%>
</head>

<body>

	<jsp:include page="/WEB-INF/jsp/header.jsp">
		<jsp:param name="page" value="tutorial" />
	</jsp:include>

	<div class="container">

		<div class="panel panel-default">
			<div class="panel-body">

				<div class="page-header">
					<h2>Tutorial</h2>
				</div>
				
				
				<div class="row">
					<div class="col-md-12">
				
					<h3 id="Input">Input</h3>
					<p>The user uploads the following set of files that includes:</p>
				<ol>

					<li>The ligand in PDBQT format. This format is widely known by
						users that work on molecular docking simulations (see <a 
						href="http://autodock.scripps.edu/faqs-help/faq/what-is-the-format-of-a-pdbqt-file" target="_blank">How to prepare a PDBQT file</a>
						for further details about this format)
					</li>

					<li>The macromolecule in PDBQT format if the user wants to
						visualize the macromolecule-ligand complex in the viewer provided
						by jMetalDock. The macromolecule's PDBQT file is larger (protein)
						than the ligand's PDBQT file (the ligand's PDBQT file represents a
						chemical compound or peptide). Further specifications about PDBQT
						format are available at <a
						href="http://autodock.scripps.edu/faqs-help/faq/what-is-the-format-of-a-pdbqt-file" target="blank">How to prepare a PDBQT file</a>.
					</li>

					<li>If flexibility is applied to the side chains of the
						macromolecule's residues, the PDBQT file with the specified
						flexible residues should be submitted. A more detailed explanation about how to create the PDBQT file is available at: <a
						href="http://autodock.scripps.edu/faqs-help/how-to/how-to-prepare-a-flexible-residue-file-for-autodock4" target="_blank">
						How to prepare a PDBQT flexible file</a>
						</li>
						

					<li>The .map files calculated by AutoGrid4 must be included
						with the rest of files. For further details, see the link: 
						<a href="http://autodock.scripps.edu/faqs-help/how-to/how-to-prepare-a-grid-parameter-files-for-autogrid4" target="_blank">
						How to prepare a grid parameter file and generate the .map files executing AutoGrid4</a>
					</li>

					<li>A DPF (docking parameter file) file where the name of the macromolecule, ligand and
						flexible macromolecule must be included.
					<a href="http://autodock.scripps.edu/faqs-help/how-to/how-to-prepare-a-docking-parameter-file-for-autodock4-1" target="_blank">How to prepare a DPF file</a>.</li>

					<li>All the files specified above must be compressed into a
						.zip file. Modifications depending on the algorithm and parameters
						chosen by the user will be lately included on the server.</li>
				</ol>
				<p>
					If the user does not provide the .zip with all the files, in the
					instance file field of the Task form, a list of instances from the
					benchmark can be selected. These instances are a set of problems
					used as benchmark by Morris <em>et al.</em> to validate the
					AutoDock 4.2 energy function <a href="#Morris">[1]</a>. The
					instances are a set of HIV-proteases and inhibitors with different
					sizes (small, medium, large size and urea cycle inhibitors). A more
					detailed description about this benchmark is specified in Morris' et <em>al.</em>
					publication.
				</p>
				

				<h3>How to Submit a Job</h3>
				
				<p>To submit a job to the jMetalDock server, the user has to
					fill a form. This form provides several options that are chosen by the
					users. These options are specified as folows:</p>
				<br />
				
				
				<div class="col-md-6">
						<a href="#" id="pop2">
							<img id= "form" class= "medium-size" src="resources/img/form.png" alt="form1"></img>
						</a>

				<!-- Creates the bootstrap modal where the image will appear -->
				<div class="modal fade" id="imagemodal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">
											<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
										</button>
										<h4 class="modal-title" id="myModalLabel">Form when user selects an instance from a list:</h4>
									</div>
									<div class="modal-body">
										<img src="resources/img/form.png" id="imagepreview" class="img-responsive"></img>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
							</div>
						</div>
					</div>
				</div>
			</div>
				
			

				<div class="col-md-6">
					<a href="#" id="pop3">
						<img id= "form2" class= "medium-size" src="resources/img/form2.png" alt="form2"></img>
					</a>
				
				<!-- Creates the bootstrap modal where the image will appear -->
				<div class="modal fade" id="imagemodal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">
											<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
										</button>
										<h4 class="modal-title" id="myModalLabel2">Form when user uploads a .zip file</h4>
									</div>
									<div class="modal-body">
										<img src="resources/img/form2.png" id="imagepreview" class="img-responsive"></img>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
							</div>
						</div>
					</div>
				</div>
				</div>
			

				<dl class="dl-horizontal">

					<dt>A.</dt>
					<dd>The input file option is the .zip file or the
						instance chosen by the user. The left image shows the case where
						user chooses one instance from the list provided by the jMetalDock
						server. The image in the right shows the case where user
						has to upload a .zip file with all files described above (see
						<a href="#Input">Input</a> section).</dd>
					<dt>B.</dt>
					<dd>
						The user can choose an instance from the list provided by
						jMetalDock. These instances are a set of ligand-protein complexes
						used in some molecular docking simulations to validate new algorithms 
						or new energy scoring functions such as is shown in <a
							href="#Morris">[1]</a>. The instance already contains all the
						input files to run a mono- or multi-objective algorithm.
					</dd>
					<dt>C.</dt>
					<dd>
						The list of algorithms that jMetalDock provides. This list has
						divided into two groups: the mono- and multi-objective algorithms.
						The first group contains standard genetic algorithms such as gGA
						(the Generational Genetic Algorithm), ssGA (State-Steady Genetic
						Algorithm), DE (Differential Evolution Algorithm) and PSO
						(Particle Swarm Optimization). According to the results obtained
						in <a href="#Camacho">[2]</a>, DE obtains the best overall results
						compared to other mono-objective metaheuristics applied to the
						AutoDock 4.2 benchmark. However, depending on the problem the user
						wants to solve, we recommend to test with more than one algorithm.
						The second group includes all the multi-objective algorithms.
						These are NSGA-II (Non-Dominated Sorting Genetic Algorithm),
						ssNSGA-II (steady-state Non Dominated Sorting Genetic Algorithm),
						GDE3 (Generalized Differential Evolution), SMPSO
						(Speed-constrained Multi-objective PSO) and its four new variants
						(SMPSO-hv, SMPSOD, SMPSOC, OMOPSO), MOEA/D (Multi-objective
						Evolutionary Algorithm Based on Decomposition) and SMS-EMOA
						(steady-state EMOA).
					</dd>
					<dt>D.</dt>
					<dd>If the user chooses the mono-objective approach, the
						objective is to minimize the final binding energy (represented as &Delta;G). 
						If the option corresponds with the multi-objective approach, the objectives to
						optimize will be the intermolecular/intramolecular energies or the
						ligand-macromolecule binding energy/RMSD score.</dd>
					<dt>E.</dt>
					<dd>This option refers to the number of runs set by the user.
						The recommended number is between 50-100 runs. More executions
						than 100 is not very recommendable.</dd>
					<dt>F.</dt>
					<dd>The population size refers to the initial number of
						population's individuals (or particles in case the selected algorithm is the
						PSO). Each individual represents the codified solution, which includes translation coordenates,
						rotation and torsions associated to the macromolecule and ligand.</dd>
					<dt>G.</dt>
					<dd>
						The number of evaluations. It equals to the multiplication between
						the number of individuals of the population and the number of
						iterations. In the mono-objective approach, in <a href="#Camacho">[2]</a>,
						the study shows the algorithm's convergence behavior revealing that 1.500.000
						is the number of evaluations where the algorithm finds the best
						solutions.
					</dd>
					<dt>H.</dt>
					<dd>The user's email where the jMetalDock notifies the status
						of the process that was launched.</dd>
				</dl>
				
				

				<h3>Output</h3>
					

				<p>If the task was completed, jMetalDock will notify the results
					obtained from the simulation, providing a URL with the results:</p>
					
					
				<div class="col-md-9">
					<a href="#" id="pop4">
						<img id= "results" class= "medium-size" src="resources/img/results.png" alt="results"></img>
					</a>
				
				
				
				<!-- Creates the bootstrap modal where the image will appear -->
				<div class="modal fade" id="imageresults" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">
											<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
										</button>
										<h4 class="modal-title" id="myModalLabel2">Task table from results:</h4>
									</div>
									<div class="modal-body">
										<img src="resources/img/results.png" id="imagepreview" class="img-responsive"></img>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
							</div>
						</div>
					</div>
				</div>
				<br/>
				</div>
				<div class="col-md-12">
				
					<p>The image above shows the task's status, the start and end times,
					the task's ID, task's hash and the user's email. The interface shows some
					buttons in the Action field: "Go to results" and "Download Docking
					Log File". The first option refers to the set of results obtained
					for each run. The image below shows how the results are displayed
					when the user clicks on "Go to results". The second option allows
					users to visualize the .dlg file through the ADT suite (<a
						href="http://autodock.scripps.edu/resources/adt" target="_blank">AutoDockTools</a>).
					In the parameters section, the fields correspond to the instance's
					name, the selected algorithm and settings (e.g. population or swarm
					size, number of runs and evaluations) and the objectives to
					optimize (in the example, the objectives that were optimized correspond with the
					intermolecular and intramolecular energies).
					</p>
				</div>
				
				<br/>
				
				<div class="col-md-9">
				
					<a href="#" id="pop6">
						<img id= "results" class= "medium-size" src="resources/img/table.png" alt="results"></img>
					</a>
				
				
				<!-- Creates the bootstrap modal where the image will appear -->
				<div class="modal fade" id="imagetable" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">
											<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
										</button>
										<h4 class="modal-title" id="myModalLabel2">Table with the results obtained from the molecular docking simulations</h4>
									</div>
									<div class="modal-body">
										<img src="resources/img/table.png" id="imagepreview" class="img-responsive"></img>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
							</div>
						</div>
					</div>
				</div>
				
				<br/>
				<br/>
				</div>
				
				<div class="col-md-12">
				
				<p>This image shows a table with the results obtained from the
					molecular docking simulations. The table contains a column with all
					runs, the task's ID (result per ID), the final binding energy, the
					objectives that were optimized (in this case the intermolecular and
					intramolecular energy) and the RMSD value associated with the
					solution. The results' page also shows two options: "the minimum
					final binding energy" and "the minimum RMSD score". If the user
					clicks on the "run" option, all the solutions from this run are shown
					in the page. If the user clicks on the ID of each solution,
					information about this specific solution is shown (see the image below):</p>
				<br /> 
				</div>
				
				<div class="col-md-9">
				
					<a href="#" id="pop7">
						<img id= "results" class= "medium-size" src="resources/img/solution.png" alt="results"></img>
					</a>
				<!-- Creates the bootstrap modal where the image will appear -->
				<div class="modal fade" id="imageuniquesolution" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">
											<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
										</button>
										<h4 class="modal-title" id="myModalLabel2">Results from a solutions selected by the user.</h4>
									</div>
									<div class="modal-body">
										<img src="resources/img/solution.png" id="imagepreview" class="img-responsive"></img>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
							</div>
						</div>
					</div>
				</div>
				<br/>
				</div>
				
				<div class="col-md-12">
				
				<p>In the results' page, the pareto font is plotted when the
					user chooses the multi-objective option to run the molecular docking simulations. In the image below, the
					pareto font represents the set of solutions. In this example,
					there are solutions that have higher or lower intermolecular and
					intramolecular energy values. The user can pick up a solution to be analyzed.</p>
				
				</div>
				
				
			<div class="col-md-9">
				
					<a href="#" id="pop8">
						<img id= "results" class= "medium-size" src="resources/img/graphics.png" alt="results"></img>
					</a>
				
				
				<div class="modal fade" id="graphics" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">
											<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
										</button>
										<h4 class="modal-title" id="myModalLabel2">Set of solutions plotted as a font. The inter-/intramolecular enegies are represented in Y and X axis.</h4>
									</div>
									<div class="modal-body">
										<img src="resources/img/graphics.png" id="imagepreview" class="img-responsive"></img>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
							</div>
						</div>
					</div>
				</div>
				<p>The user can also visualize a solution. 
				For doing that, the users have to submit the macromolecule's PDBQT file:</p>
			
			
				
					<a href="#" id="pop9">
						<img id= "results" class= "medium-size" src="resources/img/complexVisualization.png" alt="results"></img>
					</a>
				
				<div class="modal fade" id="complexVisualization" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">
											<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
										</button>
										<h4 class="modal-title" id="myModalLabel2">Visualization of the macomolecule-computed ligand complex.</h4>
									</div>
									<div class="modal-body">
										<img src="resources/img/complexVisualization.png" id="imagepreview" class="img-responsive"></img>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
							</div>
						</div>
					</div>
				</div>
				


				<h3>References</h3>
				<div class="col-md-12">

				<ol>
					<li id="Morris">Morris, Garrett M. and Huey, Ruth and
						Lindstrom, William and Sanner, Michel F. and Belew, Richard K. and
						Goodsell, David S. and Olson, Arthur J. AutoDock4 and
						AutoDockTools4: Automated docking with selective receptor
						flexibility<em>J. Comput. Chem.</em> 30(16): 2785--2791 <a
						href="https://www.ncbi.nlm.nih.gov/pubmed/19399780" target="_blank">doi:10.1002/jcc.21256</a>
					</li>
					<li id="Camacho">Esteban L&oacute;pez-Camacho, Mar&iacute;a
						Jes&uacute;s Garc&iacute;a Godoy, Jos&eacute; Garc&iacute;a-Nieto,
						Antonio J. Nebro and Jos&eacute; F. Aldana-Montes: Solving
						molecular flexible docking problems with metaheuristics: A
						comparative study <em>Applied Soft Computing</em> 28:379-393 <a
						href="http://dx.doi.org/10.1016/j.asoc.2014.10.049" target="_blank">doi:10.1016/j.asoc.2014.10.049</a>
					</li>
				</ol>
			</div>
		</div>	
		</div>
	</div>
	</div>
</div>
</div>

	

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>

<script src="resources/js/imageSpan.js"></script>

</html>
