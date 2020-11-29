package com.example.issuemanager.app.usecase.menu.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.issuemanager.R;
import com.example.issuemanager.app.usecase.login.LoginActivity;
import com.example.issuemanager.app.util.AlertConfig;
import com.example.issuemanager.db.IssueSQLiteHelper;
import com.example.issuemanager.db.model.Issue;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class IssueManagerFragment extends Fragment {
    private IssueSQLiteHelper dbHelper;
    private SQLiteDatabase db;

    String issueTitle, issuePrio, issueID, issueDesc, issueDate;
    int issueStatus, idValue;

    public IssueManagerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mgr = inflater.inflate(R.layout.fragment_issue_manager, container, false);


        // Disables back button to avoid desynch when 'btn_status' is pressed
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Log.i("msg", "Back button is disabled in this fragment!");
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


        dbHelper = new IssueSQLiteHelper(getContext());
        db = dbHelper.getWritableDatabase();

        issueTitle = getArguments().getString("ITEM_TITLE");
        issuePrio = getArguments().getString("ITEM_PRIO");
        issueID = getArguments().getString("ITEM_ID");
        issueStatus = getArguments().getInt("ITEM_STATUS");
        issueDesc = getArguments().getString("ITEM_DESC");
        issueDate = getArguments().getString("ITEM_DATE");
        idValue = Integer.parseInt(issueID.substring(4));

        refreshBtn(mgr, issueStatus);
        setInformation(mgr, issueID, issueTitle, issuePrio, issueStatus, issueDesc, issueDate);


        Button btn_status = mgr.findViewById(R.id.popupIssueStatus_btn);
        btn_status.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (issueStatus == 0) {
                    issueStatus = 1;
                    IssueSQLiteHelper.updateStatus(db, issueStatus, idValue);
                    refreshBtn(mgr, issueStatus);

                } else if (issueStatus == 1) {
                    issueStatus = 2;
                    IssueSQLiteHelper.updateStatus(db, issueStatus, idValue);
                    refreshBtn(mgr, issueStatus);
                }  else if (issueStatus == 2) {
                    issueStatus = 0;
                    IssueSQLiteHelper.updateStatus(db, issueStatus, idValue);
                    refreshBtn(mgr, issueStatus);
                }

               /* Fragment frList = new ListIssueFragment();
                FragmentManager menuManager = getFragmentManager();
                FragmentTransaction menuTransaction = menuManager.beginTransaction();
                menuTransaction.replace(R.id.mgr_fragment, frList).commit();*/
            }
        }));


        // Delete button
        Button btn_remove = mgr.findViewById(R.id.popupDelete_btn);
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeIssue(db, idValue);
            }
        });
        return mgr;
    }




    public void setInformation(View v, String id_, String title_, String prio_, int status_, String desc_, String date_) {
        TextView title = v.findViewById(R.id.popupIssueName);
        title.setText(Html.fromHtml("<b>" + getResources().getString(R.string.list_title) + ": </b>" + " " +  title_));

        TextView prio = v.findViewById(R.id.popupIssuePrio);
        if (prio_.equals("1")) {
            prio_ = "<font color=#3393ff>" + getResources().getString(R.string.prio_low) + "</font>";
        } else if (prio_.equals("2")) {
            prio_ = "<font color=#ffb533>" + getResources().getString(R.string.prio_medium) + "</font>";
        } else if (prio_.equals("3")) {
            prio_ = "<font color=#ff3339>" + getResources().getString(R.string.prio_high) + "</font>";
        }
        prio.setText((Html.fromHtml("<b>" + getResources().getString(R.string.list_priority) + ": </b>"+ " " +  prio_)));

        TextView iID = v.findViewById(R.id.popupIssueID);
        iID.setText(id_);

        TextView date = v.findViewById(R.id.popupIssueDate);
        date.setText((Html.fromHtml("<b>" + getResources().getString(R.string.extra_date) + ": </b>" + " " +  date_)));

        TextView desc = v.findViewById(R.id.popupIssueDesc);
        desc.setText(desc_);
    }


    public void refresh(){
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void refreshBtn(View v, int statusID){
        Button btn_status = v.findViewById(R.id.popupIssueStatus_btn);
        if (issueStatus == 0) {
            btn_status.setText(getResources().getString(R.string.status_pending));
            btn_status.setBackgroundColor(Color.RED);
        } else if (issueStatus == 1) {
            btn_status.setText(getResources().getString(R.string.status_assigned));
            btn_status.setBackgroundColor(Color.YELLOW);
        }  else if (issueStatus == 2) {
            btn_status.setText(getResources().getString(R.string.status_solved));
            btn_status.setBackgroundColor(Color.GREEN);
        }

    }

    public void removeIssue(SQLiteDatabase db_, int id_) {

        // Confirmation Alert
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setCustomTitle(AlertConfig.genTitle(getContext(), getResources().getString(R.string.remove_issue_alert), "#FF0000"));
        builder.setMessage(getResources().getString(R.string.remove_issue_alert_msg));
        builder.setPositiveButton(getResources().getString(R.string.remove_issue_alert_confirm),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        IssueSQLiteHelper.delDBIssue(db_, id_);
                        Toast toast = Toast.makeText(getContext(), getResources().getString(R.string.remove_issue_toast_success), Toast.LENGTH_SHORT);
                        toast.show();

                        refresh();
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
}