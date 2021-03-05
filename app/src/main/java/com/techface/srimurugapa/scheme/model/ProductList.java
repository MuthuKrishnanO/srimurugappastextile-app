package com.techface.srimurugapa.scheme.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductList {

    @SerializedName("TotalAmount")

    @Expose
    private String Totalamount;
    @SerializedName("userid")

    @Expose
    private String UserId;

    @SerializedName("product")
    @Expose
    ArrayList<product>mProducts;


    public String getTotalamount() {
        return Totalamount;
    }

    public void setTotalamount(String totalamount) {
        Totalamount = totalamount;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }



    public ArrayList<product> getmProducts() {
        return mProducts;
    }

    public void setmProducts(ArrayList<product> mProducts) {
        this.mProducts = mProducts;
    }





    public static class product implements Serializable {

   @SerializedName("id")
   @Expose
   String mProductId;

   @SerializedName("itemname")
   @Expose
   String mItemName;

   @SerializedName("itemrate")
   @Expose
   String mItemRate;

   @SerializedName("imageurl")
   @Expose
   String mImageUrl;

   @SerializedName("quantity")
   @Expose
   String mQuantity;

   @SerializedName("volume")
   @Expose
   String mVolume;

   @SerializedName("total")
   @Expose
   int   mTotal;


   public product(String id, String productname, String itemrate, String quantity,int mTotal)
   {

        this.mProductId=id;
        this.mItemName=productname;
        this.mItemRate=itemrate;
        this.mQuantity=quantity;
        this.mTotal=mTotal;
   }

        public String getmVolume() {
            return mVolume;
        }

        public void setmVolume(String mVolume) {
            this.mVolume = mVolume;
        }

        public String getmImageUrl() {
            return mImageUrl;
        }

        public void setmImageUrl(String mImageUrl) {
            this.mImageUrl = mImageUrl;
        }

        public int getmTotal() {
            return mTotal;
        }

        public void setmTotal(int mTotal) {
            this.mTotal = mTotal;
        }

        public String getmProductId() {
            return mProductId;
        }

        public void setmProductId(String mProductId) {
            this.mProductId = mProductId;
        }

        public String getmItemName() {
            return mItemName;
        }

        public void setmItemName(String mItemName) {
            this.mItemName = mItemName;
        }

        public String getmItemRate() {
            return mItemRate;
        }

        public void setmItemRate(String mItemRate) {
            this.mItemRate = mItemRate;
        }

        public String getmQuantity() {
            return mQuantity;
        }

        public void setmQuantity(String mQuantity) {
            this.mQuantity = mQuantity;
        }
    }
}