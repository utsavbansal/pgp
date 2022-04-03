package com.Irrigation.SAIAFarming.repository;

import com.Irrigation.SAIAFarming.entity.FarmDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FarmDatabaseRepository extends JpaRepository<FarmDatabase, Integer> {
    /*@Query(value = "select * from agri_farm where farm_id = CONCAT(farm_id);", nativeQuery = true )
    Optional<FarmDatabase> findById(@Param("farm_id")Integer farm_id);*/
}
