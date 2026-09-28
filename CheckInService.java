package com.greenlog.service;

import com.greenlog.dto.CheckInRequest;
import com.greenlog.entity.CheckIn;
import com.greenlog.entity.Tree;
import com.greenlog.entity.Volunteer;
import com.greenlog.enums.CheckInStatus;
import com.greenlog.enums.TreeStatus;
import com.greenlog.exception.BusinessRuleException;
import com.greenlog.exception.ResourceNotFoundException;
import com.greenlog.repository.CheckInRepository;
import com.greenlog.repository.TreeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CheckInService {

    private final CheckInRepository checkInRepository;
    private final TreeRepository treeRepository;
    private final VolunteerService volunteerService;
    private final SurvivalReportService survivalReportService;

    public CheckInService(CheckInRepository checkInRepository, TreeRepository treeRepository, 
                          VolunteerService volunteerService, SurvivalReportService survivalReportService) {
        this.checkInRepository = checkInRepository;
        this.treeRepository = treeRepository;
        this.volunteerService = volunteerService;
        this.survivalReportService = survivalReportService;
    }

    @Transactional
    public CheckIn createCheckIn(CheckInRequest request) {
        Tree tree = treeRepository.findById(request.getTreeId())
                .orElseThrow(() -> new ResourceNotFoundException("Tree not found with id: " + request.getTreeId()));
                
        if (tree.getCurrentStatus() == TreeStatus.DEAD) {
            throw new BusinessRuleException("Cannot add check-in. Tree is already marked as DEAD.");
        }
        
        Volunteer volunteer = volunteerService.getVolunteerById(request.getVolunteerId());
        
        CheckIn checkIn = new CheckIn();
        checkIn.setTree(tree);
        checkIn.setVolunteer(volunteer);
        checkIn.setCheckinDate(request.getCheckinDate());
        checkIn.setStatus(request.getStatus());
        checkIn.setRemarks(request.getRemarks());
        checkIn.setNextCheckinDate(request.getNextCheckinDate());
        
        checkIn = checkInRepository.save(checkIn);
        
        if (request.getStatus() == CheckInStatus.DEAD) {
            tree.setCurrentStatus(TreeStatus.DEAD);
        } else if (request.getStatus() == CheckInStatus.ALIVE) {
            tree.setCurrentStatus(TreeStatus.ALIVE);
        }
        
        tree.setNextCheckinDate(request.getNextCheckinDate());
        treeRepository.save(tree);
        
        survivalReportService.recalculateSurvivalRate(tree.getDrive().getDriveId());
        
        return checkIn;
    }

    public List<CheckIn> getAllCheckIns() {
        return checkInRepository.findAll();
    }

    public CheckIn getCheckInById(Long id) {
        return checkInRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CheckIn not found with id: " + id));
    }
    
    public List<CheckIn> getCheckInsByTree(Long treeId) {
        Tree tree = treeRepository.findById(treeId)
                .orElseThrow(() -> new ResourceNotFoundException("Tree not found with id: " + treeId));
        return checkInRepository.findByTree(tree);
    }
    
    public List<CheckIn> getCheckInsByVolunteer(Long volunteerId) {
        Volunteer volunteer = volunteerService.getVolunteerById(volunteerId);
        return checkInRepository.findByVolunteer(volunteer);
    }
}
