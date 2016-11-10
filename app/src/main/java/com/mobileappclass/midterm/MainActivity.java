package com.mobileappclass.midterm;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> list = new ArrayList<>();
    ArrayList<Integer> times = new ArrayList<>();

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.taskList);

        Fragment newAddFragment = new AddFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newAddFragment);
        transaction.addToBackStack(null);

        transaction.commit();

        if(savedInstanceState != null){
            list = savedInstanceState.getStringArrayList("arrayListTasks");
            times = (ArrayList<Integer>) savedInstanceState.getSerializable("arrayListTime");
            populateList(list);
        }
        else if(fileExists("tasks.txt")){
            Log.i("filewrite", "HERE");
            try {
                Scanner loadListState = new Scanner(openFileInput("ListState1.txt"));

                while (loadListState.hasNextLine()){
                    String[] lineSplit = loadListState.nextLine().split("\t");
                    list.add(lineSplit[0]);
                    times.add(Integer.parseInt(lineSplit[1]));
                }
                populateList(list);
            } catch (FileNotFoundException e) {
                Log.wtf("FileWriter", e);
                e.printStackTrace();
            }
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = list.get(position);
                int time = times.get(position);

                Bundle args = new Bundle();
                args.putString("name", name);
                args.putInt("time", time);

                Fragment newViewFragment = new ViewFragment();
                newViewFragment.setArguments(args);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newViewFragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putStringArrayList("arrayListTasks", list);
        outState.putSerializable("arrayListTime", times);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState != null){
            list = savedInstanceState.getStringArrayList("arrayListTasks");
            times = (ArrayList<Integer>) savedInstanceState.getSerializable("arrayListTime");
            populateList(list);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        try {
            PrintStream output = new PrintStream(openFileOutput("tasks.txt", MODE_PRIVATE));
            for(int i = 0; i < list.size(); i++){
                output.println(list.get(i) + "\t" + times.get(i));
            }
            output.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private boolean fileExists(String fileName){
        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }

    public void setListFromFrag(String[] listFromFrag){
        list.add(listFromFrag[0]);
        times.add(Integer.parseInt(listFromFrag[1]));
        populateList(list);

    }

    public void populateList(ArrayList<String> listToSet){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listToSet);
        listView.setAdapter(adapter);
    }

    public void addClick(View view) {

        Fragment newAddFragment = new AddFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newAddFragment);
        transaction.addToBackStack(null);

        transaction.commit();

    }

    public void startTimer(String time, String position){
        new Timer().execute(time, position);
    }

    private class Timer extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... params) {
            final int timeToSleep = Integer.parseInt(params[0]) * 1000;

            try {
                Thread.sleep(timeToSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return params[1];
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), "Task " + s + " is completed!", Toast.LENGTH_SHORT).show();
            super.onPostExecute(s);
        }
    }
}
