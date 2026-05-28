package dao;

import models.ClassRegistration;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassRegistrationDAO {
    private Connection connection;

    public ClassRegistrationDAO() {
        connection = DatabaseConnection.getConnection();
    }

    // Create: Add a new class registration
    public boolean addRegistration(ClassRegistration registration) {
        String sql = "INSERT INTO ClassRegistrations (ClassID, MemberID, RegistrationDate) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, registration.getClassID());
            stmt.setInt(2, registration.getMemberID());
            stmt.setTimestamp(3, Timestamp.valueOf(registration.getRegistrationDate()));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    registration.setRegistrationID(generatedKeys.getInt(1));
                } else {
                    return false;
                }
            }

            return true;
        } catch (SQLException e) {
            System.out.println("Error adding class registration.");
            e.printStackTrace();
            return false;
        }
    }

    // Read: Check if a member is already registered for a class
    public boolean isMemberRegistered(int memberID, int classID) {
        String sql = "SELECT * FROM ClassRegistrations WHERE MemberID = ? AND ClassID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, memberID);
            stmt.setInt(2, classID);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if a record exists
            }
        } catch (SQLException e) {
            System.out.println("Error checking class registration.");
            e.printStackTrace();
            return false;
        }
    }

    // Read: Get all registrations for a specific member
    public List<ClassRegistration> getRegistrationsByMemberID(int memberID) {
        List<ClassRegistration> registrations = new ArrayList<>();
        String sql = "SELECT * FROM ClassRegistrations WHERE MemberID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, memberID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ClassRegistration registration = new ClassRegistration();
                    registration.setRegistrationID(rs.getInt("RegistrationID"));
                    registration.setClassID(rs.getInt("ClassID"));
                    registration.setMemberID(rs.getInt("MemberID"));
                    registration.setRegistrationDate(rs.getTimestamp("RegistrationDate").toLocalDateTime());
                    registrations.add(registration);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving registrations by MemberID.");
            e.printStackTrace();
        }
        return registrations;
    }

    // Read: Get all registrations for a specific class
    public List<ClassRegistration> getRegistrationsByClassID(int classID) {
        List<ClassRegistration> registrations = new ArrayList<>();
        String sql = "SELECT * FROM ClassRegistrations WHERE ClassID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, classID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ClassRegistration registration = new ClassRegistration();
                    registration.setRegistrationID(rs.getInt("RegistrationID"));
                    registration.setClassID(rs.getInt("ClassID"));
                    registration.setMemberID(rs.getInt("MemberID"));
                    registration.setRegistrationDate(rs.getTimestamp("RegistrationDate").toLocalDateTime());
                    registrations.add(registration);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving registrations by ClassID.");
            e.printStackTrace();
        }
        return registrations;
    }

    // Delete: Remove a class registration
    public boolean deleteRegistration(int registrationID) {
        String sql = "DELETE FROM ClassRegistrations WHERE RegistrationID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, registrationID);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting class registration.");
            e.printStackTrace();
            return false;
        }
    }

    // Additional methods can be added as needed
}
