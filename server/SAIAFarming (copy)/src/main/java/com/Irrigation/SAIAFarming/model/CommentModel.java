package com.Irrigation.SAIAFarming.model;

public class CommentModel {
    String user  ;
    String email;
    String website ;
    String date ;
    String summary ;
    String comment ;

    public CommentModel() {
    }

    public CommentModel(String user, String email, String website, String date, String summary, String comment) {
        this.user = user;
        this.email = email;
        this.website = website;
        this.date = date;
        this.summary = summary;
        this.comment = comment;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
