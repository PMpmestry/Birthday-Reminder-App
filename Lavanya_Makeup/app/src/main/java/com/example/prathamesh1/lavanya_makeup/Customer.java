package com.example.prathamesh1.lavanya_makeup;

/**
 * Created by HI on 5/10/2017.
 */

public class Customer {
    private String mobileNo, name, emailId, dob, doa, lln;

    public Customer(){}
    public Customer(String mobileNo, String name, String emailId, String dob, String doa, String lln ){
        this.setMobileNo(mobileNo);
        this.setName(name);
        this.setEmailId(emailId);
        this.setDob(dob);
        this.setDoa(doa);
        this.setLln(lln);
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) { this.emailId = emailId; }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDoa() {
        return doa;
    }

    public void setDoa(String doa) {
        this.doa = doa;
    }

    public String getLln() {
        return lln;
    }

    public void setLln(String lln) {
        this.lln = lln;
    }
}
