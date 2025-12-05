package com.shubham.api.portfolio.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "skills")
@Getter
@Setter
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "skill_id", nullable = false, unique = true)
    private String skillId;

    private String name;
    private String category;
    private String icon;
    private String color;
    private String level;
    private String url;

    // getters & setters
}
