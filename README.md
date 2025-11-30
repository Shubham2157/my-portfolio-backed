# Backend API Documentation for Portfolio (Spring Boot)

This document describes the required API responses and Spring Boot implementation for the portfolio backend.

## 🚀 Tech Stack

- **Framework**: Spring Boot 3.x
- **Language**: Java 17+
- **Build Tool**: Maven or Gradle
- **Database**: PostgreSQL / MySQL
- **Web**: Spring Web MVC
- **ORM**: Spring Data JPA / Hibernate
- **API**: RESTful API with JSON

## 📋 API Base URL

```
NEXT_PUBLIC_API_BASE_URL=https://your-api.com/api
```

All endpoints should be prefixed with this base URL.

## 📁 Spring Boot Project Structure

```
src/main/java/com/portfolio/
├── PortfolioApplication.java
├── config/
│   ├── CorsConfig.java
│   └── SecurityConfig.java
├── controller/
│   ├── ProjectController.java
│   ├── BlogController.java
│   └── ContactController.java
├── service/
│   ├── ProjectService.java
│   ├── BlogService.java
│   └── ContactService.java
├── repository/
│   ├── ProjectRepository.java
│   ├── BlogRepository.java
│   └── ContactRepository.java
├── entity/
│   ├── Project.java
│   ├── BlogPost.java
│   └── ContactMessage.java
├── dto/
│   ├── ProjectDTO.java
│   ├── BlogPostDTO.java
│   ├── ContactFormDTO.java
│   └── ApiResponse.java
└── exception/
    └── GlobalExceptionHandler.java
```

---

## 🎯 API Endpoints

---

## 💻 Spring Boot Implementation

### 1. pom.xml Dependencies

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
        <relativePath/>
    </parent>

    <groupId>com.portfolio</groupId>
    <artifactId>portfolio-api</artifactId>
    <version>1.0.0</version>
    <name>Portfolio API</name>

    <properties>
        <java.version>17</java.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- Spring Data JPA -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <!-- PostgreSQL Driver -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Lombok (Optional - for reducing boilerplate) -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- Spring Boot Test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

### 2. Application Properties

**application.yml**
```yaml
spring:
  application:
    name: portfolio-api
  
  datasource:
    url: jdbc:postgresql://localhost:5432/portfolio_db
    username: postgres
    password: your_password
    driver-class-name: org.postgresql.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
    show-sql: false
  
  jackson:
    serialization:
      write-dates-as-timestamps: false
      indent-output: true

server:
  servlet:
    context-path: /api
  port: 8080
```

### 3. CORS Configuration

**CorsConfig.java**
```java
package com.portfolio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://your-portfolio-domain.com", "http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
```

### 4. Entity Classes

**Entity - BlogPost.java**
```java
package com.portfolio.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "blog_posts")
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String excerpt;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(name = "read_time")
    private Integer readTime;

    @Column(name = "author")
    private String author;

    @Column(name = "image_url")
    private String image;

    @Column(columnDefinition = "TEXT")
    private String tags;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getExcerpt() { return excerpt; }
    public void setExcerpt(String excerpt) { this.excerpt = excerpt; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public Integer getReadTime() { return readTime; }
    public void setReadTime(Integer readTime) { this.readTime = readTime; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
```

**Entity - Project.java**
```java
package com.portfolio.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(columnDefinition = "TEXT")
    private String technologies;

    @Column(name = "github_link")
    private String link;

    @Column(name = "demo_link")
    private String demo;

    @Column(name = "image_url")
    private String image;

    @Column(name = "featured")
    private Boolean featured = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getTechnologies() { return technologies; }
    public void setTechnologies(String technologies) { this.technologies = technologies; }
    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
    public String getDemo() { return demo; }
    public void setDemo(String demo) { this.demo = demo; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public Boolean getFeatured() { return featured; }
    public void setFeatured(Boolean featured) { this.featured = featured; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
```

