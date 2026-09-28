package com.greenlog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class PlantationDriveRequest {
    
    @NotBlank(message = "driveName is required")
    private String driveName;
    
    @NotBlank(message = "location is required")
    private String location;
    
    @NotNull(message = "driveDate is required")
    private LocalDate driveDate;
    
    private String description;
    
    public String getDriveName() {
        return driveName;
    }
    public void setDriveName(String driveName) {
        this.driveName = driveName;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public LocalDate getDriveDate() {
        return driveDate;
    }
    public void setDriveDate(LocalDate driveDate) {
        this.driveDate = driveDate;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
