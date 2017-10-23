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
import com.mphj.accountry.dao.StorageDao;
import com.mphj.accountry.interfaces.ImportProductView;
import com.mphj.accountry.interfaces.OnObjectItemClick;
import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.models.db.Storage;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class ImportProductActivity extends BaseActivity implements ImportProductView, OnObjectItemClick<Product> {

    Storage storage;

    private static final int REQUEST_STORAGE = 1,
            REQUEST_PRODUCT_LIST = 2,
            REQUEST_GET_COUNT = 3;

    @BindView(R.id.input_storage)
    EditText storageInput;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    ProductListAdapter productListAdapter;
    List<Product> list = new ArrayList<>();

    private Product pendingProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_product);
        ButterKnife.bind(this);
        setupRecyclerView();
        Intent data = getIntent();
        if (data != null){
            int storageId = data.getIntExtra("storage_id", -1);
            if (storageId != -1) {
                StorageDao storageDao = new StorageDao(Realm.getDefaultInstance());
                setStorage(storageDao.findById(storageId));
            }
        }
    }

    public void setupRecyclerView(){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        productListAdapter = new ProductListAdapter(list, this, this);
    }

    @OnClick(R.id.input_storage)
    void onStorageClick(){
        Intent i = new Intent(this, SelectStorageActivity.class);
        startActivityForResult(i, REQUEST_STORAGE);
    }

    @OnClick(R.id.fromList)
    void onRequestList(){
        Intent i = new Intent(this, SelectProductActivity.class);
        startActivityForResult(i, REQUEST_PRODUCT_LIST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        if (requestCode == REQUEST_STORAGE && resultCode == 200){
            setStorage((Storage) Parcels.unwrap(data.getParcelableExtra("storage")));
        }

        if (requestCode == REQUEST_PRODUCT_LIST){
            pendingProduct = Parcels.unwrap(data.getParcelableExtra("product"));
            if (isProductExists(pendingProduct.getId())){
                errProductAlreadyExists();
                return;
            }
            showCountActivity();
        }

        if (requestCode == REQUEST_GET_COUNT){
            int count = data.getIntExtra("count", 1);
            if (count <= 0){
                deleteProduct(pendingProduct.getId());
                pendingProduct = null;
            } else {
                pendingProduct.setCount(count);
                if (!isProductExists(pendingProduct.getId())){
                    list.add(pendingProduct);
                }
                recyclerView.setAdapter(productListAdapter);
            }
        }
    }

    private void errProductAlreadyExists(){
        Toast.makeText(this, "محصول تکراری میباشد", Toast.LENGTH_SHORT).show();
    }

    private boolean isProductExists(int id){
        for (Product product : list)
            if (product.getId() == id)
                return true;

        return false;
    }

    private void deleteProduct(int id){
        for (Product product : list) {
            if (product.getId() == id) {
                list.remove(product);
            }
        }
        recyclerView.setAdapter(productListAdapter);
    }


    private void showCountActivity(){
        Intent i = new Intent(this, GetCountActivity.class);
        i.putExtra("count", 1);
        startActivityForResult(i, REQUEST_GET_COUNT);
    }

    @Override
    public void setStorage(Storage storage) {
        this.storage = storage;
        storageInput.setText(storage.getName());
        storageInput.clearFocus();
    }

    @Override
    public void onClick(View v, Product object) {
        pendingProduct = object;
        showCountActivity();
    }
}
