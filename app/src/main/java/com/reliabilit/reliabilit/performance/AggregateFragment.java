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

import java.util.List;

public class AggregateFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_perf_aggregate, container, false);

        List<PerformanceResult> results = (List<PerformanceResult>) getArguments().getSerializable("results");
        int actualTravel = 0;
        int benchmarkTravel = 0;
        int actualHeadway = 0;
        int benchmarkHeadway = 0;

        for (PerformanceResult result : results) {
            actualTravel += result.getActualTravelTime();
            benchmarkTravel += result.getBenchmarkTravelTime();
            actualHeadway += result.getActualHeadway();
            benchmarkHeadway += result.getBenchmarkHeadway();
        }

        TextView message = view.findViewById(R.id.message);
        if (actualTravel > benchmarkTravel || actualHeadway > benchmarkHeadway) {
            message.setText("Looks like the T was behind schedule.");
        } else {
            message.setText("Everything's on schedule!");
        }

        ((TextView) view.findViewById(R.id.actual)).setText(Integer.toString(actualTravel));
        ((TextView) view.findViewById(R.id.benchmark)).setText(Integer.toString(benchmarkTravel));
        ((TextView) view.findViewById(R.id.actual_headway)).setText(Integer.toString(actualHeadway));
        ((TextView) view.findViewById(R.id.benchmark_headway)).setText(Integer.toString(benchmarkHeadway));

        return view;
    }
}
