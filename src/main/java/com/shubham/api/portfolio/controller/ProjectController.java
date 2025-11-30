package com.shubham.api.portfolio.controller;

import com.shubham.api.portfolio.dto.ApiResponse;
import com.shubham.api.portfolio.entity.Project;
import com.shubham.api.portfolio.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@Slf4j
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Project>>> getAllProjects() {
        try {
            List<Project> projects = projectService.getAllProjects();
            return ResponseEntity.ok(
                    ApiResponse.ok(projects, "Projects fetched successfully")
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    ApiResponse.error(e.getMessage(), "Failed to fetch projects")
            );
        }
    }
}
