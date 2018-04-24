package com.reliabilit.reliabilit.performance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reliabilit.reliabilit.R;
import com.reliabilit.reliabilit.service.PerformanceResult;

public class StationFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_perf_station, container, false);

        PerformanceResult previous = (PerformanceResult) getArguments().getSerializable("previous");
        PerformanceResult current = (PerformanceResult) getArguments().getSerializable("current");
        String next = getArguments().getString("next");

        ((TextView) view.findViewById(R.id.current_header)).setText(current.getStation().getName());
        ((TextView) view.findViewById(R.id.current)).setText(current.getStation().getName());
        if (previous != null) {
            ((TextView) view.findViewById(R.id.previous)).setText(previous.getStation().getName());
        } else {
            ((TextView) view.findViewById(R.id.previous)).setText("Start");
        }
        ((TextView) view.findViewById(R.id.next)).setText(next);

        if (previous != null) {
            ((TextView) view.findViewById(R.id.actual_prev_current)).setText(Integer.toString(previous.getActualTravelTime()));
            ((TextView) view.findViewById(R.id.benchmark_prev_current)).setText(Integer.toString(previous.getBenchmarkTravelTime()));
        }
        ((TextView) view.findViewById(R.id.actual_current_next)).setText(Integer.toString(current.getActualTravelTime()));
        ((TextView) view.findViewById(R.id.benchmark_current_next)).setText(Integer.toString(current.getBenchmarkTravelTime()));

        return view;
    }
}
