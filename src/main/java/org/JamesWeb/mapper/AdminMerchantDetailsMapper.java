package org.JamesWeb.mapper;

import org.JamesWeb.DTO.adminResponseDtos.AdminMerchantDetailsDto;
import org.JamesWeb.model.MerchantDetails;
import org.springframework.stereotype.Component;

@Component
public class AdminMerchantDetailsMapper {
    public AdminMerchantDetailsDto adminMerchantDetailsDto(MerchantDetails merchantDetails){
        AdminMerchantDetailsDto adminMerchantDetailsDto = new AdminMerchantDetailsDto();
        adminMerchantDetailsDto.setMerchantId(merchantDetails.getMerchantId());
        adminMerchantDetailsDto.setStatus(merchantDetails.getStatus());
        adminMerchantDetailsDto.setBusinessEmail(merchantDetails.getBusiness_email());
        adminMerchantDetailsDto.setPhNumber(merchantDetails.getPh_number());
        adminMerchantDetailsDto.setOwnerName(merchantDetails.getOwner_name());
        return adminMerchantDetailsDto;

    }
}
