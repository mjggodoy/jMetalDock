package es.uma.khaos.docking_service.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.mysql.jdbc.StringUtils;
import es.uma.khaos.docking_service.autodock.WorkerThread;
import es.uma.khaos.docking_service.model.ParameterSet;
import es.uma.khaos.docking_service.model.Task;
import es.uma.khaos.docking_service.properties.Constants;
import es.uma.khaos.docking_service.service.DatabaseService;
import es.uma.khaos.docking_service.service.ThreadPoolService;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

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
		
		return Response.ok(task).build();


	}
	
	
	public static void unzip(String nameFile){
	    String source = "/Users/mariajesus/Desktop/AutoDockInstance/"+ nameFile;
	    String destination = "/Users/mariajesus/Desktop/AutoDockInstance/";

	    try {
	         
	    	ZipFile zipFile = new ZipFile(source);
	        zipFile.extractAll(destination);
	    
	    } catch (ZipException e) {
	        e.printStackTrace();
	    }
	}

	
	
	protected void readFile(InputStream in) throws IOException{
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
        	System.out.println(line);
        }
        
        reader.close();	
	}
	

	private int inRangeCheck(int value, int minValue, int maxValue) {
		if (value > maxValue) return maxValue;
		else if (value < minValue) return minValue;
		else return value;
	}
	
	
	@POST
	@Path("/post")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
		public Response postPatameterTask(@QueryParam("algorithm") String algorithm,  
				@QueryParam("runs") @DefaultValue("30")  int runs,
				@QueryParam("population_size") @DefaultValue("150") int population_size, 
				@QueryParam("evaluations") @DefaultValue("1500000") int evaluations,
				@QueryParam("objectives") @DefaultValue("1") int objectiveOpt,
				@FormDataParam("file") InputStream file) throws IOException {
					
			readFile(file);
					
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
