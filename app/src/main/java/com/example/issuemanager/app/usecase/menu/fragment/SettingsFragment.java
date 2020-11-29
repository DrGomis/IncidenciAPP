package com.example.issuemanager.app.usecase.menu.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.issuemanager.R;
import com.example.issuemanager.app.MainActivity;
import com.example.issuemanager.app.usecase.login.LoginActivity;

import java.util.Locale;

public class SettingsFragment extends Fragment {

    protected static SharedPreferences sharedPref;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View settingsView = inflater.inflate(R.layout.fragment_settings, container, false);

        Button btn_ES = settingsView.findViewById(R.id.btn_ES);
        Button btn_EN = settingsView.findViewById(R.id.btn_EN);
        Button btn_JP = settingsView.findViewById(R.id.btn_JP);
        Button btn_reset = settingsView.findViewById(R.id.btn_restore);

        sharedPref = getContext().getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);

        // Languages
        btn_ES.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Switch to Spanish
                setLanguage("es");
            }
        });

        btn_EN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Switch to English
                setLanguage("en");
            }
        });

        btn_JP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Switch to Japanese
                setLanguage("jp");
            }
        });

        // Restore defaults
        btn_reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sharedPref.edit().remove("LANGUAGE").commit();
                sharedPref.edit().remove("STORED_USER").commit();
                sharedPref.edit().remove("USER_NAME").commit();
                sharedPref.edit().remove("USER_PASS").commit();
                refresh();
            }
        });

        return settingsView;
    }

    public void setLanguage(String locale) {
        Configuration config = new Configuration(getResources().getConfiguration());
        config.locale = new Locale(locale);
        sharedPref.edit().putString("LANGUAGE", locale).commit();
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        refresh();
    }

    public void refresh(){
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);

        Fragment frSettings = new SettingsFragment();

        FragmentManager menuManager = getFragmentManager();
        FragmentTransaction menuTransaction = menuManager.beginTransaction();
        menuTransaction.replace(R.id.fragmentView, frSettings).commit();
    }

}
