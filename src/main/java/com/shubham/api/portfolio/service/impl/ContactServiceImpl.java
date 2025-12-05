package com.shubham.api.portfolio.service.impl;

import com.shubham.api.portfolio.dto.ContactResponse;
import com.shubham.api.portfolio.entity.Contact;
import com.shubham.api.portfolio.exception.ResourceNotFoundException;
import com.shubham.api.portfolio.repository.ContactRepository;
import com.shubham.api.portfolio.service.ContactService;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public ContactResponse getContactDetails() {
        Contact contact = contactRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Contact info not found!"));

        ContactResponse response = new ContactResponse();

        // Contact Info
        ContactResponse.ContactInfo contactInfo = new ContactResponse.ContactInfo();
        contactInfo.setEmail(contact.getEmail());
        contactInfo.setPhone(contact.getPhone());
        response.setContact(contactInfo);

        // Social Links
        response.setSocialLinks(
            contact.getSocialLinks().stream().map(link -> {
                ContactResponse.SocialLinkInfo info = new ContactResponse.SocialLinkInfo();
                info.setId(link.getLinkId());
                info.setLabel(link.getLabel());
                info.setUrl(link.getUrl());
                info.setAriaLabel(link.getAriaLabel());
                info.setIconName(link.getIconName());
                return info;
            }).toList()
        );

        return response;
    }
}
