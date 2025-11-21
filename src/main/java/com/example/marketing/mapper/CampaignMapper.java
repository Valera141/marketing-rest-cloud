package com.example.marketing.mapper;

import com.example.marketing.dto.CampaignRequest;
import com.example.marketing.dto.CampaignResponse;
import com.example.marketing.dto.UserResponse;
import com.example.marketing.model.Campaign;
import com.example.marketing.model.User;

public class CampaignMapper {

    // --- AÑADE ESTE MÉTODO ---
    // Convierte del DTO (Request) al Modelo (Entidad)
    // Nota: Este mapper solo copia los campos simples.
    // La asignación del 'User' se hará en el Servicio.

    public static void copyToEntity(CampaignRequest dto, Campaign entity) {
        if (dto == null || entity == null) {
            return;
        }
        // Solo actualizamos los campos que vienen en el DTO
        entity.setCampaignName(dto.getCampaignName());
        entity.setIsActive(dto.getIsActive());
    }

    public static Campaign toEntity(CampaignRequest request) {
        if (request == null) {
            return null;
        }
        Campaign campaign = new Campaign();
        campaign.setCampaignName(request.getCampaignName());
        campaign.setIsActive(request.getIsActive());
        
        return campaign;
    }

    // --- MÉTODO EXISTENTE ---
    public static CampaignResponse toResponse(Campaign campaign) {
        if (campaign == null) {
            return null;
        }

        CampaignResponse dto = new CampaignResponse();
        dto.setCampaignId(campaign.getCampaignId());
        dto.setCampaignName(campaign.getCampaignName());
        dto.setIsActive(campaign.getIsActive());
        dto.setCreationDate(campaign.getCreationDate());
        
        dto.setCreatorUser(toUserResponse(campaign.getCreatorUser()));

        return dto;
    }

    // --- MÉTODO EXISTENTE ---
    private static UserResponse toUserResponse(User user) {
        if (user == null) {
            return null;
        }

        UserResponse userDto = new UserResponse();
        userDto.setUserId(user.getUserId());
        userDto.setFullName(user.getFullName());
        
        return userDto;
    }
}