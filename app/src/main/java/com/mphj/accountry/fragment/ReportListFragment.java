package com.mphj.accountry.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.alirezaafkar.sundatepicker.DatePicker;
import com.alirezaafkar.sundatepicker.interfaces.DateSetListener;
import com.mphj.accountry.R;
import com.mphj.accountry.adapter.ReportListAdapter;
import com.mphj.accountry.interfaces.fragment.ReportListView;
import com.mphj.accountry.models.db.Transaction;
import com.mphj.accountry.presenters.fragment.ReportListPresenter;
import com.mphj.accountry.presenters.fragment.ReportListPresenterImpl;
import com.mphj.accountry.utils.ViewUtils;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by mphj on 11/20/17.
 */

public class ReportListFragment extends Fragment implements ReportListView, DateSetListener{

    public static final int REQUEST_FROM_DATE = 1, REQUEST_TO_DATE = 2;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    ReportListAdapter reportListAdapter;

    ReportListPresenter presenter;

    @BindView(R.id.fromDate)
    EditText fromDateET;

    @BindView(R.id.toDate)
    EditText toDateET;

    long fromDate;
    long toDate = Long.MAX_VALUE;

    public ReportListFragment(){

    }

    public static ReportListFragment newInstance(){
        return new ReportListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ReportListPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_list, container, false);
        ButterKnife.bind(this, view);
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
                    .date(Calendar.getInstance())
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
                    .date(Calendar.getInstance())
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
            fromDateET.setText(formattedDate);
            fromDateET.clearFocus();
            fromDate = calendar.getTime().getTime() / 1000l;
        } else {
            toDateET.setText(formattedDate);
            toDateET.clearFocus();
            toDate = calendar.getTime().getTime() / 1000l;
        }
        presenter.loadList(fromDate, toDate);
    }


    @OnClick(R.id.v1)
    void exportReportV1() {
        presenter.exportV1(fromDate, toDate);
    }
}
