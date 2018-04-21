package com.hackage.genchildren.nota;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Arrays;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class CreateTask extends AppCompatActivity {

    private Realm mRealm;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        mRealm = Realm.getDefaultInstance();
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                Button whoButton = findViewById(R.id.chooseTargetButton);
                switch(checkedId)
                {
                    case R.id.forSelfRadioButton:
                        whoButton.setVisibility(View.GONE);
                        id = getIntent().getStringExtra("selfNumber");
                        break;
                    case R.id.forOthersRadioButton:
                        whoButton.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    public void chooseWhoOnClick(View view) {

            ArrayList<String> targets = new ArrayList<>();
            RealmResults<Person> persons = mRealm.where(Person.class).findAll();
            for (Person person: persons) {
                if (person.getType() == 2 || person.getType() == 3) {
                    targets.add(person.getFirstName() + " " + person.getLastName());
                }
            }
            final boolean[] checkedItemsArray = new boolean[targets.size()];

            String[] arr = new String[targets.size()];
            for (int i = 0; i < arr.length; ++i) {
                arr[i] = targets.get(i);
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Set targets")
                    .setMultiChoiceItems(arr, checkedItemsArray,
                            new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which, boolean isChecked) {
                                }
                            })
                    .setPositiveButton("Accept",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                }
                            })

                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    dialog.cancel();
                                }
                            });

            builder.create().show();
    }

    public void cancelOnClick(View view) {
        onBackPressed();
    }


    public void addTaskOnClick(View view) {
        EditText taskNameEditText = findViewById(R.id.taskName),
                taskInfoEditText = findViewById(R.id.taskInfo),
                dateEditText = findViewById(R.id.dateEditText),
                timeEdittext = findViewById(R.id.timeEditText);
        CheckBox deadlineCheckBox = findViewById(R.id.deadlineCheckBox);

        String taskName = taskNameEditText.getText().toString(),
                taskInfo = taskInfoEditText.getText().toString(),
                date = dateEditText.getText().toString(),
                time = timeEdittext.getText().toString();
        if (!taskInfo.isEmpty() && !taskName.isEmpty() && ((!date.isEmpty() && !time.isEmpty()) || !deadlineCheckBox.isChecked())) {
            mRealm.beginTransaction();
            Task task = mRealm.createObject(Task.class);
            task.setFromId(id);
            task.setContent(taskInfo);
            if (!date.isEmpty())
                task.setDate(Integer.valueOf(dateEditText.getText().toString()));
            else task.setDate(21042018);
            if (deadlineCheckBox.isChecked())
                task.setDeadline(deadlineCheckBox.isActivated());
            task.setPriority(666);
            task.setIcon(1);
            task.setName(taskName);
            if (!date.isEmpty())
                task.setTime(Integer.valueOf(timeEdittext.getText().toString()));
            else task.setTime(1000);

            RealmResults<Person> user = mRealm.where(Person.class).equalTo("type", 1).findAll();
            user.get(0).getTasks().add(task);

            mRealm.commitTransaction();
            mRealm.close();
            onBackPressed();
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void deadlineCheckOnClick(View view) {
        EditText date = findViewById(R.id.dateEditText), time = findViewById(R.id.timeEditText);
        CheckBox deadline = findViewById(R.id.deadlineCheckBox);
        if (deadline.isChecked()) {
            date.setVisibility(View.VISIBLE);
            time.setVisibility(View.VISIBLE);
        } else {
            date.setVisibility(View.GONE);
            time.setVisibility(View.GONE);
        }
    }
}
