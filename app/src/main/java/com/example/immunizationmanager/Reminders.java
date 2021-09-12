package com.example.immunizationmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.allyants.notifyme.NotifyMe;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class Reminders extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    Calendar calendar=Calendar.getInstance();
    TimePickerDialog tpd;
    DatePickerDialog dpt;
    EditText notName,notContent;
    Button  cancel,button;


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        tpd.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);


    }
    NotifyMe notifyMe= new NotifyMe.Builder(getApplicationContext())
            .title(notName.getText().toString())
            .content(notContent.getText().toString())
            .color(225,0,0,225)
            .led_color(225,225,225,225)
            .time(calendar)
            .addAction(new Intent(),"Snooze",false)
            .key("test")
            .addAction(new Intent(),"Dismiss",true,false)
            .addAction(new Intent(),"Done")
            .large_icon(R.mipmap.ic_launcher_round)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vaccine);
        
        notName=findViewById(R.id.notificationName);
        notContent=findViewById(R.id.notificationContent);
        cancel=findViewById(R.id.notificationCancel);
        button=findViewById(R.id.notification);

        dpt=DatePickerDialog.newInstance(
                Reminders.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotifyMe.cancel(getApplicationContext(),"test");
            }
        });
        
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), ChildInformation.class);
        startActivity(intent);
        finish();
    }


}