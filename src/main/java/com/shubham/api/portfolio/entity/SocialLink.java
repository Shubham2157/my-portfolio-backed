package com.shubham.api.portfolio.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "social_links")
@Getter
@Setter
public class SocialLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String linkId;
    private String label;
    private String url;
    private String ariaLabel;
    private String iconName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    // Getters & Setters
}
