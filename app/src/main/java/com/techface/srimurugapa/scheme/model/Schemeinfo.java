package com.techface.srimurugapa.scheme.model;

import com.google.gson.annotations.SerializedName;

public class Schemeinfo {

    @SerializedName("sno")
    String mSno;

    @SerializedName("cus_id")
    String mUserid;

    @SerializedName("cus_scheme_id")
    String mUserschemeid;

    @SerializedName("entry_date")
    String entrydate;

    @SerializedName("payed_amount")
    String payedamount;

    @SerializedName("payment_status")
    String mPaymentstatus;

    @SerializedName("payment_type")
    String mPaymentType;

    @SerializedName("gold_22k")
    String mgold_22k;

    @SerializedName("gold_24k")
    String mgold_24k;


    public String getMgold_22k() {
        return mgold_22k;
    }

    public void setMgold_22k(String mgold_22k) {
        this.mgold_22k = mgold_22k;
    }

    public String getMgold_24k() {
        return mgold_24k;
    }

    public void setMgold_24k(String mgold_24k) {
        this.mgold_24k = mgold_24k;
    }

    public String getmUserid() {
        return mUserid;
    }

    public void setmUserid(String mUserid) {
        this.mUserid = mUserid;
    }

    public String getmSno() {
        return mSno;
    }

    public void setmSno(String mSno) {
        this.mSno = mSno;
    }

    public String getmUserschemeid() {
        return mUserschemeid;
    }

    public void setmUserschemeid(String mUserschemeid) {
        this.mUserschemeid = mUserschemeid;
    }

    public String getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(String entrydate) {
        this.entrydate = entrydate;
    }

    public String getPayedamount() {
        return payedamount;
    }

    public void setPayedamount(String payedamount) {
        this.payedamount = payedamount;
    }

    public String getmPaymentstatus() {
        return mPaymentstatus;
    }

    public void setmPaymentstatus(String mPaymentstatus) {
        this.mPaymentstatus = mPaymentstatus;
    }

    public String getmPaymentType() {
        return mPaymentType;
    }

    public void setmPaymentType(String mPaymentType) {
        this.mPaymentType = mPaymentType;
    }
}
