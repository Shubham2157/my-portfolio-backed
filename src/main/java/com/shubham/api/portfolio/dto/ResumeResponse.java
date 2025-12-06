package com.shubham.api.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResumeResponse {

    private String fileId;
    private String url;
    private String downloadUrl;
    private List<String> quickSummary;

    // getters and setters
}
