package dao;

import models.Trainer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainerDAO {
    private Connection connection;

    public TrainerDAO() {
        connection = DatabaseConnection.getConnection();
    }

    // Create
    public boolean addTrainer(Trainer trainer) {
        String sql = "INSERT INTO Trainers (FirstName, LastName, Email, Specialization) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, trainer.getFirstName());
            stmt.setString(2, trainer.getLastName());
            stmt.setString(3, trainer.getEmail());
            stmt.setString(4, trainer.getSpecialization());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    trainer.setTrainerID(generatedKeys.getInt(1));
                } else {
                    return false;
                }
            }

            return true;
        } catch (SQLException e) {
            System.out.println("Error adding trainer.");
            e.printStackTrace();
            return false;
        }
    }

    // Read All
    public List<Trainer> getAllTrainers() {
        List<Trainer> trainers = new ArrayList<>();
        String sql = "SELECT * FROM Trainers";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Trainer trainer = new Trainer();
                trainer.setTrainerID(rs.getInt("TrainerID"));
                trainer.setFirstName(rs.getString("FirstName"));
                trainer.setLastName(rs.getString("LastName"));
                trainer.setEmail(rs.getString("Email"));
                trainer.setSpecialization(rs.getString("Specialization"));
                trainers.add(trainer);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving trainers.");
            e.printStackTrace();
        }
        return trainers;
    }

    // Update
    public boolean updateTrainer(Trainer trainer) {
        String sql = "UPDATE Trainers SET FirstName = ?, LastName = ?, Email = ?, Specialization = ? WHERE TrainerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, trainer.getFirstName());
            stmt.setString(2, trainer.getLastName());
            stmt.setString(3, trainer.getEmail());
            stmt.setString(4, trainer.getSpecialization());
            stmt.setInt(5, trainer.getTrainerID());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating trainer.");
            e.printStackTrace();
            return false;
        }
    }

    // Delete
    // Delete Trainer with constraint check
    public boolean deleteTrainer(int trainerID) {
        // First, check if any classes are assigned to this trainer
        String checkSql = "SELECT COUNT(*) AS classCount FROM Classes WHERE TrainerID = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setInt(1, trainerID);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    int classCount = rs.getInt("classCount");
                    if (classCount > 0) {
                        System.out.println("Cannot delete trainer. There are classes assigned to this trainer.");
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking trainer's classes.");
            e.printStackTrace();
            return false;
        }

        // Proceed to delete if no classes are assigned
        String sql = "DELETE FROM Trainers WHERE TrainerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, trainerID);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting trainer.");
            e.printStackTrace();
            return false;
        }
    }


    // Get Trainer by ID
    public Trainer getTrainerByID(int trainerID) {
        String sql = "SELECT * FROM Trainers WHERE TrainerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, trainerID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Trainer trainer = new Trainer();
                    trainer.setTrainerID(rs.getInt("TrainerID"));
                    trainer.setFirstName(rs.getString("FirstName"));
                    trainer.setLastName(rs.getString("LastName"));
                    trainer.setEmail(rs.getString("Email"));
                    trainer.setSpecialization(rs.getString("Specialization"));
                    return trainer;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving trainer by ID.");
            e.printStackTrace();
        }
        return null;
    }
}
