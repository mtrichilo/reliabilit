package com.reliabilit.reliabilit.home;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reliabilit.reliabilit.R;
import com.reliabilit.reliabilit.model.Line;
import com.reliabilit.reliabilit.model.RailLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LineAdapter extends RecyclerView.Adapter<LineAdapter.ViewHolder> {
    private Context context;
    private List<RailLine> lines;

    LineAdapter(Context context) {
        this.context = context;
        this.lines = new ArrayList<>();
        for(Line line : Line.getAll()) {
            this.lines.add(new RailLine(line));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_home_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RailLine railLine = this.lines.get(position);

        TextView lineName = holder.itemView.findViewById(R.id.homeLineName);
        lineName.setText(railLine.getLine().getText());
        lineName.setTextColor(ContextCompat.getColor(context, getColor(railLine.getLine())));

        TextView reliability = holder.itemView.findViewById(R.id.homeReliability);
        reliability.setText(String.format(Locale.US, "%.2f", railLine.getReliability()));
    }

    @Override
    public int getItemCount() {
        return this.lines.size();
    }

    private int getColor(Line line) {
        switch (line) {
            case GREEN_B:
            case GREEN_C:
            case GREEN_D:
            case GREEN_E:
                return R.color.greenLine;
            case RED:
                return R.color.redLine;
            case ORANGE:
                return R.color.orangeLine;
            case BLUE:
                return R.color.blueLine;
        }
        return Color.WHITE;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View view) {
            super(view);
        }
    }
}
