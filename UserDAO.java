package dao;

import models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;

    public UserDAO() {
        connection = DatabaseConnection.getConnection();
    }

    // Create: Add a new user
    public boolean addUser(User user) {
        String sql = "INSERT INTO Users (Username, PasswordHash, Role) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // Plain-text password
            stmt.setString(3, user.getRole());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Adding user failed, no rows affected.");
                return false;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setUserID(generatedKeys.getInt(1));
                } else {
                    System.out.println("Adding user failed, no ID obtained.");
                    return false;
                }
            }

            return true;
        } catch (SQLException e) {
            System.out.println("Error adding user.");
            e.printStackTrace();
            return false;
        }
    }

    // Read: Get user by username
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE Username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("PasswordHash")); // Plain-text password
                user.setRole(rs.getString("Role"));
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user.");
            e.printStackTrace();
        }
        return null;
    }

    // Update: Update user details
    public boolean updateUser(User user) {
        String sql = "UPDATE Users SET Username = ?, PasswordHash = ?, Role = ? WHERE UserID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // Plain-text password
            stmt.setString(3, user.getRole());
            stmt.setInt(4, user.getUserID());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Updating user failed, no rows affected.");
                return false;
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating user.");
            e.printStackTrace();
            return false;
        }
    }

    // Delete: Remove a user
    public boolean deleteUser(int userID) {
        String sql = "DELETE FROM Users WHERE UserID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Deleting user failed, no rows affected.");
                return false;
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error deleting user.");
            e.printStackTrace();
            return false;
        }
    }

    // Read: Get all users by role
    public List<User> getAllUsersByRole(String role) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE Role = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("PasswordHash")); // Plain-text password
                user.setRole(rs.getString("Role"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving users by role.");
            e.printStackTrace();
        }
        return users;
    }

    // Read: Get user by ID
    public User getUserByID(int userID) {
        String sql = "SELECT * FROM Users WHERE UserID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("PasswordHash")); // Plain-text password
                user.setRole(rs.getString("Role"));
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user by ID.");
            e.printStackTrace();
        }
        return null;
    }
}
