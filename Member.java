package models;

public class Member {
    private int memberID;
    private String firstName;
    private String lastName;
    private String email;
    private String membershipType; // PAYG, Open, Term
    private String status; // ACTIVE, INACTIVE

    // Constructors
    public Member() {}

    public Member(String firstName, String lastName, String email, String membershipType, String status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.membershipType = membershipType;
        this.status = status;
    }

    // Getters and Setters
    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
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
    public String getMembershipType() {
        return membershipType;
    }
    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }



    @Override
    public String toString() {
        return "MemberID: " + memberID +
                ", Name: " + firstName + " " + lastName +
                ", Email: " + email +
                ", Membership Type: " + membershipType +
                ", Status: " + status;
    }
}
