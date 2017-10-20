package com.mphj.accountry.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.mphj.accountry.R;
import com.mphj.accountry.interfaces.NewStorageView;
import com.mphj.accountry.presenters.NewStoragePresenter;
import com.mphj.accountry.presenters.NewStoragePresenterImpl;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewStorageActivity extends BaseActivity implements NewStorageView{

    @BindView(R.id.input_storage)
    EditText storage;

    @BindString(R.string.err_invalid_storage_name)
    String errStorageName;

    NewStoragePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_storage);
        ButterKnife.bind(this);
        presenter = new NewStoragePresenterImpl(this);
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
