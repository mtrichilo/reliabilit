package com.reliabilit.reliabilit.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.reliabilit.reliabilit.model.Station;

import java.util.ArrayList;
import java.util.List;

public class StationAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<Station> stationList;
    private List<Station> filteredList;

    public StationAdapter(Context context, List<Station> stationList) {
        this.context = context;
        this.stationList = stationList;
        this.filteredList = stationList;
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view;
        if (convertView != null) {
            view = (TextView) convertView;
        } else {
            view = new TextView(context);
        }

        view.setText(filteredList.get(position).getName());
        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint == null) {
                    return results;
                }

                List<Station> matches = new ArrayList<>();
                for (Station station : stationList) {
                    if (station.getName().contains(constraint)) {
                        matches.add(station);
                    }
                }

                results.values = matches;
                results.count = matches.size();
                return results;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.values != null) {
                    filteredList = (List<Station>) results.values;
                    notifyDataSetChanged();
                }
            }
        };
    }
}