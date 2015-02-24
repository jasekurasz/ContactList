package com.gmail.jasekurasz.contactlist;

public class Person {

    String name;
    String company;
    String detailsURL;
    String smallImgURL;
    String birthday;
    String workNum;
    String homeNum;
    String mobileNum;

    public Person(String name, String company, String detailsURL, String smallImgURL, String birthday) {
        this.name = name;
        this.company = company;
        this.detailsURL = detailsURL;
        this.smallImgURL = smallImgURL;
        this.birthday = birthday;
    }

    public String toString() { return name; }

    //Getters
    public String getName() {
        return this.name;
    }

    public String getCompany() {
        return this.company;
    }

    public String getDetailsURL() { return this.detailsURL; }

    public String getSmallImgURL() { return this.smallImgURL; }

    public String getBirthday() { return this.birthday; }

    public String getworkNum() { return this.workNum; }

    public String getHomeNum() { return this.homeNum; }

    public String getMobileNum() { return this.mobileNum; }

    //Setters

    public void setworkNum(String num) { this.workNum = num; }

    public void sethomeNum(String num) { this.homeNum = num; }

    public void setmobileNum(String num) { this.mobileNum = num; }
}
