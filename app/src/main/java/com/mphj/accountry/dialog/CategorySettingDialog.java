package com.mphj.accountry.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mphj.accountry.R;
import com.mphj.accountry.activity.NewCategoryActivity;
import com.mphj.accountry.activity.NewProductActivity;
import com.mphj.accountry.adapter.SimpleListAdapter;
import com.mphj.accountry.interfaces.dialog.CategorySettingView;
import com.mphj.accountry.models.SimpleListModel;
import com.mphj.accountry.models.db.Category;
import com.mphj.accountry.presenters.dialog.CategorySettingPresenter;
import com.mphj.accountry.presenters.dialog.CategorySettingPresenterImpl;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mphj on 10/23/2017.
 */

public class CategorySettingDialog extends BottomSheetDialogFragment implements CategorySettingView {


    CategorySettingPresenter presenter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    SimpleListAdapter simpleListAdapter;

    Category category;

    public static CategorySettingDialog create(Category category){
        CategorySettingDialog dialog = new CategorySettingDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("category", Parcels.wrap(Category.class, category));
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        View contentView = View.inflate(getContext(), R.layout.bs_dialog_simple_setting, null);
        dialog.setContentView(contentView);
        ButterKnife.bind(this, contentView);
        setupRecyclerView();
        if (getArguments() != null) {
            category = Parcels.unwrap(getArguments().getParcelable("category"));
        }
        presenter = new CategorySettingPresenterImpl(this);
        presenter.loadList(category);
        return dialog;
    }

    public void setupRecyclerView(){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void setAdapter(List<SimpleListModel> list) {
        simpleListAdapter = new SimpleListAdapter(list);
        recyclerView.setAdapter(simpleListAdapter);
    }

    @Override
    public void showProductList() {
        BottomSheetDialogFragment bottomSheetDialogFragment = CategoryProductListDialog.create(category);
        bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), "");
    }


    @Override
    public void showAddProductActivity() {
        Intent i = new Intent(getActivity(), NewProductActivity.class);
        i.putExtra("category", Parcels.wrap(Category.class, category));
        startActivity(i);
    }


    @Override
    public void showEditPage() {
        Intent i = new Intent(getActivity(), NewCategoryActivity.class);
        i.putExtra("id", category.getId().intValue());
        startActivity(i);
    }
}
