package com.techface.srimurugapa.scheme.model;

import com.google.gson.annotations.SerializedName;

public class SchemeDetails {

    @SerializedName("jewell_scheme_id")
    String mSchemeId;

    @SerializedName("branch_id")
    String mBranchId;

    @SerializedName("scheme_name")
    String mSchemeName;

    @SerializedName("scheme_amount")
    String mSchemeAmount;

   @SerializedName("scheme_duration")
    String mSchemeDuration;

   @SerializedName("scheme_type")
   String mSchemetype;

    public String getmSchemetype() {
        return mSchemetype;
    }

    public void setmSchemetype(String mSchemetype) {
        this.mSchemetype = mSchemetype;
    }

    @SerializedName("is_active")
    Boolean is_active;

    public String getmSchemeId() {
        return mSchemeId;
    }

    public void setmSchemeId(String mSchemeId) {
        this.mSchemeId = mSchemeId;
    }

    public String getmBranchId() {
        return mBranchId;
    }

    public void setmBranchId(String mBranchId) {
        this.mBranchId = mBranchId;
    }

    public String getmSchemeName() {
        return mSchemeName;
    }

    public void setmSchemeName(String mSchemeName) {
        this.mSchemeName = mSchemeName;
    }

    public String getmSchemeAmount() {
        return mSchemeAmount;
    }

    public void setmSchemeAmount(String mSchemeAmount) {
        this.mSchemeAmount = mSchemeAmount;
    }

    public String getmSchemeDuration() {
        return mSchemeDuration;
    }

    public void setmSchemeDuration(String mSchemeDuration) {
        this.mSchemeDuration = mSchemeDuration;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }
}
