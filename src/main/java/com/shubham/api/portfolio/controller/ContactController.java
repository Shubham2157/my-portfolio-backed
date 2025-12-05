package com.shubham.api.portfolio.controller;

import com.shubham.api.portfolio.dto.ContactResponse;
import com.shubham.api.portfolio.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public ResponseEntity<ContactResponse> getContact() {
        return ResponseEntity.ok(contactService.getContactDetails());
    }
}
