package com.techface.srimurugapa.scheme.Helper;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AlertDialog;
import android.text.Html;
import android.view.Gravity;
import android.widget.TextView;

import com.techface.srimurugapa.scheme.R;


public class Responseinfodialog {


    public static AlertDialog alertshow(Context context, String msg, int color) {

        AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.MyAlertTheme).create();
        Typeface typeface = ResourcesCompat.getFont(context, R.font.source_sans_pro);
        TextView title = new TextView(context);
        title.setTextColor(Color.parseColor("#1976d2"));
        title.setTextSize(20);

        title.setTypeface(typeface);
        title.setPadding(10, 10, 10, 70);
        title.setText(Html.fromHtml("<b>" + "STATUS" + "</b>"));
        title.setGravity(Gravity.CENTER);
        alertDialog.setCustomTitle(title);

        TextView myMsg = new TextView(context);
        myMsg.setTextColor(color);
        myMsg.setTextSize(25);

        myMsg.setTypeface(typeface);

        myMsg.setPadding(10, 70, 10, 70);
        myMsg.setText(msg);
        myMsg.setGravity(Gravity.CENTER);

        alertDialog.setView(myMsg);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });


        return alertDialog;
    }
}






