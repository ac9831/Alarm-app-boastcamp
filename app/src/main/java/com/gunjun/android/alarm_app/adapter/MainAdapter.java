package com.gunjun.android.alarm_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gunjun.android.alarm_app.AlarmEditActivity;
import com.gunjun.android.alarm_app.R;
import com.gunjun.android.alarm_app.models.AlarmInfo;
import com.gunjun.android.alarm_app.viewholder.MainViewHolder;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;

/**
 * Created by gunjunLee on 2017-01-25.
 */

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder>{

    public Context context;
    private RealmResults<AlarmInfo> mItems;


    public MainAdapter(RealmResults<AlarmInfo> items, Context mContext) {
        context = mContext;
        mItems = items;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);
        MainViewHolder holder = new MainViewHolder(v, this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.time.setText(mItems.get(position).getHour() < 10 ? "0" : "" + mItems.get(position).getHour() + ":" + (mItems.get(position).getMinute() < 10 ? "0" : "") + mItems.get(position).getMinute());
        holder.timeDivide.setText(mItems.get(position).getTimeDivide() == 0 ? "AM" : "PM");
        holder.dayWeek.setText("(" + mItems.get(position).getWeek() + ")");
        holder.itemId.setText(Integer.toString(mItems.get(position).getId()));
        holder.txtMemo.setText(mItems.get(position).getMemo());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void refresh() {
        notifyDataSetChanged();
    }

}
