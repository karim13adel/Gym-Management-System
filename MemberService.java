package services;

import dao.MemberDAO;
import models.Member;
import java.util.List;

public class MemberService {
    private MemberDAO memberDAO;
    private ClassRegistrationService classRegistrationService;

    public MemberService() {
        memberDAO = new MemberDAO();
        classRegistrationService = new ClassRegistrationService();
    }

    // Register a new member
    public boolean registerMember(Member member) {
        // Additional business logic (e.g., validation) can be added here
        return memberDAO.addMember(member);
    }

    // Retrieve all members
    public List<Member> listAllMembers() {
        return memberDAO.getAllMembers();
    }

    // Update member details
    public boolean updateMember(Member member) {
        return memberDAO.updateMember(member);
    }

    // Remove a member by MemberID
    public boolean removeMember(int memberID) {
        return memberDAO.deleteMember(memberID);
    }

    // Get a member by MemberID
    public Member getMemberByID(int memberID) {
        return memberDAO.getMemberByID(memberID);
    }

    // Get members assigned to a specific TrainerID
    public List<Member> getMembersByTrainerID(int trainerID) {
        return memberDAO.getMembersByTrainerID(trainerID);
    }

    // Register a member for a class
    public boolean registerForClass(int memberID, int classID) {
        return classRegistrationService.registerForClass(memberID, classID);
    }

}
