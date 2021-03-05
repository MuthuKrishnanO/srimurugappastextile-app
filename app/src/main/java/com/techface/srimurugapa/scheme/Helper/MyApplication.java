package com.techface.srimurugapa.scheme.Helper;

import android.content.Context;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;


/**
 * Created by ridsys-001 on 5/1/18.
 */

public class MyApplication extends MultiDexApplication {

    private static MyApplication mInstance;

//        static {
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

//        Validator.registerAnnotation(DynamicValidation.class);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
        MultiDex.install(this);
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectionListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.mListener = listener;
    }


}