package com.mphj.accountry.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mphj.accountry.R;
import com.mphj.accountry.adapter.SectionsPagerAdapter;
import com.mphj.accountry.interfaces.DashboardView;
import com.mphj.accountry.presenters.DashboardPresenter;
import com.mphj.accountry.presenters.DashboardPresenterImpl;
import com.mphj.accountry.utils.ViewAnimator;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends BaseActivity implements DashboardView, ViewPager.OnPageChangeListener, View.OnClickListener{


    @BindView(R.id.container)
    ViewPager mViewPager;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    int lastPosition = SectionsPagerAdapter.HOME;


    @BindViews({
            R.id.bottombar_home,
            R.id.bottombar_reports,
            R.id.bottombar_products,
            R.id.bottombar_customers,
            R.id.bottombar_checks
    })
    List<LinearLayout> bottomBar;

    DashboardPresenter presenter;

    SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_v1);
        ButterKnife.bind(this);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        presenter = new DashboardPresenterImpl(this);
        initTabs();
    }


    private void initTabs() {
        changeCurrentTo(mSectionsPagerAdapter.getCount() - 1, true);
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++){
            bottomBar.get(i).setOnClickListener(this);
            bottomBar.get(i).setTag(mSectionsPagerAdapter.getCount() - 1 - i);
            ((TextView) bottomBar.get(i).getChildAt(1)).setText(mSectionsPagerAdapter.getPageTitle(i));
        }
    }


    private void changeCurrentTo(int position, boolean changeViewPager){
        presenter.onPageChanged(position);
        ViewAnimator.defaultScale(
                bottomBar.get(4 - lastPosition).getChildAt(1),
                (float) 1
        );
        lastPosition = position;
        if (changeViewPager)
            mViewPager.setCurrentItem(position, true);
        ViewAnimator.defaultScale(
                bottomBar.get(4 - position).getChildAt(1),
                (float) 1.2
        );
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
        startActivity(new Intent(this, NewProductActivity.class));
    }

    @Override
    public void showNewCategoryActivity() {
        startActivity(new Intent(this, NewCategoryActivity.class));
    }

    @Override
    public void showNewCustomerActivity() {
        startActivity(new Intent(this, NewCustomerActivity.class));
    }
    @Override
    public void showExportProductActivity() {
        startActivity(new Intent(this, ExportProductActivity.class));
    }

    @Override
    public void onPageSelected(int position) {
        changeCurrentTo(position, false);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        int position = (int)v.getTag();
        Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
        changeCurrentTo(position, true);
    }
}
