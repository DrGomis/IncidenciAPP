package com.example.issuemanager.app.usecase.menu.fragment;

import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.issuemanager.R;
import com.example.issuemanager.app.MainActivity;
import com.example.issuemanager.db.IssueSQLiteHelper;
import com.example.issuemanager.db.model.Issue;

import java.util.ArrayList;

public class TopMenuFragment extends Fragment {

    public IssueSQLiteHelper dbHelper;
    public SQLiteDatabase db;
    public boolean isEmpty;

    public TopMenuFragment() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View frMenu = inflater.inflate(R.layout.fragment_menu, container, false);

            Button btnAdd = frMenu.findViewById(R.id.btn_add);
            Button btnList = frMenu.findViewById(R.id.btn_list);
            Button btnDel = frMenu.findViewById(R.id.btn_del);
            Button btnSettings = frMenu.findViewById(R.id.btn_settings);

            dbHelper = new IssueSQLiteHelper(getContext());
            db = dbHelper.getReadableDatabase();

            // Add Issue
            btnAdd.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Fragment frAddIssue = new AddIssueFragment();
                    MenuManager(frAddIssue);
                }
            });

            // List Issues
            btnList.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    isEmpty = IssueSQLiteHelper.isDBEmpty(db);
                    if (!isEmpty) {
                        Fragment frListIssues = new ListIssueFragment();
                        MenuManager(frListIssues);
                    } else {
                        Toast toast = Toast.makeText(getContext(), getResources().getString(R.string.menu_no_issues_added), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });

            // Remove Issue
            btnDel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    isEmpty = IssueSQLiteHelper.isDBEmpty(db);
                    if (!isEmpty) {
                        Fragment frRemoveIssue = new DeleteIssueFragment();
                        MenuManager(frRemoveIssue);
                    } else {
                        Toast toast = Toast.makeText(getContext(), getResources().getString(R.string.menu_no_issues_added), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });

            btnSettings.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Fragment frSettings = new SettingsFragment();
                    MenuManager(frSettings);
                }
            });
            return frMenu;
        }

        public void MenuManager(Fragment param) {
            FragmentManager menuManager = getFragmentManager();
            FragmentTransaction menuTransaction = menuManager.beginTransaction();

            menuTransaction.replace(R.id.fragmentView, param);
            menuTransaction.commit();
        }
}