**Entity - ContactMessage.java**
```java
package com.portfolio.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contact_messages")
public class ContactMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(name = "submitted_at", updatable = false)
    private LocalDateTime submittedAt;

    @Column(name = "status")
    private String status = "received";

    @PrePersist
    protected void onCreate() {
        submittedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
```

### 5. DTO Classes

**ApiResponse.java**
```java
package com.portfolio.dto;

public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private String error;
    private String timestamp;

    public ApiResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    public ApiResponse(boolean success, String message, String error) {
        this.success = success;
        this.message = message;
        this.error = error;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
```

**BlogPostDTO.java**
```java
package com.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogPostDTO {
    private String id;
    private String slug;
    private String title;
    private String excerpt;
    private String content;  // Only included in detail view
    private LocalDateTime date;
    private Integer readTime;
    private String author;
    private String image;
    private String tags;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getExcerpt() { return excerpt; }
    public void setExcerpt(String excerpt) { this.excerpt = excerpt; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public Integer getReadTime() { return readTime; }
    public void setReadTime(Integer readTime) { this.readTime = readTime; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
}
```

### 6. Repository Classes

**ProjectRepository.java**
```java
package com.portfolio.repository;

import com.portfolio.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
}
```

**BlogRepository.java**
```java
package com.portfolio.repository;

import com.portfolio.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<BlogPost, String> {
    BlogPost findBySlug(String slug);
}
```

**ContactRepository.java**
```java
package com.portfolio.repository;

import com.portfolio.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<ContactMessage, String> {
}
```

### 7. Service Classes

**BlogService.java**
```java
package com.portfolio.service;

import com.portfolio.entity.BlogPost;
import com.portfolio.dto.BlogPostDTO;
import com.portfolio.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    public List<BlogPostDTO> getAllBlogPosts() {
        return blogRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BlogPostDTO getBlogPostById(String id) {
        BlogPost post = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog post not found"));
        return convertToDetailDTO(post);
    }

    public BlogPostDTO getBlogPostBySlug(String slug) {
        BlogPost post = blogRepository.findBySlug(slug);
        if (post == null) {
            throw new RuntimeException("Blog post not found");
        }
        return convertToDetailDTO(post);
    }

    private BlogPostDTO convertToDTO(BlogPost post) {
        BlogPostDTO dto = new BlogPostDTO();
        dto.setId(post.getId());
        dto.setSlug(post.getSlug());
        dto.setTitle(post.getTitle());
        dto.setExcerpt(post.getExcerpt());
        dto.setDate(post.getDate());
        dto.setReadTime(post.getReadTime());
        dto.setAuthor(post.getAuthor());
        dto.setImage(post.getImage());
        dto.setTags(post.getTags());
        // Note: content is NOT included in list view
        return dto;
    }

    private BlogPostDTO convertToDetailDTO(BlogPost post) {
        BlogPostDTO dto = convertToDTO(post);
        dto.setContent(post.getContent());  // Include content only in detail view
        return dto;
    }
}
```

**ProjectService.java**
```java
package com.portfolio.service;

import com.portfolio.entity.Project;
import com.portfolio.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Arrays;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public void convertTechnologiesToArray(Project project) {
        // Store technologies as comma-separated string, but can be converted to array
        if (project.getTechnologies() != null) {
            // Technologies are stored as "Tech1,Tech2,Tech3"
            String[] techs = project.getTechnologies().split(",");
            // Return as array when needed
        }
    }
}
```

**ContactService.java**
```java
package com.portfolio.service;

import com.portfolio.entity.ContactMessage;
import com.portfolio.dto.ContactFormDTO;
import com.portfolio.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    @Autowired(required = false)
    private JavaMailSender mailSender;

    public ContactMessage saveContactMessage(ContactFormDTO dto) {
        ContactMessage message = new ContactMessage();
        message.setName(dto.getName());
        message.setEmail(dto.getEmail());
        message.setMessage(dto.getMessage());
        message.setStatus("received");

        ContactMessage saved = contactRepository.save(message);

        // Optional: Send email notification
        if (mailSender != null) {
            sendEmailNotification(saved);
        }

        return saved;
    }

    private void sendEmailNotification(ContactMessage message) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo("your-email@example.com");
            email.setSubject("New Contact Message from " + message.getName());
            email.setText("Email: " + message.getEmail() + "\n\n" + message.getMessage());
            mailSender.send(email);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}
```

