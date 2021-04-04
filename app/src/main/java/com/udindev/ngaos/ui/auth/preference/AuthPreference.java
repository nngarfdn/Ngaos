package com.udindev.ngaos.ui.auth.preference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.udindev.ngaos.data.model.Account;

public class AuthPreference {
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    private static final String PREFERENCE_NAME = "account_preference";
    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String DISPLAY_NAME = "name";

    @SuppressLint("CommitPrefEdits")
    public AuthPreference(Context context){
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setData(Account account){
        editor.putString(ID, account.getId());
        editor.putString(EMAIL, account.getEmail());
        editor.putString(DISPLAY_NAME, account.getDisplayName());
        editor.apply();
    }

    public void resetData(){
        editor.clear().apply();
    }

    public String getId(){
        return sharedPreferences.getString(ID, null);
    }

    public String getEmail(){
        return sharedPreferences.getString(EMAIL, null);
    }

    public String getDisplayName(){
        return sharedPreferences.getString(DISPLAY_NAME, null);
    }
}
