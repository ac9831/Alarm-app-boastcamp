package com.gunjun.android.alarm_app.adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
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
import com.gunjun.android.alarm_app.receiver.BootReceiver;
import com.gunjun.android.alarm_app.viewholder.MainOnClickListener;
import com.gunjun.android.alarm_app.viewholder.MainViewHolder;

import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by gunjunLee on 2017-01-25.
 */

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder>{

    private Context context;
    private RealmResults<AlarmInfo> mItems;
    private Realm mRealm;
    private final View.OnClickListener mOnClickListener = new MainOnClickListener();


    public MainAdapter(RealmResults<AlarmInfo> items, Context mContext, Realm realm) {
        context = mContext;
        mItems = items;
        mRealm = realm;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);
        v.setOnClickListener(mOnClickListener);
        MainViewHolder holder = new MainViewHolder(v, this, context, mRealm);
        return holder;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.time.setText((mItems.get(position).getHour() < 10 ? "0" : "") + mItems.get(position).getHour() + ":" + (mItems.get(position).getMinute() < 10 ? "0" : "") + mItems.get(position).getMinute());
        holder.timeDivide.setText(mItems.get(position).getTimeDivide() == 0 ? "AM" : "PM");
        holder.dayWeek.setText("(" + mItems.get(position).getWeek() + ")");
        holder.itemId.setText(Integer.toString(mItems.get(position).getId()));
        holder.txtMemo.setText(mItems.get(position).getMemo());
        String[] week_txt = {"월", "화", "수", "목", "금", "토", "일"};
        Boolean weeks = false;

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void registerAlarm(Context context, AlarmInfo info, boolean F) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Calendar curTime = Calendar.getInstance();

        curTime.set(Calendar.HOUR, info.getHour());
        curTime.set(Calendar.MINUTE, info.getMinute());
        curTime.set(Calendar.SECOND, 0);
        curTime.set(Calendar.AM_PM, info.getTimeDivide());

        Long alarmTime = curTime.getTimeInMillis();

        Intent intent = new Intent(context, BootReceiver.class);
        intent.putExtra("id",info.getId());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, info.getId(), intent, 0);

        if(F) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarmTime, 24*60*60*1000, pIntent );
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pIntent);
        }
    }
}
