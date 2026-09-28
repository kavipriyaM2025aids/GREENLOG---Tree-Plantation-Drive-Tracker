package com.greenlog.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenlog.entity.PlantationDrive;
import com.greenlog.entity.Tree;
import com.greenlog.enums.TreeStatus;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Long> {

    List<Tree> findByDrive(PlantationDrive drive);

    List<Tree> findBySpecies(String species);

    List<Tree> findByCurrentStatus(TreeStatus status);

    List<Tree> findByNextCheckinDateLessThanEqual(LocalDate date);

    List<Tree> findByNextCheckinDateLessThanEqualAndCurrentStatus(
            LocalDate date,
            TreeStatus status
    );

    List<Tree> findByDriveDriveId(Long driveId);

    List<Tree> findBySpeciesIgnoreCase(String species);

    long countByDriveDriveId(Long driveId);

    long countByDriveDriveIdAndCurrentStatus(
            Long driveId,
            TreeStatus status
    );
}