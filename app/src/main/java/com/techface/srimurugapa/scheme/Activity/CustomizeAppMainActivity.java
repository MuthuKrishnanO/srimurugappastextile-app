package com.techface.srimurugapa.scheme.Activity;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import androidx.core.content.res.ResourcesCompat;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.text.pdf.draw.LineSeparator;
import com.techface.srimurugapa.scheme.Helper.CommonUtils;
import com.techface.srimurugapa.scheme.Helper.Constants;
import com.techface.srimurugapa.scheme.Helper.FileUtils;
import com.techface.srimurugapa.scheme.Helper.FooterUtils;
import com.techface.srimurugapa.scheme.Helper.PermissionChecker;
import com.techface.srimurugapa.scheme.Helper.Responseinfodialog;
import com.techface.srimurugapa.scheme.Helper.SearchableSpinner;
import com.techface.srimurugapa.scheme.R;
import com.techface.srimurugapa.scheme.model.Schemeinfo;
import com.techface.srimurugapa.scheme.model.billscheme;
import com.techface.srimurugapa.scheme.network.ApiClient;
import com.techface.srimurugapa.scheme.network.ServiceGenerator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.techface.srimurugapa.scheme.Activity.PermissionsActivity.PERMISSION_REQUEST_CODE;
import static java.lang.String.valueOf;

public class CustomizeAppMainActivity extends AppCompatActivity {


    @BindView(R.id.toolbar_home)
    Toolbar mToolbar;

