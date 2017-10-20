package com.mphj.accountry.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.mphj.accountry.R;
import com.mphj.accountry.adapter.SectionsPagerAdapter;
import com.mphj.accountry.interfaces.DashboardView;
import com.mphj.accountry.presenters.DashboardPresenter;
import com.mphj.accountry.presenters.DashboardPresenterImpl;
import com.mphj.accountry.utils.TabLayoutUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends BaseActivity implements DashboardView, TabLayout.OnTabSelectedListener{


    @BindView(R.id.container)
    ViewPager mViewPager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.network_error)
    ImageView networkError;

    DashboardPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(this);
        TabLayoutUtils.changeTabsFont(tabLayout);

        presenter = new DashboardPresenterImpl(this);
    }

    @OnClick(R.id.fab)
    public void onFabClick(){
        presenter.onFabClick();
    }

    @Override
    public void startProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void endProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showFab() {
        fab.animate()
                .translationY(0)
                .setDuration(400)
                .alpha(1)
                .start();
    }

    @Override
    public void hideFab() {
        fab.animate()
                .translationY(120)
                .alpha(0)
                .setDuration(400)
                .start();
    }

    @Override
    public void showNewProductActivity() {
        // @TODO
    }

    @Override
    public void showNewStorageActivity() {
        startActivity(new Intent(this, NewStorageActivity.class));
    }

    @Override
    public void showNewTransactionActivity() {
        // @TODO
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        presenter.onPageChanged(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
