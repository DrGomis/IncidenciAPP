package com.example.issuemanager.app.util;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

public class AlertConfig {

    public static TextView genTitle(Context context, String text, String hexColor) {
        TextView title = new TextView(context);
        title.setText("Select an option");
        title.setPadding(50, 30, 20, 30);
        title.setTextSize(20F);
        title.setText(text);
        title.setTextColor(Color.parseColor(hexColor));
        return title;
    }
}
