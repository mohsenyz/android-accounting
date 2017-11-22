package com.mphj.accountry.interfaces.fragment;

import com.mphj.accountry.models.db.Check;

import java.util.List;

/**
 * Created by mphj on 11/20/17.
 */

public interface CheckListView {
    void setAdapter(List<Check> list);
}