### 8. Controller Classes

**BlogController.java**
```java
package com.portfolio.controller;

import com.portfolio.dto.ApiResponse;
import com.portfolio.dto.BlogPostDTO;
import com.portfolio.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BlogPostDTO>>> getAllBlogPosts() {
        try {
            List<BlogPostDTO> posts = blogService.getAllBlogPosts();
            return ResponseEntity.ok(
                new ApiResponse<>(true, posts, "Blog posts fetched successfully")
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                new ApiResponse<>(false, "Failed to fetch blog posts", e.getMessage())
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BlogPostDTO>> getBlogPostById(
            @PathVariable String id) {
        try {
            BlogPostDTO post = blogService.getBlogPostById(id);
            return ResponseEntity.ok(
                new ApiResponse<>(true, post, "Blog post fetched successfully")
            );
        } catch (Exception e) {
            return ResponseEntity.status(404).body(
                new ApiResponse<>(false, "Blog post not found", e.getMessage())
            );
        }
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResponse<BlogPostDTO>> getBlogPostBySlug(
            @PathVariable String slug) {
        try {
            BlogPostDTO post = blogService.getBlogPostBySlug(slug);
            return ResponseEntity.ok(
                new ApiResponse<>(true, post, "Blog post fetched successfully")
            );
        } catch (Exception e) {
            return ResponseEntity.status(404).body(
                new ApiResponse<>(false, "Blog post not found", e.getMessage())
            );
        }
    }
}
```

**ProjectController.java**
```java
package com.portfolio.controller;

import com.portfolio.dto.ApiResponse;
import com.portfolio.entity.Project;
import com.portfolio.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Project>>> getAllProjects() {
        try {
            List<Project> projects = projectService.getAllProjects();
            return ResponseEntity.ok(
                new ApiResponse<>(true, projects, "Projects fetched successfully")
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                new ApiResponse<>(false, "Failed to fetch projects", e.getMessage())
            );
        }
    }
}
```

**ContactController.java**
```java
package com.portfolio.controller;

import com.portfolio.dto.ApiResponse;
import com.portfolio.dto.ContactFormDTO;
import com.portfolio.entity.ContactMessage;
import com.portfolio.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<ApiResponse<ContactMessage>> submitContact(
            @RequestBody ContactFormDTO contactForm) {
        try {
            // Validation
            if (contactForm.getName() == null || contactForm.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, "Invalid form data", "Name is required")
                );
            }
            if (contactForm.getEmail() == null || !contactForm.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, "Invalid form data", "Valid email is required")
                );
            }
            if (contactForm.getMessage() == null || contactForm.getMessage().length() < 10) {
                return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, "Invalid form data", "Message must be at least 10 characters")
                );
            }

            ContactMessage message = contactService.saveContactMessage(contactForm);
            return ResponseEntity.ok(
                new ApiResponse<>(true, message, "Message received successfully")
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                new ApiResponse<>(false, "Failed to send message", e.getMessage())
            );
        }
    }
}
```

---

## 🎯 API Endpoints

**Response (200 OK):**
```json
{
  "success": true,
  "data": [
    {
      "id": "1",
      "title": "E-Commerce Platform",
      "description": "Full-stack e-commerce solution with payment integration",
      "technologies": [
        "Next.js",
        "Stripe",
        "PostgreSQL",
        "Tailwind CSS"
      ],
      "link": "https://github.com/yourprofile/ecommerce",
      "demo": "https://ecommerce-demo.com",
      "image": "https://your-cdn.com/project1.jpg"
    },
    {
      "id": "2",
      "title": "AI Chat Application",
      "description": "Real-time chat application with AI-powered responses",
      "technologies": [
        "React",
        "Node.js",
        "WebSocket",
        "OpenAI API"
      ],
      "link": "https://github.com/yourprofile/ai-chat",
      "demo": "https://ai-chat-demo.com"
    }
  ],
  "message": "Projects fetched successfully"
}
```

