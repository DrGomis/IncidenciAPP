package com.example.issuemanager.db;

import android.provider.BaseColumns;

import java.util.Date;

public class IssueContract {

    private IssueContract(){}

    public static class IssueEntry implements BaseColumns {
        public static final String ISSUE_TABLE = "issues";
        public static final String ISSUE_NAME = "title";
        public static final String ISSUE_ID = "id";
        public static final String ISSUE_PRIORITY = "priority";

        public static final String ISSUE_DATE = "date";
        public static final String ISSUE_STATUS = "status";
        public static final String ISSUE_DESC = "description";

    }
}
