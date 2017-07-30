package com.example.apis.ToDoTask;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateTask extends AppCompatActivity implements View.OnClickListener {
    DatabaseConnection con;
    EditText id, name, task_name, task_date;
    Button button_add, button_back, button_date;
    private int mYear, mMonth, mDay;
    Calendar dateTime = Calendar.getInstance();
    DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        con = new DatabaseConnection(this);

        id = (EditText)findViewById(R.id.id_text);
        name = (EditText)findViewById(R.id.name_text);
        task_name = (EditText)findViewById(R.id.task_text);
        task_date = (EditText)findViewById(R.id.date_task);

        button_add = (Button)findViewById(R.id.save_button);
        button_back = (Button)findViewById(R.id.back_button);
        button_date = (Button)findViewById(R.id.date_button);

        button_add.setOnClickListener(this);
        button_back.setOnClickListener(this);
        button_date.setOnClickListener(this);




    }


    @Override
    public void onClick(View v)
    {
        if(v == button_date)
        {
            updateDate();
            updateTime();
            updateTextLabel();
//            final Calendar c = Calendar.getInstance();
//            mYear = c.get(Calendar.YEAR);
//            mMonth = c.get(Calendar.MONTH);
//            mDay = c.get(Calendar.DAY_OF_MONTH);
//
//
//            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
//                    new DatePickerDialog.OnDateSetListener()
//                    {
//                        @Override
//                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
//                        {
//                            task_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//                        }
//                    }, mYear, mMonth, mDay);
//            datePickerDialog.show();
        }
        if(v == button_back)
        {
            finish();
        }
        if(v == button_add)
        {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

            String ID = id.getText().toString(), Name = name.getText().toString(),
                    Task_Name = task_name.getText().toString(), Task_Date = task_date.getText().toString();

            if(TextUtils.isEmpty(ID))
            {
                id.setError("The item ID cannot be empty.");
                return;
            }
            if(TextUtils.isEmpty(Name))
            {
                name.setError("The item name cannot be empty.");
                return;
            }
            if(TextUtils.isEmpty(Task_Name))
            {
                task_name.setError("The item task name cannot be empty.");
                return;
            }
            if(TextUtils.isEmpty(Task_Date))
            {
                task_date.setError("The item date cannot be empty.");
                return;
            }
            try
            {
                SQLiteDatabase db = con.getReadableDatabase();
                db.execSQL("insert into task (id, name, task_name, task_date, task_dateCreated, task_dateUpdated) values ('" +
                        ID +" ' , ' " +
                        Name+" ' , ' " +
                        Task_Name+" ' , ' " +
                        Task_Date+" ' , ' " +
                        currentDateTimeString +" ' , ' " +
                        " " +"')");
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                finish();
                MainActivity.ma.RefreshTask();
                finish();

            }
            catch (SQLException e)
            {
                Toast.makeText(CreateTask.this, "ID already Exits", Toast.LENGTH_SHORT).show();
            }

        }



    }

    private void updateDate()
            {
                new DatePickerDialog(this, d, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();
            }

        private void updateTime()
        {
            new TimePickerDialog(this, t, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), true).show();
        }

        DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, monthOfYear);
            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener()
    {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);

        }
    };

    private void updateTextLabel()
    {
        task_date.setText(formatDateTime.format(dateTime.getTime()));
    }
}
