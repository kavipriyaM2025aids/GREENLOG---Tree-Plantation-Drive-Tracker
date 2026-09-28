package com.greenlog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.greenlog.dto.VolunteerRequest;
import com.greenlog.entity.Volunteer;
import com.greenlog.service.VolunteerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/volunteers")
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Volunteer createVolunteer(@Valid @RequestBody VolunteerRequest request) {
        return volunteerService.createVolunteer(request);
    }

    @GetMapping
    public List<Volunteer> getAllVolunteers() {
        return volunteerService.getAllVolunteers();
    }

    @GetMapping("/leaderboard")
    public List<Object[]> getVolunteerLeaderboard() {
        return volunteerService.getVolunteerLeaderboard();
    }

    @GetMapping("/{id}")
    public Volunteer getVolunteerById(@PathVariable Long id) {
        return volunteerService.getVolunteerById(id);
    }

    @PutMapping("/{id}")
    public Volunteer updateVolunteer(
            @PathVariable Long id,
            @Valid @RequestBody VolunteerRequest request) {
        return volunteerService.updateVolunteer(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVolunteer(@PathVariable Long id) {
        volunteerService.deleteVolunteer(id);
    }
}