package com.shubham.api.portfolio.controller;

import com.shubham.api.portfolio.dto.ResumeResponse;
import com.shubham.api.portfolio.entity.Resume;
import com.shubham.api.portfolio.entity.ResumeSummary;
import com.shubham.api.portfolio.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @GetMapping("/resume")
    public ResponseEntity<ResumeResponse> getResume() {
        Resume resume = resumeService.getResume();

        ResumeResponse response = new ResumeResponse(
                resume.getFileId(),
                resume.getUrl(),
                resume.getDownloadUrl(),
                resume.getQuickSummary()
                        .stream()
                        .map(ResumeSummary::getSummaryText)
                        .toList()
        );

        return ResponseEntity.ok(response);
    }
}
