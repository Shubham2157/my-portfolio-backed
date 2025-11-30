package com.shubham.api.portfolio.service.impl;

import com.shubham.api.portfolio.entity.Project;
import com.shubham.api.portfolio.repository.ProjectRepo;
import com.shubham.api.portfolio.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepo projectRepo;

    @Override
    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    @Override
    public Project saveProject(Project p) {
        if (p.getCreatedAt() == null) p.setCreatedAt(LocalDateTime.now());
        return projectRepo.save(p);
    }

    @Override
    public Project getById(String id) {
        return projectRepo.findById(id).orElse(null);
    }
}
