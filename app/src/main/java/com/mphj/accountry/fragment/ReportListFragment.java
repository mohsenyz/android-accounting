package com.mphj.accountry.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alirezaafkar.sundatepicker.DatePicker;
import com.alirezaafkar.sundatepicker.interfaces.DateSetListener;
import com.mphj.accountry.R;
import com.mphj.accountry.activity.DashboardActivity;
import com.mphj.accountry.adapter.ReportListAdapter;
import com.mphj.accountry.interfaces.fragment.ReportListView;
import com.mphj.accountry.models.db.Transaction;
import com.mphj.accountry.presenters.fragment.ReportListPresenter;
import com.mphj.accountry.presenters.fragment.ReportListPresenterImpl;
import com.mphj.accountry.utils.ViewUtils;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by mphj on 11/20/17.
 */

public class ReportListFragment extends Fragment implements ReportListView, DateSetListener, DashboardActivity.OnBackPressedListener{

    public static final int REQUEST_FROM_DATE = 1, REQUEST_TO_DATE = 2;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.fromDate)
    EditText fromDateView;

    @BindView(R.id.toDate)
    EditText toDateView;

    @BindView(R.id.report_types)
    CardView reportTypesContainer;

    @BindView(R.id.select_person)
    CardView selectPersonPan;

    @BindView(R.id.nav_bar)
    LinearLayout navBar;

    @BindViews({R.id.reports_value, R.id.reports_product, R.id.reports_category, R.id.reports_customer})
    List<Button> reportTypes;

    Calendar fromDateCal = Calendar.getInstance();
    Calendar toDateCal = Calendar.getInstance();

    ReportListAdapter reportListAdapter;

    ReportListPresenter presenter;

    public ReportListFragment(){

    }

    public static ReportListFragment newInstance(){
        return new ReportListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ReportListPresenterImpl(this);
        Activity activity = getActivity();
        if (activity instanceof DashboardActivity) {
            ((DashboardActivity) activity).setOnBackPressedListener(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_list, container, false);
        ButterKnife.bind(this, view);
        hidePans();
        return view;
    }


    public void setupRecyclerView(){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        setupRecyclerView();
        presenter.onResume();
        presenter.loadList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }


    @OnTouch(R.id.fromDate)
    boolean onRequestFromDate(View view, MotionEvent motionEvent) {
        if (ViewUtils.isInViewBound(view, motionEvent) && motionEvent.getAction() == MotionEvent.ACTION_UP) {
            DatePicker.Builder builder = new DatePicker.Builder()
                    .date(fromDateCal)
                    .id(REQUEST_FROM_DATE)
                    .theme(R.style.DatePickerDialog);
            DatePicker datePicker = builder.build(this);
            datePicker.show(getFragmentManager(), "Request from date");
        }
        return true;
    }

    @OnTouch(R.id.toDate)
    boolean onRequestToDate(View view, MotionEvent motionEvent) {
        if (ViewUtils.isInViewBound(view, motionEvent) && motionEvent.getAction() == MotionEvent.ACTION_UP) {
            DatePicker.Builder builder = new DatePicker.Builder()
                    .date(toDateCal)
                    .id(REQUEST_TO_DATE)
                    .theme(R.style.DatePickerDialog);
            DatePicker datePicker = builder.build(this);
            datePicker.show(getFragmentManager(), "Request to date");
        }
        return true;
    }

    @Override
    public void setAdapter(List<Transaction> checks) {
        reportListAdapter = new ReportListAdapter(checks, getActivity());
        recyclerView.setAdapter(reportListAdapter);
    }

    private String formatDate(int day, int month, int year) {
        return "" + year + "/" + month + "/" + day;
    }

    @Override
    public void onDateSet(int id, @Nullable Calendar calendar, int day, int month, int year) {
        String formattedDate = formatDate(day, month, year);
        if (id == REQUEST_FROM_DATE) {
            fromDateView.setText("از " + formattedDate);
            fromDateView.clearFocus();
            fromDateCal = calendar;
        } else {
            toDateView.setText("تا " + formattedDate);
            toDateView.clearFocus();
            toDateCal = calendar;
        }
        //presenter.loadList(fromDate, toDate);
    }

    Integer reportType = null;

    @OnClick({R.id.reports_value, R.id.reports_product, R.id.reports_category, R.id.reports_customer})
    public void onClickReportType(View view) {
        reportType = view.getId();
        for (Button btn : reportTypes) {
            if (btn.getId() != view.getId()) {
                btn.setVisibility(View.GONE);
            }
        }
        TransitionManager.beginDelayedTransition(navBar);
        switch (reportType) {
            case R.id.reports_customer:
                selectPersonPan.setVisibility(View.VISIBLE);
                break;
        }
    }


    @Override
    public boolean onBackPressed() {
        if (reportType == null)
            return true;

        TransitionManager.beginDelayedTransition(reportTypesContainer);
        for (Button btn : reportTypes) {
                btn.setVisibility(View.VISIBLE);
        }
        hidePans();
        reportType = null;
        return false;
    }


    void hidePans() {
        selectPersonPan.setVisibility(View.GONE);
    }
}
