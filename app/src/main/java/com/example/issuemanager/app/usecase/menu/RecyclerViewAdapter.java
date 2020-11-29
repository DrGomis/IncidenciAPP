package com.example.issuemanager.app.usecase.menu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.issuemanager.R;
import com.example.issuemanager.app.usecase.menu.fragment.IssueManagerFragment;
import com.example.issuemanager.app.usecase.menu.fragment.ListIssueFragment;
import com.example.issuemanager.db.model.Issue;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private ArrayList<Issue> issueArrayList;
    private Context context;

    // If does not work, add Context context or ListIssueFragment
    // Gets all Issues from ListIssueFragment
    public RecyclerViewAdapter(Context con, ArrayList<Issue> arr){
        issueArrayList = arr;
        context = con;
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

            String statusText = "";
            int color = 0;
            int textColor = 0;

            if (issueArrayList.get(position).getStatusID() == 0) {
                statusText = context.getResources().getString(R.string.status_pending);
                color = Color.RED;
                textColor = Color.WHITE;

            } else if (issueArrayList.get(position).getStatusID() == 1) {
                statusText = context.getResources().getString(R.string.status_assigned);
                color = Color.YELLOW;
                textColor = Color.BLACK;
            } else if (issueArrayList.get(position).getStatusID() == 2) {
                statusText = context.getResources().getString(R.string.status_solved);
                color = Color.GREEN;
                textColor = Color.WHITE;
            }
            holder.issueStatus.setText(statusText);
            holder.issueStatus.setBackgroundColor(color);
            holder.issueStatus.setTextColor(textColor);

            holder.issueID.setText(issueArrayList.get(position).getID());
            holder.issueName.setText(issueArrayList.get(position).getTitle());

            // Translates priority
            String prio = issueArrayList.get(position).getPriority();
            if (prio.equals("1")) {
                prio = context.getResources().getString(R.string.prio_low);
            } else if (prio.equals("2")) {
                prio = context.getResources().getString(R.string.prio_medium);
            } else if (prio.equals("3")) {
                prio = context.getResources().getString(R.string.prio_high);
            }
            holder.issuePrio.setText(prio);

            // Issue management fragment
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity)v.getContext();
                    IssueManagerFragment popup = new IssueManagerFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("ITEM_ID", issueArrayList.get(position).getID());
                    bundle.putString("ITEM_TITLE", issueArrayList.get(position).getTitle());
                    bundle.putString("ITEM_PRIO", issueArrayList.get(position).getPriority());
                    bundle.putInt("ITEM_STATUS", issueArrayList.get(position).getStatusID());
                    bundle.putString("ITEM_DESC", issueArrayList.get(position).getDesc());
                    bundle.putString("ITEM_DATE", issueArrayList.get(position).getDate());

                    popup.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout2, popup).addToBackStack(null).commit();
                }
            });


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
        TextView issueStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            issueID = itemView.findViewById(R.id.itemListID);
            issueName = itemView.findViewById(R.id.itemListIssue);
            issuePrio = itemView.findViewById(R.id.itemListPrio);
            issueStatus = itemView.findViewById(R.id.itemListStatus);
        }
    }
}
