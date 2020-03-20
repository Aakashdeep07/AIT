package com.noone.ait;

public class complaintHandler {
    public String userId, userEmail, subject, complaint;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public complaintHandler(String userId, String userEmail, String subject, String complaint) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.subject = subject;
        this.complaint = complaint;
    }
}
