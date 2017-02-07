package es.uma.khaos.docking_service.resource;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.mysql.jdbc.StringUtils;

import es.uma.khaos.docking_service.autodock.WorkerThread;
import es.uma.khaos.docking_service.model.ParameterSet;
import es.uma.khaos.docking_service.model.Task;
import es.uma.khaos.docking_service.properties.Constants;
import es.uma.khaos.docking_service.service.DatabaseService;
import es.uma.khaos.docking_service.service.ThreadPoolService;

@Path("/task3/")
public class TaskRunResource2 extends Application {
	
	
	private Response launchTask(int popSize, int evals, int runs, String algorithm, int objectiveOpt) throws Exception {

		Random sr = SecureRandom.getInstance("SHA1PRNG");
		String token = new BigInteger(130, sr).toString(32);

		Task task = DatabaseService.getInstance().insertTask(token);
		ParameterSet parameters = DatabaseService.getInstance().insertParameter(algorithm, evals, popSize, runs, objectiveOpt,
						task.getId());
		task.setParameters(parameters);
		Runnable worker = new WorkerThread("DOCKING", task.getId(), algorithm,
				runs, popSize, evals, objectiveOpt);
		ThreadPoolService.getInstance().execute(worker);
		
		System.out.println("Parameters: " + task.getParameters().getAlgorithm() + " " +	
				task.getParameters().getEvaluation() + " " + task.getParameters().getPopulationSize() 
				+ " " + task.getParameters().getRun());
		
		return Response.ok(task).build();


	}

	private int inRangeCheck(int value, int minValue, int maxValue) {
		if (value > maxValue) return maxValue;
		else if (value < minValue) return minValue;
		else return value;
	}
	
	
	@POST
	@Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
		public Response postPatameterTask(@QueryParam("algorithm") String algorithm, @QueryParam("runs") String  runs,
				@QueryParam("population_size") String population_size, @QueryParam("evaluations") String evaluations ) {
		
		int runs_param = Integer.parseInt(runs);
		int population_size_param = Integer.parseInt(population_size);
		int evaluations_param = Integer.parseInt(evaluations);
		int objectiveOpt = 0;

			try{
				
				if (StringUtils.isNullOrEmpty(algorithm)) {
				
					return Response.status(Response.Status.EXPECTATION_FAILED).entity(Constants.RESPONSE_MANDATORY_PARAMETER_ERROR).build();			
			
				}else{
					
					if (StringUtils.isNullOrEmpty(runs)) {
						
						runs_param = Constants.DEFAULT_NUMBER_RUNS;
						
					} else {
						runs_param = inRangeCheck(runs_param,
								Constants.DEFAULT_MIN_NUMBER_RUNS,
								Constants.DEFAULT_MAX_NUMBER_RUNS);
					}
							
					
					if (StringUtils.isNullOrEmpty(population_size)) {
						
						population_size_param = Constants.DEFAULT_NUMBER_POPULATION_SIZE;
						
					} else {
						
						population_size_param = inRangeCheck(
								population_size_param,
								Constants.DEFAULT_MIN_NUMBER_POPULATION_SIZE,
								Constants.DEFAULT_MAX_NUMBER_POPULATION_SIZE);
					}
					
					if (StringUtils.isNullOrEmpty(evaluations)) {
						
						evaluations_param = Constants.DEFAULT_NUMBER_EVALUATIONS;
						
					} else {
						
						evaluations_param = inRangeCheck(
								evaluations_param,
								Constants.DEFAULT_MIN_NUMBER_EVALUATIONS,
								Constants.DEFAULT_MAX_NUMBER_EVALUATIONS);
					}
					
					return launchTask(population_size_param, evaluations_param, runs_param, algorithm, objectiveOpt);

				}
			
			} catch (Exception e) {
			
				e.printStackTrace();
				return Response.serverError().build();
		}
			
	}
	
}
