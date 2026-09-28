package com.greenlog.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenlog.dto.VolunteerRequest;
import com.greenlog.entity.Volunteer;
import com.greenlog.exception.BusinessRuleException;
import com.greenlog.exception.ResourceNotFoundException;
import com.greenlog.repository.VolunteerRepository;

@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public Volunteer createVolunteer(VolunteerRequest request) {
        if (volunteerRepository.existsByEmail(request.getEmail())) {
            throw new BusinessRuleException(
                    "Volunteer email already exists: " + request.getEmail()
            );
        }

        Volunteer volunteer = new Volunteer();
        volunteer.setName(request.getName());
        volunteer.setEmail(request.getEmail());
        volunteer.setPhone(request.getPhone());
        volunteer.setJoinedDate(request.getJoinedDate());

        return volunteerRepository.save(volunteer);
    }

    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    public Volunteer getVolunteerById(Long id) {
        return volunteerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Volunteer not found with id: " + id
                        )
                );
    }

    @Transactional
    public Volunteer updateVolunteer(Long id, VolunteerRequest request) {
        Volunteer volunteer = getVolunteerById(id);

        if (!volunteer.getEmail().equals(request.getEmail())
                && volunteerRepository.existsByEmail(request.getEmail())) {

            throw new BusinessRuleException(
                    "Volunteer email already exists: " + request.getEmail()
            );
        }

        volunteer.setName(request.getName());
        volunteer.setEmail(request.getEmail());
        volunteer.setPhone(request.getPhone());
        volunteer.setJoinedDate(request.getJoinedDate());

        return volunteerRepository.save(volunteer);
    }

    @Transactional
    public void deleteVolunteer(Long id) {
        Volunteer volunteer = getVolunteerById(id);
        volunteerRepository.delete(volunteer);
    }

    // Volunteer Leaderboard
    public List<Object[]> getVolunteerLeaderboard() {
        return volunteerRepository.findVolunteerLeaderboard();
    }
}