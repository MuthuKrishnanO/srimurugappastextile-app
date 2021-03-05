package com.techface.srimurugapa.scheme.Activity;



import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.techface.srimurugapa.scheme.DialogueFragment.AboutFragment;
import com.techface.srimurugapa.scheme.DialogueFragment.ChangePasswordFragment;
import com.techface.srimurugapa.scheme.DialogueFragment.PersonalInformationFragment;
import com.techface.srimurugapa.scheme.Helper.Constants;
import com.techface.srimurugapa.scheme.Helper.SessionManager;
import com.techface.srimurugapa.scheme.R;
import com.techface.srimurugapa.scheme.service.Config;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivty extends AppCompatActivity {
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;
    private static final String TAG = SplashActivity.class.getSimpleName();
    @BindView(R.id.operator_name)
    TextView mPersonalInformation;
    @BindView(R.id.toolbar_home)
    Toolbar mToolbar;

    @BindView(R.id.appbar_home)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.menuTitle)
    TextView mMenuTitle;
    @BindView(R.id.Log_out)
    TextView mLogout;

    @BindView(R.id.about)
    TextView mAbout;

    @BindView(R.id.key)
    TextView mKey;

    @BindView(R.id.subscriber_name)
    TextView mUsername;

    private  String Username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        mMenuTitle.setText("Setting");
        setSupportActionBar(mToolbar);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Username = sharedPreferences.getString(Constants.USERNAME, "");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmExit();

            }
        });

        mUsername.setText(Username);


        mKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPassword();
            }
        });

        mAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDiaLog();
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });


        mPersonalInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadPersonaldata();
            }
        });

      //  displayFirebaseRegId();





    }
;
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            txtRegId.setText("Firebase Reg Id: " + regId);
        else
            txtRegId.setText("Firebase Reg Id is not received yet!");

    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Username = sharedPreferences.getString(Constants.USERNAME, "");
        mUsername.setText(Username);
    }

    private void LoadPersonaldata() {
        PersonalInformationFragment dialogue = PersonalInformationFragment.newInstance("","");
        dialogue.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialogue.show(getSupportFragmentManager(), "fragment_new_package");
        dialogue.setCancelable(false);


    }

    private void confirmExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogTheme);
        builder.setMessage("Do you want to log out ?")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SessionManager.logoutUser(SettingActivty.this);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }



    private void loadDiaLog() {
        AboutFragment pin = AboutFragment.newInstance("","");
        pin.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        pin.show(getSupportFragmentManager(), "Title");
    }



    private void loadPassword() {
        ChangePasswordFragment dialogue = ChangePasswordFragment.newInstance("","");
        dialogue.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialogue.show(getSupportFragmentManager(), "fragment_new_package");
        dialogue.setCancelable(false);




    }
  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.download, menu);



        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_item:

                //this item has your app icon
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

}