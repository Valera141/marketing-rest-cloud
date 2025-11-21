package com.example.marketing.dto;

import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class CampaignResponse {
    private Integer campaignId;
    private String campaignName;
    private Boolean isActive;
    private OffsetDateTime creationDate;
    
    private UserResponse creatorUser; 
}