package com.greenlog.repository;

import com.greenlog.entity.CheckIn;
import com.greenlog.entity.Tree;
import com.greenlog.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {

    List<CheckIn> findByTree(Tree tree);
        List<CheckIn> findByVolunteer(Volunteer volunteer);
    
    Optional<CheckIn> findTopByTreeOrderByCheckinDateDesc(Tree tree);
    
    long countByTree(Tree tree);
}
