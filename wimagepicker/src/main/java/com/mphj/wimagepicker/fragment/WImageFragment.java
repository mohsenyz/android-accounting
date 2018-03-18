package com.mphj.wimagepicker.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mphj.wimagepicker.R;
import com.mphj.wimagepicker.adapter.WImageListAdapter;
import com.mphj.wimagepicker.model.WimageItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mphj on 12/20/17.
 */

public class WImageFragment extends Fragment {
    public static final String ARG_DEFAULT_IMAGE_DRAWABLE = "def_image_drawable"
            , ARG_DEFAULT_IMAGE_BORDER_COLOR = "def_image_border_color"
            , ARG_DEFAULT_LIST_IMAGE_BORDER_COLOR = "def_list_image_border_color"
            , ARG_PICK_FROM_GALLERY_ENABLED = "pick_from_gallery_enabled"
            , ARG_PICK_FROM_CAMERA_ENABLED = "pick_from_camera_enabled";

    public static class Builder {

        FragmentManager fragmentManager;
        int fragmentContainer = -1;
        Bundle arguments;

        public Builder(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
            arguments = new Bundle();
        }

        public Builder defaultImageBorderColor(int color) {
            arguments.putInt(ARG_DEFAULT_IMAGE_BORDER_COLOR, color);
            return this;
        }

        public Builder defaultListImageBorderColor(int color) {
            arguments.putInt(ARG_DEFAULT_LIST_IMAGE_BORDER_COLOR, color);
            return this;
        }

        public Builder pickFromGalleryEnabled(boolean enabled) {
            arguments.putBoolean(ARG_PICK_FROM_GALLERY_ENABLED, enabled);
            return this;
        }

        public Builder pickFromCameraEnabled(boolean enabled) {
            arguments.putBoolean(ARG_PICK_FROM_CAMERA_ENABLED, enabled);
            return this;
        }

        public Builder defaultImageDrawable(int drawable) {
            arguments.putInt(ARG_DEFAULT_IMAGE_DRAWABLE, drawable);
            return this;
        }

        public Builder into(int containerId) {
            fragmentContainer = containerId;
            return this;
        }

        public WImageFragment build() {
            WImageFragment wImageFragment = WImageFragment.newInstance(arguments);
            if (fragmentContainer != -1) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(fragmentContainer, wImageFragment);
                fragmentTransaction.commit();
            }
            return wImageFragment;
        }
    }

    private RecyclerView list;
    private CircleImageView selectImageView;
    WImageListAdapter wImageListAdapter;
    List<WimageItem> wimageItems;
    int defaultImageDrawable;
    int defaultImageBorderColor;
    int defaultListImageBorderColor;
    boolean pickFromGalleryEnabled = true;
    boolean pickFromCameraEnabled = true;

    public static WImageFragment newInstance(Bundle arguments) {
        WImageFragment wImageFragment = new WImageFragment();
        wImageFragment.setArguments(arguments);
        return wImageFragment;
    }

    public static Builder with(FragmentManager fragmentManager) {
        return new Builder(fragmentManager);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            initVars(arguments);
        }
    }


    private void initVars(Bundle argument) {
        defaultImageDrawable = argument.getInt(ARG_DEFAULT_IMAGE_DRAWABLE, -1);
        defaultImageBorderColor = argument.getInt(ARG_DEFAULT_IMAGE_BORDER_COLOR, Color.BLACK);
        defaultListImageBorderColor = argument.getInt(ARG_DEFAULT_LIST_IMAGE_BORDER_COLOR, Color.BLACK);
        pickFromCameraEnabled = argument.getBoolean(ARG_PICK_FROM_CAMERA_ENABLED, true);
        pickFromGalleryEnabled = argument.getBoolean(ARG_PICK_FROM_GALLERY_ENABLED, true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wimage_fragment_layout, container, false);
        list = (RecyclerView) view.findViewById(R.id.list);
        selectImageView = (CircleImageView) view.findViewById(R.id.image);
        selectImageView.setImageResource(R.drawable.ic_wimage_camera);
        selectImageView.setBorderColor(Color.BLACK);

        wimageItems = WimageItem.createDefault();
        wImageListAdapter = new WImageListAdapter(wimageItems);
        wImageListAdapter.setOnItemClickListener(new WimageItem.OnItemClickListener() {
            @Override
            public void onItemClick(WimageItem item, WImageListAdapter.SimpleViewHolder holder, int pos) {
                if (item.borderColor == Color.YELLOW) {
                    item.borderColor = Color.RED;
                } else {
                    item.borderColor = Color.YELLOW;
                }
                wImageListAdapter.notifyItemChanged(pos);
            }
        });
        list.setAdapter(wImageListAdapter);
        list.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager.scrollToPosition(Math.round(wImageListAdapter.getItemCount() / 2));
        list.setLayoutManager(linearLayoutManager);
        return view;
    }
}
