package com.mphj.accountry.presenters;

import com.mphj.accountry.models.db.Storage;

/**
 * Created by mphj on 10/23/2017.
 */

public interface D_StorageSettingPresenter extends BasePresenter{
    void loadList(Storage storage);
}
