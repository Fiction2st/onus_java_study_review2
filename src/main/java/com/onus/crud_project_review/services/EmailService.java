package com.onus.crud_project_review.services;

public interface EmailService {
    void sendEmail(String to, String name);
    void sendVerificationCodeEmail(String to, String code);
}
