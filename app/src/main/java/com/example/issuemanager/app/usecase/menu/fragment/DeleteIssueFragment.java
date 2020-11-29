package com.example.issuemanager.app.usecase.menu.fragment;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.issuemanager.R;
import com.example.issuemanager.app.util.AlertConfig;
import com.example.issuemanager.db.IssueSQLiteHelper;
import com.example.issuemanager.db.model.Issue;

import java.util.ArrayList;

public class DeleteIssueFragment extends Fragment {
    private IssueSQLiteHelper dbHelper;
    private SQLiteDatabase db;
    private ArrayList<Issue> issueArrayList;

    public DeleteIssueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View delView = inflater.inflate(R.layout.fragment_remove_issue, container, false);

        dbHelper = new IssueSQLiteHelper(getContext());
        db = dbHelper.getWritableDatabase();

        final EditText issueID = delView.findViewById(R.id.field_issueID);
        final Button btnDelIssue = delView.findViewById(R.id.btn_RemoveIssue);

        btnDelIssue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String id = issueID.getText().toString();

                issueArrayList = IssueSQLiteHelper.getAllIssues(db, 3);

                // Confirmation Alert
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setCustomTitle(AlertConfig.genTitle(getContext(), getResources().getString(R.string.remove_issue_alert), "#FF0000"));
                builder.setMessage(getResources().getString(R.string.remove_issue_alert_msg) + " " + id);
                builder.setPositiveButton(getResources().getString(R.string.remove_issue_alert_confirm),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // If 'confirm', then
                                int id_number = 0;
                                boolean idExist = false;

                                // Checks if that ID does exist
                                for (int i = 0; i < issueArrayList.size(); i++) {
                                    if(id.equalsIgnoreCase(issueArrayList.get(i).getID())) {
                                        idExist = true;
                                        id_number = Integer.parseInt(issueArrayList.get(i).getID().substring(4)); // Removes JDA-
                                        break;
                                    }
                                }

                                if (idExist) {
                                    // Removes the Issue from database
                                    IssueSQLiteHelper.delDBIssue(db, id_number);
                                    Toast toast = Toast.makeText(getContext(), getResources().getString(R.string.remove_issue_toast_success), Toast.LENGTH_SHORT);
                                    toast.show();

                                    getActivity().getFragmentManager().popBackStack();
                                } else {
                                    // Does nothing
                                    Toast toast = Toast.makeText(getContext(), getResources().getString(R.string.remove_issue_toast_error), Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            }
                        });
                builder.setNegativeButton(getResources().getString(R.string.remove_issue_alert_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // If 'cancel', then does nothing
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return delView;
    }

}
