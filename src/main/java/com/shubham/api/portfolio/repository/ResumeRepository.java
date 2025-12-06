package com.shubham.api.portfolio.repository;

import com.shubham.api.portfolio.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}
