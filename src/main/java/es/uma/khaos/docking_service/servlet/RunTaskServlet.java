package es.uma.khaos.docking_service.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;

import es.uma.khaos.docking_service.exception.DatabaseException;
import es.uma.khaos.docking_service.model.Execution;
import es.uma.khaos.docking_service.model.Result;
import es.uma.khaos.docking_service.model.Task;
import es.uma.khaos.docking_service.properties.Constants;
import es.uma.khaos.docking_service.service.DatabaseService;

/**
 * Servlet implementation class RunTaskServlet
 */
public class RunTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RunTaskServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			 
    	
		Task task= null;
		String id = request.getParameter("id");
		String token = request.getParameter("token");
		String run = request.getParameter("run");
		Result result= null;
		List<Execution> executionList = new ArrayList<Execution>();
		List <Result> resultList = new ArrayList<Result>();
		

		response.setContentType("application/json");

		try {
			
			if (StringUtils.isNullOrEmpty(id)) {
				
				response.sendError(
				HttpServletResponse.SC_BAD_REQUEST,
				String.format(Constants.RESPONSE_MANDATORY_PARAMETER_ERROR, "id"));
			
			}else{
				
				
				if(StringUtils.isNullOrEmpty(run)){
				
					int idTask = Integer.parseInt(id);		
					task = DatabaseService.getInstance().getTask(idTask);
					executionList = DatabaseService.getInstance().getExecutionByTaskId(idTask);
				
					for(Execution element : executionList){
					
						resultList.add(DatabaseService.getInstance().getResultByExecutionId(element.getId()));			
				
					}
				
				}else{
					
					int idTask = Integer.parseInt(id);		
					int runTask = Integer.parseInt(run);
					task = DatabaseService.getInstance().getTask(idTask);
					result = DatabaseService.getInstance().getResultByTaskIdAndRun(idTask, runTask);
					
				}
			
				if (task==null || !task.getHash().equals(token)) {
					response.sendError(HttpServletResponse.SC_FORBIDDEN, Constants.RESPONSE_TASK_MSG_UNALLOWED);
					task = null;
				} else {
					response.setStatus(HttpServletResponse.SC_OK);
				}
				
			}
				
		} catch (NumberFormatException e) {
			response.sendError(
					HttpServletResponse.SC_BAD_REQUEST,
					String.format(Constants.RESPONSE_NOT_A_NUMBER_ERROR, "id"));
		} catch (DatabaseException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Constants.RESPONSE_ERROR_DATABASE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		if(task!=null && run == null){
			
			Gson gson = new Gson();
			PrintWriter out = response.getWriter();
			out.print(gson.toJson(resultList));
			out.flush();
			
		}
		
		
		if(task!=null && run!=null){
			
			Gson gson = new Gson();
			PrintWriter out = response.getWriter();
			out.print(gson.toJson(result));
			out.flush();
			
		}
    }
    
		

}
