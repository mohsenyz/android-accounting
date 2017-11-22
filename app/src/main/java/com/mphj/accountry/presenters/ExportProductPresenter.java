package com.mphj.accountry.presenters;

import android.support.annotation.Nullable;

import com.mphj.accountry.interfaces.fragment.export_activity.InfoView;
import com.mphj.accountry.interfaces.fragment.export_activity.ProductListView;
import com.mphj.accountry.interfaces.fragment.export_activity.ReaddedListView;
import com.mphj.accountry.models.db.Check;

/**
 * Created by mphj on 10/24/2017.
 */

public interface ExportProductPresenter extends BasePresenter {
    void submit(InfoView infoView, ProductListView productListView, ReaddedListView readdedListView, int paymentType, @Nullable Check check);
}
