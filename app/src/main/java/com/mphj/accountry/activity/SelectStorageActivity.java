package com.mphj.accountry.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.mphj.accountry.R;
import com.mphj.accountry.fragment.StorageListFragment;
import com.mphj.accountry.interfaces.OnObjectItemClick;
import com.mphj.accountry.models.db.Storage;

import org.parceler.Parcels;

public class SelectStorageActivity extends BaseActivity implements OnObjectItemClick<Storage>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_storage);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        StorageListFragment fragment = StorageListFragment.newInstance(true);
        fragment.setOnObjectItemClickListener(this);
        ft.add(R.id.fragment, fragment);
        ft.commit();
        setFinishOnTouchOutside(false);
    }

    @Override
    public void onClick(View v, Storage object) {
        Intent i = new Intent();
        i.putExtra("storage", Parcels.wrap(Storage.class, object));
        setResult(200, i);
        finish();
    }
}
