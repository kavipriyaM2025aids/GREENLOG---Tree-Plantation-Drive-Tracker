package com.greenlog.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import com.greenlog.enums.TreeStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tree")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long treeId;

    @Column(nullable = false)
    private String species;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private LocalDate plantedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TreeStatus currentStatus = TreeStatus.ALIVE;

    private LocalDate nextCheckinDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drive_id", nullable = false)
    private PlantationDrive drive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id", nullable = false)
    private Volunteer volunteer;

    @OneToMany(mappedBy = "tree")
    @JsonIgnore
    private List<CheckIn> checkIns;

    public Tree() {
    }

    public Tree(String species, String location, LocalDate plantedDate, TreeStatus currentStatus, LocalDate nextCheckinDate, PlantationDrive drive, Volunteer volunteer) {
        this.species = species;
        this.location = location;
        this.plantedDate = plantedDate;
        this.currentStatus = currentStatus;
        this.nextCheckinDate = nextCheckinDate;
        this.drive = drive;
        this.volunteer = volunteer;
    }

    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }

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

    public TreeStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(TreeStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public LocalDate getNextCheckinDate() {
        return nextCheckinDate;
    }

    public void setNextCheckinDate(LocalDate nextCheckinDate) {
        this.nextCheckinDate = nextCheckinDate;
    }

    public PlantationDrive getDrive() {
        return drive;
    }

    public void setDrive(PlantationDrive drive) {
        this.drive = drive;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public List<CheckIn> getCheckIns() {
        return checkIns;
    }

    public void setCheckIns(List<CheckIn> checkIns) {
        this.checkIns = checkIns;
    }
}
