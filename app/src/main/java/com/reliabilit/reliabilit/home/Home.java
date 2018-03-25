package com.reliabilit.reliabilit.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.reliabilit.reliabilit.R;
import com.reliabilit.reliabilit.service.SubwayStationService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    private static List<String> stations;
    private static ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        stations = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stations);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        new GetRoutesTask().execute();

        AutoCompleteTextView origin = findViewById(R.id.origin);
        AutoCompleteTextView destination = findViewById(R.id.destination);
        origin.setAdapter(adapter);
        destination.setAdapter(adapter);
    }

    private static class GetRoutesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                stations.addAll(new SubwayStationService().getStationNames());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            adapter.notifyDataSetChanged();
        }
    }
}
