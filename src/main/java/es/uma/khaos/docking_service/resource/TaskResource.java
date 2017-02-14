package es.uma.khaos.docking_service.resource;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.mysql.jdbc.StringUtils;

import es.uma.khaos.docking_service.autodock.WorkerThread;
import es.uma.khaos.docking_service.exception.DatabaseException;
import es.uma.khaos.docking_service.model.ParameterSet;
import es.uma.khaos.docking_service.model.Task;
import es.uma.khaos.docking_service.properties.Constants;
import es.uma.khaos.docking_service.response.JspResponseBuilder;
import es.uma.khaos.docking_service.response.PojoResponseBuilder;
import es.uma.khaos.docking_service.response.ResponseBuilder;
import es.uma.khaos.docking_service.service.DatabaseService;
import es.uma.khaos.docking_service.service.ThreadPoolService;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

@Path("/task")
public class TaskResource extends Application {
	
	private Response getTask(int id, String token, ResponseBuilder builder) {
		try{
			Task task = DatabaseService.getInstance().getTaskParameter(id);
			
			System.out.println(task);
			System.out.println(token);
			System.out.println(task.getHash());
			
			if (task == null || !task.getHash().equals(token)) {
				return Response.status(Response.Status.FORBIDDEN).entity(Constants.RESPONSE_TASK_MSG_UNALLOWED).build();			
			}else{
				return builder.buildResponse(task);
			}
		
		}catch (NumberFormatException e) {
			
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(Constants.RESPONSE_NOT_A_NUMBER_ERROR).build();
		
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
	}
	
	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response doGetAsJson(@NotNull @PathParam("id") int id,  @QueryParam("token") String token) throws DatabaseException {
		return getTask(id, token, new PojoResponseBuilder());
	}
	
	@GET
	@Path("/{id}")
    @Produces("text/html")
    public Response index(@NotNull @PathParam("id") int id, @QueryParam("token") String token) throws DatabaseException {
		// TODO: Sacar path del jsp a fichero de propiedades (PRIVATE)
		return getTask(id, token, new JspResponseBuilder("/task.jsp"));
    }
	
	// TODO: Ordenar la parte del POST
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
		
		return Response.ok(task).build();
	}
	
	
	public static void unzip(String nameFile){
	    String source = "/Users/mariajesus/Desktop/AutoDockInstance/"+ nameFile;
	    System.out.println("source: " + source);
	    String destination = "/Users/mariajesus/Desktop/AutoDockInstance/";

	    try {
	         
	    	ZipFile zipFile = new ZipFile(source);
	        zipFile.extractAll(destination);
	    
	    } catch (ZipException e) {
	        e.printStackTrace();
	    }
	}
	

	private int inRangeCheck(int value, int minValue, int maxValue) {
		if (value > maxValue) return maxValue;
		else if (value < minValue) return minValue;
		else return value;
	}
	
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
		public Response postPatameterTask(@QueryParam("algorithm") String algorithm,  
				@QueryParam("runs") @DefaultValue("30")  int runs,
				@QueryParam("population_size") @DefaultValue("150") int population_size, 
				@QueryParam("evaluations") @DefaultValue("1500000") int evaluations,
				@QueryParam("objectives") @DefaultValue("1") int objectiveOpt,
				@FormDataParam("file") FormDataContentDisposition file) throws IOException {
					
		
			String nameFile = file.getFileName();
			System.out.println("Name file: " + nameFile);
			unzip(nameFile);
					
			try{
				
				if (StringUtils.isNullOrEmpty(algorithm)) {
				
					return Response.status(Response.Status.EXPECTATION_FAILED).entity(Constants.RESPONSE_MANDATORY_PARAMETER_ERROR).build();			
			
				}else{
					
					
					runs = inRangeCheck(
							runs,
							Constants.DEFAULT_MIN_NUMBER_RUNS,
							Constants.DEFAULT_MAX_NUMBER_RUNS);
					
					population_size = inRangeCheck(
							population_size,
							Constants.DEFAULT_MIN_NUMBER_POPULATION_SIZE,
							Constants.DEFAULT_MAX_NUMBER_POPULATION_SIZE);
				
					evaluations = inRangeCheck(
								evaluations,
								Constants.DEFAULT_MIN_NUMBER_EVALUATIONS,
								Constants.DEFAULT_MAX_NUMBER_EVALUATIONS);
					
					return launchTask(population_size, evaluations, runs, algorithm, objectiveOpt);

				}
			
			} catch (Exception e) {
				
				Response.status(Response.Status.EXPECTATION_FAILED).entity(Constants.RESPONSE_MANDATORY_PARAMETER_ERROR).build();			
			
				//e.printStackTrace();
				return Response.serverError().build();
		}
			
	}
	
}
