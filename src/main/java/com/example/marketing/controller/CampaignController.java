package com.example.marketing.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.marketing.dto.CampaignRequest;
import com.example.marketing.dto.CampaignResponse;
import com.example.marketing.service.CampaignService;

import java.util.Map; 

@RestController
@RequestMapping("/api/v1/campaigns")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    // --- Endpoint para OBTENER TODAS (paginado) ---
    @GetMapping
        public ResponseEntity<Page<CampaignResponse>> getAllCampaigns(
            @PageableDefault(page = 0, size = 10, sort = "campaignId") Pageable pageable
        ) {
            Page<CampaignResponse> campaigns = campaignService.getAllCampaigns(pageable);
            return ResponseEntity.ok(campaigns);
        }

    // --- Endpoint para OBTENER POR ID ---
    @GetMapping("/{id}")
    public ResponseEntity<CampaignResponse> getCampaignById(@PathVariable Integer id) {
        CampaignResponse campaign = campaignService.getCampaignById(id);
        return ResponseEntity.ok(campaign);
    }

    // --- Endpoint para CREAR ---
    @PostMapping
    public ResponseEntity<CampaignResponse> createCampaign(@Valid @RequestBody CampaignRequest request) {
        CampaignResponse createdCampaign = campaignService.createCampaign(request);
        return new ResponseEntity<>(createdCampaign, HttpStatus.CREATED);
    }

    // --- Endpoint para ACTUALIZAR ---
    @PutMapping("/{id}")
    public ResponseEntity<CampaignResponse> updateCampaign(@PathVariable Integer id, @Valid @RequestBody CampaignRequest request) {
        CampaignResponse updatedCampaign = campaignService.updateCampaign(id, request);
        return ResponseEntity.ok(updatedCampaign);
    }

    // --- Endpoint para BORRAR ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Integer>> deleteCampaign(@PathVariable Integer id) {
        Integer deletedId = campaignService.deleteCampaign(id);
        return ResponseEntity.ok(Map.of("deletedCampaignId", deletedId));
    }
}