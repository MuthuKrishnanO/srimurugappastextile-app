package com.techface.srimurugapa.scheme.model;

import com.google.gson.annotations.SerializedName;

public class ExitSchemeDetails {

    @SerializedName("cus_scheme_id")
    String  mUserSchemeId;


    @SerializedName("status")
    String mStatus;

    @SerializedName("cus_id")
   String musers_id;

    @SerializedName("agent_id")
    String mAgentId;

    @SerializedName("scheme_name")
    String mScheme_name;

    @SerializedName("scheme_startdate")
    String mScheme_startdate;

    @SerializedName("scheme_enddate")
    String mScheme_enddate;


    @SerializedName("scheme_amount")
    String scheme_amount;

    @SerializedName("scheme_duration")
    String mScheme_duration;

    @SerializedName("pay_count")
    String mSchemecount;

    @SerializedName("scheme_id")
    String mJewll_scheme_id;

    @SerializedName("gold_22k")
    String mGold_22k;

    @SerializedName("gold_24k")
    String mGold_24k;

    @SerializedName("tot")
    String mTotal;

    @SerializedName("is_active")
    String isactive;

    public String getmAgentId() {
        return mAgentId;
    }

    public void setmAgentId(String mAgentId) {
        this.mAgentId = mAgentId;
    }

    public String getmTotal() {
        return mTotal;
    }

    public void setmTotal(String mTotal) {
        this.mTotal = mTotal;
    }

    public String getmGold_22k() {
        return mGold_22k;
    }

    public void setmGold_22k(String mGold_22k) {
        this.mGold_22k = mGold_22k;
    }

    public String getmGold_24k() {
        return mGold_24k;
    }

    public void setmGold_24k(String mGold_24k) {
        this.mGold_24k = mGold_24k;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public String getmSchemecount() {
        return mSchemecount;
    }

    public void setmSchemecount(String mSchemecount) {
        this.mSchemecount = mSchemecount;
    }

    public String getmUserSchemeId() {
        return mUserSchemeId;
    }

    public void setmUserSchemeId(String mUserSchemeId) {
        this.mUserSchemeId = mUserSchemeId;
    }

    public String getMusers_id() {
        return musers_id;
    }

    public void setMusers_id(String musers_id) {
        this.musers_id = musers_id;
    }

    public String getmScheme_name() {
        return mScheme_name;
    }

    public void setmScheme_name(String mScheme_name) {
        this.mScheme_name = mScheme_name;
    }

    public String getmScheme_startdate() {
        return mScheme_startdate;
    }

    public void setmScheme_startdate(String mScheme_startdate) {
        this.mScheme_startdate = mScheme_startdate;
    }

    public String getmScheme_enddate() {
        return mScheme_enddate;
    }

    public void setmScheme_enddate(String mScheme_enddate) {
        this.mScheme_enddate = mScheme_enddate;
    }

    public String getScheme_amount() {
        return scheme_amount;
    }

    public void setScheme_amount(String scheme_amount) {
        this.scheme_amount = scheme_amount;
    }

    public String getmScheme_duration() {
        return mScheme_duration;
    }

    public void setmScheme_duration(String mScheme_duration) {
        this.mScheme_duration = mScheme_duration;
    }

    public String getmJewll_scheme_id() {
        return mJewll_scheme_id;
    }

    public void setmJewll_scheme_id(String mJewll_scheme_id) {
        this.mJewll_scheme_id = mJewll_scheme_id;
    }
}
