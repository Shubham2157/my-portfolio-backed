package com.shubham.api.portfolio.service.impl;

import com.shubham.api.portfolio.dto.SkillResponse;
import com.shubham.api.portfolio.exception.ResourceNotFoundException;
import com.shubham.api.portfolio.repository.SkillRepository;
import com.shubham.api.portfolio.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Override
    public List<SkillResponse> getAllSkills() {
        List<SkillResponse> skills = skillRepository.findAll()
                .stream()
                .map(skill -> new SkillResponse(
                        skill.getSkillId(),
                        skill.getName(),
                        skill.getCategory(),
                        skill.getIcon(),
                        skill.getColor(),
                        skill.getLevel(),
                        skill.getUrl()
                ))
                .toList();

        if (skills.isEmpty()) {
            throw new ResourceNotFoundException("No skills found!");
        }

        return skills;
    }
}