**Error Response (500):**
```json
{
  "success": false,
  "message": "Failed to fetch projects",
  "error": "Database connection error"
}
```

**Note:** If API fails or returns error, frontend will fallback to config data from `config/projects.ts`

---

### 2. GET `/blog`

Fetch all blog posts (list view only - no content).

**Request:**
```
GET /api/blog
```

**Response (200 OK):**
```json
{
  "success": true,
  "data": [
    {
      "id": "1",
      "slug": "getting-started-nextjs-15",
      "title": "Getting Started with Next.js 15",
      "excerpt": "Explore the new features and improvements in Next.js 15",
      "date": "2024-01-15",
      "readTime": 5,
      "author": "Shubham Kumar Jha"
    },
    {
      "id": "2",
      "slug": "web-performance-optimization",
      "title": "Web Performance Optimization Tips",
      "excerpt": "Essential techniques to improve your web application's performance",
      "date": "2024-01-10",
      "readTime": 8,
      "author": "Shubham Kumar Jha"
    }
  ],
  "message": "Blog posts fetched successfully"
}
```

**Error Response (500):**
```json
{
  "success": false,
  "message": "Failed to fetch blog posts",
  "error": "Database connection error"
}
```

**Note:** If API fails, frontend will fallback to config data from `config/blog.ts`

---

### 3. GET `/blog/:id` (or `/blog/:slug`)

Fetch a specific blog post with full markdown content.

**Request:**
```
GET /api/blog/1
or
GET /api/blog/getting-started-nextjs-15
```

**Response (200 OK):**
```json
{
  "success": true,
  "data": {
    "id": "1",
    "slug": "getting-started-nextjs-15",
    "title": "Getting Started with Next.js 15",
    "excerpt": "Explore the new features and improvements in Next.js 15",
    "date": "2024-01-15",
    "readTime": 5,
    "author": "Shubham Kumar Jha",
    "content": "# Getting Started with Next.js 15\n\nNext.js 15 brings exciting new features and improvements...\n\n## Key Features\n\n### 1. Improved Performance\n- Faster build times\n- Better runtime performance\n\n### 2. Enhanced Developer Experience\n- Better error messages\n- Improved TypeScript support\n\n## Getting Started\n\n```bash\nnpx create-next-app@latest my-app\ncd my-app\nnpm run dev\n```\n\n## Conclusion\n\nNext.js 15 is a major step forward in React framework development."
  },
  "message": "Blog post fetched successfully"
}
```

**Error Response (404):**
```json
{
  "success": false,
  "message": "Blog post not found",
  "error": "No blog post with id '1' found"
}
```

**Note:** If API fails, frontend will fallback to config data from `config/blog.ts`

---

### 4. POST `/contact`

Submit a contact form message.

**Request:**
```
POST /api/contact
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "message": "Hello, I'd like to discuss a project..."
}
```

**Response (200 OK):**
```json
{
  "success": true,
  "data": {
    "id": "contact-123",
    "name": "John Doe",
    "email": "john@example.com",
    "message": "Hello, I'd like to discuss a project...",
    "submittedAt": "2025-01-15T10:30:00Z",
    "status": "received"
  },
  "message": "Message received successfully"
}
```

**Error Response (400):**
```json
{
  "success": false,
  "message": "Invalid form data",
  "errors": {
    "name": "Name is required",
    "email": "Valid email is required",
    "message": "Message must be at least 10 characters"
  }
}
```

**Error Response (500):**
```json
{
  "success": false,
  "message": "Failed to send message",
  "error": "Email service unavailable"
}
```

---

## 📊 Data Schemas

### Project Schema

```typescript
interface Project {
  id: string                    // Unique identifier
  title: string                 // Project name
  description: string           // Project description
  technologies: string[]        // Tech stack (e.g., ["React", "Node.js"])
  link?: string                 // GitHub or project link
  demo?: string                 // Live demo URL (optional)
  image?: string                // Project image URL (optional)
  featured?: boolean            // Feature this project (optional)
}
```

