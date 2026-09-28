package com.greenlog.controller;

import com.greenlog.dto.CheckInRequest;
import com.greenlog.entity.CheckIn;
import com.greenlog.service.CheckInService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/check-ins")
public class CheckInController {

    private final CheckInService checkInService;

    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CheckIn createCheckIn(@Valid @RequestBody CheckInRequest request) {
        return checkInService.createCheckIn(request);
    }

    @GetMapping
    public List<CheckIn> getAllCheckIns() {
        return checkInService.getAllCheckIns();
    }

    @GetMapping("/{id}")
    public CheckIn getCheckInById(@PathVariable Long id) {
        return checkInService.getCheckInById(id);
    }

    @GetMapping("/tree/{treeId}")
    public List<CheckIn> getCheckInsByTree(@PathVariable Long treeId) {
        return checkInService.getCheckInsByTree(treeId);
    }

    @GetMapping("/volunteer/{volunteerId}")
    public List<CheckIn> getCheckInsByVolunteer(@PathVariable Long volunteerId) {
        return checkInService.getCheckInsByVolunteer(volunteerId);
    }
}
