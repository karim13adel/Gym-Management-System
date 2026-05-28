package dao;

import models.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    private Connection connection;

    public MemberDAO() {
        connection = DatabaseConnection.getConnection();
    }

    // Create
    public boolean addMember(Member member) {
        String sql = "INSERT INTO Members (FirstName, LastName, Email, MembershipType, Status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, member.getFirstName());
            stmt.setString(2, member.getLastName());
            stmt.setString(3, member.getEmail());
            stmt.setString(4, member.getMembershipType());
            stmt.setString(5, member.getStatus());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    member.setMemberID(generatedKeys.getInt(1));
                } else {
                    return false;
                }
            }

            return true;
        } catch (SQLException e) {
            System.out.println("Error adding member.");
            e.printStackTrace();
            return false;
        }
    }

    // Read All
    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM Members";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Member member = new Member();
                member.setMemberID(rs.getInt("MemberID"));
                member.setFirstName(rs.getString("FirstName"));
                member.setLastName(rs.getString("LastName"));
                member.setEmail(rs.getString("Email"));
                member.setMembershipType(rs.getString("MembershipType"));
                member.setStatus(rs.getString("Status"));
                members.add(member);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving members.");
            e.printStackTrace();
        }
        return members;
    }

    // Update
    public boolean updateMember(Member member) {
        String sql = "UPDATE Members SET FirstName = ?, LastName = ?, Email = ?, MembershipType = ?, Status = ? WHERE MemberID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, member.getFirstName());
            stmt.setString(2, member.getLastName());
            stmt.setString(3, member.getEmail());
            stmt.setString(4, member.getMembershipType());
            stmt.setString(5, member.getStatus());
            stmt.setInt(6, member.getMemberID());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating member.");
            e.printStackTrace();
            return false;
        }
    }

    // Delete
    public boolean deleteMember(int memberID) {
        String sql = "DELETE FROM Members WHERE MemberID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, memberID);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting member.");
            e.printStackTrace();
            return false;
        }
    }

    // Get Member by ID
    public Member getMemberByID(int memberID) {
        String sql = "SELECT * FROM Members WHERE MemberID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, memberID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Member member = new Member();
                    member.setMemberID(rs.getInt("MemberID"));
                    member.setFirstName(rs.getString("FirstName"));
                    member.setLastName(rs.getString("LastName"));
                    member.setEmail(rs.getString("Email"));
                    member.setMembershipType(rs.getString("MembershipType"));
                    member.setStatus(rs.getString("Status"));
                    return member;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving member by ID.");
            e.printStackTrace();
        }
        return null;
    }

    // Get Members by TrainerID
    public List<Member> getMembersByTrainerID(int trainerID) {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT m.* FROM Members m " +
                "JOIN ClassRegistrations cr ON m.MemberID = cr.MemberID " +
                "JOIN Classes c ON cr.ClassID = c.ClassID " +
                "WHERE c.TrainerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, trainerID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Member member = new Member();
                    member.setMemberID(rs.getInt("MemberID"));
                    member.setFirstName(rs.getString("FirstName"));
                    member.setLastName(rs.getString("LastName"));
                    member.setEmail(rs.getString("Email"));
                    member.setMembershipType(rs.getString("MembershipType"));
                    member.setStatus(rs.getString("Status"));
                    members.add(member);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving members by TrainerID.");
            e.printStackTrace();
        }
        return members;
    }
}
