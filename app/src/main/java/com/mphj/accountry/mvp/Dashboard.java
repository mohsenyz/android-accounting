package com.mphj.accountry.mvp;

import com.mphj.accountry.interfaces.CommonView;
import com.mphj.accountry.models.viewmodel.BottomBarModel;

import java.util.List;

/**
 * Created by mphj on 11/22/17.
 */

public interface Dashboard {

    interface View extends
            Mvp.View,
            CommonView.Progressbar,
            CommonView.ReactiveFab{

        void showPage(int pageId);
        void setBottomBarItems(List<BottomBarModel> list);

    }


    interface Presenter extends Mvp.Presenter<View> { }


    interface Model {

        List<BottomBarModel> getBottomBarItems();

    }

}
