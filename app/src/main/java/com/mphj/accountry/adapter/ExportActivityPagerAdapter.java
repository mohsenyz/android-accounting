package com.mphj.accountry.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.mphj.accountry.fragment.PlaceHolderFragment;
import com.mphj.accountry.fragment.export_activity.ProductListFragment;
import com.mphj.accountry.fragment.export_activity.ReaddedListFragment;

/**
 * Created by mphj on 11/10/2017.
 */

public class ExportActivityPagerAdapter extends FragmentPagerAdapter {

    ProductListFragment exportActivity_productListFragment;
    ReaddedListFragment exportActivity_readdedListFragment;

    public ExportActivityPagerAdapter(FragmentManager fragmentManager,
                                      ProductListFragment fragment1,
                                      ReaddedListFragment fragment2) {
        super(fragmentManager);
        exportActivity_productListFragment = fragment1;
        exportActivity_readdedListFragment = fragment2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return exportActivity_productListFragment;
            case 1:
                return exportActivity_readdedListFragment;
        }
        return PlaceHolderFragment.newInstance(position + 1);
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "لیست محصولات";
            case 1:
                return "اضافات و کسورات";
            case 2:
                return "مشخصات فاکتور";
        }
        return null;
    }



}
