package com.example.issuemanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.issuemanager.db.IssueContract.*;
import com.example.issuemanager.db.model.Issue;

import java.util.ArrayList;

public class IssueSQLiteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "issues.db";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + IssueEntry.ISSUE_TABLE + "(" + IssueEntry.ISSUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + IssueEntry.ISSUE_NAME + " TEXT, " + IssueEntry.ISSUE_PRIORITY + " TEXT)";

    public IssueSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertIssue(SQLiteDatabase db, Issue issue) {
        if (db.isOpen()) {
            //Creation of tje content with all the issue values
            ContentValues values = new ContentValues();

            values.put(IssueEntry.ISSUE_NAME, issue.getTitle());
            values.put(IssueEntry.ISSUE_PRIORITY, issue.getPriority());
            //Adding data to the database
            db.insert(IssueEntry.ISSUE_TABLE, null, values);
        } else {
            Log.d("sql", "Database is closed");
        }

    }

    public static ArrayList<Issue> getAllIssues(SQLiteDatabase db){
        Cursor cursor = db.rawQuery("SELECT * FROM " + IssueEntry.ISSUE_TABLE, null);
        ArrayList<Issue> issueArrayList = new ArrayList<>();
        Issue issue;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                issue = new Issue(cursor.getString(1), cursor.getString(2));
                issue.setID(cursor.getInt(0));
                issueArrayList.add(issue);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return issueArrayList;
    }

    public static void delDBIssue(SQLiteDatabase db, int _id) {
        String deleteCall = "DELETE FROM " + IssueEntry.ISSUE_TABLE + " WHERE " + IssueEntry.ISSUE_ID + " = " + _id;

        db.execSQL(deleteCall);
    }

    public static boolean isDBEmpty(SQLiteDatabase db) {
        String countCall = "SELECT * FROM " + IssueEntry.ISSUE_TABLE;
        Cursor cursor = db.rawQuery(countCall, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }
    }
}

