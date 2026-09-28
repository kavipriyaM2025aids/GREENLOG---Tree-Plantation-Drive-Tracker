package com.greenlog.dto;

import com.greenlog.enums.CheckInStatus;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class CheckInRequest {

    @NotNull(message = "treeId is required")
    private Long treeId;

    @NotNull(message = "volunteerId is required")
    private Long volunteerId;

    @NotNull(message = "checkinDate is required")
    private LocalDate checkinDate;

    @NotNull(message = "status is required")
    private CheckInStatus status;

    private String remarks;

    private LocalDate nextCheckinDate;
    
    public Long getTreeId() {
        return treeId;
    }
    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }
    public Long getVolunteerId() {
        return volunteerId;
    }
    public void setVolunteerId(Long volunteerId) {
        this.volunteerId = volunteerId;
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
}
