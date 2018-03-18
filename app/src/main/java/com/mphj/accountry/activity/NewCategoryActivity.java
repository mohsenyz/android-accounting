package com.mphj.accountry.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mphj.accountry.R;
import com.mphj.accountry.interfaces.NewCategoryView;
import com.mphj.accountry.models.db.Category;
import com.mphj.accountry.presenters.NewCategoryPresenter;
import com.mphj.accountry.presenters.NewCategoryPresenterImpl;
import com.mphj.accountry.utils.DaoManager;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewCategoryActivity extends BaseActivity implements NewCategoryView {

    @BindView(R.id.input_storage)
    EditText storage;

    @BindString(R.string.err_invalid_storage_name)
    String errStorageName;

    @BindView(R.id.submit)
    Button submit;

    @BindView(R.id.title)
    TextView title;

    NewCategoryPresenter presenter;

    int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_storage);
        ButterKnife.bind(this);
        presenter = new NewCategoryPresenterImpl(this);
        categoryId = getIntent().getIntExtra("id", -1);
        if (categoryId != -1) {
            title.setText("ویرایش دسته بندی");
            submit.setText("ویرایش");
            Category category = DaoManager.session().getCategoryDao().load((long) categoryId);
            storage.setText(category.getName());
        }
    }

    @Override
    public void invalidStorageName() {
        storage.setError(errStorageName);
    }


    @OnClick(R.id.submit)
    void onSubmit(){
        if (categoryId == -1) {
            presenter.newStorage(storage.getText().toString());
        } else {
            presenter.updateStorage(storage.getText().toString(), categoryId);
        }
    }


    @Override
    public void finishActivity() {
        finish();
    }
}
