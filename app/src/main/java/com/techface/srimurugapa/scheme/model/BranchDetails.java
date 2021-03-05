package com.techface.srimurugapa.scheme.model;

import com.google.gson.annotations.SerializedName;

public class BranchDetails {

    @SerializedName("admin_id")
    String mAdmin_id;

    @SerializedName("status")
    String status;

    @SerializedName("data")
    String data;

    @SerializedName("bankapi")
    String mBankApi;

    @SerializedName("branch_id")
    String mBranch_id;

    @SerializedName("branch_name")
    String mBranchName;

    @SerializedName("branch_address")
    String mBranchaddress;


    @SerializedName("update_date")
    String updatedate;

    @SerializedName("mobilenumber")
    String mMobileNumber;


    @SerializedName("about_us")
    String aboutus;

    public String getmBankApi() {
        return mBankApi;
    }

    public void setmBankApi(String mBankApi) {
        this.mBankApi = mBankApi;
    }

    public String getmMobileNumber() {
        return mMobileNumber;
    }

    public void setmMobileNumber(String mMobileNumber) {
        this.mMobileNumber = mMobileNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAboutus() {
        return aboutus;
    }

    public void setAboutus(String aboutus) {
        this.aboutus = aboutus;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }



    public String getmAdmin_id() {
        return mAdmin_id;
    }

    public void setmAdmin_id(String mAdmin_id) {
        this.mAdmin_id = mAdmin_id;
    }

    public String getmBranch_id() {
        return mBranch_id;
    }

    public void setmBranch_id(String mBranch_id) {
        this.mBranch_id = mBranch_id;
    }

    public String getmBranchName() {
        return mBranchName;
    }

    public void setmBranchName(String mBranchName) {
        this.mBranchName = mBranchName;
    }

    public String getmBranchaddress() {
        return mBranchaddress;
    }

    public void setmBranchaddress(String mBranchaddress) {
        this.mBranchaddress = mBranchaddress;
    }
}
