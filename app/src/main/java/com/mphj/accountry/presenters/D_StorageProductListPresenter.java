package com.mphj.accountry.presenters;

import com.mphj.accountry.models.db.Storage;

/**
 * Created by mphj on 10/24/2017.
 */

public interface D_StorageProductListPresenter extends BasePresenter {

    void loadList(Storage storage);

}
