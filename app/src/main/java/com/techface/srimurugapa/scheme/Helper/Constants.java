package com.techface.srimurugapa.scheme.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Constants {

    public static final String ANDROID_ID = "androidId";
    public static final String DEVICE_ID = "deviceId";
    public static final String PRODUCT_ID = "product_id";
    public static  final String USERNAME="username";
    public  static final String PASSWORD ="password";
    public  static final String UserId="userid";
    public  static   final String BRANCHID="branchid";
    public static final String EMAIL="email";
    public static final String PHONE="phone";
    public  static final String BRANCHNAME="branchname";
    public static  final String ADDRESS="address";
    public static final String CITY="city";
    public static  final String STATE="state";
    public static  final String PINCODE="pincode";
    public static final String COUNTRY="country";
    public static final String DOB="dob";
    public static final String  PROOFTYPE="prooftype";
    public static  final String PROOFNUMBER="proffnumber";
    public static final String NOMINEE="nominee";
    public static final String NOMINEERELATION="mNomineeRelation";
    public static  final String PANNO="panno";
    public static  final String  BANKAPI="bankapi";
    public static final String MOBILENUMBER="mobilenumber";
    public static final String DATE="date";
    public static  final String GOLD_22k="gold_22k";
    public static final String GOLD_24k="gold_24k";
    public static final String MTITLE="mtilte";
    public static final String MMESSAGE="mmessage";
    public static final String MIMAGEURL="mimageurl";
    public static final String MTIMES="mTIMES";
    public static final String MPAYLOAD="mpayload";
    public static final String BRANCHADDRESS="mbranchaddress";
    public static final String ABOUTUS="aboutus";
    public static final String AGENTID="agentid";
    public static final String AGENTNAME="agentname";
    public static  final String AGENTPASSWORD="agentpassword";






    private static Constants mConstants;
    public static final long API_CONNECTION_TIMEOUT = 1201;
    public static final long API_READ_TIMEOUT = 901;

  //public static final String SERVERURL = "http://192.168.43.173";
  //public static final String SERVERURL="http://192.168.0.138";
  public static final String SERVERURL = "http://techface.tech/murugappasapi/";
  //public static  final String  SERVERURL= "http://bill.murugappasstores.com/murugappasapi/";


   private static final String BASE_URL_KEY = "base_url";


    public static Constants getInstance() {

        if (mConstants == null) {
            mConstants = new Constants();
        }
        return mConstants;
    }

    public String getBASE_URL(Context context) {

        SharedPreferences sP = PreferenceManager.getDefaultSharedPreferences(context);
        String str = sP.getString(BASE_URL_KEY, "");
        return str;
    }

    public void setBASE_URL(String baseUrl, Context context) {

        SharedPreferences sP = PreferenceManager.getDefaultSharedPreferences(context);
        String str = sP.getString(BASE_URL_KEY, "");
        if (!str.equals("") || !str.equals(baseUrl)) {
            SharedPreferences.Editor edit = sP.edit();
            edit.putString(BASE_URL_KEY, baseUrl);
            edit.apply();
        }
    }
}
