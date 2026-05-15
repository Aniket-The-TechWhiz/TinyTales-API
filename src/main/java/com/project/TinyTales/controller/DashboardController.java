package com.project.TinyTales.controller;

import com.project.TinyTales.dto.response.DashboardResponse;
import com.project.TinyTales.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;
    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboardData(){
        return ResponseEntity.ok(dashboardService.getDashboardData());
    }
}
