package com.greenlog.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "plantation_drive")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PlantationDrive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driveId;

    @Column(nullable = false)
    private String driveName;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private LocalDate driveDate;

    private String description;

    @Column(precision = 5, scale = 2)
    private BigDecimal survivalRate = BigDecimal.valueOf(0.00);

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "drive")
    @JsonIgnore
    private List<Tree> trees;

    public PlantationDrive() {
    }

    public PlantationDrive(String driveName, String location, LocalDate driveDate, String description, BigDecimal survivalRate, LocalDateTime createdAt) {
        this.driveName = driveName;
        this.location = location;
        this.driveDate = driveDate;
        this.description = description;
        this.survivalRate = survivalRate;
        this.createdAt = createdAt;
    }

    public Long getDriveId() {
        return driveId;
    }

    public void setDriveId(Long driveId) {
        this.driveId = driveId;
    }

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

    public BigDecimal getSurvivalRate() {
        return survivalRate;
    }

    public void setSurvivalRate(BigDecimal survivalRate) {
        this.survivalRate = survivalRate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Tree> getTrees() {
        return trees;
    }

    public void setTrees(List<Tree> trees) {
        this.trees = trees;
    }
}
