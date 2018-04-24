package com.reliabilit.reliabilit.home;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.TimePicker;

import com.reliabilit.reliabilit.R;
import com.reliabilit.reliabilit.model.Station;
import com.reliabilit.reliabilit.performance.PerformanceActivity;
import com.reliabilit.reliabilit.service.PerformanceResult;
import com.reliabilit.reliabilit.service.PerformanceService;
import com.reliabilit.reliabilit.service.StationService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity {
    StationService stationService;
    private Collection<Station> stations = new ArrayList<>();
    private static DateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.US);
    private static DateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Calendar c = Calendar.getInstance();
        Date current = c.getTime();
        c.add(Calendar.HOUR, -1);
        Date pastHour = c.getTime();

        ((Button) findViewById(R.id.start_date)).setText(dateFormat.format(pastHour));
        ((Button) findViewById(R.id.end_date)).setText(dateFormat.format(current));

        ((Button) findViewById(R.id.start_time)).setText(timeFormat.format(pastHour));
        ((Button) findViewById(R.id.end_time)).setText(timeFormat.format(current));

        AutoCompleteTextView origin = findViewById(R.id.origin);
        AutoCompleteTextView destination = findViewById(R.id.destination);
        StationAdapter oAdapter = new StationAdapter(this, this.stations);
        StationAdapter dAdapter = new StationAdapter(this, this.stations);
        origin.setAdapter(oAdapter);
        destination.setAdapter(dAdapter);
        origin.setOnItemClickListener(oAdapter);
        destination.setOnItemClickListener(dAdapter);

        this.stationService = new StationService();
        this.stationService.fetchStations(() -> this.stations.addAll(this.stationService.getStations()));
    }

    public void onGoClick(View view) {
        AutoCompleteTextView origin = findViewById(R.id.origin);
        AutoCompleteTextView destination = findViewById(R.id.destination);
        Station o = ((StationAdapter) origin.getAdapter()).getSelected();
        Station d = ((StationAdapter) destination.getAdapter()).getSelected();

        List<List<Station>> route = stationService.findRoute(o, d);

        Date startDate = Calendar.getInstance().getTime();
        Date endDate = Calendar.getInstance().getTime();
        Date startTime = Calendar.getInstance().getTime();
        Date endTime = Calendar.getInstance().getTime();
        try {
            startDate = dateFormat.parse(((Button) findViewById(R.id.start_date)).getText().toString());
            endDate = dateFormat.parse(((Button) findViewById(R.id.end_date)).getText().toString());

            startTime = timeFormat.parse(((Button) findViewById(R.id.start_time)).getText().toString());
            endTime = timeFormat.parse(((Button) findViewById(R.id.end_time)).getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long start = (startDate.getTime() + startTime.getTime()) / 1000;
        long end = (endDate.getTime() + endTime.getTime()) / 1000;

        PerformanceService service = new PerformanceService();
        service.fetchPerformance(route, start, end, () -> {
            Intent i = new Intent(this, PerformanceActivity.class);
            i.putExtra("results", (ArrayList) service.getResult());
            startActivity(i);
        });
    }

    public void showDatePickerDialog(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("viewId", view.getId());
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(bundle);
        fragment.show(getFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("viewId", view.getId());
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(bundle);
        fragment.show(getFragmentManager(), "timePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            int viewId = getArguments().getInt("viewId");
            Button dateButton = getActivity().findViewById(viewId);
            Calendar c = Calendar.getInstance();
            c.set(year, month, day, 0,0, 0);
            dateButton.setText(dateFormat.format(c.getTime()));
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute, false);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            int viewId = getArguments().getInt("viewId");
            Button timeButton = getActivity().findViewById(viewId);
            Calendar c = Calendar.getInstance();
            c.set(1970, 1, 1, hourOfDay, minute, 0);
            timeButton.setText(timeFormat.format(c.getTime()));
        }
    }
}
