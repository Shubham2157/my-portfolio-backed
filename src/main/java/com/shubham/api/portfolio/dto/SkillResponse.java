package com.shubham.api.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SkillResponse {
    private String id;
    private String name;
    private String category;
    private String icon;
    private String color;   // nullable
    private String level;
    private String url;     // nullable
}
