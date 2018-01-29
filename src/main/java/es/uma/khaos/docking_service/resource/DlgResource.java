package es.uma.khaos.docking_service.resource;

import es.uma.khaos.docking_service.exception.DatabaseException;
import es.uma.khaos.docking_service.model.ErrorResponse;
import es.uma.khaos.docking_service.model.Result;
import es.uma.khaos.docking_service.model.Task;
import es.uma.khaos.docking_service.properties.Constants;
import es.uma.khaos.docking_service.service.DatabaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.*;
@Api(value=".dlg result")
@Path("/task")
public class DlgResource extends AbstractResource {

    //TODO: Chequear y cambiar para que funcione tanto en API como en html (incluyendo mensajes de error)

    @GET
    @Path("/{id}/dlg")
    @ApiOperation(value = ".dlg file from the molecular docking simulation",
	notes= "This method gets the .dlg file resulting from a molecular docking simulation")
    @ApiResponses(value ={
			@ApiResponse(code = 403, 
					message = "You are not allowed to see this task"),
			@ApiResponse(code = 500, 
					message = "Internal server error"),
			@ApiResponse(code = 404 , 
			message = "Not found")
	})
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadDlg(
    		@ApiParam(value = "Task id: the identification number of a task", required = true) @NotNull @PathParam("id") int id,
            @ApiParam(value = "Token: a code related to a task", required = true) @QueryParam("token") String token,
            @Context HttpHeaders headers) throws DatabaseException {

        Task task = DatabaseService.getInstance().getTaskParameter(id);

        if (task == null || !task.getToken().equals(token)) {
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse(Response.Status.FORBIDDEN, Constants.RESPONSE_TASK_MSG_UNALLOWED))
                    .build();
        }

        //TODO: Check token
        Response response = null;
        Connection conn = null; // connection to the database
        PreparedStatement ps = null;

        try {
            // connects to the database
            //TODO: Cambiar esto:
            conn = DatabaseService.getInstance().getConnection();

            // queries the database
            String sql = "SELECT * FROM dlg WHERE task_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();
            if (result.next()) {
                final InputStream in = result.getBinaryStream("file");
                String fileName = id+".dlg";

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int data = in.read();
                while (data >= 0) {
                    out.write((char) data);
                    data = in.read();
                }
                out.flush();

                response = Response.ok(out.toByteArray(), MediaType.APPLICATION_OCTET_STREAM)
                        .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"" ) //optional
                        .build();

            } else {
                // no file found
                response = Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse(Response.Status.NOT_FOUND, "DLG file not found!"))
                        .build();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            response = Response.serverError().build();
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.serverError().build();
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();

            }
        }
        return response;
    }
}
