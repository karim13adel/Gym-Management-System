package models;

public class ClassEntity {
    private int classID;
    private String className;
    private String schedule; // Using String for simplicity; consider using Date or LocalDateTime
    private int trainerID;

    // Constructors
    public ClassEntity() {}

    public ClassEntity(String className, String schedule, int trainerID) {
        this.className = className;
        this.schedule = schedule;
        this.trainerID = trainerID;
    }

    // Getters and Setters
    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public int getTrainerID() {
        return trainerID;
    }

    public void setTrainerID(int trainerID) {
        this.trainerID = trainerID;
    }

    @Override
    public String toString() {
        return "ClassID: " + classID +
                ", Name: " + className +
                ", Schedule: " + schedule +
                ", TrainerID: " + (trainerID > 0 ? trainerID : "Unassigned");
    }
}
