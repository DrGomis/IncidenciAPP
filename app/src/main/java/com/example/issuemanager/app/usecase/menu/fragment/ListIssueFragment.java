package com.example.issuemanager.app.usecase.menu.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.issuemanager.app.usecase.menu.RecyclerViewAdapter;

import com.example.issuemanager.app.MainActivity;
import com.example.issuemanager.R;
import com.example.issuemanager.db.IssueSQLiteHelper;
import com.example.issuemanager.db.model.Issue;

import java.util.List;


public class ListIssueFragment extends Fragment {

    private IssueSQLiteHelper dbHelper;
    private SQLiteDatabase db;

    public ListIssueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View listView = inflater.inflate(R.layout.fragment_list_issue, container, false);

        sendList(listView, 3);

        String [] statusValues = {getResources().getString(R.string.status_all), getResources().getString(R.string.status_pending), getResources().getString(R.string.status_assigned), getResources().getString(R.string.status_solved)};

        final Spinner statusDropdown = (Spinner) listView.findViewById(R.id.spr_issueStatus);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, statusValues);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        statusDropdown.setAdapter(adapter);


        Button btn_filter = listView.findViewById(R.id.filter_btn);
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = statusDropdown.getSelectedItem().toString();
                int filterID = 3;

                if (status.equals(getResources().getString(R.string.status_all))) {
                    filterID = 3;
                } else if (status.equals(getResources().getString(R.string.status_pending))) {
                    filterID = 0;
                } else if (status.equals(getResources().getString(R.string.status_assigned))) {
                    filterID = 1;
                } else if (status.equals(getResources().getString(R.string.status_solved))) {
                    filterID = 2;
                }

                sendList(listView, filterID);
            }
        });


        return listView;
    }

    public void sendList(View v, int filter) {
        dbHelper = new IssueSQLiteHelper(getContext());
        db = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager((v.getContext())));

        // Sends all DB Issues
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), IssueSQLiteHelper.getAllIssues(db, filter));
        recyclerView.setAdapter(adapter);
    }
}