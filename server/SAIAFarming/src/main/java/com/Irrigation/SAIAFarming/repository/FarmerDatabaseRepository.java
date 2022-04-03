package com.Irrigation.SAIAFarming.repository;

//import com.Irrigation.SAIAFarming.entity.UserDatabase;
import com.Irrigation.SAIAFarming.entity.FarmerDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmerDatabaseRepository extends JpaRepository<FarmerDatabase, Integer> {



}
