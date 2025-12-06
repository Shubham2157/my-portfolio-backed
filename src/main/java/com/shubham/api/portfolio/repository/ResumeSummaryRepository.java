package com.shubham.api.portfolio.repository;

import com.shubham.api.portfolio.entity.ResumeSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeSummaryRepository extends JpaRepository<ResumeSummary, Long> {
}