### Blog Post (List) Schema

```typescript
interface BlogPostSummary {
  id: string                    // Unique identifier
  slug: string                  // URL-friendly slug (e.g., "getting-started-nextjs-15")
  title: string                 // Blog post title
  excerpt: string               // Short preview (150-200 chars)
  date: string                  // Publication date (ISO format: "2024-01-15")
  readTime: number              // Estimated read time in minutes
  author?: string               // Author name (optional)
  image?: string                // Featured image URL (optional)
  tags?: string[]               // Tags/categories (optional)
}
```

### Blog Post (Detail) Schema

```typescript
interface BlogPost {
  id: string                    // Unique identifier
  slug: string                  // URL-friendly slug
  title: string                 // Blog post title
  excerpt: string               // Short preview
  date: string                  // Publication date (ISO format)
  readTime: number              // Estimated read time
  author?: string               // Author name
  image?: string                // Featured image URL
  tags?: string[]               // Tags/categories
  content: string               // Full markdown content
  createdAt?: string            // Creation timestamp
  updatedAt?: string            // Last update timestamp
}
```

### Contact Form Schema

```typescript
interface ContactForm {
  name: string                  // Sender's name (required)
  email: string                 // Sender's email (required, valid email)
  message: string               // Message content (required, min 10 chars)
}
```

### Contact Response Schema

```typescript
interface ContactResponse {
  id: string                    // Response ID
  name: string                  // Sender's name
  email: string                 // Sender's email
  message: string               // Message content
  submittedAt: string           // Submission timestamp (ISO format)
  status: "received" | "processed" | "replied"
}
```

---

## 🔄 API Response Wrapper

All API responses should follow this standard wrapper format:

```typescript
interface ApiResponse<T> {
  success: boolean              // Operation success status
  data?: T                       // Response data (if successful)
  message: string               // Human-readable message
  error?: string                // Error details (if failed)
  timestamp?: string            // Response timestamp
  errors?: Record<string, string>  // Field-level errors (validation)
}
```

---

## 🏃 Running the Application

### Build and Run

```bash
# Clone the repository
git clone https://github.com/yourprofile/portfolio-api.git
cd portfolio-api

# Build with Maven
mvn clean install

# Run the application
mvn spring-boot:run

# Or build JAR and run
mvn clean package
java -jar target/portfolio-api-1.0.0.jar
```

The API will start on `http://localhost:8080/api`

### Database Setup

```sql
-- Create database
CREATE DATABASE portfolio_db;

-- Create tables (Spring will handle with JPA ddl-auto: update)
-- Or manually run:

CREATE TABLE blog_posts (
    id UUID PRIMARY KEY,
    slug VARCHAR(255) UNIQUE NOT NULL,
    title VARCHAR(255) NOT NULL,
    excerpt TEXT NOT NULL,
    content TEXT NOT NULL,
    date TIMESTAMP NOT NULL,
    read_time INTEGER,
    author VARCHAR(255),
    image_url VARCHAR(255),
    tags TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE projects (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    technologies TEXT,
    github_link VARCHAR(255),
    demo_link VARCHAR(255),
    image_url VARCHAR(255),
    featured BOOLEAN DEFAULT false,
    created_at TIMESTAMP
);

CREATE TABLE contact_messages (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    submitted_at TIMESTAMP,
    status VARCHAR(50) DEFAULT 'received'
);
```

## ⚙️ Advanced Configuration

### 1. **Rate Limiting**

Add to your controller:
```java
@Component
public class RateLimitingInterceptor implements HandlerInterceptor {
    private final Map<String, List<LocalDateTime>> userRequests = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
                           Object handler) throws Exception {
        String clientIp = request.getRemoteAddr();
        userRequests.computeIfAbsent(clientIp, k -> new ArrayList<>())
            .add(LocalDateTime.now());
        
        List<LocalDateTime> requests = userRequests.get(clientIp);
        long allowedRequests = requests.stream()
            .filter(time -> time.isAfter(LocalDateTime.now().minusHours(1)))
            .count();
        
        if (allowedRequests > 100) {
            response.setStatus(HttpServletResponse.SC_TOO_MANY_REQUESTS);
            return false;
        }
        return true;
    }
}
```

