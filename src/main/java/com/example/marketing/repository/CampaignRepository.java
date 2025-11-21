package com.example.marketing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.marketing.model.Campaign;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
    // Spring Data JPA crea los métodos automáticamente
}   