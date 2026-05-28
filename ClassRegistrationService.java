package services;

import dao.ClassRegistrationDAO;
import models.ClassRegistration;
import java.util.List;

public class ClassRegistrationService {
    private ClassRegistrationDAO registrationDAO;

    public ClassRegistrationService() {
        registrationDAO = new ClassRegistrationDAO();
    }

    // Register a member for a class
    public boolean registerForClass(int memberID, int classID) {
        // Check if the member is already registered for the class
        if (registrationDAO.isMemberRegistered(memberID, classID)) {
            System.out.println("You are already registered for this class.");
            return false;
        }

        // Create a new ClassRegistration object
        ClassRegistration registration = new ClassRegistration(classID, memberID);
        return registrationDAO.addRegistration(registration);
    }

    // Retrieve all registrations for a specific member
    public List<ClassRegistration> getRegistrationsByMember(int memberID) {
        return registrationDAO.getRegistrationsByMemberID(memberID);
    }

    // Retrieve all registrations for a specific class
    public List<ClassRegistration> getRegistrationsByClass(int classID) {
        return registrationDAO.getRegistrationsByClassID(classID);
    }

    // Cancel a registration by RegistrationID
    public boolean cancelRegistration(int registrationID) {
        return registrationDAO.deleteRegistration(registrationID);
    }


}
