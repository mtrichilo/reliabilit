package com.reliabilit.reliabilit.home;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.reliabilit.reliabilit.R;
import com.reliabilit.reliabilit.model.Station;
import com.reliabilit.reliabilit.service.PerformanceResult;
import com.reliabilit.reliabilit.service.StationService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity {
    private Collection<Station> stations;
    private List<PerformanceResult> result;
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

        ((Button) findViewById(R.id.start_date)).setText(dateFormat.format(current));
        ((Button) findViewById(R.id.end_date)).setText(dateFormat.format(current));

        ((Button) findViewById(R.id.start_time)).setText(timeFormat.format(pastHour));
        ((Button) findViewById(R.id.end_time)).setText(timeFormat.format(current));

        StationService stationService = new StationService();
        stationService.fetchStations(() -> this.stations = stationService.getStations());

//        Station[] s = this.stations.toArray(new Station[0]);
//        List<List<Station>> route = stationService.findRoute(s[3], s[40]);
//
//        PerformanceService perfService = new PerformanceService();
//        perfService.fetchPerformance(route, 1524528000000L, 1524531600000L, () ->
//                this.result = perfService.getResult());
//
//        int i = 4 + 4;
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
            Calendar c = new Calendar.Builder().setDate(year, month, day).build();
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
            Calendar c = new Calendar.Builder().setTimeOfDay(hourOfDay, minute, 0).build();
            timeButton.setText(timeFormat.format(c.getTime()));
        }
    }
}
