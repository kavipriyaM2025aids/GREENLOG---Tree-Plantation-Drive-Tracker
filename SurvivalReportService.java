package com.greenlog.service;

import com.greenlog.entity.PlantationDrive;
import com.greenlog.entity.Tree;
import com.greenlog.enums.TreeStatus;
import com.greenlog.repository.PlantationDriveRepository;
import com.greenlog.repository.TreeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class SurvivalReportService {

    private final TreeRepository treeRepository;
    private final PlantationDriveRepository driveRepository;

    public SurvivalReportService(TreeRepository treeRepository, PlantationDriveRepository driveRepository) {
        this.treeRepository = treeRepository;
        this.driveRepository = driveRepository;
    }

    @Transactional
    public void recalculateSurvivalRate(Long driveId) {
        PlantationDrive drive = driveRepository.findById(driveId)
                .orElseThrow(() -> new IllegalArgumentException("Drive not found: " + driveId));
        
        List<Tree> trees = treeRepository.findByDrive(drive);
        BigDecimal survivalRate = calculateRate(trees);
        
        drive.setSurvivalRate(survivalRate);
        driveRepository.save(drive);
    }
    
    public BigDecimal getSurvivalReportByDrive(Long driveId) {
        PlantationDrive drive = driveRepository.findById(driveId)
                .orElseThrow(() -> new IllegalArgumentException("Drive not found: " + driveId));
        List<Tree> trees = treeRepository.findByDrive(drive);
        return calculateRate(trees);
    }
    
    public BigDecimal getSurvivalReportBySpecies(String species) {
        List<Tree> trees = treeRepository.findBySpecies(species);
        return calculateRate(trees);
    }
    
    private BigDecimal calculateRate(List<Tree> trees) {
        if (trees == null || trees.isEmpty()) {
            return BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP);
        }
        
        long total = trees.size();
        long aliveCount = trees.stream()
                .filter(t -> t.getCurrentStatus() == TreeStatus.ALIVE)
                .count();
                
        double percentage = ((double) aliveCount / total) * 100.0;
        return BigDecimal.valueOf(percentage).setScale(2, RoundingMode.HALF_UP);
    }
}
