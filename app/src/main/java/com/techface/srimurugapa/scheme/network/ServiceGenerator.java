package com.techface.srimurugapa.scheme.network;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.techface.srimurugapa.scheme.Helper.Constants;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

  private static Gson gson = new GsonBuilder().setLenient()
          .serializeNulls()
          .create();
  private static OkHttpClient.Builder httpClientBuilder = null;
  private static final String BASE_URL = Constants.SERVERURL;

  private static Retrofit.Builder  builder = new Retrofit.Builder().
          baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create(gson));


  private static Retrofit retrofit = builder.build();
  private  static OkHttpClient.Builder getUnsafeOkHttpClient() {

    try {
      // Create a trust manager that does not validate certificate chains
      final TrustManager[] trustAllCerts = new TrustManager[]{
              new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                  return new java.security.cert.X509Certificate[]{};
                }
              }
      };

      // Install the all-trusting trust manager
      final SSLContext sslContext = SSLContext.getInstance("SSL");
      sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

      // Create an ssl socket factory with our all-trusting manager
      final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

      OkHttpClient.Builder builder = new OkHttpClient.Builder();
      builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
      builder.hostnameVerifier(new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
          return true;
        }
      });
      return builder;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
          .setLevel(HttpLoggingInterceptor.Level.BODY);

  private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
          .connectTimeout(50, TimeUnit.SECONDS)
          .readTimeout(50,TimeUnit.SECONDS)
          .writeTimeout(50,TimeUnit.SECONDS);


  public static <S> S createService(Class<S> serviceClass){
    if (!httpClient.interceptors().contains(loggingInterceptor)){
      httpClient.addInterceptor(loggingInterceptor);
      builder.client(httpClient.build());
      retrofit = builder.build();
    }
    return retrofit.create(serviceClass);
  }




  /*//  private static final String API_VERSION = BuildConfig.VERSION;

    private static OkHttpClient.Builder httpClientBuilder = null;

    public static Retrofit getInstance(Context context) {
        if (retrofit == null) {

            httpClientBuilder = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS);
            initHttpLogging();
            initSSL(context);

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClientBuilder.build());


            retrofit = builder.build();

        }
        return retrofit;
    }*/


   /* private static void initHttpLogging() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (BuildConfig.DEBUG) httpClientBuilder.addInterceptor(logging);
    }

    private static void initSSL(Context context) {

        SSLContext sslContext = null;
        try {
            sslContext = createCertificate(context.getResources().openRawResource(R.raw.certificate));
        } catch (CertificateException | IOException | KeyStoreException | KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if(sslContext!=null){
            httpClientBuilder.sslSocketFactory(sslContext.getSocketFactory(), systemDefaultTrustManager());
        }

    }

    private static SSLContext createCertificate(InputStream trustedCertificateIS) throws CertificateException, IOException, KeyStoreException, KeyManagementException, NoSuchAlgorithmException{

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate ca;
        try {
            ca = cf.generateCertificate(trustedCertificateIS);
        } finally {
            trustedCertificateIS.close();
        }

        // creating a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        // creating a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // creating an SSLSocketFactory that uses our TrustManager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);
        return sslContext;

    }

    private static X509TrustManager systemDefaultTrustManager() {

        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            return (X509TrustManager) trustManagers[0];
        } catch (GeneralSecurityException e) {
            throw new AssertionError(); // The system has no TLS. Just give up.
        }

    }

*/




}
