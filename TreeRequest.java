package com.greenlog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class TreeRequest {

    @NotBlank(message = "species is required")
    private String species;

    @NotBlank(message = "location is required")
    private String location;

    @NotNull(message = "plantedDate is required")
    private LocalDate plantedDate;

    @NotNull(message = "driveId is required")
    private Long driveId;

    @NotNull(message = "volunteerId is required")
    private Long volunteerId;
    
    public String getSpecies() {
        return species;
    }
    public void setSpecies(String species) {
        this.species = species;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public LocalDate getPlantedDate() {
        return plantedDate;
    }
    public void setPlantedDate(LocalDate plantedDate) {
        this.plantedDate = plantedDate;
    }
    public Long getDriveId() {
        return driveId;
    }
    public void setDriveId(Long driveId) {
        this.driveId = driveId;
    }
    public Long getVolunteerId() {
        return volunteerId;
    }
    public void setVolunteerId(Long volunteerId) {
        this.volunteerId = volunteerId;
    }
}
