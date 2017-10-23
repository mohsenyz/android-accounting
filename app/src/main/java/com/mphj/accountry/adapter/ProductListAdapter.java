package com.mphj.accountry.adapter;

import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mphj.accountry.R;
import com.mphj.accountry.dialog.ProductSettingDialog;
import com.mphj.accountry.interfaces.OnObjectItemClick;
import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.models.db.ProductPrice;
import com.mphj.accountry.utils.BarcodeGenerator;
import com.mphj.accountry.utils.DeviceUtils;
import com.mphj.accountry.utils.LocaleUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mphj on 10/22/2017.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    List<Product> list;
    FragmentActivity fragmentActivity;
    OnObjectItemClick<Product> onObjectItemClick;

    public ProductListAdapter(List<Product> list, FragmentActivity fragmentActivity){
        this.list = list;
        this.fragmentActivity = fragmentActivity;
    }

    public ProductListAdapter(List<Product> list, FragmentActivity fragmentActivity, OnObjectItemClick<Product> click){
        this.list = list;
        this.fragmentActivity = fragmentActivity;
        this.onObjectItemClick = click;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_list_item, viewGroup, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder viewHolder, int i) {
        final Product product = list.get(i);
        if (product.getServerId() != 0)
            viewHolder.loading.setVisibility(View.GONE);
        viewHolder.text.setText(product.getName());
        BarcodeGenerator.bind(viewHolder.barcode, product.getToken(), DeviceUtils.getScreenWidth(), 60);
        ProductPrice productPrice = product.getCurrentProductPrice();
        Log.i("hi", "" + productPrice.getPrice());
        viewHolder.rightText.setText(
                Html.fromHtml(viewHolder.rightText.getResources().getString(
                        R.string.html_price_eq).replace("xxx", LocaleUtils.englishNumberToArabic( "" + productPrice.getPrice()))
                )
        );
        viewHolder.leftText.setText(
                Html.fromHtml(viewHolder.leftText.getResources().getString(
                        R.string.html_off_eq).replace("xxx", LocaleUtils.englishNumberToArabic("" + productPrice.getOff()))
                )
        );
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onObjectItemClick != null){
                    onObjectItemClick.onClick(v, product);
                    return;
                }
                BottomSheetDialogFragment bottomSheetDialogFragment = ProductSettingDialog.create(product);
                bottomSheetDialogFragment.show(fragmentActivity.getSupportFragmentManager(), "");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.text)
        public TextView text;

        @BindView(R.id.leftText)
        public TextView leftText;

        @BindView(R.id.rightText)
        public TextView rightText;

        @BindView(R.id.loading)
        public ImageView loading;

        @BindView(R.id.barcode)
        public ImageView barcode;

        @BindView(R.id.container)
        public CardView container;

        public ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}