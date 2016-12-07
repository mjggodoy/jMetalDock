package es.uma.khaos.docking_service.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import es.uma.khaos.docking_service.model.Parameter;
import es.uma.khaos.docking_service.model.Task;
import es.uma.khaos.docking_service.model.response.TaskParameterResponse;
import es.uma.khaos.docking_service.model.response.TaskResponse;
import es.uma.khaos.docking_service.model.response.TaskRunResponse;
import es.uma.khaos.docking_service.service.DatabaseService;

/**
 * Servlet implementation class TaskParameterServlet
 */
public class TaskParameterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskParameterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int taskId;
		TaskParameterResponse objResp = null;
		String id = request.getParameter("id");		

		response.setContentType("application/json");
		
		try {
			taskId = Integer.parseInt(id);
			Task task = DatabaseService.getInstance().getTaskParameter(taskId);
			objResp = new TaskParameterResponse(taskId, task.getHash(), task.getState(), task.getParameter());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		if (objResp!=null) {
			Gson gson = new Gson();
			PrintWriter out = response.getWriter();
			out.print(gson.toJson(objResp));
			out.flush();
		}
		
		
	}

	
}
