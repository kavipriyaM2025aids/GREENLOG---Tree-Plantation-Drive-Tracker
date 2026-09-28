package com.greenlog.controller;

import com.greenlog.dto.PlantationDriveRequest;
import com.greenlog.entity.PlantationDrive;
import com.greenlog.service.PlantationDriveService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drives")
public class PlantationDriveController {

    private final PlantationDriveService plantationDriveService;

    public PlantationDriveController(PlantationDriveService plantationDriveService) {
        this.plantationDriveService = plantationDriveService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlantationDrive createDrive(@Valid @RequestBody PlantationDriveRequest request) {
        return plantationDriveService.createDrive(request);
    }

    @GetMapping
    public List<PlantationDrive> getAllDrives() {
        return plantationDriveService.getAllDrives();
    }

    @GetMapping("/{id}")
    public PlantationDrive getDriveById(@PathVariable Long id) {
        return plantationDriveService.getDriveById(id);
    }

    @PutMapping("/{id}")
    public PlantationDrive updateDrive(@PathVariable Long id, @Valid @RequestBody PlantationDriveRequest request) {
        return plantationDriveService.updateDrive(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDrive(@PathVariable Long id) {
        plantationDriveService.deleteDrive(id);
    }
}
