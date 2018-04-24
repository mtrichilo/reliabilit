package com.reliabilit.reliabilit.performance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.reliabilit.reliabilit.R;
import com.reliabilit.reliabilit.service.PerformanceResult;

import java.util.ArrayList;
import java.util.List;

public class PerformanceActivity extends FragmentActivity {
    private List<PerformanceResult> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);

        this.results = (List<PerformanceResult>) getIntent().getSerializableExtra("results");

        ViewPager viewPager = findViewById(R.id.perf_view_pager);
        viewPager.setAdapter(new PerformancePagerAdapter(getSupportFragmentManager()));
    }

    private class PerformancePagerAdapter extends FragmentPagerAdapter {

        PerformancePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle b = new Bundle();
            Fragment fragment;
            switch (position) {
                case 0:
                    b.putSerializable("results", (ArrayList) results);
                    fragment = new AggregateFragment();
                    fragment.setArguments(b);
                    return fragment;
                default:
                    PerformanceResult previous = position > 1 ? results.get(position - 2) : null;
                    String next = position < getCount() - 1 ? results.get(position).getStation().getName() : "End";
                    b.putSerializable("previous", previous);
                    b.putSerializable("current", results.get(position - 1));
                    b.putString("next", next);
                    fragment = new StationFragment();
                    fragment.setArguments(b);
                    return fragment;
            }
        }

        @Override
        public int getCount() {
            return results.size() + 1;
        }
    }
}
