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
				(see <a href="http://autodock.scripps.edu/faqs-help/faq/what-is-the-format-of-a-pdbqt-file" target="_blank">PDBQT</a> for further details about PDBQT format)
				</li>
			
				<li>The macromolecule in PDBQT format if the user wants to visualize the macromolecule-ligand 
				complex when the docking solution is obtained. The macro PDBQT file is larger (protein) than the ligand PDBQT file (chemical compound or peptide). 
				Further specifications about PDBQT format are available at <a href="http://autodock.scripps.edu/faqs-help/faq/what-is-the-format-of-a-pdbqt-file" target="_blank">PDBQT</a>.
				</li>
				
				<li>If flexibility is applied to the side chains of the macromolecule's residues, the PDBQT file with the specified flexible residues must be submitted.
				</li>
				
				<li>The .map files calculated by AutoGrid4 must be included with the rest of files.
				</li>
				
				<li>A DPF file where the name of the macromolecule, ligand and flexible macromolecule must be included.
				</li>
				
				<li> All the files specified above must be compressed into a zip file. Modifications depending on the algorithm and parameters chosen by the user will be lately included on the server. 
				</li>
				</ol>
				<p>If the user does not provide the .zip with all the files, in the instance file field of the Task form, a list of instances from the benchmark can be chosen.
				 These instances correspond to the benchmark used by Morris <em>et al.</em> to validate the AutoDock 4.2 energy function <a href="#Morris">[1]</a>. The instances are a set of HIV-proteases and inhibitors with different sizes (small, medium, large size and urea cycle inhibitors). 
				 </p>
				 <ol>
				 <li id="#Morris">Morris, Garrett M. and Huey, Ruth and Lindstrom, William and Sanner, Michel F. and Belew, Richard K. 
				 and Goodsell, David S. and Olson, Arthur J. AutoDock4 and AutoDockTools4: Automated docking with selective receptor flexibility<em>J. Comput. Chem.</em> 30(16): 2785--2791
								<a href="https://www.ncbi.nlm.nih.gov/pubmed/19399780" target="_blank">doi:10.1002/jcc.21256</a>
				</li>
				 </ol>
				<h3>How to Submit a Job</h3>
				
				To submit a job to the jMetalDock server, the user has to fill a form. This form provides several options to choose by the users and are specified as follows:
				<br/>
				<img src="resources/img/form.png" alt="Chania" width="420" height="400">
				<img src="resources/img/form2.png" alt="Chania" width="470" height="440">
				<br/>
				<br/>
				
				<dl class="dl-horizontal">

					 <dt>A.</dt>
					 <dd>The input file refers to the .zip file or the instance chosen by the user. The left image shows the case when user chooses an instance from the list of benchmark instances that jMetalDock server provides. 
					 The image on the right shows the case when user has to upload a .zip file with all files described in the Input section. </dd>
					 <dt>B.</dt>
					 <dd> The user can choose an instance from the list of instances provided by jMetalDock. The instance already contains all the input files to run the mono- or multi-objective algorithm.</dd>
					 <dt>C.</dt>
					 <dd>The list of algorithms that jMetalDock provides. This list has divided into two groups: the mono- and multi-objective algorithms. The first group contains the standard genetic algorithms known as gGA (the Generational Genetic Algorithm), ssGA (State-Steady Genetic Algorithm)
					 and DE (Differential Evolution Algorithm) and PSO (Particle Swarm Optimization). According to the results obtained in <a href="#Camacho">[2]</a>, DE obtains the best overall results compared to other mono-objective metaheuristics applied to the AutoDock 4.2 benchmark. 
					 However, depending on the problem the user wants to solve, we recommend to test with more than one algorithm. The second group includes all the multi-objective algorithms. These are NSGA-II (Non-Dominated Sorting Genetic Algorithm), ssNSGA-II (steady-state Non Dominated Sorting Genetic Algorithm), GDE3 (Generalized Differential Evolution), 
					 SMPSO (Speed-constrained Multi-objective PSO)
					 and its four new variants (SMPSO-hv, SMPSOD, SMPSOC, OMOPSO), MOEA/D (Multi-objective Evolutionary Algorithm Based on Decomposition) and SMS-EMOA (steady-state EMOA). </dd>
					 <dt>D.</dt>
					 <dd>If the user chooses the mono-objective approach, the objective to minimize will be the final binding energy. If the option corresponds with the multi-objective approach, 
					 the objectives to optimize will be the intermolecular/intramolecular energies or the ligand-macromolecule binding energy/RMSD score.</dd>
					 <dt>E.</dt>
					 <dd>This option refers to the number of molecular docking simulations runs that is set by the user. This number can be between the 50-100 executions. More executions than 100 are not very recommendable.</dd>
					 <dt>F.</dt>
					 <dd>The population size refers to the initial number of individuals (or particles in case of PSO) 
					 of a population (swarms in case of PSO). Each individual represents the codified solution (translation orientations, rotation and torsions associated to the macromolecule and ligand)</dd>
					<dt>G.</dt>
				    <dd>The number of evaluations refers to the number of evaluations of a given solution.
				     It equals to the multiplication between the individuals of the population and the iterations. 
				     In the mono-objective approach, in <a href="#Camacho"></a>, a study based on the convergence behavior evaluations/best solution found in terms of energy revealed that
				     1.500.000 is the number of evaluations where the algorithm finds the best solutions.</dd>
					<dt>H.</dt>
					<dd>The user's email where the jMetalDock notifies the status of the process that was launched. </dd>
				</dl>
				
				 <ol start="2">
				<li id="#Camacho">Esteban L&oacute;pez-Camacho, Mar&iacute;a Jes&uacute;s Garc&iacute;a Godoy, Jos&eacute;
				Garc&iacute;a-Nieto, Antonio J. Nebro and Jos&eacute; F. Aldana-Montes: Solving molecular flexible docking problems with metaheuristics: A
				comparative study.<em>Applied Soft Computing</em> 28:379-393
				<a href="http://dx.doi.org/10.1016/j.asoc.2014.10.049" target="_blank">doi:10.1016/j.asoc.2014.10.049</a>
				</li>
				</ol>
				
				<h3>Output</h3>
				

			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>
</html>
