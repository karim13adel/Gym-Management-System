package services;

import dao.ClassDAO;
import models.ClassEntity;
import java.util.List;

public class ClassService {
    private ClassDAO classDAO;

    public ClassService() {
        classDAO = new ClassDAO();
    }

    // Add a new class
    public boolean addClass(ClassEntity gymClass) {
        return classDAO.addClass(gymClass);
    }

    // Retrieve all classes
    public List<ClassEntity> listAllClasses() {
        return classDAO.getAllClasses();
    }

    // Retrieve a class by ID
    public ClassEntity getClassByID(int classID) {
        return classDAO.getClassByID(classID);
    }

    // Update class details
    public boolean updateClass(ClassEntity gymClass) {
        return classDAO.updateClass(gymClass);
    }

    // Delete a class
    public boolean removeClass(int classID) {
        return classDAO.deleteClass(classID);
    }

    // Assign a trainer to a class
    public boolean assignTrainerToClass(int classID, int trainerID) {
        ClassEntity gymClass = classDAO.getClassByID(classID);
        if (gymClass != null) {
            gymClass.setTrainerID(trainerID);
            return classDAO.updateClass(gymClass);
        }
        return false;
    }

}
