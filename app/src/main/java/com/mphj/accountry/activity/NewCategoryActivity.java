package com.mphj.accountry.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.mphj.accountry.R;
import com.mphj.accountry.interfaces.NewCategoryView;
import com.mphj.accountry.presenters.NewCategoryPresenter;
import com.mphj.accountry.presenters.NewCategoryPresenterImpl;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewCategoryActivity extends BaseActivity implements NewCategoryView {

    @BindView(R.id.input_storage)
    EditText storage;

    @BindString(R.string.err_invalid_storage_name)
    String errStorageName;

    NewCategoryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_storage);
        ButterKnife.bind(this);
        presenter = new NewCategoryPresenterImpl(this);
    }

    @Override
    public void invalidStorageName() {
        storage.setError(errStorageName);
    }


    @OnClick(R.id.submit)
    void onSubmit(){
        presenter.newStorage(storage.getText().toString());
    }


    @Override
    public void finishActivity() {
        finish();
    }
}
