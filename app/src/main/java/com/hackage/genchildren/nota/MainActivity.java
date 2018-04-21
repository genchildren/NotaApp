package com.hackage.genchildren.nota;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import database.App;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Task> tasks;
    private RecyclerView rv;
    private String selfNumber;
    private HashMap<Integer, String> map = new HashMap<>();
    private CustomRecyclerAdapter adapter;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

        realm = Realm.getDefaultInstance();

        if (realm.isEmpty()) {
            Intent toLoginIntent = new Intent(this, LoginActivity.class);
            startActivityForResult(toLoginIntent, 0);
        }
        setNavigationViewListener();

        selfNumber = realm.where(Person.class).equalTo("type", 1).findFirst().getPhoneNumber();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toCreateTaskIntent = new Intent(MainActivity.this, CreateTask.class);
                toCreateTaskIntent.putExtra("selfNumber", selfNumber);
                startActivity(toCreateTaskIntent);
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        RealmResults<Person> persons = realm.where(Person.class).findAll();
        int i = 4;
        for (Person person : persons) {
            menu.add(person.getFirstName() + " " + person.getLastName());
            map.put(menu.getItem(i).getItemId(), person.getPhoneNumber());
        }
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        tasks = new ArrayList<>();

        rv = findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapter = new CustomRecyclerAdapter(tasks);
        rv.setAdapter(adapter);
        RealmList<Task> res = realm.where(Person.class).equalTo("phoneNumber", selfNumber).findFirst().getTasks();
        for (Task elem : res) {
            tasks.add(elem);
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int n = realm.where(Person.class).findAll().size();
        SimpleDateFormat localeDateFormat = new SimpleDateFormat("dd MM yyyy HH mm");
        String date = localeDateFormat.format(new Date());
        String[] arr = date.split(" ");

        int day = Integer.valueOf(arr[0]);
        int month = Integer.valueOf(arr[1]);
        int year = Integer.valueOf(arr[2]);
        LinearLayoutManager layoutManager;
        RealmList<Task> res;

        switch (item.getItemId()) {
            case (R.id.add_user):
                Intent toAddUser = new Intent(this, AddUserActivity.class);
                startActivityForResult(toAddUser, 0);
                break;
            case (R.id.todayTasks):
                tasks = new ArrayList<>();

                rv = findViewById(R.id.rv);
                layoutManager = new LinearLayoutManager(this);
                rv.setLayoutManager(layoutManager);
                adapter = new CustomRecyclerAdapter(tasks);
                rv.setAdapter(adapter);
                res = realm.where(Person.class).equalTo("phoneNumber", selfNumber).findFirst().getTasks();
                for (Task elem : res) {
                    if (elem.getDate() / 1000000 == day && (elem.getDate()/10000)%100 == month && elem.getDate()%10000 == year) {
                        tasks.add(elem);
                    }
                }
                adapter.notifyDataSetChanged();

                break;
            case (R.id.weekTasks):
                tasks = new ArrayList<>();

                rv = findViewById(R.id.rv);
                layoutManager = new LinearLayoutManager(this);
                rv.setLayoutManager(layoutManager);
                adapter = new CustomRecyclerAdapter(tasks);
                rv.setAdapter(adapter);
                res = realm.where(Person.class).equalTo("phoneNumber", selfNumber).findFirst().getTasks();
                for (Task elem : res) {
                    if (elem.getDate() / 1000000 + 7 >= day && (elem.getDate()/10000)%100 == month && elem.getDate()%10000 == year) {
                        tasks.add(elem);
                    }
                }
                adapter.notifyDataSetChanged();

                break;
            case (R.id.monthTasks):
                tasks = new ArrayList<>();

                rv = findViewById(R.id.rv);
                layoutManager = new LinearLayoutManager(this);
                rv.setLayoutManager(layoutManager);
                adapter = new CustomRecyclerAdapter(tasks);
                rv.setAdapter(adapter);
                res = realm.where(Person.class).equalTo("phoneNumber", selfNumber).findFirst().getTasks();
                for (Task elem : res) {
                    if ((elem.getDate()/10000)%100 == month && elem.getDate()%10000 == year) {
                        tasks.add(elem);
                    }
                }
                adapter.notifyDataSetChanged();
                break;
            default:
                int id = item.getItemId();
                String number = map.get(id);
                tasks = new ArrayList<>();
                rv = findViewById(R.id.rv);
                layoutManager = new LinearLayoutManager(this);
                rv.setLayoutManager(layoutManager);
                adapter = new CustomRecyclerAdapter(tasks);
                rv.setAdapter(adapter);
                RealmList<Task> t = realm.where(Person.class).equalTo("phoneNumber", number).findFirst().getTasks();
                for (Task task: t) {
                    tasks.add(task);
                }
                adapter.notifyDataSetChanged();
                break;

        }
        return true;
    }



        @Override
        protected void onActivityResult( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 0) {
                NavigationView navigationView = findViewById(R.id.nav_view);
                Menu menu = navigationView.getMenu();
                Person person = realm.where(Person.class).findAll().last();
                menu.add(person.getFirstName() + " " + person.getLastName());
            }
        }

}
