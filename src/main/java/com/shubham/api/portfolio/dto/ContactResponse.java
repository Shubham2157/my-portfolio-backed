package com.shubham.api.portfolio.dto;

import lombok.Data;
import java.util.List;

@Data
public class ContactResponse {
    private ContactInfo contact;
    private List<SocialLinkInfo> socialLinks;

    @Data
    public static class ContactInfo {
        private String email;
        private String phone;
    }

    @Data
    public static class SocialLinkInfo {
        private String id;
        private String label;
        private String url;
        private String ariaLabel;
        private String iconName;
    }
}
