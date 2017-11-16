package com.mphj.accountry.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.mphj.accountry.R;
import com.mphj.accountry.adapter.ExportActivityPagerAdapter;
import com.mphj.accountry.fragment.export_activity.ProductListFragment;
import com.mphj.accountry.fragment.export_activity.ReaddedListFragment;
import com.mphj.accountry.utils.TabLayoutUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExportProductActivity_v1 extends BaseActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.container)
    ViewPager viewPager;

    ProductListFragment fragment1;
    ReaddedListFragment fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_product_v1);
        ButterKnife.bind(this);
        fragment1 = ProductListFragment.newInstance();
        fragment2 = new ReaddedListFragment();
        ExportActivityPagerAdapter exportActivityPagerAdapter =
                new ExportActivityPagerAdapter(getSupportFragmentManager(), fragment1, fragment2);
        viewPager.setAdapter(exportActivityPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        TabLayoutUtils.changeTabsFont(tabLayout);
    }
}