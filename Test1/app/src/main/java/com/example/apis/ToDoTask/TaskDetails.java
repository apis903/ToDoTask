package com.example.apis.ToDoTask;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TaskDetails extends AppCompatActivity implements View.OnClickListener {
    TextView id, name, task, taskD, taskC, taskU;
    Button back;
    DatabaseConnection con;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        con = new DatabaseConnection(this);

        id = (TextView) findViewById(R.id.id);
        name = (TextView)findViewById(R.id.name);
        task = (TextView)findViewById(R.id.task);
        taskD = (TextView)findViewById(R.id.date_task);
        taskC = (TextView)findViewById(R.id.date_create);
        taskU = (TextView)findViewById(R.id.date_update);

        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(this);



        SQLiteDatabase db = con.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM task WHERE task_name = '" +
                            getIntent().getStringExtra("task_name") +"'", null);
        cursor.moveToFirst();
        if(cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            id.setText(cursor.getString(0).toString());
            name.setText(cursor.getString(1).toString());
            task.setText(cursor.getString(2).toString());
            taskD.setText(cursor.getString(3).toString());
            taskC.setText(cursor.getString(4).toString());
            taskU.setText(cursor.getString(5).toString());
        }
    }

    @Override
    public void onClick(View v)
    {
        if(v == back)
        {
            finish();
        }
    }
}
