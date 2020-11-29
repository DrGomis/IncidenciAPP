package com.example.issuemanager.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import com.example.issuemanager.db.IssueSQLiteHelper;
import com.example.issuemanager.db.model.Issue;
import com.example.issuemanager.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public IssueSQLiteHelper dbHelper;
    public SQLiteDatabase db;
    ArrayList<Issue> issueArrayList;
    public static boolean isEmpty = true;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Logged as
        sharedPref = this.getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);
        String username = sharedPref.getString("USER_NAME", "");

        final TextView issueName = findViewById(R.id.welcome_lbl);
        issueName.setText((Html.fromHtml(getResources().getString(R.string.logged_as) + " " + "<font color=#ff3339>" + username + "</font>")));

        dbHelper = new IssueSQLiteHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        issueArrayList = IssueSQLiteHelper.getAllIssues(db, 3);
        if (issueArrayList.size() > 0) {
            isEmpty = false;
        }
       // issueArrayList = (ArrayList<Issue>) getIntent().getSerializableExtra("IssueArray")
    }
}