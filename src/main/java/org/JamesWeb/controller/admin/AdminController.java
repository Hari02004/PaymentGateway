package org.JamesWeb.controller.admin;

import org.JamesWeb.DTO.*;
import org.JamesWeb.DTO.adminResponseDtos.AdminMerchantDetailsDto;
import org.JamesWeb.DTO.adminResponseDtos.AdminResponseDto;
import org.JamesWeb.service.AdminLoginService;
import org.JamesWeb.service.admin.AdminMerchantDetailsPagination;
import org.JamesWeb.service.admin.Dashboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    @Autowired
    private AdminLoginService adminLoginService;
    @Autowired
    private Dashboard dashboard;
    @Autowired
    private AdminMerchantDetailsPagination adminMerchantDetailsPagination;
    @GetMapping("/admin/dashboard")
    public ResponseEntity<?> totalMerchants(){
        AdminResponseDto<TransactionDto,AdminMerchantDetailsDto> adminResponseDto = dashboard.dashboardResponse();
        ApiResponse<AdminResponseDto<TransactionDto,AdminMerchantDetailsDto>> apiResponse = new ApiResponse<>(true,"Dashboard details served successfully",adminResponseDto);
        return ResponseEntity.ok(apiResponse);
    }
    @PostMapping("/api/admin/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto){
        String token = adminLoginService.login(requestDto);
        ApiResponse<JwtResponse> apiResponse = new ApiResponse<>(true,"Admin login successFull",new JwtResponse(token,requestDto.getUsername()));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/admin/page/merchantDetails")
    public ResponseEntity<?> totalMerchantDetails(@RequestParam(name="page") int page,@RequestParam(name="size") int size){
        PaginationTransactionDetails<AdminMerchantDetailsDto> paginationTransactionDetails = adminMerchantDetailsPagination.adminMerchantDetailsDto(page,size);
        ApiResponse<PaginationTransactionDetails<AdminMerchantDetailsDto>> apiResponse = new ApiResponse<>(true,"Pagination details fetched successFully",paginationTransactionDetails);
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/admin/page/transactionDetails")
    public ResponseEntity<?> totalTransactionDetails(@RequestParam(name = "page") int page,@RequestParam(name="size") int size){
        PaginationTransactionDetails<TransactionDto> transactionDtoPaginationTransactionDetails =
                adminMerchantDetailsPagination.transactionDtoPaginationTransactionDetails(page, size);
        ApiResponse<PaginationTransactionDetails<TransactionDto>> apiResponse = new ApiResponse<>(true,"Transaction details fetched",transactionDtoPaginationTransactionDetails);
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/admin/activateMerchant")
    public ResponseEntity<?> activateMerchant(@RequestParam(name="merchantId")String merchantId){
        String activateMerchants = adminMerchantDetailsPagination.merchantActivate(merchantId);
        return new ResponseEntity<>(activateMerchants, HttpStatus.CREATED);
    }
}
