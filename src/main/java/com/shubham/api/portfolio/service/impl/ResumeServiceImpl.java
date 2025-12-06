package com.shubham.api.portfolio.service.impl;

import com.shubham.api.portfolio.entity.Resume;
import com.shubham.api.portfolio.exception.ResourceNotFoundException;
import com.shubham.api.portfolio.repository.ResumeRepository;
import com.shubham.api.portfolio.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Override
    public Resume getResume() {
        return resumeRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Resume data not found!"));
    }
}
