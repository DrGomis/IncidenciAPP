package com.example.issuemanager.app.usecase.menu.fragment;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.issuemanager.db.IssueSQLiteHelper;
import com.example.issuemanager.db.model.Issue;
import com.example.issuemanager.R;

public class AddIssueFragment extends Fragment {
    private IssueSQLiteHelper dbHelper;
    private SQLiteDatabase db;

    public AddIssueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Gets dbHelper from MainActivity
        dbHelper = new IssueSQLiteHelper(getContext());
        db = dbHelper.getWritableDatabase();

        // Updates issueArrayList
        //issueArrayList = (ArrayList<Issue>) getActivity().getIntent().getSerializableExtra("IssueArray");

        // Spinner Options
        View v = inflater.inflate(R.layout.fragment_add_issue, container, false);

        String [] prioValues = {getResources().getString(R.string.prio_low), getResources().getString(R.string.prio_medium), getResources().getString(R.string.prio_high)};

        final Spinner prioDropdown = (Spinner) v.findViewById(R.id.spr_issuePrio);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, prioValues);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        prioDropdown.setAdapter(adapter);

        final EditText issueName = v.findViewById(R.id.field_issueName);

        final Button btnSaveIssue = v.findViewById(R.id.btn_Add);
        btnSaveIssue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String title = issueName.getText().toString();
                String prio = prioDropdown.getSelectedItem().toString();
                checkData(title, prio);

                if (title.length() > 0) {
                    Issue issue = new Issue(title, prio);
                    dbHelper.insertIssue(db, issue);
                }
            }
        });

        return v;
    }
/*
    @Override
    public void onDestroy() {
        dbHelper.close();
        db.close();
        super.onDestroy();
    }*/

    public void checkData(String title, String prio) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();

        if (title.length() > 0) { // Data is correct
            alertDialog.setTitle(getResources().getString(R.string.alert_add_success));
            alertDialog.setMessage(getResources().getString(R.string.alert_success_msg1) + title + "\n" + getResources().getString(R.string.alert_success_msg2) + prio);
        } else {
            alertDialog.setTitle(getResources().getString(R.string.alert_add_error));
            alertDialog.setMessage(getResources().getString(R.string.alert_error_msg));
        }
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.alert_close),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}