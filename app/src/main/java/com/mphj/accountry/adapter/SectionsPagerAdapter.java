package com.mphj.accountry.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mphj.accountry.activity.DashboardActivity;
import com.mphj.accountry.fragment.CategoryListFragment;
import com.mphj.accountry.fragment.CheckListFragment;
import com.mphj.accountry.fragment.CustomerListFragment;
import com.mphj.accountry.fragment.MainFragment;
import com.mphj.accountry.fragment.PlaceHolderFragment;

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
            case HOME:
                return MainFragment.newInstance();
            case PRODUCTS:
                return CategoryListFragment.newInstance();
            case CUSTOMERS:
                return CustomerListFragment.newInstance();
            case CHECKS:
                return CheckListFragment.newInstance();
        }
        return PlaceHolderFragment.newInstance(position + 1);
    }


    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "خانه";
            case 1:
                return "گزارشات";
            case 2:
                return "محصولات";
            case 3:
                return "مشتریان";
            case 4:
                return "چک ها";
        }
        return null;
    }

    public static final int HOME = 4, REPORTS = 3, PRODUCTS = 2, CUSTOMERS = 1, CHECKS = 0;
}
