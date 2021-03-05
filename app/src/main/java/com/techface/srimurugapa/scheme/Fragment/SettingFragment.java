package com.techface.srimurugapa.scheme.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techface.srimurugapa.scheme.DialogueFragment.AboutFragment;
import com.techface.srimurugapa.scheme.DialogueFragment.ChangePasswordFragment;
import com.techface.srimurugapa.scheme.DialogueFragment.PersonalInformationFragment;
import com.techface.srimurugapa.scheme.Helper.Constants;
import com.techface.srimurugapa.scheme.Helper.SessionManager;
import com.techface.srimurugapa.scheme.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    @BindView(R.id.operator_name)
    TextView mPersonalInformation;

    @BindView(R.id.Log_out)
    TextView mLogout;

    @BindView(R.id.about)
    TextView mAbout;

    @BindView(R.id.key)
    TextView mKey;

    @BindView(R.id.subscriber_name)
    TextView mUsername;

    private  String Username;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this,rootView);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        Username = sharedPreferences.getString(Constants.USERNAME, "");

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


        mPersonalInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadPersonaldata();
            }
        });

        return  rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        Username = sharedPreferences.getString(Constants.USERNAME, "");
        mUsername.setText(Username);
    }

    private void LoadPersonaldata() {
        PersonalInformationFragment dialogue = PersonalInformationFragment.newInstance("","");
        dialogue.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialogue.show(getActivity().getSupportFragmentManager(), "fragment_new_package");
        dialogue.setCancelable(false);


    }

    private void confirmExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
        builder.setMessage("Do you want to log out ?")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SessionManager.logoutUser(getContext());

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
        pin.show(getFragmentManager(), "Title");
    }



    private void loadPassword() {
        ChangePasswordFragment dialogue = ChangePasswordFragment.newInstance("","");
        dialogue.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialogue.show(getActivity().getSupportFragmentManager(), "fragment_new_package");
        dialogue.setCancelable(false);




    }
}