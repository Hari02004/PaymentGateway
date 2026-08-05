package org.JamesWeb.controller;

import org.JamesWeb.DTO.ApiResponse;
import org.JamesWeb.DTO.JwtResponse;
import org.JamesWeb.DTO.LoginRequestDto;
import org.JamesWeb.service.AdminLoginService;
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
    @GetMapping("/admin/hai")
    public ResponseEntity<?> testingAdmin(){
        return ResponseEntity.ok("Hai");
    }
    @PostMapping("/api/admin/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto){
        System.out.println("Im in");
        String token = adminLoginService.login(requestDto);
        ApiResponse<JwtResponse> apiResponse = new ApiResponse<>(true,"Admin login successFull",new JwtResponse(token,requestDto.getUsername()));
        return ResponseEntity.ok(apiResponse);
    }
}
