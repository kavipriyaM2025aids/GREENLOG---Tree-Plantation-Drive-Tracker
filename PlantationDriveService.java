package com.greenlog.service;

import com.greenlog.dto.PlantationDriveRequest;
import com.greenlog.entity.PlantationDrive;
import com.greenlog.exception.ResourceNotFoundException;
import com.greenlog.repository.PlantationDriveRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlantationDriveService {

    private final PlantationDriveRepository driveRepository;

    public PlantationDriveService(PlantationDriveRepository driveRepository) {
        this.driveRepository = driveRepository;
    }

    public PlantationDrive createDrive(PlantationDriveRequest request) {
        PlantationDrive drive = new PlantationDrive();
        drive.setDriveName(request.getDriveName());
        drive.setLocation(request.getLocation());
        drive.setDriveDate(request.getDriveDate());
        drive.setDescription(request.getDescription());
        drive.setCreatedAt(LocalDateTime.now());
        return driveRepository.save(drive);
    }

    public List<PlantationDrive> getAllDrives() {
        return driveRepository.findAll();
    }

    public PlantationDrive getDriveById(Long id) {
        return driveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PlantationDrive not found with id: " + id));
    }

    @Transactional
    public PlantationDrive updateDrive(Long id, PlantationDriveRequest request) {
        PlantationDrive drive = getDriveById(id);
        drive.setDriveName(request.getDriveName());
        drive.setLocation(request.getLocation());
        drive.setDriveDate(request.getDriveDate());
        drive.setDescription(request.getDescription());
        return driveRepository.save(drive);
    }

    @Transactional
    public void deleteDrive(Long id) {
        PlantationDrive drive = getDriveById(id);
        driveRepository.delete(drive);
    }
}
