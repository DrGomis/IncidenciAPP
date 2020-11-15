package com.example.issuemanager.app.usecase.menu.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.issuemanager.app.usecase.menu.RecyclerViewAdapter;

import com.example.issuemanager.app.MainActivity;
import com.example.issuemanager.R;
import com.example.issuemanager.db.IssueSQLiteHelper;
import com.example.issuemanager.db.model.Issue;


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

        dbHelper = new IssueSQLiteHelper(getContext());
        db = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = (RecyclerView)listView.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager((listView.getContext())));

        // Sends all DB Issues
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(IssueSQLiteHelper.getAllIssues(db));
        recyclerView.setAdapter(adapter);

        return listView;
    }
}