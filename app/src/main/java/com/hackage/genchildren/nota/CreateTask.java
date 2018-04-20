package com.hackage.genchildren.nota;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import io.realm.Realm;
import io.realm.RealmObject;

public class CreateTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
    }

    public void chooseWhoOnClick(View view) {
      //TODO make who list
    }

    public void cancelOnClick(View view) {
        onBackPressed();
    }


    public void addTaskOnClick(View view) {
        Intent inIntent = getIntent();
        EditText taskNameEditText = findViewById(R.id.taskName),
                taskInfoEditText = findViewById(R.id.taskInfo);

        // Получение значений полей введенных пользователем
        String taskName = taskNameEditText.getText().toString(),
                taskInfo = taskInfoEditText.getText().toString();

        Task task = new Task();
        task.setFromId(0);
        task.setContent("kupi doshik");
        task.setData(12);
        task.setDeadline(true);
        task.setPriority(666);
        task.setIcon(1);
        task.setName("Mission");
        task.setTime(1);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final Task managedTask = realm.copyToRealm(task);
        Person person = realm.createObject(Person.class);
        realm.commitTransaction();
    }
}
