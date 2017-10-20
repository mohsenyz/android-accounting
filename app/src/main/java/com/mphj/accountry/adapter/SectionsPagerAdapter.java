package com.mphj.accountry.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mphj.accountry.activity.DashboardActivity;
import com.mphj.accountry.fragment.CustomerListFragment;
import com.mphj.accountry.fragment.PlaceHolderFragment;
import com.mphj.accountry.fragment.StorageListFragment;

/**
 * Created by mphj on 10/20/2017.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    DashboardActivity dashboardActivity;

    public SectionsPagerAdapter(FragmentManager fm, DashboardActivity dashboardActivity) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return StorageListFragment.newInstance();
            case 3:
                return CustomerListFragment.newInstance();
        }
        return PlaceHolderFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "انبار";
            case 1:
                return "محصولات";
            case 2:
                return "گزارشات";
            case 3:
                return "مشتریان";
        }
        return null;
    }


    public static final int STORAGE = 0, PRODUCTS = 1, REPORTS = 2, CUSTOMERS = 3;
}
