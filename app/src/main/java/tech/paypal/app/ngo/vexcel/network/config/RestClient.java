package tech.paypal.app.ngo.vexcel.network.config;

import com.squareup.okhttp.OkHttpClient;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import tech.paypal.app.ngo.vexcel.model.login.Login;

/**
 * Created by chokkar
 */

public class RestClient {

    private static Restapi REST_CLIENT;
    private static Restapi REST_CLIENT_ICICI;
    private static Restapi REST_CLIENT_ATM;
    private static Restapi REST_CLIENT_PARTICIPANT;
    private static Restapi REST_CLIENT_WALLET;

    private static String ROOT =
//            "https://52.22.24.182/api/v1/";
//                "https://api.pinlabs.in/geoapp/v1/";
//            "http://nearndear.au-syd.mybluemix.net/v1/";
//            "http://commplus.herokuapp.com/v1/";
//            "http://52.23.201.114:8080";
            "https://v-excel-inventory.herokuapp.com";
    public static Restapi get() {
        return REST_CLIENT;
    }

    public static void setupRestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(ROOT)
                .setClient(new OkClient(getUnsafeOkHttpClient()))
                .setRequestInterceptor(new SessionRequestInterceptor())
                .build();

        REST_CLIENT = restAdapter.create(Restapi.class);
    }

    public static void setupLoginRestClient(Login user) {
        ApiRequestInterceptor requestInterceptor = new ApiRequestInterceptor();
        requestInterceptor.setUser(user);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(ROOT)
                .setClient(new OkClient(getUnsafeOkHttpClient()))
                .setRequestInterceptor(requestInterceptor)
                .build();

        REST_CLIENT = restAdapter.create(Restapi.class);
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setSslSocketFactory(sslSocketFactory);
            okHttpClient.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ICICI Login
    private static String ROOT_ICICI = "http://corporate_bank.mybluemix.net/corporate_banking/mybank/";
    public static Restapi getLoginIciciApi() {
        return REST_CLIENT_ICICI;
    }

    public static void setupLoginIciciRestClient() {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ROOT_ICICI).setLogLevel(RestAdapter.LogLevel.FULL).build();

        //Creating Rest Services
        REST_CLIENT_ICICI = adapter.create(Restapi.class);

    }


    // ICICI ATM
    private static String ROOT_ICICI_ATM = "http://retailbanking.mybluemix.net/banking/icicibank/";
    public static Restapi getAtmIciciApi() {
        return REST_CLIENT_ATM;
    }

    public static void setupAtmIciciRestClient() {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ROOT_ICICI_ATM).setLogLevel(RestAdapter.LogLevel.FULL).build();

        //Creating Rest Services
        REST_CLIENT_ATM = adapter.create(Restapi.class);

    }

    private static String ROOT_ICICI_PARTICIPANT = "https://participant_mapping.mybluemix.net/banking/icicibank/";
    public static Restapi getParticipantIciciApi() {
        return REST_CLIENT_PARTICIPANT;
    }

    public static void setupParticipantIciciRestClient() {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ROOT_ICICI_PARTICIPANT).setLogLevel(RestAdapter.LogLevel.FULL).build();

        //Creating Rest Services
        REST_CLIENT_PARTICIPANT = adapter.create(Restapi.class);

    }


    private static String ROOT_ICICI_WALLET = "https://alphaiciapi2.mybluemix.net/rest/Wallet/";
    public static Restapi getWalletIciciApi() {
        return REST_CLIENT_WALLET;
    }

    public static void setupWalletIciciRestClient() {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ROOT_ICICI_WALLET).setLogLevel(RestAdapter.LogLevel.FULL).build();

        //Creating Rest Services
        REST_CLIENT_WALLET = adapter.create(Restapi.class);
    }
}