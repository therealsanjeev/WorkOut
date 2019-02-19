package com.android.workout.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.workout.R;
import com.android.workout.adapters.ReminderAdapter;
import com.android.workout.model.Reminder;

import java.util.ArrayList;
import java.util.List;

public class ReminderActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    TimePicker analogClock;
    Button btn_close, btn_ok, btn_cancel_days, btn_ok_days;
    String hour = "";
    String minute = "";
    String am_pm = "";
    CheckBox checkBox_sunday, checkBox_tuesday, checkBox_monday, checkBox_wednesday, checkBox_thursday, checkBox_friday, checkBox_saturday;
    ArrayList<String> arrayList;
    RecyclerView recyclerView;
    private ReminderAdapter adapter;
    private List<Reminder> reminderList;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        floatingActionButton = findViewById(R.id.fab);
        textView = findViewById(R.id.textView2);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        progressDialog = new ProgressDialog(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        reminderList = new ArrayList<>();


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePopupWindow();
            }
        });
    }

    private PopupWindow pwindo;
    private PopupWindow pwindow_days;
    private void initiatePopupWindow()
    {
        try
        {
            // We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) ReminderActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.screen_popup,(ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, 620, 830, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            analogClock = layout.findViewById(R.id.timePicker);
            analogClock.setIs24HourView(true);
            btn_close = layout.findViewById(R.id.cancel);
            btn_ok = layout.findViewById(R.id.ok);
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        hour = analogClock.getHour()+"";
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        minute = analogClock.getMinute()+"";
                    }
                    Toast.makeText(layout.getContext(),hour+":"+minute,Toast.LENGTH_SHORT).show();
                    initiatePopupWindow_days();
                }
            });
            btn_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pwindo.dismiss();
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void initiatePopupWindow_days() {
        pwindo.dismiss();
        arrayList = new ArrayList<String>();
        LayoutInflater inflater = (LayoutInflater) ReminderActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.days,(ViewGroup) findViewById(R.id.popup_element));
        pwindow_days = new PopupWindow(layout, 630, 830, true);
        pwindow_days.showAtLocation(layout, Gravity.CENTER, 0, 0);
        checkBox_sunday = layout.findViewById(R.id.sunday);
        checkBox_monday = layout.findViewById(R.id.monday);
        checkBox_tuesday = layout.findViewById(R.id.tuesday);
        checkBox_wednesday = layout.findViewById(R.id.wednesday);
        checkBox_thursday = layout.findViewById(R.id.thursday);
        checkBox_friday = layout.findViewById(R.id.friday);
        checkBox_saturday = layout.findViewById(R.id.saturday);
        btn_ok_days = layout.findViewById(R.id.ok_days);
        btn_cancel_days = layout.findViewById(R.id.cancel_days);
        if (checkBox_sunday.isChecked())
        {
            arrayList.add("Sunday");
        }
        if (checkBox_sunday.isChecked())
        {
            arrayList.add("Monday");
        }
        if (checkBox_sunday.isChecked())
        {
            arrayList.add("Tuesday");
        }
        if (checkBox_sunday.isChecked())
        {
            arrayList.add("Wednesday");
        }
        if (checkBox_sunday.isChecked())
        {
            arrayList.add("Thursday");
        }
        if (checkBox_sunday.isChecked())
        {
            arrayList.add("Friday");
        }
        if (checkBox_sunday.isChecked())
        {
            arrayList.add("Saturday");
        }
        btn_ok_days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.INVISIBLE);
                Reminder reminder = new Reminder(hour+":"+minute,"Monday"+"");
                reminderList.add(reminder);
//                adapter = new ReminderAdapter(getApplicationContext(), reminderList);
                adapter = new ReminderAdapter(getApplicationContext(),reminderList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Toast.makeText(layout.getContext(),arrayList+"",Toast.LENGTH_LONG).show();
                pwindow_days.dismiss();
            }
        });
        btn_cancel_days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwindow_days.dismiss();
            }
        });
    }
}