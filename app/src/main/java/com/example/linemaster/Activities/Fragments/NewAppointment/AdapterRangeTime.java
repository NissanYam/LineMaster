package com.example.linemaster.Activities.Fragments.NewAppointment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.linemaster.Data.TimeRange;
import com.example.linemaster.MySignal;
import com.example.linemaster.R;

import java.util.List;

public class AdapterRangeTime extends RecyclerView.Adapter<AdapterRangeTime.RangeTimeViewHolder> {
    private List<TimeRange> timeRanges;
    private OnTimeRangeClickListener onTimeRangeClickListener;
    private TimeRange timeRangeClicked = null;
    private RangeTimeViewHolder holderClicked = null;

    public AdapterRangeTime() {
    }

    public List<TimeRange> getTimeRanges() {
        return timeRanges;
    }

    public AdapterRangeTime setTimeRanges(List<TimeRange> timeRanges) {
        this.timeRanges = timeRanges;
        return this;
    }

    public OnTimeRangeClickListener getOnTimeRangeClickListener() {
        return onTimeRangeClickListener;
    }

    public AdapterRangeTime setOnTimeRangeClickListener(OnTimeRangeClickListener onTimeRangeClickListener) {
        this.onTimeRangeClickListener = onTimeRangeClickListener;
        return this;
    }

    @NonNull
    @Override
    public RangeTimeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.range_time_item, viewGroup, false);
        return new RangeTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RangeTimeViewHolder holder, int position) {
        TimeRange timeRange = getItem(position);
        holder.setTimeRange(timeRange);
        holder.range_time_text.setText
                (MySignal.getInstance()
                        .formatTime(timeRange.getStartHour(), timeRange.getStartMinutes())
                        .concat(" - ")
                        .concat(MySignal.getInstance().formatTime(timeRange.getEndHour(), timeRange.getEndMinutes())));

        if (timeRangeClicked != null) {
            if (timeRangeClicked.equals(timeRange)) {
                holder.range_time_item.setBackgroundResource(R.drawable.editback3);
                holder.range_time_text.setTextColor(ContextCompat.getColor(holder.itemView, R.color.white));
            } else {
                holder.range_time_item.setBackgroundResource(R.drawable.editback2);
                holder.range_time_text.setTextColor(ContextCompat.getColor(holder.itemView, R.color.black));
            }
        }
    }

    @Override
    public int getItemCount() {
        return timeRanges == null ? 0 : timeRanges.size();
    }

    private TimeRange getItem(int position) {
        return timeRanges.get(position);
    }

    public void clear() {
        timeRanges = null;
        timeRangeClicked = null;
        holderClicked = null;
    }

    public interface OnTimeRangeClickListener {
        void timeRangeClick(View v, TimeRange item, int adapterPosition);
    }

    public class RangeTimeViewHolder extends RecyclerView.ViewHolder {
        private TimeRange timeRange;
        private TextView range_time_text;
        private LinearLayout range_time_item;
        private Context itemView;
        public RangeTimeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView.getContext();
            findViews(itemView);
            initViews();
        }

        public RangeTimeViewHolder setTimeRange(TimeRange timeRange) {
            this.timeRange = timeRange;
            return this;
        }

        private void findViews(View itemView) {
            range_time_text = itemView.findViewById(R.id.range_time_text);
            range_time_item = itemView.findViewById(R.id.range_time_item);
        }

        private RangeTimeViewHolder getInstance() {
            return this;
        }

        private void initViews() {

            range_time_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holderClicked != null){
                        holderClicked.range_time_item.setBackgroundResource(R.drawable.editback2);
                        holderClicked.range_time_text.setTextColor(ContextCompat.getColor(v.getContext(), R.color.black));
                    }
                    holderClicked = getInstance();
                    timeRangeClicked = timeRange;
                    holderClicked.range_time_item.setBackgroundResource(R.drawable.editback3);
                    holderClicked.range_time_text.setTextColor(ContextCompat.getColor(v.getContext(), R.color.white));
                    onTimeRangeClickListener.timeRangeClick(v, getItem(getAdapterPosition()), getAdapterPosition());
                }
            });
        }


    }
}
