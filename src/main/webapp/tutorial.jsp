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
				
				<h3>Input</h3>
				<p>
				The user uploads the following set of files that includes:
				</p>
				<ol>
				
				<li>The ligand in PDBQT format, which is widely 
				known by users that work on molecular docking simulations 
				(see <a href="http://autodock.scripps.edu/faqs-help/faq/what-is-the-format-of-a-pdbqt-file" target="_blank">PDBQT</a> for further details about this format)
				</li>
			
				<li>The macromolecule in PDBQT format if the user wants to visualize the macromolecule-ligand 
				complex. The macromolecule's PDBQT file is larger (protein) than the ligand's PDBQT file (that is a chemical compound or peptide). 
				Further specifications about PDBQT format are available at <a href="http://autodock.scripps.edu/faqs-help/faq/what-is-the-format-of-a-pdbqt-file" target="_blank">PDBQT</a>.
				</li>
				
				<li>If flexibility is applied to the side chains of the macromolecule's residues, the PDBQT file with the specified flexible residues should be submitted.
				</li>
				
				<li>The .map files calculated by AutoGrid4 must be included with the rest of files.
				</li>
				
				<li>A DPF file where the name of the macromolecule, ligand and flexible macromolecule must be included.
				</li>
				
				<li> All the files specified above must be compressed into a .zip file. Modifications depending on the algorithm and parameters chosen by the user will be lately included on the server. 
				</li>
				</ol>
				<p>If the user does not provide the .zip with all the files, in the instance file field of the Task form, a list of instances from the benchmark can be chosen.
				 These instances are a set of problems used as benchmark by Morris <em>et al.</em> to validate the AutoDock 4.2 energy function <a href="#Morris">[1]</a>. The instances are a set of HIV-proteases and inhibitors with different sizes (small, medium, large size and urea cycle inhibitors). 
				 </p>
				 
				<h3>How to Submit a Job</h3>
				
				To submit a job to the jMetalDock server, the user has to fill a form. This form provides several options to choose by the users. The options in the form are the following:
				<br/>
				<img src="resources/img/form.png" alt="Chania" width="450" height="440">
				<img src="resources/img/form2.png" alt="Chania" width="400" height="440">
				<br/>
				<br/>
				
				<dl class="dl-horizontal">

					 <dt>A.</dt>
					 <dd>The input file option refers to the .zip file or the instance chosen by the user. The left image shows the case when user chooses one instance from the benchmark that jMetalDock server provides. 
					 The image on the right shows the case when user has to upload a .zip file with all files described above (see Input section). </dd>
					 <dt>B.</dt>
					 <dd> The user can choose an instance from the list provided by jMetalDock. These instances are a set of ligand-protein complexes used in the literature to validate molecular docking simulations <a href="#Morris">[1]</a>.
					  The instance already contains all the input files to run the mono/multi-objective algorithm.</dd>
					 <dt>C.</dt>
					 <dd>The list of algorithms that jMetalDock provides. This list has divided into two groups: the mono- and multi-objective algorithms. The first group contains the standard genetic algorithms such as gGA (the Generational Genetic Algorithm), ssGA (State-Steady Genetic Algorithm)
					 and DE (Differential Evolution Algorithm) and PSO (Particle Swarm Optimization). According to the results obtained in <a href="#Camacho">[2]</a>, DE obtains the best overall results compared to other mono-objective metaheuristics applied to the AutoDock 4.2 benchmark. 
					 However, depending on the problem the user wants to solve, we recommend to test with more than one algorithm. The second group includes all the multi-objective algorithms. These are NSGA-II (Non-Dominated Sorting Genetic Algorithm), ssNSGA-II (steady-state Non Dominated Sorting Genetic Algorithm), GDE3 (Generalized Differential Evolution), 
					 SMPSO (Speed-constrained Multi-objective PSO)
					 and its four new variants (SMPSO-hv, SMPSOD, SMPSOC, OMOPSO), MOEA/D (Multi-objective Evolutionary Algorithm Based on Decomposition) and SMS-EMOA (steady-state EMOA). </dd>
					 <dt>D.</dt>
					 <dd>If the user chooses the mono-objective approach, the objective to minimize is the final binding energy. If the option corresponds with the multi-objective approach, 
					 the objectives to optimize will be the intermolecular/intramolecular energies or the ligand-macromolecule binding energy/RMSD score.</dd>
					 <dt>E.</dt>
					 <dd>This option refers to the number of molecular docking simulations runs set by the user. The recommended number is between 50-100 runs. More executions than 100 is not very recommendable.</dd>
					 <dt>F.</dt>
					 <dd>The population size refers to the initial number of individuals (or particles in case the selected algorithm is the PSO) 
					 of a population (or particles in case the selected algorithm is the PSO). Each individual represents the codified solution (translation coordinates, rotation and torsions associated to the macromolecule and ligand)</dd>
					<dt>G.</dt>
				    <dd>The number of evaluations. It equals to the multiplication between the individuals of the population and the iterations. 
				     In the mono-objective approach, in <a href="#Camacho">[2]</a>, a study based on the convergence behavior showed revealed that
				     1.500.000 is the number of evaluations where the algorithm finds the best solutions.</dd>
					<dt>H.</dt>
					<dd>The user's email where the jMetalDock notifies the status of the process that was launched. </dd>
				</dl>
				
				<h3>Output</h3>
				
				If the task was completed, jMetalDock will notify the results obtained from the simulation, providing a URL with the results:
				<br/>
				<br/>
				<img src="resources/img/results.png" alt="Chania" width="700" height="450">
				<br/>	
				<br/>		
				<p>The image above shows the task's status, the start and end times, the task's id and its hash associated with the corresponding task and the user's email. 
				The interface shows the user some options: "Go to results"  and "Download Docking Log File". The second option allows user 
				to visualize the .dlg by using the ADT suite (<a href="http://autodock.scripps.edu/resources/adt" target="_blank">AutoDockTools</a>). 
				In the parameters section, the fields correspond to
				the instance's name, the selected algorithm and settings (e.g. population or swarm size, number of runs and evaluations) and the objectives to optimize (in the example, 
				the objectives to optimize are the inter/intramolecular energies). The following image shows how the results are displayed when the user clicks on "Go to results": 
				</p>
				<br/>
				<img src="resources/img/resultsTable.png" alt="Chania" width="700" height="500">
				<br/>
				<br/>
				<p>This image shows a table with the results obtained from the molecular docking simulations. The table contains the column run, ID (result per ID), the final binding energy, 
				the objectives that were optimized (in this case the intermolecular and intramolecular energy) and the RMSD value associated to the solution. 
				The results' page  also show two options: "the minimum final binding energy" and "the minimum RMSD score". If the user clicks on the "run" option, all the solutions of this run are shown in the page.
				If the user clicks on the ID of each solution, information about this specific solution is shown. The image below shows the information about a specific solution.
				</p>
				<br/>
				<img src="resources/img/solution.png" alt="Chania" width="700" height="500">
				<br/>
				<br/>
				
				<p>In the results' page, the pareto font is displayed when the user chooses the multi-objective option. In the image below, the pareto font is shown with the set of solutions. In this example, there are solutions that have higher or lower intermolcular energy values. 
				The user can pick up a solution. <p/>
				<img src="resources/img/graphics.png" alt="Chania" width="700" height="500">
				<br/>
				<br/>
				
				The user can also visualize a solution. For doing that, the users had to submit the macromolecule's PDBQT file:
				
				<img src="resources/img/complexVisualization.png" alt="Chania" width="700" height="500">
				
				
				<h3>References</h3>
				
				 <ol>
				    <li id="Morris">Morris, Garrett M. and Huey, Ruth and Lindstrom, William and Sanner, Michel F. and Belew, Richard K. 
				 	and Goodsell, David S. and Olson, Arthur J. AutoDock4 and AutoDockTools4: Automated docking with selective receptor flexibility<em>J. Comput. Chem.</em> 30(16): 2785--2791
					<a href="https://www.ncbi.nlm.nih.gov/pubmed/19399780" target="_blank">doi:10.1002/jcc.21256</a>
					</li>
					<li id="Camacho">Esteban L&oacute;pez-Camacho, Mar&iacute;a Jes&uacute;s Garc&iacute;a Godoy, Jos&eacute;
					Garc&iacute;a-Nieto, Antonio J. Nebro and Jos&eacute; F. Aldana-Montes: Solving molecular flexible docking problems with metaheuristics: A
					comparative study <em>Applied Soft Computing</em> 28:379-393
					<a href="http://dx.doi.org/10.1016/j.asoc.2014.10.049" target="_blank">doi:10.1016/j.asoc.2014.10.049</a>
					</li>
				 </ol>
				 
				 
				

			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>
</html>
