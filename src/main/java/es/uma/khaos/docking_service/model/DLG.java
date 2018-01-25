package es.uma.khaos.docking_service.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="resulting .dlg file", description = "A resulting file from the molecular docking simulation performed on the server")

public class DLG {

    private int id;
    private String file;
    private int taskId;

    public DLG(int id, String file, int taskId) {
        this.id = id;
        this.file = file;
        this.taskId = taskId;
    }

	@ApiModelProperty(value = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
	@ApiModelProperty(value = "file")
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
    
	@ApiModelProperty(value = "task id")
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
