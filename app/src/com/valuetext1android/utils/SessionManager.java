package com.valuetext1android.utils;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by JANI SHAIK on 28/12/2019
 */

public class SessionManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "VALUETEXT";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession() {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
//        editor.putString("JSON", jsonLogin);

//        editor.putString(KEY_LOG_DEFAULT_ROLE, role);
        // Login Date
        //editor.putString(KEY_DATE, date);

        // commit changes
        editor.commit();
    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
//        editor.clear();
        editor.putBoolean(IS_LOGIN, false);

        editor.commit();
    }

    public void setVersion(String version) {
        editor.putString("version", version);
        editor.apply();
    }

    public String getVersion() {
        return pref.getString("version", "");
    }

    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

}

