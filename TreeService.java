package com.greenlog.service;

import com.greenlog.dto.TreeRequest;
import com.greenlog.entity.PlantationDrive;
import com.greenlog.entity.Tree;
import com.greenlog.entity.Volunteer;
import com.greenlog.enums.TreeStatus;
import com.greenlog.exception.ResourceNotFoundException;
import com.greenlog.repository.TreeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TreeService {

    private final TreeRepository treeRepository;
    private final PlantationDriveService driveService;
    private final VolunteerService volunteerService;

    public TreeService(TreeRepository treeRepository, PlantationDriveService driveService, VolunteerService volunteerService) {
        this.treeRepository = treeRepository;
        this.driveService = driveService;
        this.volunteerService = volunteerService;
    }

    @Transactional
    public Tree createTree(TreeRequest request) {
        PlantationDrive drive = driveService.getDriveById(request.getDriveId());
        Volunteer volunteer = volunteerService.getVolunteerById(request.getVolunteerId());

        Tree tree = new Tree();
        tree.setSpecies(request.getSpecies());
        tree.setLocation(request.getLocation());
        tree.setPlantedDate(request.getPlantedDate());
        tree.setCurrentStatus(TreeStatus.ALIVE);
        tree.setDrive(drive);
        tree.setVolunteer(volunteer);
        
        return treeRepository.save(tree);
    }

    public List<Tree> getAllTrees() {
        return treeRepository.findAll();
    }

    public Tree getTreeById(Long id) {
        return treeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tree not found with id: " + id));
    }

    @Transactional
    public Tree updateTree(Long id, TreeRequest request) {
        Tree tree = getTreeById(id);
        
        PlantationDrive drive = driveService.getDriveById(request.getDriveId());
        Volunteer volunteer = volunteerService.getVolunteerById(request.getVolunteerId());
        
        tree.setSpecies(request.getSpecies());
        tree.setLocation(request.getLocation());
        tree.setPlantedDate(request.getPlantedDate());
        tree.setDrive(drive);
        tree.setVolunteer(volunteer);
        
        return treeRepository.save(tree);
    }

    @Transactional
    public void deleteTree(Long id) {
        Tree tree = getTreeById(id);
        treeRepository.delete(tree);
    }

    public List<Tree> getTreesByDrive(Long driveId) {
        PlantationDrive drive = driveService.getDriveById(driveId);
        return treeRepository.findByDrive(drive);
    }

    public List<Tree> getTreesBySpecies(String species) {
        return treeRepository.findBySpecies(species);
    }

    public List<Tree> getTreesByStatus(TreeStatus status) {
        return treeRepository.findByCurrentStatus(status);
    }

    public List<Tree> getDueCheckIns() {
        return treeRepository.findByNextCheckinDateLessThanEqual(LocalDate.now());
    }
}
