package com.mphj.accountry.adapter;

import android.content.Intent;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mphj.accountry.R;
import com.mphj.accountry.activity.NewCategoryActivity;
import com.mphj.accountry.activity.NewProductActivity;
import com.mphj.accountry.dialog.CategoryProductListDialog;
import com.mphj.accountry.dialog.CategorySettingDialog;
import com.mphj.accountry.interfaces.OnObjectItemClick;
import com.mphj.accountry.models.db.Category;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mphj on 10/20/2017.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.StorageViewHolder> {

    List<Category> list;
    FragmentActivity fragmentActivity;
    OnObjectItemClick<Category> onObjectItemClick;

    public CategoryListAdapter(List<Category> list, FragmentActivity fragmentActivity){
        this.list = list;
        this.fragmentActivity = fragmentActivity;
    }

    public CategoryListAdapter(List<Category> list, FragmentActivity fragmentActivity, OnObjectItemClick<Category> click){
        this.list = list;
        this.fragmentActivity = fragmentActivity;
        this.onObjectItemClick = click;
    }

    @Override
    public StorageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.storage_list_item, viewGroup, false);
        return new StorageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StorageViewHolder viewHolder, int i) {
        final Category category = list.get(i);
        if (category.getServerId() != 0)
            viewHolder.loading.setVisibility(View.GONE);
        viewHolder.text.setText(category.getName());
        viewHolder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onObjectItemClick != null){
                    onObjectItemClick.onClick(v, category);
                    return true;
                }
                BottomSheetDialogFragment bottomSheetDialogFragment = CategorySettingDialog.create(category);
                bottomSheetDialogFragment.show(fragmentActivity.getSupportFragmentManager(), "");
                return true;
            }
        });
        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), NewCategoryActivity.class);
                i.putExtra("id", category.getId().intValue());
                v.getContext().startActivity(i);
            }
        });
        viewHolder.addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), NewProductActivity.class);
                i.putExtra("category", Parcels.wrap(Category.class, category));
                v.getContext().startActivity(i);
            }
        });
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogFragment bottomSheetDialogFragment = CategoryProductListDialog.create(category);
                bottomSheetDialogFragment.show(fragmentActivity.getSupportFragmentManager(), "");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StorageViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.text)
        public TextView text;

        @BindView(R.id.loading)
        public ImageView loading;

        @BindView(R.id.container)
        public CardView container;

        @BindView(R.id.button_add)
        Button addProduct;

        @BindView(R.id.bottombar_edit)
        TextView edit;

        public StorageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
