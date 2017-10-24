package com.mphj.accountry.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mphj.accountry.R;
import com.mphj.accountry.adapter.ProductListAdapter;
import com.mphj.accountry.interfaces.ImportProductView;
import com.mphj.accountry.interfaces.OnObjectItemClick;
import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.models.db.Storage;
import com.mphj.accountry.presenters.ImportProductPresenter;
import com.mphj.accountry.presenters.ImportProductPresenterImpl;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImportProductActivity extends BaseActivity implements ImportProductView, OnObjectItemClick<Product> {

    private static final int REQUEST_STORAGE = 1,
            REQUEST_PRODUCT_LIST = 2,
            REQUEST_GET_COUNT = 3,
            REQUEST_CAMERA = 4;

    @BindView(R.id.input_storage)
    EditText storageInput;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    ProductListAdapter productListAdapter;

    ImportProductPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_product);
        ButterKnife.bind(this);
        presenter = new ImportProductPresenterImpl(this);
        setupRecyclerView();
        Intent data = getIntent();
        if (data != null){
            int storageId = data.getIntExtra("storage_id", -1);
            if (storageId != -1) {
                presenter.setStorageById(storageId);
            }
        }
    }

    public void setupRecyclerView(){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        productListAdapter = new ProductListAdapter(presenter.getProductList(), this, this);
    }

    @OnClick(R.id.input_storage)
    void onStorageClick(){
        Intent i = new Intent(this, SelectStorageActivity.class);
        startActivityForResult(i, REQUEST_STORAGE);
    }

    @OnClick(R.id.submit)
    void submit(){
        presenter.saveTransaction();
    }

    @OnClick(R.id.fromList)
    void onRequestList(){
        Intent i = new Intent(this, SelectProductActivity.class);
        startActivityForResult(i, REQUEST_PRODUCT_LIST);
    }

    @OnClick(R.id.fromCamera)
    void onRequestCamera(){
        Intent i = new Intent(this, BarcodeReaderActivity.class);
        startActivityForResult(i, REQUEST_CAMERA);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        if (requestCode == REQUEST_STORAGE && resultCode == 200){
            presenter.setStorage((Storage) Parcels.unwrap(data.getParcelableExtra("storage")));
        }

        if (requestCode == REQUEST_PRODUCT_LIST){
            Product product = Parcels.unwrap(data.getParcelableExtra("product"));
            presenter.addProduct(product);
        }

        if (requestCode == REQUEST_GET_COUNT){
            int count = data.getIntExtra("count", 1);
                    if (count < 0)
                        showGetCountActivity();
            presenter.setProductCount(count);
        }

        if (requestCode == REQUEST_CAMERA){
            String serial = data.getStringExtra("serial");
            presenter.addProduct(serial);
        }
    }

    @Override
    public void productAlreadyExists() {
        Toast.makeText(this, "محصول تکراری میباشد", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void productNotFound() {
        Toast.makeText(this, "محصول یافت نشد", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showGetCountActivity() {
        Intent i = new Intent(this, GetCountActivity.class);
        i.putExtra("count", 1);
        startActivityForResult(i, REQUEST_GET_COUNT);
    }

    @Override
    public void setStorageName(String name) {
        storageInput.setText(name);
    }

    @Override
    public void notifyDataSetChanged() {
        productListAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(productListAdapter);
    }

    @Override
    public void onClick(View v, Product object) {
        presenter.setPendingProduct(object);
        showGetCountActivity();
    }

    @Override
    public void finishActivity() {
        finish();
    }
}