    @BindView(R.id.appbar_home)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.menuTitle)
    TextView mMenuTitle;

    @BindView(R.id.table_layout_resume)
    TableLayout mTablelayout;

    @BindView(R.id.spinnermain)
    LinearLayout mLinearLayout;

    private TableRow mTableRow;


    @BindView(R.id.pb_resume)
    ProgressBar mProgressBar;
    public String date = "";

    @BindView(R.id.spin_cart)
    SearchableSpinner mPlanspinner;

    @BindView(R.id.downloadpdf)
    Button mDownload;

    @BindView(R.id.total)
    TextView mTotal;

    @BindView(R.id.gold22kgram)
    TextView mGold22k;

    @BindView(R.id.gold24kgram)
    TextView mGold24k;

    @BindView(R.id.totalpage)
    LinearLayout mLayout;


    private Double TotalAmount=0.0;
    private Double gold22k=0.0;
    private Double gold24k=0.0;



    PermissionChecker checker;

    private  static final String TAG= BillingActivity.class.getSimpleName();
    private  ArrayList<Integer>mSchemeid=new ArrayList<>();
    private ArrayList<String> mSchemeName=new ArrayList<>();
    private  ArrayList<String>scheme_startdate=new ArrayList<>();
    private  ArrayList<String>scheme_endate=new ArrayList<>();
    private  ArrayList<String>scheme_amount=new ArrayList<>();
    private  ArrayList<String>scheme_duration=new ArrayList<>();
    private  ArrayList<String>count=new ArrayList<>();
    private List<billscheme> mBillList;
    private String UserID;
    private ProgressDialog mProgressdialog;
    private AlertDialog mInfo;
    private List<Schemeinfo>mResponseList;
    //private double gold22k=0.0;

    private Integer selectedAreaId=0;
    private String SelectedAreaName="ALL";
    private MenuItem mPdf;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_app_main);
        ButterKnife.bind(this);
        mMenuTitle.setText("My GOLD Gram");
        setSupportActionBar(mToolbar);
        mProgressdialog = CommonUtils.getProgressDialog(this);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        UserID = sharedPreferences.getString(Constants.UserId, "");
        loadBillDetails();
        addHeaders1();
        mLayout.setVisibility(View.GONE);
        mTotal.setText(String.valueOf(" : ₹"+TotalAmount));
        mGold22k.setText(String.valueOf(" :  "+new DecimalFormat("##.###").format(gold22k)+" gram"));
        mGold24k.setText(String.valueOf(" :  "+new DecimalFormat("##.###").format(gold24k)+" gram"));




        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checker.lacksPermissions(PermissionChecker.REQUIRED_PERMISSION)) {
                    PermissionsActivity.startActivityForResult(CustomizeAppMainActivity.this, PERMISSION_REQUEST_CODE, PermissionChecker.REQUIRED_PERMISSION);
                } else {


                    createPdf(FileUtils.getAppPath(CustomizeAppMainActivity.this) + "TechFace.pdf");
                    //createPdf1(FileUtils.getAppPath(getContext()) + "operator.pdf");

                }


            }
        });


        mPlanspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                boolean areaid=mPlanspinner._isDirty;
                if(areaid==true)
                {

                    selectedAreaId =mSchemeid.get(position);
                    SelectedAreaName=mSchemeName.get(position);

                }

                mTablelayout.removeAllViewsInLayout();
                addHeaders1();
                TotalAmount=0.0;
                gold22k=0.0;
                gold24k=0.0;
                loadtable(String.valueOf(selectedAreaId));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        isReadStoragePermissionGranted();
        isWriteStoragePermissionGranted();
        checker = new PermissionChecker(CustomizeAppMainActivity.this);

    }

    private void loadBillDetails() {

        ApiClient service = ServiceGenerator.createService(ApiClient.class);
        Call<List<billscheme>> call=service.getCusSchemeGram(UserID);
        mProgressdialog.show();
        call.enqueue(new Callback<List<billscheme>>() {
            @Override
            public void onResponse(Call<List<billscheme>> call, Response<List<billscheme>> response) {
                mProgressdialog.cancel();
                Log.i(TAG, "ResponseCode : " + response.code());
                Log.i(TAG, "onResponse: ---------------------------");
                Log.i(TAG, "onResponse: " + response.body());
                Log.i(TAG, "onResponse: ____________________________");
                if(response.body()!=null) {
                    loadData(response.body());

                }
            }

            @Override
            public void onFailure(Call<List<billscheme>> call, Throwable t) {
                mProgressdialog.cancel();
                if(CustomizeAppMainActivity.this !=null) {
                    mInfo = Responseinfodialog.alertshow(CustomizeAppMainActivity.this, "NO SCHEME REGISTER OR CONTECTION FAILED", Color.RED);
                    mInfo.show();
                    mLinearLayout.setVisibility(View.GONE);
                    // mPdf.setVisible(false);
                    mDownload.setVisibility(View.GONE);


                }
                t.printStackTrace();
            }
        });
    }

    private void loadData(List<billscheme> body) {
        mBillList = body;
        if (body != null) {
            mLinearLayout.setVisibility(View.VISIBLE);
            mDownload.setVisibility(View.VISIBLE);

            ArrayList<String> areaListSpinner = new ArrayList<>();
            for (int i = -1; i < mBillList.size(); i++) {
                if (i == -1) {
                    areaListSpinner.add("All ");
                    mSchemeid.add(0);
                    mSchemeName.add("All ");
                } else {
                    areaListSpinner.add(mBillList.get(i).getmSchemename()+"("+mBillList.get(i).getmSchemeId()+")");
                    mSchemeid.add(Integer.parseInt(mBillList.get(i).getmSchemeId()));
                    mSchemeName.add(mBillList.get(i).getmSchemename());
                }
            }
            ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this ,
                    android.R.layout.simple_spinner_dropdown_item, areaListSpinner);
            mPlanspinner.setTitle("Search Area");
            mPlanspinner.setAdapter(mAdapter);


        }
    }

    private void loadtable(String mParam1) {

        ApiClient service = ServiceGenerator.createService(ApiClient.class);
        Call<List<Schemeinfo>> call=service.getCustomizeSchemeInfo(mParam1,UserID);
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

                mInfo = Responseinfodialog.alertshow(CustomizeAppMainActivity.this, "", Color.RED);
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
                mTableRow = new TableRow(this);
                mLayout.setVisibility(View.VISIBLE);
                Typeface typeface = ResourcesCompat.getFont(this, R.font.source_sans_pro);
                mTableRow.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                TotalAmount=TotalAmount+Double.parseDouble(mResponseList.get(i).getPayedamount());
                gold22k=gold22k+Double.parseDouble(mResponseList.get(i).getMgold_22k());
                gold24k=gold24k+Double.parseDouble(mResponseList.get(i).getMgold_24k());

                mTotal.setText(String.valueOf(" : ₹"+TotalAmount));
                mGold22k.setText(String.valueOf(" : "+new DecimalFormat("##.###").format(gold22k)+"gram"));
                mGold24k.setText(String.valueOf(" :  "+new DecimalFormat("##.###").format(gold24k)+"gram"));

                if (i % 2 == 1) {
                    mTableRow.setBackgroundResource(R.drawable.bg_table_row_gradient);
                }
                /** Creating a TextView to add to the row **/
                TextView ChannelTV = new TextView(this);
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
                TextView amountTV = new TextView(this);
                // amountTV.setText();
                amountTV.setGravity(Gravity.LEFT);
                amountTV.setTextColor(Color.BLUE);
                amountTV.setText(str);
                amountTV.setTypeface(typeface);
                amountTV.setPadding(4, 4, 40, 4);
                mTableRow.addView(amountTV);  // Adding textView to tablerow.

                TextView amountvaliday = new TextView(this);
                // amountTV.setText();
                amountvaliday .setGravity(Gravity.LEFT);
                amountvaliday.setTextColor(Color.BLUE);
                amountvaliday.setText(responses.get(i).getPayedamount());
                amountvaliday.setTypeface(typeface);
                amountvaliday.setPadding(4, 4, 40, 4);
                mTableRow.addView(amountvaliday);  // Adding textView to tablerow.


                TextView gold22k = new TextView(this);
                // amountTV.setText();
                gold22k .setGravity(Gravity.LEFT);
                gold22k.setTextColor(Color.BLUE);
                gold22k.setText(responses.get(i).getMgold_22k());
                gold22k.setTypeface(typeface);
                gold22k.setPadding(4, 4, 40, 4);
                mTableRow.addView(gold22k);
                TextView gold24k = new TextView(this);
                // amountTV.setText();
                gold24k .setGravity(Gravity.LEFT);
                gold24k.setTextColor(Color.BLUE);
                gold24k.setText(responses.get(i).getMgold_24k());
                gold24k.setTypeface(typeface);
                gold24k.setPadding(4, 4, 40, 4);
                mTableRow.addView(gold24k);
                TextView dateTV = new TextView(this);
                // amountTV.setText();
                dateTV.setGravity(Gravity.LEFT);
                dateTV.setTextColor(Color.BLUE);
                String s="success";
                if(responses.get(i).getmPaymentstatus().equals("1"))
                {
                    s="success";
                }
                else {
                    s="success update";
                }
                dateTV.setText(s);
                dateTV.setTypeface(typeface);
                dateTV.setPadding(4, 4, 40, 4);
                mTableRow.addView(dateTV);  // Adding textView to tablerow.

                TextView amountvaliday1 = new TextView(this);
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

            mTableRow = new TableRow(this);
            mTableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView companyTV = new TextView(this);
            companyTV.setText("No Package Details Available !!!");
            companyTV.setGravity(Gravity.CENTER);
            companyTV.setTypeface(Typeface.DEFAULT);
            companyTV.setPadding(4, 4, 4, 4);
            mTableRow.addView(companyTV);


            mTablelayout.addView(mTableRow, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            mLayout.setVisibility(View.GONE);


        }


    }

    private void addHeaders1() {
        mTableRow = new TableRow(this);
        mTableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT
                , TableRow.LayoutParams.WRAP_CONTENT));
        mTableRow.setBackgroundResource(R.drawable.table_header_bg);

        Typeface typeface = ResourcesCompat.getFont(this, R.font.source_sans_pro);
        TextView mHeader1 = new TextView(this);
        mHeader1.setText("S.No");
        mHeader1.setTextColor(Color.BLACK);
        mHeader1.setGravity(Gravity.LEFT);
        mHeader1.setTypeface(typeface, Typeface.BOLD);
        //   mHeader1.setTextAppearance(android.R.style.TextAppearance_Widget_Button);
        mHeader1.setPadding(4, 4, 40, 4);
        mTableRow.addView(mHeader1);


        TextView mHeader2 = new TextView(this);
        mHeader2.setText("Payment Date");
        mHeader2.setTextColor(Color.BLACK);
        mHeader2.setGravity(Gravity.LEFT);
        mHeader2.setTypeface(typeface, Typeface.BOLD);
        //  mHeader2.setTextAppearance(android.R.style.TextAppearance_Widget_Button);
        mHeader2.setPadding(4, 4, 40, 4);
        mTableRow.addView(mHeader2);

        TextView mHeader3 = new TextView(this);
        mHeader3.setText("Amount");
        mHeader3.setTextColor(Color.BLACK);
        mHeader3.setGravity(Gravity.LEFT);
        mHeader3.setTypeface(typeface, Typeface.BOLD);
        //  mHeader2.setTextAppearance(android.R.style.TextAppearance_Widget_Button);
        mHeader3.setPadding(4, 4, 40, 4);
        mTableRow.addView(mHeader3);
        TextView mHeader22 = new TextView(this);
        mHeader22.setText("22k Gold(Gram)");
        mHeader22.setTextColor(Color.BLACK);
        mHeader22.setGravity(Gravity.LEFT);
        mHeader22.setTypeface(typeface, Typeface.BOLD);
        //  mHeader2.setTextAppearance(android.R.style.TextAppearance_Widget_Button);
        mHeader22.setPadding(4, 4, 40, 4);
        mTableRow.addView(mHeader22);

        TextView mHeader23 = new TextView(this);
        mHeader23.setText("24k Gold(Gram)");
        mHeader23.setTextColor(Color.BLACK);
        mHeader23.setGravity(Gravity.LEFT);
        mHeader23.setTypeface(typeface, Typeface.BOLD);
        //  mHeader2.setTextAppearance(android.R.style.TextAppearance_Widget_Button);
        mHeader23.setPadding(4, 4, 40, 4);
        mTableRow.addView(mHeader23);

        TextView mHeader4 = new TextView(this);
        mHeader4.setText("Payment Status");
        mHeader4.setTextColor(Color.BLACK);
        mHeader4.setGravity(Gravity.LEFT);
        mHeader4.setTypeface(typeface, Typeface.BOLD);
        //  mHeader2.setTextAppearance(android.R.style.TextAppearance_Widget_Button);
        mHeader4.setPadding(4, 4, 40, 4);
        mTableRow.addView(mHeader4);

        TextView mHeader5 = new TextView(this);
        mHeader5.setText("Ref.No");
        mHeader5.setTextColor(Color.BLACK);
        mHeader5.setGravity(Gravity.LEFT);
        mHeader5.setTypeface(typeface, Typeface.BOLD);
        //  mHeader2.setTextAppearance(android.R.style.TextAppearance_Widget_Button);
        mHeader5.setPadding(4, 4, 40, 4);
        mTableRow.addView(mHeader5);


        mTablelayout.addView(mTableRow, new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.download, menu);

        mPdf=menu.findItem(R.id.menu_item);

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
    }


    public static PdfPCell designCell4(String content, int colspan, int rowspan) {
        Font font = new Font();
        font.setSize(20f);
        font.setStyle(Font.BOLD);
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setColspan(colspan);
        cell.setRowspan(rowspan);

        //  cell.setBackgroundColor(new BaseColor(255, 150, 0));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }
    public PdfPCell getCellnewamount(String content,int colspan, int rowspan) {
        Font font = new Font();
        font.setSize(10f);
        font.setStyle(Font.BOLD);
        font.setColor(BaseColor.RED);
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setColspan(colspan);
        cell.setRowspan(rowspan);
        cell.setPadding(6);
        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }
    public PdfPCell getCellnewColor(String content) {
        Font f = new Font();
        f.setSize(13f);
        f.setStyle(Font.BOLD);
        f.setColor(BaseColor.WHITE);
        PdfPCell cell = new PdfPCell(new Phrase(content,f));
        cell.setPadding(2);

        cell.setBackgroundColor(new BaseColor(0, 50, 205));
        cell.setBorder(Rectangle.BOX);
        return cell;
    }
    public void createPdf(String dest) {

        if (new File(dest).exists()) {
            new File(dest).delete();
        }
        try {

            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
            FooterUtils event = new FooterUtils();
            writer.setPageEvent(event);
            document.open();
            Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 25, Font.BOLD, BaseColor.BLACK);
            Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLUE);
            Font font3 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
            document.setPageSize(PageSize.A4);
            Drawable d = getResources().getDrawable(R.mipmap.ic_techface);
            BitmapDrawable bitDw = ((BitmapDrawable) d);
            Bitmap bmp = bitDw.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 1, stream);

            Image image = Image.getInstance(stream.toByteArray());
            image.setAlignment(Element.ALIGN_CENTER);
            // image.setAbsolutePosition(445f,804f);
            image.scaleToFit(152,60);
            document.add(image);


            Paragraph para = new Paragraph(" GOLD SCHEME BILL ", font1);
            para.setAlignment(Element.ALIGN_CENTER);
            para.setLeading(0, 1);
            //   document.add(Chunk.TABBING);
            document.add(para);
            document.add(Chunk.SPACETABBING);

            if (date.equals("")) {
                Paragraph operatorname1 = new Paragraph("DATE : Today", font3);
                operatorname1.setAlignment(Element.ALIGN_CENTER);
                document.add(Chunk.TABBING);
                document.add(operatorname1);
                document.add(Chunk.SPACETABBING);
            } else {
                Paragraph operatorname1 = new Paragraph("DATE : " + date, font3);
                document.add(Chunk.TABBING);
                operatorname1.setAlignment(Element.ALIGN_CENTER);
                document.add(operatorname1);
                document.add(Chunk.SPACETABBING);
            }

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String operatorname = sharedPreferences.getString(Constants.USERNAME, "");
            String retailername=sharedPreferences.getString(Constants.BRANCHNAME,"");

            PdfPTable pdfPTableheader = new PdfPTable(4);
            pdfPTableheader.setWidthPercentage(100);
            pdfPTableheader.setWidths(new int[]{30, 34, 30, 28});

            PdfPCell pdfCellop = new PdfPCell(new Phrase("NAME: ", font2));

            PdfPCell pdfCellsub = new PdfPCell(new Phrase(operatorname.toUpperCase(), font3));
            PdfPCell pdfCell3 = new PdfPCell(new Phrase("BRANCH  :", font2));
            PdfPCell pdfCell4= new PdfPCell(new Phrase(retailername.toUpperCase(), font3));
            pdfCellop.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfCellsub.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfCellop.setBorder(Rectangle.NO_BORDER);
            pdfCellsub.setBorder(Rectangle.NO_BORDER);
            pdfCell3.setBorder(Rectangle.NO_BORDER);
            pdfCell4.setBorder(Rectangle.NO_BORDER);
            pdfPTableheader.addCell(pdfCellop);
            pdfPTableheader.addCell(pdfCellsub);
            pdfPTableheader.addCell(pdfCell3);
            pdfPTableheader.addCell(pdfCell4);

            document.add(pdfPTableheader);
            document.add(Chunk.NEWLINE);
            PdfPTable pdfPTable = new PdfPTable(5);
            pdfPTable.setWidths(new int[]{10,20, 20, 20, 20});
            PdfPCell pdfC1=  new PdfPCell(designCell4(SelectedAreaName,5,1));
            PdfPCell pdfC2= new PdfPCell(getCellnewamount("TOTAL count : "+valueOf(mResponseList.size()),5,1));
            PdfPCell pdfC3=new PdfPCell(getCellnewColor("S.NO"));
            PdfPCell pdfCell1 = new PdfPCell(getCellnewColor(" Payment Date"));
            PdfPCell pdfCell2 = new PdfPCell(getCellnewColor("Amount"));
            PdfPCell pdfCell33 = new PdfPCell(getCellnewColor("22k gold(gram)"));
            PdfPCell pdfCell44 = new PdfPCell(getCellnewColor("24k gold(gram)"));
            //  PdfPCell pdfCell5 = new PdfPCell(getCellnewColor("PaymentStatus"));
            //  PdfPCell pdfCell6 = new PdfPCell(getCellnewColor("PaymentTYpe"));
            pdfCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            //   pdfCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
            //   pdfCell6.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfC1);
            pdfPTable.addCell(pdfC2);
            pdfPTable.addCell(pdfC3);

            pdfPTable.addCell(pdfCell1);
            pdfPTable.addCell(pdfCell2);
            pdfPTable.addCell(pdfCell33);
            pdfPTable.addCell(pdfCell44);
            //   pdfPTable.addCell(pdfCell5);
            // pdfPTable.addCell(pdfCell6);
        /*    PdfPCell[] cells = pdfPTable.getRow(0).getCells();
            for (int j = 0; j < cells.length; j++) {
                cells[j].setBackgroundColor(BaseColor.YELLOW);
            }*/

            for(int i=0;i<mResponseList.size();i++){



                Log.i("mResponseList",mResponseList.get(i).getmSno());
                pdfPTable.addCell(valueOf(i+1));
                pdfPTable.addCell(mResponseList.get(i).getEntrydate());
                pdfPTable.addCell(mResponseList.get(i).getPayedamount());
                pdfPTable.addCell(mResponseList.get(i).getMgold_22k());
                pdfPTable.addCell(mResponseList.get(i).getMgold_24k());
                //   pdfPTable.addCell(mResponseList.get(i).getmPaymentstatus());
                //    pdfPTable.addCell(mResponseList.get(i).getmPaymentType());

            }
            pdfPTable.setWidthPercentage(100);
            document.add(pdfPTable);
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));
            document.add(new Paragraph(""));
            document.add(new Chunk(lineSeparator));
            document.add(new Paragraph(""));
            document.close();
            Toast.makeText(this, "Downloaded... :)", Toast.LENGTH_SHORT).show();
            FileUtils.openFile(this, new File(dest));

        } catch (IOException | DocumentException ie) {

            Log.i("createPdf: Error ", ie.getLocalizedMessage());
        } catch (ActivityNotFoundException ae) {
            Toast.makeText(CustomizeAppMainActivity.this, "No application found to open this file.", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 22) {
            if (ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted1");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        } else {
            Log.v(TAG, "Permission is granted1");
            return true;
        }
    }


    public boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 22) {
            if (ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted2");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted2");
            return true;
        }
    }

}