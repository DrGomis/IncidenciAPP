package com.example.issuemanager.app.usecase.menu.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.issuemanager.R;
import com.example.issuemanager.app.MainActivity;

import java.util.Locale;

public class SettingsFragment extends Fragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View settingsView = inflater.inflate(R.layout.fragment_settings, container, false);

        Button btn_ES = settingsView.findViewById(R.id.btn_ES);
        Button btn_EN = settingsView.findViewById(R.id.btn_EN);
        Button btn_JP = settingsView.findViewById(R.id.btn_JP);


        Configuration config = new Configuration(getResources().getConfiguration());

        btn_ES.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Switch to Spanish
                config.locale = new Locale("es");

                getResources().updateConfiguration(config, getResources().getDisplayMetrics());

            }
        });

        btn_EN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Switch to English
                config.locale = new Locale("en");
                getResources().updateConfiguration(config, getResources().getDisplayMetrics());


            }
        });

        btn_JP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Switch to Japanese
                config.locale = new Locale("jp");
                getResources().updateConfiguration(config, getResources().getDisplayMetrics());
            }
        });

        return settingsView;
    }
}
