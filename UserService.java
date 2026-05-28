package services;

import dao.UserDAO;
import models.User;

import java.util.List;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public boolean registerUser(User user) {
        // Implement password hashing before storing
        // For demonstration, we'll use plain text (NOT RECOMMENDED)
        return userDAO.addUser(user);
    }

    public User authenticateUser(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    public boolean deleteUser(int userID) {
        return userDAO.deleteUser(userID);
    }

    public List<User> getAllUsersByRole(String role) {
        return userDAO.getAllUsersByRole(role);
    }

    public User getUserByID(int userID) {
        return userDAO.getUserByID(userID);
    }
}
