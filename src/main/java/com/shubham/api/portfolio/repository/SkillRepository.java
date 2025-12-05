package com.shubham.api.portfolio.repository;

import com.shubham.api.portfolio.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
