package com.techface.srimurugapa.scheme.DialogueFragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.techface.srimurugapa.scheme.Helper.Responseinfodialog;
import com.techface.srimurugapa.scheme.R;
import com.techface.srimurugapa.scheme.model.Schemeinfo;
import com.techface.srimurugapa.scheme.network.ApiClient;
import com.techface.srimurugapa.scheme.network.ServiceGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SchemeiInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SchemeiInfoFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static String TAG = SchemeiInfoFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;

    @BindView(R.id.table_layout_resume)
    TableLayout mTablelayout;
    private TableRow mTableRow;

    @BindView(R.id.resumeno)
    Button mResumeno;

    @BindView(R.id.pb_resume)
    ProgressBar mProgressBar;

    @BindView(R.id.Header_resume)
    TextView mHeader;
    private AlertDialog mInfo;
    private List<Schemeinfo> mResponseList;

    public SchemeiInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SchemeiInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SchemeiInfoFragment newInstance(String param1, String param2, String param3, int Param4) {
        SchemeiInfoFragment fragment = new SchemeiInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, String.valueOf(Param4));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_schemei_info, container, false);
        ButterKnife.bind(this, rootView);

        if (Integer.parseInt(mParam4)==1) {
            mHeader.setText(mParam3);
            addHeaders1();
            loaddata(mParam1, mParam2);
        } else if (Integer.parseInt(mParam4)==2){
            mHeader.setText(mParam3);
            addHeaders2();
            loaddatagram(mParam1,mParam3);


        }else
        {
            mHeader.setText(mParam3);
            addHeaders2();
            loaddatacustomize(mParam1,mParam3);
        }

        mResumeno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return rootView;
    }

    private void loaddatacustomize(String mParam1, String mParam3) {

        ApiClient service = ServiceGenerator.createService(ApiClient.class);
        Call<List<Schemeinfo>> call=service.getCustomizeSchemeInfo(mParam1,mParam2);
        mProgressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<List<Schemeinfo>>() {
            @Override
            public void onResponse(Call<List<Schemeinfo>> call, Response<List<Schemeinfo>> response) {
                mProgressBar.setVisibility(View.GONE);


                Log.i(TAG, "Response Code : " + response.code());
                if (response.body() != null) {
                    handleUpdationResponsegram(response.body());
                } else {
                    Log.i(TAG, "Response: is Empty ");
                }
            }

            @Override
            public void onFailure(Call<List<Schemeinfo>> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);

                mInfo = Responseinfodialog.alertshow(getContext(), "CONNECTION FAILURE", Color.RED);
                mInfo.show();
                t.printStackTrace();

            }
        });
    }

    private void loaddatagram(String mParam1, String mParam3) {

        ApiClient service = ServiceGenerator.createService(ApiClient.class);
        Call<List<Schemeinfo>> call=service.getGramSchemeInfo(mParam1,mParam2);
        mProgressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<List<Schemeinfo>>() {
            @Override
            public void onResponse(Call<List<Schemeinfo>> call, Response<List<Schemeinfo>> response) {
                mProgressBar.setVisibility(View.GONE);


                Log.i(TAG, "Response Code : " + response.code());
                if (response.body() != null) {
                    handleUpdationResponsegram(response.body());
                } else {
                    Log.i(TAG, "Response: is Empty ");
                }
            }

            @Override
            public void onFailure(Call<List<Schemeinfo>> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);

                mInfo = Responseinfodialog.alertshow(getContext(), "CONNECTION FAILURE", Color.RED);
                mInfo.show();
                t.printStackTrace();

            }
        });

    }

    private void handleUpdationResponsegram(List<Schemeinfo> responses) {

        if (responses != null && responses.size() != 0) {
            mResponseList = responses;

            for (int i = 0; i < responses.size(); i++) {
                /** Create a TableRow dynamically **/
                mTableRow = new TableRow(getContext());
                Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.source_sans_pro);
                mTableRow.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                if (i % 2 == 1) {
                    mTableRow.setBackgroundResource(R.drawable.bg_table_row_gradient);
                }
                /** Creating a TextView to add to the row **/
                TextView ChannelTV = new TextView(getContext());
                ChannelTV.setText(String.valueOf(i+1));
                ChannelTV.setGravity(Gravity.LEFT);
                ChannelTV.setTypeface(typeface);
                ChannelTV.setTextColor(Color.BLACK);
                ChannelTV.setPadding(4, 4, 40, 4);
                mTableRow.addView(ChannelTV);  // Adding textView to tablerow.

                String outputPattern = "dd-MMM-yyyy";
                String inputPattern = "yyyy-MM-dd";

                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

                Date date = null;

                String str = null;

                try {
                    date = inputFormat.parse(responses.get(i).getEntrydate());
                    str = outputFormat.format(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                TextView amountTV = new TextView(getContext());
                // amountTV.setText();
                amountTV.setGravity(Gravity.LEFT);
                amountTV.setTextColor(Color.BLUE);
                amountTV.setText(str);
                amountTV.setTypeface(typeface);
                amountTV.setPadding(4, 4, 40, 4);
                mTableRow.addView(amountTV);  // Adding textView to tablerow.

                TextView amountvaliday = new TextView(getContext());
                // amountTV.setText();
                amountvaliday .setGravity(Gravity.LEFT);
                amountvaliday.setTextColor(Color.BLUE);
                amountvaliday.setText(responses.get(i).getPayedamount());
                amountvaliday.setTypeface(typeface);
                amountvaliday.setPadding(4, 4, 40, 4);
                mTableRow.addView(amountvaliday);  // Adding textView to tablerow.


                TextView gold22k = new TextView(getContext());
                // amountTV.setText();
                gold22k .setGravity(Gravity.LEFT);
                gold22k.setTextColor(Color.BLUE);
                gold22k.setText(responses.get(i).getMgold_22k());
                gold22k.setTypeface(typeface);
                gold22k.setPadding(4, 4, 40, 4);
                mTableRow.addView(gold22k);  // Adding textView to tablerow.

                TextView gold24k = new TextView(getContext());
                // amountTV.setText();
                gold24k .setGravity(Gravity.LEFT);
                gold24k.setTextColor(Color.BLUE);
                gold24k.setText(responses.get(i).getMgold_24k());
                gold24k.setTypeface(typeface);
                gold24k.setPadding(4, 4, 40, 4);
                mTableRow.addView(gold24k);

                TextView dateTV = new TextView(getContext());
                // amountTV.setText();
                dateTV.setGravity(Gravity.LEFT);
                dateTV.setTextColor(Color.BLUE);
                String s="success";
                if(responses.get(i).getmPaymentstatus().equals("1"))
                {
                    s="success";
                }
                else {
                    s="success  update";
                }
                dateTV.setText(s);
                dateTV.setTypeface(typeface);
                dateTV.setPadding(4, 4, 40, 4);
                mTableRow.addView(dateTV);  // Adding textView to tablerow.

                TextView amountvaliday1 = new TextView(getContext());
                // amountTV.setText();
                amountvaliday1 .setGravity(Gravity.LEFT);
                amountvaliday1.setTextColor(Color.BLUE);
                amountvaliday1.setText(responses.get(i).getmPaymentType());
                amountvaliday1.setTypeface(typeface);
                amountvaliday1.setPadding(4, 4, 40, 4);
                mTableRow.addView(amountvaliday1);

                mTablelayout.addView(mTableRow, new TableLayout.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
            }
        }
        else {

            mTableRow = new TableRow(getContext());
            mTableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView companyTV = new TextView(getContext());
            companyTV.setText("No Package Details Available !!!");
            companyTV.setGravity(Gravity.CENTER);
            companyTV.setTypeface(Typeface.DEFAULT);
            companyTV.setPadding(4, 4, 4, 4);
            mTableRow.addView(companyTV);


            mTablelayout.addView(mTableRow, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));


        }


    }

    private void addHeaders2() {

        mTableRow = new TableRow(getContext());
        mTableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT
                , TableRow.LayoutParams.WRAP_CONTENT));
        mTableRow.setBackgroundResource(R.drawable.table_header_bg);

        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.source_sans_pro);
        TextView mHeader1 = new TextView(getContext());
        mHeader1.setText("S.No");
        mHeader1.setTextColor(Color.BLACK);
        mHeader1.setGravity(Gravity.LEFT);
        mHeader1.setTypeface(typeface, Typeface.BOLD);
        //   mHeader1.setTextAppearance(android.R.style.TextAppearance_Widget_Button);
        mHeader1.setPadding(4, 4, 40, 4);
        mTableRow.addView(mHeader1);


        TextView mHeader2 = new TextView(getContext());
        mHeader2.setText("Entry Date");
        mHeader2.setTextColor(Color.BLACK);
        mHeader2.setGravity(Gravity.LEFT);
        mHeader2.setTypeface(typeface, Typeface.BOLD);
        //  mHeader2.setTextAppearance(android.R.style.TextAppearance_Widget_Button);
        mHeader2.setPadding(4, 4, 40, 4);
        mTableRow.addView(mHeader2);

        TextView mHeader3 = new TextView(getContext());
        mHeader3.setText("Amount");
        mHeader3.setTextColor(Color.BLACK);
        mHeader3.setGravity(Gravity.LEFT);
        mHeader3.setTypeface(typeface, Typeface.BOLD);
        //  mHeader2.setTextAppearance(android.R.style.TextAppearance_Widget_Button);
        mHeader3.setPadding(4, 4, 40, 4);
        mTableRow.addView(mHeader3);

        TextView mHeader4 = new TextView(getContext());
        mHeader4.setText("Gold_22k");
        mHeader4.setTextColor(Color.BLACK);
        mHeader4.setGravity(Gravity.LEFT);
        mHeader4.setTypeface(typeface, Typeface.BOLD);
        //  mHeader2.setTextAppearance(android.R.style.TextAppearance_Widget_Button);
        mHeader4.setPadding(4, 4, 40, 4);
        mTableRow.addView(mHeader4);

        TextView mHeader44 = new TextView(getContext());
        mHeader44.setText("Gold_24k");
        mHeader44.setTextColor(Color.BLACK);
        mHeader44.setGravity(Gravity.LEFT);
        mHeader44.setTypeface(typeface, Typeface.BOLD);
        //  mHeader2.setTextAppearance(android.R.style.TextAppearance_Widget_Button);
        mHeader44.setPadding(4, 4, 40, 4);
        mTableRow.addView(mHeader44);


        TextView mHeader41 = new TextView(getContext());
        mHeader41.setText("Payment Status");
        mHeader41.setTextColor(Color.BLACK);
        mHeader41.setGravity(Gravity.LEFT);
        mHeader41.setTypeface(typeface, Typeface.BOLD);
        //  mHeader2.setTextAppearance(android.R.style.TextAppearance_Widget_Button);
        mHeader41.setPadding(4, 4, 40, 4);
        mTableRow.addView(mHeader41);

        TextView mHeader5 = new TextView(getContext());
        mHeader5.setText("Payment Type");
        mHeader5.setTextColor(Color.BLACK);
        mHeader5.setGravity(Gravity.LEFT);
        mHeader5.setTypeface(typeface, Typeface.BOLD);
        //  mHeader2.setTextAppearance(android.R.style.TextAppearance_Widget_Button);
        mHeader5.setPadding(4, 4, 40, 4);
        mTableRow.addView(mHeader5);


        mTablelayout.addView(mTableRow, new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
    }




    private void loaddata(String mParam1,String mParam2) {

        ApiClient service = ServiceGenerator.createService(ApiClient.class);
        Call<List<Schemeinfo>> call=service.getSchemeInfoNew(mParam1,mParam2);
        mProgressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<List<Schemeinfo>>() {
            @Override
            public void onResponse(Call<List<Schemeinfo>> call, Response<List<Schemeinfo>> response) {
                mProgressBar.setVisibility(View.GONE);


                Log.i(TAG, "Response Code : " + response.code());
                if (response.body() != null) {
                    handleUpdationResponse(response.body());
                } else {
                    Log.i(TAG, "Response: is Empty ");
                }
            }

            @Override
            public void onFailure(Call<List<Schemeinfo>> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);

                mInfo = Responseinfodialog.alertshow(getContext(), "CONNECTION FAILURE", Color.RED);
                mInfo.show();
                t.printStackTrace();

            }
        });

    }

    private void handleUpdationResponse(List<Schemeinfo> responses) {

        if (responses != null && responses.size() != 0) {
            mResponseList = responses;

            for (int i = 0; i < responses.size(); i++) {
                /** Create a TableRow dynamically **/
                mTableRow = new TableRow(getContext());
                Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.source_sans_pro);
                mTableRow.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                if (i % 2 == 1) {
                    mTableRow.setBackgroundResource(R.drawable.bg_table_row_gradient);
                }
                /** Creating a TextView to add to the row **/
                TextView ChannelTV = new TextView(getContext());
                ChannelTV.setText(String.valueOf(i+1));
                ChannelTV.setGravity(Gravity.LEFT);
                ChannelTV.setTypeface(typeface);
                ChannelTV.setTextColor(Color.BLACK);
                ChannelTV.setPadding(4, 4, 40, 4);
                mTableRow.addView(ChannelTV);  // Adding textView to tablerow.

                String outputPattern = "dd-MMM-yyyy";
                String inputPattern = "yyyy-MM-dd";

                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

                Date date = null;

                String str = null;

                try {
                    date = inputFormat.parse(responses.get(i).getEntrydate());
                    str = outputFormat.format(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                TextView amountTV = new TextView(getContext());
                // amountTV.setText();
                amountTV.setGravity(Gravity.LEFT);
                amountTV.setTextColor(Color.BLUE);
                amountTV.setText(str);
                amountTV.setTypeface(typeface);
                amountTV.setPadding(4, 4, 40, 4);
                mTableRow.addView(amountTV);  // Adding textView to tablerow.

                TextView amountvaliday = new TextView(getContext());
                // amountTV.setText();
                amountvaliday .setGravity(Gravity.LEFT);
                amountvaliday.setTextColor(Color.BLUE);
                amountvaliday.setText(responses.get(i).getPayedamount());
                amountvaliday.setTypeface(typeface);
                amountvaliday.setPadding(4, 4, 40, 4);
                mTableRow.addView(amountvaliday);  // Adding textView to tablerow.

                TextView dateTV = new TextView(getContext());
                // amountTV.setText();
                dateTV.setGravity(Gravity.LEFT);
                dateTV.setTextColor(Color.BLUE);
                String s="success";
                if(responses.get(i).getmPaymentstatus().equals("1"))
                {
                    s="success";
                }
                else {
                    s="success  update";
                }
                dateTV.setText(s);
                dateTV.setTypeface(typeface);
                dateTV.setPadding(4, 4, 40, 4);
                mTableRow.addView(dateTV);  // Adding textView to tablerow.

                TextView amountvaliday1 = new TextView(getContext());
                // amountTV.setText();
                amountvaliday1 .setGravity(Gravity.LEFT);
                amountvaliday1.setTextColor(Color.BLUE);
                amountvaliday1.setText(responses.get(i).getmPaymentType());
                amountvaliday1.setTypeface(typeface);
                amountvaliday1.setPadding(4, 4, 40, 4);
                mTableRow.addView(amountvaliday1);

                mTablelayout.addView(mTableRow, new TableLayout.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
            }
        }
        else {

            mTableRow = new TableRow(getContext());
            mTableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView companyTV = new TextView(getContext());
            companyTV.setText("No Package Details Available !!!");
            companyTV.setGravity(Gravity.CENTER);
            companyTV.setTypeface(Typeface.DEFAULT);
            companyTV.setPadding(4, 4, 4, 4);
            mTableRow.addView(companyTV);


            mTablelayout.addView(mTableRow, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));


        }


    }

    private void addHeaders1() {
        mTableRow = new TableRow(getContext());
        mTableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT
                , TableRow.LayoutParams.WRAP_CONTENT));
        mTableRow.setBackgroundResource(R.drawable.table_header_bg);

        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.source_sans_pro);
        TextView mHeader1 = new TextView(getContext());
        mHeader1.setText("S.No");
        mHeader1.setTextColor(Color.BLACK);
        mHeader1.setGravity(Gravity.LEFT);
        mHeader1.setTypeface(typeface, Typeface.BOLD);
        //   mHeader1.setTextAppearance(android.R.style.TextAppearance_Widget_Button);
        mHeader1.setPadding(4, 4, 40, 4);
        mTableRow.addView(mHeader1);


        TextView mHeader2 = new TextView(getContext());
        mHeader2.setText("Entry Date");
        mHeader2.setTextColor(Color.BLACK);
        mHeader2.setGravity(Gravity.LEFT);
        mHeader2.setTypeface(typeface, Typeface.BOLD);
        //  mHeader2.setTextAppearance(android.R.style.TextAppearance_Widget_Button);
        mHeader2.setPadding(4, 4, 40, 4);
        mTableRow.addView(mHeader2);

        TextView mHeader3 = new TextView(getContext());
        mHeader3.setText("Amount");
        mHeader3.setTextColor(Color.BLACK);
        mHeader3.setGravity(Gravity.LEFT);
        mHeader3.setTypeface(typeface, Typeface.BOLD);
        //  mHeader2.setTextAppearance(android.R.style.TextAppearance_Widget_Button);
        mHeader3.setPadding(4, 4, 40, 4);
        mTableRow.addView(mHeader3);

        TextView mHeader4 = new TextView(getContext());
        mHeader4.setText("Payment Status");
        mHeader4.setTextColor(Color.BLACK);
        mHeader4.setGravity(Gravity.LEFT);
        mHeader4.setTypeface(typeface, Typeface.BOLD);
        //  mHeader2.setTextAppearance(android.R.style.TextAppearance_Widget_Button);
        mHeader4.setPadding(4, 4, 40, 4);
        mTableRow.addView(mHeader4);

        TextView mHeader5 = new TextView(getContext());
        mHeader5.setText("Payment Type");
        mHeader5.setTextColor(Color.BLACK);
        mHeader5.setGravity(Gravity.LEFT);
        mHeader5.setTypeface(typeface, Typeface.BOLD);
        //  mHeader2.setTextAppearance(android.R.style.TextAppearance_Widget_Button);
        mHeader5.setPadding(4, 4, 40, 4);
        mTableRow.addView(mHeader5);


        mTablelayout.addView(mTableRow, new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
    }
}