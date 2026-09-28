package com.greenlog.repository;

import com.greenlog.entity.PlantationDrive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantationDriveRepository extends JpaRepository<PlantationDrive, Long> {
}
