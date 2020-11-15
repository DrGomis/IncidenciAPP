package com.example.issuemanager.app.usecase.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.issuemanager.R;
import com.example.issuemanager.app.usecase.menu.fragment.ListIssueFragment;
import com.example.issuemanager.db.model.Issue;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private ArrayList<Issue> issueArrayList;

    // If does not work, add Context context or ListIssueFragment
    // Gets all Issues from ListIssueFragment
    public RecyclerViewAdapter(ArrayList<Issue> arr){
        issueArrayList = arr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (issueArrayList != null) {
            holder.issueID.setText(issueArrayList.get(position).getID());
            holder.issueName.setText(issueArrayList.get(position).getTitle());
            holder.issuePrio.setText(issueArrayList.get(position).getPriority());
        } else {
            // Hacer algo si el array está vacío
        }
    }

    @Override
    public int getItemCount() {
        return issueArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView issueName;
        TextView issueID;
        TextView issuePrio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            issueID = itemView.findViewById(R.id.itemListID);
            issueName = itemView.findViewById(R.id.itemListIssue);
            issuePrio = itemView.findViewById(R.id.itemListPrio);
        }
    }
}
