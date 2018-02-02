package com.mphj.accountry.adapter;

import android.content.Intent;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mphj.accountry.R;
import com.mphj.accountry.activity.GetCountActivity;
import com.mphj.accountry.activity.NewProductActivity;
import com.mphj.accountry.dialog.CategoryProductListDialog;
import com.mphj.accountry.dialog.ProductSettingDialog;
import com.mphj.accountry.interfaces.OnObjectItemClick;
import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.models.db.ProductPrice;
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
    CategoryProductListDialog categoryProductListDialog;

    public ProductListAdapter(List<Product> list, FragmentActivity fragmentActivity){
        this.list = list;
        this.fragmentActivity = fragmentActivity;
    }

    public ProductListAdapter(List<Product> list, FragmentActivity fragmentActivity, OnObjectItemClick<Product> click){
        this.list = list;
        this.fragmentActivity = fragmentActivity;
        this.onObjectItemClick = click;
    }

    public ProductListAdapter(List<Product> list, FragmentActivity fragmentActivity, CategoryProductListDialog categoryProductListDialog){
        this.list = list;
        this.fragmentActivity = fragmentActivity;
        this.categoryProductListDialog = categoryProductListDialog;
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
        if (product.getPendingCount() == 0) {
            viewHolder.centerText.setText(LocaleUtils.englishNumberToArabic( "" + product.getCount()));
        } else {
            viewHolder.centerText.setText(LocaleUtils.englishNumberToArabic( "" + product.getPendingCount()));
        }
        viewHolder.text.setText(product.getName());
        ProductPrice productPrice = product.getCurrentProductPrice();
        if (productPrice != null) {
            viewHolder.rightText.setText(
                    Html.fromHtml(viewHolder.rightText.getResources().getString(
                            R.string.html_price_eq)
                            .replace("xxx", LocaleUtils.englishNumberToArabic( "" + (int)productPrice.getPrice()))
                    )
            );
        }
        viewHolder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onObjectItemClick != null){
                    onObjectItemClick.onClick(v, product);
                    return true;
                }
                BottomSheetDialogFragment bottomSheetDialogFragment = ProductSettingDialog.create(product);
                bottomSheetDialogFragment.show(fragmentActivity.getSupportFragmentManager(), "");
                return true;
            }
        });
        viewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryProductListDialog.pendingProduct = product;
                categoryProductListDialog.startActivityForResult(
                        new Intent(v.getContext(), GetCountActivity.class),
                        ProductSettingDialog.INCREASE_PRODUCT
                );
            }
        });
        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NewProductActivity.class);
                intent.putExtra("id", product.getId().intValue());
                v.getContext().startActivity(intent);
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

        @BindView(R.id.centerText)
        public TextView centerText;

        @BindView(R.id.rightText)
        public TextView rightText;

        @BindView(R.id.container)
        public CardView container;

        @BindView(R.id.bottombar_edit)
        TextView edit;

        @BindView(R.id.bottombar_add)
        TextView add;

        @BindView(R.id.bottombar_stats)
        TextView stat;

        public ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}