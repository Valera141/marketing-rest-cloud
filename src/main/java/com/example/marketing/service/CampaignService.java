package com.example.marketing.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.marketing.dto.CampaignRequest;
import com.example.marketing.dto.CampaignResponse;

public interface CampaignService {

    Page<CampaignResponse> getAllCampaigns(Pageable pageable);
    
    CampaignResponse getCampaignById(Integer id);
    
    CampaignResponse createCampaign(CampaignRequest request);

    CampaignResponse updateCampaign(Integer id, CampaignRequest request);

    Integer deleteCampaign(Integer id);
}