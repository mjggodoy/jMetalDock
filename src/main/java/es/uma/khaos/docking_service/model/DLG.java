package es.uma.khaos.docking_service.model;

public class DLG {

    private int id;
    private String file;
    private int taskId;

    public DLG(int id, String file, int taskId) {
        this.id = id;
        this.file = file;
        this.taskId = taskId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