### 2. **Exception Handler**

**GlobalExceptionHandler.java**
```java
package com.portfolio.exception;

import com.portfolio.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(500).body(
            new ApiResponse<>(false, "An error occurred", e.getMessage())
        );
    }
}
```

### 3. **Pagination Support**

Add Spring Data Pagination:
```java
@GetMapping
public ResponseEntity<Page<BlogPostDTO>> getBlogPosts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
    Page<BlogPost> posts = blogRepository.findAll(PageRequest.of(page, size));
    return ResponseEntity.ok(posts.map(this::convertToDTO));
}
```

### 4. **Caching**

Add Spring Cache:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

```java
@Cacheable("blogPosts")
public List<BlogPostDTO> getAllBlogPosts() {
    return blogRepository.findAll()
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
}
```

### 5. **Email Configuration**

Add to pom.xml:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

Add to application.yml:
```yaml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: your-email@gmail.com
    password: your-app-password
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
```

### 6. **Markdown Validation**

For markdown validation:
```java
private boolean isValidMarkdown(String content) {
    // Basic validation - check for common markdown patterns
    return content != null && content.length() > 0 && content.contains("#");
}
```

### 7. **Response Time**
- Keep response times under 2-3 seconds for better UX
- Use `@Cacheable` for frequently accessed data
- Implement pagination for large collections

### 8. **Markdown Format**
Blog post content should be valid markdown:
```markdown
# Heading 1
## Heading 2
**Bold** and *italic*
- List item
- Another item

\`\`\`language
code block
\`\`\`

[Link](https://example.com)
```

---

## 📱 Frontend Integration

### Projects
```typescript
// Tries API first, falls back to config
const response = await fetch(`${process.env.NEXT_PUBLIC_API_BASE_URL}/projects`)
const projects = response.ok ? response.data : demoProjects
```

### Blog (List)
```typescript
// Tries API first, falls back to config
const response = await fetch(`${process.env.NEXT_PUBLIC_API_BASE_URL}/blog`)
const posts = response.ok ? response.data : demoBlogPosts
```

### Blog (Detail)
```typescript
// Tries API first, falls back to config
const response = await fetch(`${process.env.NEXT_PUBLIC_API_BASE_URL}/blog/${slug}`)
const post = response.ok ? response.data : demoBlogPosts.find(p => p.slug === slug)
```

### Contact
```typescript
// Direct submission - no fallback
const response = await fetch(`${process.env.NEXT_PUBLIC_API_BASE_URL}/contact`, {
  method: "POST",
  headers: { "Content-Type": "application/json" },
  body: JSON.stringify(form)
})
```

---

## 🧪 Testing with cURL

### Get Projects
```bash
curl -X GET http://localhost:8080/api/projects \
  -H "Content-Type: application/json"
```

### Get Blog List
```bash
curl -X GET http://localhost:8080/api/blog \
  -H "Content-Type: application/json"
```

### Get Blog Detail by ID
```bash
curl -X GET http://localhost:8080/api/blog/uuid-here \
  -H "Content-Type: application/json"
```

### Get Blog Detail by Slug
```bash
curl -X GET http://localhost:8080/api/blog/slug/getting-started-nextjs-15 \
  -H "Content-Type: application/json"
```

### Submit Contact Form
```bash
curl -X POST http://localhost:8080/api/contact \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "message": "Hello, I am interested in your services"
  }'
```

## 🏃 Running the Application

### Build and Run

```bash
# Clone the repository
git clone https://github.com/yourprofile/portfolio-api.git
cd portfolio-api

# Build with Maven
mvn clean install

# Run the application
mvn spring-boot:run

# Or build JAR and run
mvn clean package
java -jar target/portfolio-api-1.0.0.jar
```

The API will start on `http://localhost:8080/api`

