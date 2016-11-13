package tech.paypal.app.ngo.vexcel.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.multidex.MultiDexApplication;

import com.pixplicity.easyprefs.library.Prefs;

/**
 * Created by Ravikumar on 11/3/2016.
 */

public class CommunityApplication extends MultiDexApplication {
    private static final String PREFS_NAME = "com.geofence.droidapps";
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the Prefs class
        sContext = this;

        new Prefs.Builder().setContext(this).setMode(ContextWrapper.MODE_PRIVATE).setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true).build();
    }

    public static Context getAppContext() {
        return sContext;
    }
}
