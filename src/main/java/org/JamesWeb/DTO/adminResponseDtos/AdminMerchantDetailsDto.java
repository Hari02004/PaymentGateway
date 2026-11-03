package org.JamesWeb.DTO.adminResponseDtos;

import lombok.Data;

@Data
public class AdminMerchantDetailsDto {
    String merchantId;
    String businessEmail;
    Long phNumber;
    String ownerName;
    String status;
}