### Database Setup

```sql
-- Create database
CREATE DATABASE portfolio_db;

-- Create tables (Spring will handle with JPA ddl-auto: update)
-- Or manually run:

CREATE TABLE blog_posts (
    id UUID PRIMARY KEY,
    slug VARCHAR(255) UNIQUE NOT NULL,
    title VARCHAR(255) NOT NULL,
    excerpt TEXT NOT NULL,
    content TEXT NOT NULL,
    date TIMESTAMP NOT NULL,
    read_time INTEGER,
    author VARCHAR(255),
    image_url VARCHAR(255),
    tags TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE projects (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    technologies TEXT,
    github_link VARCHAR(255),
    demo_link VARCHAR(255),
    image_url VARCHAR(255),
    featured BOOLEAN DEFAULT false,
    created_at TIMESTAMP
);

CREATE TABLE contact_messages (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    submitted_at TIMESTAMP,
    status VARCHAR(50) DEFAULT 'received'
);
```

## ⚙️ Advanced Configuration

### 1. **Rate Limiting**

Add to your controller:
```java
@Component
public class RateLimitingInterceptor implements HandlerInterceptor {
    private final Map<String, List<LocalDateTime>> userRequests = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
                           Object handler) throws Exception {
        String clientIp = request.getRemoteAddr();
        userRequests.computeIfAbsent(clientIp, k -> new ArrayList<>())
            .add(LocalDateTime.now());
        
        List<LocalDateTime> requests = userRequests.get(clientIp);
        long allowedRequests = requests.stream()
            .filter(time -> time.isAfter(LocalDateTime.now().minusHours(1)))
            .count();
        
        if (allowedRequests > 100) {
            response.setStatus(HttpServletResponse.SC_TOO_MANY_REQUESTS);
            return false;
        }
        return true;
    }
}
```

### 2. **Exception Handler**

**GlobalExceptionHandler.java**
```java
package com.portfolio.exception;

import com.portfolio.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(500).body(
            new ApiResponse<>(false, "An error occurred", e.getMessage())
        );
    }
}
```

### 3. **Pagination Support**

Add Spring Data Pagination:
```java
@GetMapping
public ResponseEntity<Page<BlogPostDTO>> getBlogPosts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
    Page<BlogPost> posts = blogRepository.findAll(PageRequest.of(page, size));
    return ResponseEntity.ok(posts.map(this::convertToDTO));
}
```

### 4. **Caching**

Add Spring Cache:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

```java
@Cacheable("blogPosts")
public List<BlogPostDTO> getAllBlogPosts() {
    return blogRepository.findAll()
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
}
```

### 5. **Email Configuration**

Add to pom.xml:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

Add to application.yml:
```yaml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: your-email@gmail.com
    password: your-app-password
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
```

### 6. **Markdown Validation**

For markdown validation:
```java
private boolean isValidMarkdown(String content) {
    // Basic validation - check for common markdown patterns
    return content != null && content.length() > 0 && content.contains("#");
}
```

### 7. **Response Time**
- Keep response times under 2-3 seconds for better UX
- Use `@Cacheable` for frequently accessed data
- Implement pagination for large collections

### 8. **Markdown Format**
Blog post content should be valid markdown:
```markdown
# Heading 1
## Heading 2
**Bold** and *italic*
- List item
- Another item

\`\`\`language
code block
\`\`\`

[Link](https://example.com)
```

---

## 📝 Fallback Configuration

If your backend API is unavailable, the portfolio uses fallback data from config files:

- **Projects**: `config/projects.ts`
- **Blog List**: `config/blog.ts`
- **Blog Detail**: `config/blog.ts` (filtered by ID/slug)
- **Skills**: `data/skills.json` (always from config, no API)
- **Social Links**: `config/social.ts` (always from config, no API)
- **Resume**: `config/resume.ts` (always from config, no API)

This ensures your portfolio continues to work even if the backend is down.

## 🔐 Security Best Practices

### 1. **Input Validation**
- Validate all form inputs on backend
- Sanitize markdown content to prevent XSS
- Verify email format using regex or validators

