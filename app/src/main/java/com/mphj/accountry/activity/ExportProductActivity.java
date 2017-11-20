package com.mphj.accountry.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.mphj.accountry.R;
import com.mphj.accountry.adapter.ExportActivityPagerAdapter;
import com.mphj.accountry.fragment.export_activity.InfoFragment;
import com.mphj.accountry.fragment.export_activity.ProductListFragment;
import com.mphj.accountry.fragment.export_activity.ReaddedListFragment;
import com.mphj.accountry.interfaces.ExportProductView;
import com.mphj.accountry.models.db.Check;
import com.mphj.accountry.models.db.Transaction;
import com.mphj.accountry.presenters.ExportProductPresenter;
import com.mphj.accountry.presenters.ExportProductPresenterImpl;
import com.mphj.accountry.utils.TabLayoutUtils;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class ExportProductActivity extends BaseActivity implements ExportProductView{

    public static final int REQUEST_PAYMENT_TYPE = 1;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.container)
    ViewPager viewPager;

    ProductListFragment fragment1;
    ReaddedListFragment fragment2;
    InfoFragment fragment3;

    ExportProductPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_product);
        ButterKnife.bind(this);
        fragment1 = ProductListFragment.newInstance();
        fragment2 = ReaddedListFragment.newInstance();
        fragment3 = InfoFragment.newInstance();
        fragment3.setProductListView(fragment1);
        fragment3.setReaddedListView(fragment2);
        ExportActivityPagerAdapter exportActivityPagerAdapter =
                new ExportActivityPagerAdapter(
                        getSupportFragmentManager(),
                        fragment1,
                        fragment2,
                        fragment3);
        viewPager.setAdapter(exportActivityPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        TabLayoutUtils.changeTabsFont(tabLayout);
        presenter = new ExportProductPresenterImpl(this);
    }


    boolean isPaymentTypeSelected = false;
    Check check = null;
    int paymentType;


    @OnClick(R.id.submit)
    void onSubmit() {
        if (!isPaymentTypeSelected) {
            startActivityForResult(new Intent(this, SelectPaymentTypeActivity.class), REQUEST_PAYMENT_TYPE);
        } else {
            presenter.submit(fragment3, fragment1, fragment2, paymentType, check);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PAYMENT_TYPE) {
            if (resultCode == Activity.RESULT_OK) {
                paymentType = data.getIntExtra("type", Transaction.PAYMENT_CREDIT);
                if (paymentType == Transaction.PAYMENT_CHECK) {
                    check = Parcels.unwrap(data.getParcelableExtra("check"));
                }
                isPaymentTypeSelected = true;
                onSubmit();
            }
        }
    }

    @Override
    public void transactionSaved(int transactionId) {
        Toasty.success(this, "فاکتور با موفقیت ثبت شد").show();
        finish();
    }
}
