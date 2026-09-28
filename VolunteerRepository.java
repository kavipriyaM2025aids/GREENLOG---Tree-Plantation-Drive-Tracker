package com.greenlog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.greenlog.entity.Volunteer;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    boolean existsByEmail(String email);

    @Query("""
        SELECT v.volunteerId, v.name, COUNT(t.treeId)
        FROM Volunteer v
        LEFT JOIN Tree t ON t.volunteer.volunteerId = v.volunteerId
        GROUP BY v.volunteerId, v.name
        ORDER BY COUNT(t.treeId) DESC
    """)
    List<Object[]> findVolunteerLeaderboard();
}