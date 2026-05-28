package services;

import dao.TrainerDAO;
import models.Trainer;

import java.util.List;

public class TrainerService {
    private TrainerDAO trainerDAO;

    public TrainerService() {
        trainerDAO = new TrainerDAO();
    }

    public boolean addTrainer(Trainer trainer) {
        return trainerDAO.addTrainer(trainer);
    }

    public List<Trainer> listAllTrainers() {
        return trainerDAO.getAllTrainers();
    }

    public boolean updateTrainer(Trainer trainer) {
        return trainerDAO.updateTrainer(trainer);
    }

    public boolean removeTrainer(int trainerID) {
        return trainerDAO.deleteTrainer(trainerID);
    }

    public Trainer getTrainerByID(int trainerID) {
        return trainerDAO.getTrainerByID(trainerID);
    }
}
