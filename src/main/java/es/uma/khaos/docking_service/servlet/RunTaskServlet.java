package es.uma.khaos.docking_service.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import es.uma.khaos.docking_service.model.response.TaskRunResponse;

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
		
    	 
    	ArrayList<TaskRunResponse> taskRunResponse = new ArrayList<TaskRunResponse>() ;
		
		String id = request.getParameter("id");
		String execution_task_id = request.getParameter("execution_task_id");
		
		System.out.println("id: " + id);
		System.out.println("execution_task_id: " + execution_task_id);
		
		response.setContentType("application/json");
		
		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		out.print(gson.toJson(taskRunResponse));
		out.flush();
			
	}

}
