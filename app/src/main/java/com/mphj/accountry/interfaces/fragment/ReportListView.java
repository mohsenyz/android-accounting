package com.mphj.accountry.interfaces.fragment;

import com.mphj.accountry.models.db.Transaction;

import java.util.List;

/**
 * Created by mphj on 11/20/17.
 */

public interface ReportListView {
    void setAdapter(List<Transaction> list);
}