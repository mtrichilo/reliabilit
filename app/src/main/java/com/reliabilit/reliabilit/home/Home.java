package com.reliabilit.reliabilit.home;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.reliabilit.reliabilit.R;
import com.reliabilit.reliabilit.service.SubwayStationService;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        new GetRoutesTask().execute();

//        RecyclerView recyclerView = findViewById(R.id.homeRecycler);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(new LineAdapter(this));
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private class GetRoutesTask extends AsyncTask<String, Integer, List<String>> {
        @Override
        protected List<String> doInBackground(String... strings) {
            List<String> routes = new ArrayList<>();
            new SubwayStationService().getRoutes().forEach(r -> routes.add(r.getName()));
            return routes;
        }

        @Override
        protected void onPostExecute(List<String> routes) {
            Spinner s = findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Home.this, android.R.layout.simple_spinner_item, routes);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(adapter);
        }
    }
}
