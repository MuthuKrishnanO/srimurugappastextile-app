package com.techface.srimurugapa.scheme.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import com.google.android.material.appbar.AppBarLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.techface.srimurugapa.scheme.Helper.CommonUtils;
import com.techface.srimurugapa.scheme.Helper.UnicodeFormatter;
import com.techface.srimurugapa.scheme.R;
import com.techface.srimurugapa.scheme.model.ProductList;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Printer extends AppCompatActivity implements Runnable {

    protected static final String TAG = Printer.class.getSimpleName();
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    Button mScan, mPrint, mDisc;
    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;
    private List<ProductList.product>mList;
    private List<String>mIterateList;
    int quantity=0;
    int mTotal=0;
    private String  Cash;
    private  String Total;
    private  String changedue;
    private TableRow mTableRow;
    @BindView(R.id.toolbarpayment)
    Toolbar mToolbar;
    private ProgressDialog mProgressdialog;


    @BindView(R.id.table_layout_resume)
    TableLayout mTablelayout;

    @BindView(R.id.toolbarPayment)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.mSumofTotal)
    TextView mSumOfAmount;

    @BindView(R.id.mPayAmount)
    TextView mPayAmount;

    @BindView(R.id.mChangedue)
    TextView mChangeDue;


    @Override
    public void onCreate(Bundle mSavedInstanceState) {
        super.onCreate(mSavedInstanceState);
        setContentView(R.layout.activity_printer);

        ButterKnife.bind(this);
        mToolbar.setBackgroundColor(Color.parseColor("#ffffff"));
        mToolbar.setTitleTextColor((Color.parseColor("#000000")));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Make toolbar show navigation button (i.e back button with arrow icon)
        mToolbar.setNavigationIcon(R.drawable.ic_arrow);
        mScan = (Button) findViewById(R.id.Scan);
        mProgressdialog = CommonUtils.getProgressDialog(Printer.this);


        Cash=getIntent().getStringExtra("cash");
        Total=getIntent().getStringExtra("total");
        changedue=getIntent().getStringExtra("changedue");
        mList = (List<ProductList.product>) getIntent().getSerializableExtra("printerlist");
        mSumOfAmount.setText(" ₹ "+Total);
        mPayAmount.setText(" ₹ "+Cash);
        mChangeDue.setText(" ₹ "+changedue);
        Log.i(TAG,Cash+"--"+Total+"=="+changedue);
        for(int i=0;i<mList.size();i++ )
        {
          //mIterateList.add(mList.get(i).getmProductId()+"-"+mList.get(i).getmItemName()+"-"+mList.get(i).getmQuantity()+"-"+mList.get(i).getmItemRate()+"-"+mList.get(i).)
            Log.i(TAG,mList.get(i).getmProductId());
        }

        Log.e("order",  new Gson().toJson( mList ) );
        addDateAdjustmentHeaders();
        addContent();
        mScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View mView) {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    Toast.makeText(Printer.this, "Message1", Toast.LENGTH_SHORT).show();
                } else {
                    if (!mBluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(
                                BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent,
                                REQUEST_ENABLE_BT);
                    } else {
                        ListPairedDevices();
                        Intent connectIntent = new Intent(Printer.this,
                                DeviceListActivity.class);
                        startActivityForResult(connectIntent,
                                REQUEST_CONNECT_DEVICE);
                    }
                }
            }
        });

        mPrint = (Button) findViewById(R.id.mPrint);
        mPrint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View mView) {
                Thread t = new Thread() {
                    public void run() {
                        try {


                            OutputStream os = mBluetoothSocket.getOutputStream();

                            String BILL = "";

                            BILL = "                 XXXX MART    \n"
                                    + "              XX.AA.BB.CC.     \n " +
                                    "                NO 25 ABC ABCDE    \n" +
                                    "                XXXXX YYYYYY      \n" +
                                    "                MMM 590019091      \n";
                            BILL = BILL
                                    + "--------------------------------\n";


                            BILL = BILL + String.format("%1$-10s %2$5s %3$5s %4$8s", "Item", "Qty", "Rate", "Totel");
                            BILL = BILL + "\n";
                            BILL = BILL
                                    + "--------------------------------";

                            for(int i=0;i<mList.size();i++) {
                                BILL = BILL + "\n " + String.format("%1$-10s %2$5s %3$5s %4$8s", mList.get(i).getmItemName(), mList.get(i).getmQuantity(), mList.get(i).getmItemRate(), mList.get(i).getmTotal());
                                quantity+=Integer.parseInt(mList.get(i).getmQuantity());
                                mTotal+=mList.get(i).getmTotal();



                            }

                            Log.i(TAG,String.valueOf(mTotal+" "+quantity));

                            //  %1$-10s %2$10s %3$11s %4$10s

                            BILL = BILL
                                    + "\n--------------------------------";
                            BILL = BILL + "\n\n ";

                            BILL = BILL + "Total Qty:" + "       " + quantity + "\n";
                            BILL = BILL + "Total Value:" + "     " + mTotal + "\n";

                            BILL = BILL
                                    + "--------------------------------\n";
                            BILL = BILL + "\n\n ";
                            os.write(BILL.getBytes());
                            //This is printer specific code you can comment ==== > Start

                            // Setting height
                            int gs = 29;
                            os.write(intToByteArray(gs));
                            int h = 104;
                            os.write(intToByteArray(h));
                            int n = 162;
                            os.write(intToByteArray(n));

                            // Setting Width
                            int gs_width = 29;
                            os.write(intToByteArray(gs_width));
                            int w = 119;
                            os.write(intToByteArray(w));
                            int n_width = 2;
                            os.write(intToByteArray(n_width));


                        } catch (Exception e) {
                            Log.e("MainActivity", "Exe ", e);
                            //Toast.makeText(getApplicationContext(),"PRINTER IS NOT CONNECTED ",Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                t.start();
            }
        });

        mDisc = (Button) findViewById(R.id.dis);
        mDisc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View mView) {
                if (mBluetoothAdapter != null)
                    mBluetoothAdapter.disable();
            }
        });

    }// onCreate

 /*   @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        try {
            if (mBluetoothSocket != null)
                mBluetoothSocket.close();
        } catch (Exception e) {
            Log.e("Tag", "Exe ", e);
        }
    }
*/
 /*   @Override
    public void onBackPressed() {
    super.onBackPressed();
        try {
            mBluetoothSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mBluetoothConnectProgressDialog.dismiss();
    }*/




    public void onActivityResult(int mRequestCode, int mResultCode,
                                 Intent mDataIntent) {
        super.onActivityResult(mRequestCode, mResultCode, mDataIntent);

        switch (mRequestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (mResultCode == Activity.RESULT_OK) {
                    Bundle mExtra = mDataIntent.getExtras();
                    String mDeviceAddress = mExtra.getString("DeviceAddress");
                    Log.v(TAG, "Coming incoming address " + mDeviceAddress);
                    mBluetoothDevice = mBluetoothAdapter
                            .getRemoteDevice(mDeviceAddress);
                    mBluetoothConnectProgressDialog = ProgressDialog.show(this,
                            "Connecting...", mBluetoothDevice.getName() + " : "
                                    + mBluetoothDevice.getAddress(), true, false);

                    Thread mBlutoothConnectThread = new Thread(this);
                    mBlutoothConnectThread.start();
                    // pairToDevice(mBluetoothDevice); This method is replaced by
                    // progress dialog with thread
                }
                break;

            case REQUEST_ENABLE_BT:
                if (mResultCode == Activity.RESULT_OK) {
                    ListPairedDevices();
                    Intent connectIntent = new Intent(Printer.this,
                            DeviceListActivity.class);
                    startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                } else {
                    Toast.makeText(Printer.this, "Message", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void ListPairedDevices() {
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter
                .getBondedDevices();
        if (mPairedDevices.size() > 0) {
            for (BluetoothDevice mDevice : mPairedDevices) {
                Log.v(TAG, "PairedDevices: " + mDevice.getName() + "  "
                        + mDevice.getAddress());
            }
        }
    }

    public void run() {
        try {
            mBluetoothSocket = mBluetoothDevice
                    .createRfcommSocketToServiceRecord(applicationUUID);
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothSocket.connect();
            mHandler.sendEmptyMessage(0);
        } catch (IOException eConnectException) {
            Log.d(TAG, "CouldNotConnectToSocket", eConnectException);
            closeSocket(mBluetoothSocket);
            return;
        }
    }

    private void closeSocket(BluetoothSocket nOpenSocket) {
        try {
            nOpenSocket.close();
            Log.d(TAG, "SocketClosed");
        } catch (IOException ex) {
            Log.d(TAG, "CouldNotCloseSocket");
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            mBluetoothConnectProgressDialog.dismiss();
            Toast.makeText(Printer.this, "DeviceConnected", Toast.LENGTH_SHORT).show();
        }
    };

    public static byte intToByteArray(int value) {
        byte[] b = ByteBuffer.allocate(4).putInt(value).array();

        for (int k = 0; k < b.length; k++) {
            System.out.println("Selva  [" + k + "] = " + "0x"
                    + UnicodeFormatter.byteToHex(b[k]));
        }

        return b[3];
    }

    public byte[] sel(int val) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putInt(val);
        buffer.flip();
        return buffer.array();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }






    //tablelayout



    private void addDateAdjustmentHeaders() {
        mTableRow = new TableRow(Printer.this);
        mTableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT
                , TableRow.LayoutParams.WRAP_CONTENT));
        mTableRow.setBackgroundResource(R.drawable.table_header_bg);

        TextView mHeader1 = new TextView(Printer.this);
        mHeader1.setText("ProductName");
        mHeader1.setTextColor(Color.BLACK);
        mHeader1.setGravity(Gravity.LEFT);
        mHeader1.setTypeface(Typeface.SERIF, Typeface.BOLD);
        //   mHeader1.setTextAppearance(android.R.style.TextAppearance_Widget_Button);
        mHeader1.setPadding(4, 4, 40, 4);
        mTableRow.addView(mHeader1);




        TextView mHeader2 = new TextView(Printer.this);
        mHeader2.setText("Quantity");
        mHeader2.setTextColor(Color.BLACK);
        mHeader2.setGravity(Gravity.LEFT);
        mHeader2.setTypeface(Typeface.SERIF, Typeface.BOLD);
        //  mHeader2.setTextAppearance(android.R.style.TextAppearance_Widget_Button);
        mHeader2.setPadding(4, 4, 40, 4);
        mTableRow.addView(mHeader2);



        TextView mHeader4 = new TextView(Printer.this);
        mHeader4.setText(" Rate");
        mHeader4.setTextColor(Color.BLACK);
        mHeader4.setGravity(Gravity.LEFT);
        mHeader4.setTypeface(Typeface.SERIF, Typeface.BOLD);
        //  mHeader2.setTextAppearance(android.R.style.TextAppearance_Widget_Button);
        mHeader4.setPadding(4, 4, 40, 4);
        mTableRow.addView(mHeader4);

        TextView mHeader5 = new TextView(Printer.this);

        mHeader5.setText("TOTAL");
        mHeader5.setVisibility(View.VISIBLE);
        mHeader5.setTextColor(Color.BLACK);
        mHeader5.setGravity(Gravity.LEFT);
        mHeader5.setTypeface(Typeface.SERIF, Typeface.BOLD);
        //  mHeader2.setTextAppearance(android.R.style.TextAppearance_Widget_Button);
        mHeader5.setPadding(4, 4, 40, 4);
        mTableRow.addView(mHeader5);


        mTablelayout.addView(mTableRow, new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
    }

         void  addContent()
        {
            for (int i = 0; i < mList.size(); i++) {
                /** Create a TableRow dynamically **/
                mTableRow = new TableRow(Printer.this);
                Typeface typeface = ResourcesCompat.getFont(Printer.this, R.font.source_sans_pro);
                mTableRow.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                if (i % 2 == 1) {
                    mTableRow.setBackgroundResource(R.drawable.rectangle_border);
                }
                /** Creating a TextView to add to the row **/
                TextView ChannelTV = new TextView(Printer.this);
                ChannelTV.setText(mList.get(i).getmItemName());
                ChannelTV.setGravity(Gravity.LEFT);
                ChannelTV.setTypeface(typeface);
                ChannelTV.setTextColor(Color.BLACK);
                ChannelTV.setPadding(4, 4, 40, 4);
                mTableRow.addView(ChannelTV);  // Adding textView to tablerow.

                TextView amountTV = new TextView(Printer.this);
                // amountTV.setText();
                amountTV.setGravity(Gravity.LEFT);
                amountTV.setTextColor(Color.BLUE);
                amountTV.setText(mList.get(i).getmQuantity());
                amountTV.setTypeface(typeface);
                amountTV.setPadding(4, 4, 40, 4);
                mTableRow.addView(amountTV);  // Adding textView to tablerow.

                TextView amountvaliday = new TextView(Printer.this);
                // amountTV.setText();
                amountvaliday .setGravity(Gravity.LEFT);
                amountvaliday.setTextColor(Color.BLUE);
                amountvaliday.setText(mList.get(i).getmItemRate());
                amountvaliday.setTypeface(typeface);
                amountvaliday.setPadding(4, 4, 40, 4);
                mTableRow.addView(amountvaliday);  // Adding textView to tablerow.

                    TextView dateTV = new TextView(Printer.this);
                    // amountTV.setText();
                    dateTV.setGravity(Gravity.LEFT);
                    dateTV.setTextColor(Color.BLUE);
                    dateTV.setText(String.valueOf(mList.get(i).getmTotal()));
                    dateTV.setTypeface(typeface);
                    dateTV.setPadding(4, 4, 40, 4);
                    mTableRow.addView(dateTV);  // Adding textView to tablerow.

                mTablelayout.addView(mTableRow, new TableLayout.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
            }
            
            
        }
}
