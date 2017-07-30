package com.example.apis.ToDoTask;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
{
    DatabaseConnection con;
    ListView listTasks;
    String[] tasks;
    Cursor cursor;
    public static MainActivity ma;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.table);
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this, CreateTask.class);
                startActivity(i);
            }
        });
        ma = this;
        con = new DatabaseConnection(this);
        RefreshTask();
    }

    public void RefreshTask()
    {
        SQLiteDatabase db = con.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM task", null);
        tasks = new String[cursor.getCount()];
        cursor.moveToFirst();

        for (int x = 0; x < cursor.getCount(); x++)
        {
            cursor.moveToPosition(x);
            tasks[x] = cursor.getString(2).toString();

        }


        listTasks = (ListView) findViewById(R.id.listTask);
        listTasks.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, tasks));
        listTasks.setSelected(true);
        listTasks.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3)
            {
                final String selection = tasks[arg2];
                final CharSequence[] option = {"View Task", "Update Task", "Delete Task"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Option");
                builder.setItems(option, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int option)
                    {
                        switch (option)
                        {
                            case 0:
                                Intent i = new Intent(getApplicationContext(), TaskDetails.class);
                                i.putExtra("task_name", selection);
                                startActivity(i);
                                break;
                            case 1:
                                Intent in = new Intent(getApplicationContext(), UpdateTask.class);
                                in.putExtra("task_name", selection);
                                startActivity(in);
                                break;
                            case 2:
                                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        switch (which)
                                        {
                                            case DialogInterface.BUTTON_POSITIVE:
                                                SQLiteDatabase db = con.getWritableDatabase();
                                                db.execSQL("delete from task where task_name = '" + selection + "'");
                                                RefreshTask();
                                                break;

                                            case DialogInterface.BUTTON_NEGATIVE:

                                                break;
                                        }
                                    }
                                };
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("Are you sure want to delete the selected task?").setPositiveButton("Yes", dialogClickListener)
                                        .setNegativeButton("No", dialogClickListener).show();

                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter) listTasks.getAdapter()).notifyDataSetInvalidated();


    }
}
