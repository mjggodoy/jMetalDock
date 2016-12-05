package es.uma.khaos.docking_service.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import es.uma.khaos.docking_service.exception.DatabaseException;
import es.uma.khaos.docking_service.model.Execution;
import es.uma.khaos.docking_service.model.Result;
import es.uma.khaos.docking_service.model.Task;
import es.uma.khaos.docking_service.model.response.TaskResponse;
import es.uma.khaos.docking_service.model.response.TaskRunResponse;
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
			 
    	
		int idExecution;
    	TaskRunResponse taskRunResponse = null;   
		String id = request.getParameter("id");
		Result result = null;
		
		try {
			idExecution = Integer.parseInt(id);
			result = DatabaseService.getInstance().getResult(idExecution);
			taskRunResponse = new TaskRunResponse(idExecution, result.getFinalBindingEnergy(),result.getObjectives(), result.getExecutionTaskId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		response.setContentType("application/json");
		
		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		out.print(gson.toJson(taskRunResponse));
		out.flush();
			
    
    }
    
		

}
