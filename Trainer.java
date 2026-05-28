package models;

public class Trainer {
    private int trainerID;
    private String firstName;
    private String lastName;
    private String email;
    private String specialization;

    // Constructors
    public Trainer() {}

    public Trainer(String firstName, String lastName, String email, String specialization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.specialization = specialization;
    }

    // Getters and Setters
    public int getTrainerID() {
        return trainerID;
    }

    public void setTrainerID(int trainerID) {
        this.trainerID = trainerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSpecialization() {
        return specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }


    @Override
    public String toString() {
        return "TrainerID: " + trainerID +
                ", Name: " + firstName + " " + lastName +
                ", Email: " + email +
                ", Specialization: " + specialization;
    }
}
