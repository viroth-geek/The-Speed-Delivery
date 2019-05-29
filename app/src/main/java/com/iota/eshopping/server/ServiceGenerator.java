package com.iota.eshopping.server;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.iota.eshopping.constant.ApplicationConfiguration;
import com.iota.eshopping.util.LoggerHelper;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by channarith.bong on 12/21/17.
 *
 * @author channarith.bong
 */

public class ServiceGenerator {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    /**
     *
     */
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(ApplicationConfiguration.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create());

    /**
     * Simple request without Token
     *
     * @param serviceClass Class
     * @param <S> type
     * @return type of class
     */
    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    /**
     * For request with access Token
     *
     * @param serviceClass Class
     * @param token String
     * @param <S> S
     * @return S S
     */
    public static <S> S createService(Class<S> serviceClass, final String token) {
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.writeTimeout(30, TimeUnit.SECONDS);

        X509TrustManager trustManager = loadTrueManager();
        SSLSocketFactory sslSocketFactory = generateSSLAdapter();
        if (trustManager != null && sslSocketFactory != null) {
            httpClient.sslSocketFactory(sslSocketFactory, trustManager);
            httpClient.hostnameVerifier((s, sslSession) -> true);
        }

//        enableAllTrustCertificate();

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder;
            if (token != null) {
                requestBuilder = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("Authorization", "Bearer " + token)
                        .method(original.method(), original.body());
            } else {
                requestBuilder = original.newBuilder()
                        .header("Accept", "application/json")
                        .method(original.method(), original.body());
            }
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        OkHttpClient client = httpClient.build();
        if (ApplicationConfiguration.DEVELOPER_MODE) {
            logDisplay();
        }

        Retrofit retrofit = ServiceGenerator.builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    /**
     * Show log Tag 'OkHttp'
     */
    private static void logDisplay() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // add logging as last interceptor
        httpClient.addInterceptor(logging);
    }

    /**
     * @return SSLSocketFactory
     */
    private static SSLSocketFactory generateSSLAdapter() {
        SSLSocketFactory socketFactory;
        try {
            // loading CAs from an InputStream
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream cert = new BufferedInputStream(ServiceGenerator.class.getResourceAsStream("/res/raw/" + ApplicationConfiguration.CERT_NAME));
            //InputStream cert = context.getResources().openRawResource(R.raw.apache);
            Certificate ca = null;
            try {
                ca = cf.generateCertificate(cert);
                LoggerHelper.showDebugLog("Certificate = " + ((X509Certificate) ca).getSubjectDN());
            }
            catch (Exception e) {
                LoggerHelper.showErrorLog("Error: ", e);
            } finally {
                cert.close();
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
            sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());
            socketFactory = sslContext.getSocketFactory();
            return socketFactory;
        } catch (Exception e) {
            LoggerHelper.showErrorLog("SSL/TLS" + e.getMessage(), e);
            return null;
        }
    }

    /**
     * @return X509TrustManager
     */
    private static X509TrustManager loadTrueManager() {
        X509TrustManager trustManager = null;
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                LoggerHelper.showErrorLog("Unexpected default trust managers:" + Arrays.toString(trustManagers));
                return null;
            }
            trustManager = (X509TrustManager) trustManagers[0];
            return trustManager;
        } catch (NoSuchAlgorithmException | KeyStoreException e) {
            LoggerHelper.showErrorLog("X509" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    static class AnyTrust extends X509ExtendedTrustManager {
        static final X509Certificate[] ANY_CA = {};
        @Override public X509Certificate[] getAcceptedIssuers() { return ANY_CA; }
        @Override public void checkServerTrusted(final X509Certificate[] c, final String t)  {}
        @Override public void checkClientTrusted(final X509Certificate[] c, final String t)  { }
        @Override public void checkServerTrusted(final X509Certificate[] c, final String t, final SSLEngine e)  { }
        @Override public void checkServerTrusted(final X509Certificate[] c, final String t, final Socket e)  { }
        @Override public void checkClientTrusted(final X509Certificate[] c, final String t, final SSLEngine e)  { }
        @Override public void checkClientTrusted(final X509Certificate[] c, final String t, final Socket    e)  { }
    }

    /**
     * enable trust all certificate
     */
    private static void enableAllTrustCertificate() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new AnyTrust()}, null);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        httpClient.sslSocketFactory(sslContext.getSocketFactory(), new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        });
        httpClient.hostnameVerifier((s, sslSession) -> true);
    }

}