package com.mphj.accountry.fragment.export_activity;

import android.os.Bundle;
import android.os.LocaleList;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mphj.accountry.R;
import com.mphj.accountry.interfaces.fragment.export_activity.InfoView;
import com.mphj.accountry.interfaces.fragment.export_activity.ProductListView;
import com.mphj.accountry.interfaces.fragment.export_activity.ReaddedListView;
import com.mphj.accountry.presenters.fragment.export_activity.InfoPresenter;
import com.mphj.accountry.presenters.fragment.export_activity.InfoPresenterImpl;
import com.mphj.accountry.utils.LocaleUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

/**
 * Created by mphj on 11/17/17.
 */

public class InfoFragment extends Fragment implements
        InfoView{

    @BindView(R.id.totalOff)
    TextView totalOff;

    @BindView(R.id.totalPrice)
    TextView totalPrice;

    @BindView(R.id.input_off)
    EditText off;

    @BindView(R.id.input_tax)
    EditText tax;

    @BindView(R.id.input_description)
    EditText description;

    InfoPresenter presenter;

    ProductListView productListView;
    ReaddedListView readdedListView;

    int totalCustomerPrice = 0;

    public void setProductListView(ProductListView view) {
        this.productListView = view;
    }

    public void setReaddedListView(ReaddedListView view) {
        this.readdedListView = view;
    }

    public static InfoFragment newInstance(){
        return new InfoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new InfoPresenterImpl(this);
        presenter.configWith(productListView);
        presenter.configWith(readdedListView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_export_product_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public int getOff() {
        try{
            return Integer.parseInt(off.getText().toString());
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public int getTax() {
        try{
            return Integer.parseInt(tax.getText().toString());
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public void setTotalPriceWithOff(double totalPriceWithOff) {
        totalPrice.setText(LocaleUtils.englishNumberToArabic("" + totalPriceWithOff) + " تومان");
    }

    @Override
    public void setTotalOffPrice(double totalOffPrice) {
        totalCustomerPrice = (int) totalOffPrice;
        totalOff.setText(LocaleUtils.englishNumberToArabic("" + totalOffPrice) + " تومان");
    }

    @Override
    public String getFactorDescription() {
        return description.getText().toString();
    }

    @OnTextChanged({R.id.input_off, R.id.input_tax})
    public void onTextChanged() {
        presenter.load();
    }


    @Override
    public int getTotalCustomerPrice() {
        presenter.load();
        return totalCustomerPrice;
    }

    @Override
    public void onResume() {
        super.onResume();
        onTextChanged();
    }
}