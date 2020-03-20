package com.noone.ait;

import android.graphics.Bitmap;

public class User {
    public String student_name, roll, email, mobile, course, semester, profile_pic;
    public User(){

    }


    public User(String student_name, String roll, String email, String mobile, String course, String semester, String profile_pic) {
        this.student_name = student_name;
        this.roll = roll;
        this.email = email;
        this.mobile = mobile;
        this.course = course;
        this.semester = semester;
        this.profile_pic = profile_pic;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

}
