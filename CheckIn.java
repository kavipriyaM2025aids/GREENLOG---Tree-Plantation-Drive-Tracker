package com.greenlog.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.greenlog.enums.CheckInStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "check_in")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CheckIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkinId;

    @Column(nullable = false)
    private LocalDate checkinDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CheckInStatus status;

    private String remarks;

    private LocalDate nextCheckinDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tree_id", nullable = false)
    private Tree tree;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id", nullable = false)
    private Volunteer volunteer;

    public CheckIn() {
    }

    public CheckIn(LocalDate checkinDate, CheckInStatus status, String remarks, LocalDate nextCheckinDate, Tree tree, Volunteer volunteer) {
        this.checkinDate = checkinDate;
        this.status = status;
        this.remarks = remarks;
        this.nextCheckinDate = nextCheckinDate;
        this.tree = tree;
        this.volunteer = volunteer;
    }

    public Long getCheckinId() {
        return checkinId;
    }

    public void setCheckinId(Long checkinId) {
        this.checkinId = checkinId;
    }

    public LocalDate getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(LocalDate checkinDate) {
        this.checkinDate = checkinDate;
    }

    public CheckInStatus getStatus() {
        return status;
    }

    public void setStatus(CheckInStatus status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDate getNextCheckinDate() {
        return nextCheckinDate;
    }

    public void setNextCheckinDate(LocalDate nextCheckinDate) {
        this.nextCheckinDate = nextCheckinDate;
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }
}