```java
@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
private String email;
```

### 2. **Rate Limiting**
- Limit contact form submissions to 5 per hour per IP
- Implement rate limiting on API endpoints
- Use libraries like `bucket4j` or `resilience4j`

### 3. **CORS Configuration**
- Only allow requests from your domain
- Don't use `Access-Control-Allow-Origin: *` in production
- Specify exact allowed methods and headers

```java
.allowedOrigins("https://your-portfolio-domain.com")
.allowedMethods("GET", "POST", "PUT", "DELETE")
.allowCredentials(true)
```

### 4. **HTTPS Only**
- All API calls should use HTTPS
- Redirect HTTP to HTTPS in production
- Set `Strict-Transport-Security` headers

### 5. **Content Security**
- Escape all user-generated content
- Use CSP headers to prevent XSS
- Never execute user-provided code

### 6. **Database Security**
- Use strong credentials
- Enable SQL parameterization (JPA does this automatically)
- Regular backups and monitoring
- Never expose sensitive data in logs

### 7. **API Security**
```java
@RestController
@RequestMapping("/api")
public class ApiController {
    @PostMapping("/contact")
    public ResponseEntity<?> submitContact(@RequestBody @Valid ContactFormDTO dto) {
        // @Valid ensures validation
        // Use DTO instead of raw entity
        return ResponseEntity.ok(new ApiResponse<>(true, result, "Success"));
    }
}
```

## 📊 Database Schema Diagram

```
BlogPost:
├── id (UUID, Primary Key)
├── slug (VARCHAR, Unique)
├── title (VARCHAR)
├── excerpt (TEXT)
├── content (TEXT)
├── date (TIMESTAMP)
├── readTime (INTEGER)
├── author (VARCHAR)
├── image (VARCHAR)
├── tags (TEXT)
├── createdAt (TIMESTAMP)
└── updatedAt (TIMESTAMP)

Project:
├── id (UUID, Primary Key)
├── title (VARCHAR)
├── description (TEXT)
├── technologies (TEXT - CSV)
├── link (VARCHAR)
├── demo (VARCHAR)
├── image (VARCHAR)
├── featured (BOOLEAN)
└── createdAt (TIMESTAMP)

ContactMessage:
├── id (UUID, Primary Key)
├── name (VARCHAR)
├── email (VARCHAR)
├── message (TEXT)
├── submittedAt (TIMESTAMP)
└── status (VARCHAR)
```

## 📚 Useful Spring Boot Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Web MVC](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)
- [Hibernate Documentation](https://hibernate.org/orm/)
- [PostgreSQL JDBC Driver](https://jdbc.postgresql.org/)

## ✅ Deployment Checklist

- [ ] Database set up and migrated
- [ ] All endpoints tested with cURL
- [ ] CORS configured properly
- [ ] Rate limiting implemented
- [ ] Error handling and logging in place
- [ ] Email service configured (optional)
- [ ] Security headers added
- [ ] SSL/HTTPS enabled
- [ ] Environment variables configured
- [ ] Database backups scheduled
- [ ] API documentation complete
- [ ] Frontend `.env.local` updated with API URL
- [ ] Deployed to production server

## 🚀 Deployment Options

### Option 1: Heroku
```bash
heroku create your-portfolio-api
git push heroku main
```

### Option 2: AWS EC2
```bash
# Build JAR
mvn clean package

# Transfer to EC2
scp -i key.pem target/portfolio-api-1.0.0.jar ec2-user@your-instance:/home/ec2-user/

# Run on EC2
java -jar portfolio-api-1.0.0.jar
```

### Option 3: Docker
```dockerfile
FROM openjdk:17-slim
COPY target/portfolio-api-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

```bash
docker build -t portfolio-api .
docker run -p 8080:8080 portfolio-api
```

### Option 4: Railway/Render
- Push to GitHub
- Connect repository
- Auto-deploy on push
- Environment variables configured in dashboard

---

**Ready to build your Spring Boot backend! Refer to the full implementation examples above.**
