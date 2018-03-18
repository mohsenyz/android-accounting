package com.mphj.wimagepicker.model;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.mphj.wimagepicker.R;
import com.mphj.wimagepicker.adapter.WImageListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mphj on 12/21/17.
 */

public class WimageItem {
    public static final int TYPE_GALLERY_PICKER = 1, TYPE_CAMERA_PICKER = 2, TYPE_DEFAULT = 3;

    public int imageResource = -1;
    public Bitmap imageBitmap = null;
    public int borderColor = Color.BLACK;
    public int tag = -1;

    public interface OnItemClickListener {
        void onItemClick(WimageItem item, WImageListAdapter.SimpleViewHolder holder, int pos);
    }

    public static List<WimageItem> createDefault() {
        List<WimageItem> list = new ArrayList<>();
        WimageItem camera = new WimageItem();
        camera.imageResource = R.drawable.ic_wimage_camera;
        camera.borderColor = Color.parseColor("#297cde");
        camera.tag = TYPE_CAMERA_PICKER;
        list.add(camera);

        WimageItem gallery = new WimageItem();
        gallery.imageResource = R.drawable.ic_wimage_image;
        gallery.borderColor = Color.parseColor("#297cde");
        gallery.tag = TYPE_GALLERY_PICKER;
        list.add(gallery);

        return list;
    }
}
