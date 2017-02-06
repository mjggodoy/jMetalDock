package es.uma.khaos.docking_service.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.mysql.jdbc.StringUtils;
import es.uma.khaos.docking_service.properties.Constants;

@Path("/task3/")
public class TaskRunResource2 extends Application {

	@POST
	@Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
		public Response postPatameterTask(@QueryParam("algorithm") String algorithm, @QueryParam("runs") String  runs,
				@QueryParam("population_size") String population_size, @QueryParam("evaluations") String evaluations ) {
		
		int runs_param = Integer.parseInt(runs);
		int population_size_param = Integer.parseInt(population_size);
		int evaluations_param = Integer.parseInt(evaluations);

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
						
						population_size = inRangeCheck(
								population_size_param,
								Constants.DEFAULT_MIN_NUMBER_POPULATION_SIZE,
								Constants.DEFAULT_MAX_NUMBER_POPULATION_SIZE);
					}
					
					if (StringUtils.isNullOrEmpty(evalsParam)) {
						
						evaluations_param = Constants.DEFAULT_NUMBER_EVALUATIONS;
						
					} else {
						
						evaluations_param = inRangeCheck(
								evaluations_param,
								Constants.DEFAULT_MIN_NUMBER_EVALUATIONS,
								Constants.DEFAULT_MAX_NUMBER_EVALUATIONS);
					}

				
				}
			
			} catch (Exception e) {
			
				e.printStackTrace();
				return Response.serverError().build();
		}
	}
	
}
