package models;

import java.time.LocalDateTime;

public class ClassRegistration {
    private int registrationID;
    private int classID;
    private int memberID;
    private LocalDateTime registrationDate;

    // Constructors
    public ClassRegistration() {}

    public ClassRegistration(int classID, int memberID) {
        this.classID = classID;
        this.memberID = memberID;
        this.registrationDate = LocalDateTime.now();
    }

    // Getters and Setters
    public int getRegistrationID() {
        return registrationID;
    }

    public void setRegistrationID(int registrationID) {
        this.registrationID = registrationID;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "RegistrationID: " + registrationID +
                ", ClassID: " + classID +
                ", MemberID: " + memberID +
                ", RegistrationDate: " + registrationDate;
    }
}
