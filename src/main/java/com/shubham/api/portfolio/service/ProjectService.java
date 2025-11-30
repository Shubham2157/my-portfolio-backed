package com.shubham.api.portfolio.service;


import com.shubham.api.portfolio.entity.Project;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects();
    Project saveProject(Project p);
    Project getById(String id);
}
