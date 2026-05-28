package dao;

import models.ClassEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassDAO {
    private Connection connection;

    public ClassDAO() {
        connection = DatabaseConnection.getConnection();
    }

    // Create: Add a new class
    public boolean addClass(ClassEntity gymClass) {
        String sql = "INSERT INTO Classes (ClassName, Schedule, TrainerID) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, gymClass.getClassName());
            stmt.setString(2, gymClass.getSchedule());
            stmt.setInt(3, gymClass.getTrainerID());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    gymClass.setClassID(generatedKeys.getInt(1));
                } else {
                    return false;
                }
            }

            return true;
        } catch (SQLException e) {
            System.out.println("Error adding class.");
            e.printStackTrace();
            return false;
        }
    }

    // Read: Retrieve all classes
    public List<ClassEntity> getAllClasses() {
        List<ClassEntity> classes = new ArrayList<>();
        String sql = "SELECT * FROM Classes";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ClassEntity gymClass = new ClassEntity();
                gymClass.setClassID(rs.getInt("ClassID"));
                gymClass.setClassName(rs.getString("ClassName"));
                gymClass.setSchedule(rs.getString("Schedule"));
                gymClass.setTrainerID(rs.getInt("TrainerID"));
                classes.add(gymClass);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving classes.");
            e.printStackTrace();
        }
        return classes;
    }

    // Read: Retrieve a class by its ID
    public ClassEntity getClassByID(int classID) {
        String sql = "SELECT * FROM Classes WHERE ClassID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, classID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ClassEntity gymClass = new ClassEntity();
                    gymClass.setClassID(rs.getInt("ClassID"));
                    gymClass.setClassName(rs.getString("ClassName"));
                    gymClass.setSchedule(rs.getString("Schedule"));
                    gymClass.setTrainerID(rs.getInt("TrainerID"));
                    return gymClass;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving class by ID.");
            e.printStackTrace();
        }
        return null;
    }

    // Update: Modify class details
    public boolean updateClass(ClassEntity gymClass) {
        String sql = "UPDATE Classes SET ClassName = ?, Schedule = ?, TrainerID = ? WHERE ClassID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, gymClass.getClassName());
            stmt.setString(2, gymClass.getSchedule());
            stmt.setInt(3, gymClass.getTrainerID());
            stmt.setInt(4, gymClass.getClassID());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating class.");
            e.printStackTrace();
            return false;
        }
    }

    // Delete: Remove a class
    public boolean deleteClass(int classID) {
        String sql = "DELETE FROM Classes WHERE ClassID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, classID);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting class.");
            e.printStackTrace();
            return false;
        }
    }

    // Additional methods can be added as needed
}
