package com.shubham.api.portfolio.repository;

import com.shubham.api.portfolio.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
