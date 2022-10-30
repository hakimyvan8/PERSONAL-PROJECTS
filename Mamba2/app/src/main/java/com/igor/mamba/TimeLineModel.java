package com.igor.mamba;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.github.vipulasri.timelineview.TimelineView;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimeLineModel extends RecyclerView.Adapter<TimeLineModel.TimeLineViewHolder> {

    private List<timeModel> tmodels;
    private Context context;

    public TimeLineModel(List<timeModel> theList, Context context) {

        this.tmodels = theList;
        this.context = context;
    }
    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_timeline, null);
        return new TimeLineViewHolder(view, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, int position) {

        final timeModel timeModel = tmodels.get(position);


        holder.Date.setText(String.valueOf(timeModel.getDate()));
        holder.message.setText(timeModel.getMessageString());
        holder.imageView.setImageIcon(timeModel.getImageIcon());

            if(timeModel.getStatus() == OrderStatus.ACTIVE){

                holder.mTimelineView.setMarkerColor(Color.rgb(255,140,0));
                holder.mTimelineView.setStartLineColor(Color.rgb(255,140,0),0);
                holder.mTimelineView.setEndLineColor(Color.rgb(255,140,0),0);
                holder.Date.setTextColor(Color.rgb(255,140,0));
                holder.message.setTextColor(Color.rgb(255,140,0));
                holder.mTimelineView.setMarkerPaddingBottom(10);
                holder.imageView.setColorFilter(Color.rgb(255,140,0));
            }
            if(timeModel.getStatus() == OrderStatus.INACTIVE){

                holder.mTimelineView.setMarkerColor(Color.GRAY);
                holder.mTimelineView.setStartLineColor(Color.GRAY,0);
                holder.mTimelineView.setEndLineColor(Color.GRAY,0);
                holder.Date.setTextColor(Color.GRAY);
                holder.message.setTextColor(Color.GRAY);
                holder.mTimelineView.setMarkerPaddingBottom(10);
                holder.imageView.setColorFilter(Color.GRAY);
            }
    }

    @Override
    public int getItemCount() {

        return tmodels.size();
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {
        public TimelineView mTimelineView;
        TextView Date;
        TextView message;
       ImageView imageView;

       MaterialCardView materialCardView;
        public TimeLineViewHolder(View itemView, int viewType) {
            super(itemView);
            mTimelineView = itemView.findViewById(R.id.timeline);

            imageView = itemView.findViewById(R.id.image);
             Date = itemView.findViewById(R.id.text_timeline_date);
            message = itemView.findViewById(R.id.text_timeline_title);

            materialCardView =itemView.findViewById(R.id.backggg);
            mTimelineView.initLine(viewType);




        }
    }
}