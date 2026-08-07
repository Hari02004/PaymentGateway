package org.JamesWeb.controller.admin;

import org.JamesWeb.DTO.ApiResponse;
import org.JamesWeb.DTO.JwtResponse;
import org.JamesWeb.DTO.LoginRequestDto;
import org.JamesWeb.DTO.adminResponseDtos.AdminResponseDto;
import org.JamesWeb.service.AdminLoginService;
import org.JamesWeb.service.admin.Dashboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @Autowired
    private AdminLoginService adminLoginService;
    @Autowired
    private Dashboard dashboard;
    @GetMapping("/admin/dashboard")
    public ResponseEntity<?> totalMerchants(){
        AdminResponseDto adminResponseDto = dashboard.dashboardResponse();
        ApiResponse<AdminResponseDto> apiResponse = new ApiResponse<>(true,"Dashboard details served successfully",adminResponseDto);
        return ResponseEntity.ok(apiResponse);
    }
    @PostMapping("/api/admin/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto){
        String token = adminLoginService.login(requestDto);
        ApiResponse<JwtResponse> apiResponse = new ApiResponse<>(true,"Admin login successFull",new JwtResponse(token,requestDto.getUsername()));
        return ResponseEntity.ok(apiResponse);
    }
}
