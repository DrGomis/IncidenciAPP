package com.example.issuemanager.app;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new IssueSQLiteHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        issueArrayList = IssueSQLiteHelper.getAllIssues(db);
        if (issueArrayList.size() > 0) {
            isEmpty = false;
        }



       // issueArrayList = (ArrayList<Issue>) getIntent().getSerializableExtra("IssueArray")

    }
}