package com.techface.srimurugapa.scheme.Helper;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.techface.srimurugapa.scheme.R;


public class CommonUtils {

    public static ProgressDialog getProgressDialog(Context context) {

        ProgressDialog pd = new ProgressDialog(context, R.style.MyAlertTheme);
        pd.setMax(100);
        pd.setMessage("Loading Please Wait.....");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        pd.setIndeterminate(true);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        return pd;

    }

}



