package com.techface.srimurugapa.scheme.network;

import com.techface.srimurugapa.scheme.model.BranchDetails;
import com.techface.srimurugapa.scheme.model.DataStatus;
import com.techface.srimurugapa.scheme.model.ExitSchemeDetails;
import com.techface.srimurugapa.scheme.model.Imageslide;
import com.techface.srimurugapa.scheme.model.LoginResponse;
import com.techface.srimurugapa.scheme.model.ProductList;
import com.techface.srimurugapa.scheme.model.SchemeDetails;
import com.techface.srimurugapa.scheme.model.Schemeinfo;
import com.techface.srimurugapa.scheme.model.billscheme;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface  ApiClient {


    @POST("srimurugapatextileapi/user_login.php")
    @FormUrlEncoded
    Call<List<LoginResponse>>getLoginDetails(@Field("mobilenumber")String mEmail,
                                             @Field("firebase")String firebase);



    @POST("srimurugapatextileapi/loginactivestatus.php")
    @FormUrlEncoded
    Call<List<LoginResponse>>getLoginDetailsActiveStatus(@Field("userid")String mEmail);


    @POST("srimurugapatextileapi/existingscheme.php")
    @FormUrlEncoded
    Call<List<ExitSchemeDetails>>getExitisingSchemeNew(@Field("userid")String userid,
                                                    @Field("schemetype")int mSchemeid,
                                                    @Field("agent_id")String agentid);


    @POST("srimurugapatextileapi/schemeinfo.php")
    @FormUrlEncoded
    Call<List<Schemeinfo>>getSchemeInfoNew(@Field("scheme_userid")String schemuserid,@Field("userid")String userid);


    @POST("srimurugapatextileapi/payexistscheme.php")
    @FormUrlEncoded
    Call<List<DataStatus>>getPaymentSchemeInfoNew(@Field("userid") String userid,
                                               @Field("schemeid")String schemeid,
                                               @Field("entrydate")String startdate,
                                               @Field("amount")String amount,
                                               @Field("payment_type")String mPaymenttype,
                                               @Field("payment_status")String mPaymentstatus,
                                               @Field("duration")String mDuration,
                                               @Field("count")String mCount,
                                               @Field("branchid")String branchid);

    @POST("srimurugapatextileapi/billsscheme.php")
    @FormUrlEncoded
    Call<List<billscheme>>getBillNew(@Field("userid")String userid);


    @POST("srimurugapatextileapi/personaljson.php")
    @FormUrlEncoded
    Call<List<LoginResponse>>getPersonalJsonNew(@Field("user_id")String userid);


    @POST("srimurugapatextileapi/updatepersonalinformation.php")
    @FormUrlEncoded
    Call<List<DataStatus>>getUpdatePersonalStatusNew(@Field("userid")String userid,
                                                  @Field("username")String username,
                                                  @Field("email")String email,
                                                  @Field("dob")String dob,
                                                  @Field("address")String address,
                                                  @Field("city")String city,
                                                  @Field("state")String mState,
                                                  @Field("proofnumber")String proofnumber,
                                                  @Field("nomineename")String nominee,
                                                  @Field("nomineerelation")String nomineeName);

    @GET("srimurugapatextileapi/branchdetails.php")
    Call<List<BranchDetails>>getBranchDetailsNew();


    @GET("srimurugapatextileapi/imagedetails.php")
    Call<List<Imageslide>>getImageDetailsNew();


    /*--------------------------------------------------------------------------------------------------------*/


    // Get the Product Details
    @GET("/bins/zlu6c")
    Call<List<ProductList>>getProductdetail();


    @GET("goldgramscheme/goldscheme/branchdetails.php")
    Call<List<BranchDetails>>getBranchDetails();

    @POST("goldgramscheme/goldscheme/golddaliyvalue.php")
    @FormUrlEncoded
    Call<List<BranchDetails>>getDailyGoldValueDetails(@Field("branch_id")String branchid);


    @POST("goldgramscheme/goldscheme/user_login.php")
    @FormUrlEncoded
    Call<List<LoginResponse>>getLoginDetails(@Field("email")String mEmail,
                                             @Field("password")String mPassword,
                                             @Field("firebase")String firebase);


    @POST("goldgramscheme/goldscheme/schemedetails.php")
    @FormUrlEncoded
    Call<List<SchemeDetails>>getschemeDetails(@Field("branch_id")String mBranchid,@Field("scheme_type")String mscheme);

    //singnupdetails

    @POST("goldgramscheme/goldscheme/user_singnup.php")
    @FormUrlEncoded
    Call<List<DataStatus>>getSingnUpStatus(@Field("username")String Username,
                                           @Field("email")String email,
                                           @Field("password")String password,
                                           @Field("mobilenumber")String mobilenumber,
                                           @Field("branchid") String branchid,
                                           @Field("firebaseid")String firebaseid);


    @POST("goldgramscheme/goldscheme/otpverification.php")
    @FormUrlEncoded
    Call<List<DataStatus>>getOTPVerification(@Field("mobilenumber")String Phone);



    @POST("goldgramscheme/goldscheme/addnewscheme.php")
    @FormUrlEncoded
    Call<List<DataStatus>>getJoinSchemeStatus(@Field("userid") String userid,
                                              @Field("schemeid")String schemeid,
                                              @Field("schemename")String schemename,
                                              @Field("startdate")String startdate,
                                              @Field("enddate") String enddate,
                                              @Field("duration")String duration,
                                              @Field("amount")String amount,
                                              @Field("payment_type")String mPaymenttype,
                                              @Field("payment_status")String mPaymentstatus,
                                              @Field("branch_id")String branch_id);


    @POST("goldgramscheme/goldscheme/addgramnewscheme.php")
    @FormUrlEncoded
    Call<List<DataStatus>>getJoinGramScheme(@Field("userid") String userid,
                                            @Field("schemeid")String schemeid,
                                            @Field("schemename")String schemename,
                                            @Field("startdate")String startdate,
                                            @Field("enddate") String enddate,
                                            @Field("duration")String duration,
                                            @Field("amount")String amount,
                                            @Field("payment_type")String mPaymenttype,
                                            @Field("payment_status")String mPaymentstatus,
                                            @Field("branch_id")String branch_id);
    @POST("goldgramscheme/goldscheme/addCustomizenewscheme.php")
    @FormUrlEncoded
    Call<List<DataStatus>>getJoinCustomizeScheme(@Field("userid") String userid,
                                            @Field("schemeid")String schemeid,
                                            @Field("schemename")String schemename,
                                            @Field("startdate")String startdate,
                                            @Field("enddate") String enddate,
                                            @Field("duration")String duration,
                                            @Field("amount")String amount,
                                            @Field("payment_type")String mPaymenttype,
                                            @Field("payment_status")String mPaymentstatus,
                                            @Field("branch_id")String branch_id);




    @POST("goldgramscheme/goldscheme/existingscheme.php")
    @FormUrlEncoded
    Call<List<ExitSchemeDetails>>getExitisingScheme(@Field("userid")String userid,
                                                    @Field("schemetype")int mSchemeid);

    @POST("goldgramscheme/goldscheme/schemeinfo.php")
    @FormUrlEncoded
    Call<List<Schemeinfo>>getSchemeInfo(@Field("scheme_userid")String schemuserid,@Field("userid")String userid);


    @POST("goldgramscheme/goldscheme/schemegraminfo.php")
    @FormUrlEncoded
    Call<List<Schemeinfo>>getGramSchemeInfo(@Field("scheme_userid")String schemuserid,@Field("userid")String userid);

    @POST("goldgramscheme/goldscheme/schemecutomize.php")
    @FormUrlEncoded
    Call<List<Schemeinfo>>getCustomizeSchemeInfo(@Field("scheme_userid")String schemuserid,@Field("userid")String userid);

    @POST("goldgramscheme/goldscheme/payexistscheme.php")
    @FormUrlEncoded
    Call<List<DataStatus>>getPaymentSchemeInfo(@Field("userid") String userid,
                                               @Field("schemeid")String schemeid,
                                               @Field("entrydate")String startdate,
                                               @Field("amount")String amount,
                                               @Field("payment_type")String mPaymenttype,
                                               @Field("payment_status")String mPaymentstatus,
                                               @Field("duration")String mDuration,
                                               @Field("count")String mCount,
                                               @Field("branchid")String branchid);

    @POST("goldgramscheme/goldscheme/payexistgramscheme.php")
    @FormUrlEncoded
    Call<List<DataStatus>>getPaymentGramSchemeInfo(@Field("userid") String userid,
                                               @Field("schemeid")String schemeid,
                                               @Field("entrydate")String startdate,
                                               @Field("amount")String amount,
                                               @Field("payment_type")String mPaymenttype,
                                               @Field("payment_status")String mPaymentstatus,
                                               @Field("duration")String mDuration,
                                               @Field("count")String mCount,
                                                @Field("branchid")String branchid);
    @POST("goldgramscheme/goldscheme/payexistcustomizescheme.php")
    @FormUrlEncoded
    Call<List<DataStatus>>getPaymentCustomizeSchemeInfo(@Field("userid") String userid,
                                                   @Field("schemeid")String schemeid,
                                                   @Field("entrydate")String startdate,
                                                   @Field("amount")String amount,
                                                   @Field("payment_type")String mPaymenttype,
                                                   @Field("payment_status")String mPaymentstatus,
                                                   @Field("duration")String mDuration,
                                                   @Field("count")String mCount,
                                                   @Field("branchid")String branchid);




    @POST("goldgramscheme/goldscheme/updatepersonalinformation.php")
    @FormUrlEncoded
    Call<List<DataStatus>>getUpdatePersonalStatus(@Field("userid")String userid,
                                                  @Field("username")String username,
                                                  @Field("dob")String dob,
                                                  @Field("address")String address,
                                                  @Field("city")String city,
                                                  @Field("panno")String panno,
                                                  @Field("prooftype") String prooftype,
                                                  @Field("proofnumber")String proofnumber,
                                                  @Field("nominee")String nominee,
                                                  @Field("nomineename")String nomineeName);


    @POST("goldgramscheme/goldscheme/changepassword.php")
    @FormUrlEncoded
    Call<List<DataStatus>>getChangePassword(@Field("user_id")String userid,
                                            @Field("oldpassword")String oldpassword,
                                            @Field("newpassword")String password);



    @POST("goldgramscheme/goldscheme/personaljson.php")
    @FormUrlEncoded
    Call<List<LoginResponse>>getPersonalJson(@Field("user_id")String userid);


    @POST("goldgramscheme/goldscheme/schemecheck.php")
    @FormUrlEncoded
    Call<List<DataStatus>>getSchemeCheck(@Field("userid")String Userid,
                                         @Field("jewellschemeid")String jewellid,
                                         @Field("schemetype")String schemetype);


    @POST("goldgramscheme/goldscheme/billsscheme.php")
    @FormUrlEncoded
    Call<List<billscheme>>getBill(@Field("userid")String userid);

    @POST("goldgramscheme/goldscheme/mygoldscheme.php")
    @FormUrlEncoded
    Call<List<billscheme>>getGOldGram(@Field("userid")String userid);

    @POST("goldgramscheme/goldscheme/cusscheme.php")
    @FormUrlEncoded
    Call<List<billscheme>>getCusSchemeGram(@Field("userid")String userid);


    @POST("goldgramscheme/goldscheme/imagedetails.php")
    @FormUrlEncoded
    Call<List<Imageslide>>getImageDetails(@Field("branch_id")String branchid);

    @POST("goldgramscheme/goldscheme/forgetpassword.php")
    @FormUrlEncoded
    Call<List<DataStatus>>getForgetPassword(@Field("mobilenumber")String mobilenumber);


    @POST("goldgramscheme/goldscheme/schemegramdetails.php")
    @FormUrlEncoded
    Call<List<SchemeDetails>>getSchemeGramDetails(@Field("branch_id")String mBranchid,@Field("scheme_type")String schemetype);


}
