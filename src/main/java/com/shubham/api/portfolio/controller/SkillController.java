package com.shubham.api.portfolio.controller;

import com.shubham.api.portfolio.dto.SkillResponse;
import com.shubham.api.portfolio.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @GetMapping("/skills")
    public ResponseEntity<List<SkillResponse>> getSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }
}
