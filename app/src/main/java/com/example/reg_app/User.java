package com.example.reg_app;


public class User {
    String name;
    String mail;
    String contact;
    String branch;
    String date;
    String volunteer;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(String volunteer) {
        this.volunteer = volunteer;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    String year;

    public User(String name, String mail, String contact,String year,String branch,String date,String volunteer) {
        this.name = name;
        this.mail = mail;
        this.contact = contact;
        this.year=year;
        this.branch=branch;
        this.date=date;
        this.volunteer=volunteer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}