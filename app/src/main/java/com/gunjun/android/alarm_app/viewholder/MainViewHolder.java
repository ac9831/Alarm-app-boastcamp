package com.gunjun.android.alarm_app.viewholder;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gunjun.android.alarm_app.AlarmActivity;
import com.gunjun.android.alarm_app.AlarmEditActivity;
import com.gunjun.android.alarm_app.MainActivity;
import com.gunjun.android.alarm_app.R;
import com.gunjun.android.alarm_app.adapter.MainAdapter;
import com.gunjun.android.alarm_app.models.AlarmInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by gunjunLee on 2017-01-26.
 */

public class MainViewHolder extends RecyclerView.ViewHolder {

    private Realm realm;
    private MainAdapter mainAdapter;

    @BindView(R.id.txt_day_week)
    public TextView dayWeek;

    @BindView(R.id.txt_time)
    public TextView time;

    @BindView(R.id.time_divide)
    public TextView timeDivide;

    @BindView(R.id.item_id)
    public TextView itemId;

    @BindView(R.id.txt_memo)
    public TextView txtMemo;

    public MainViewHolder(View itemView, MainAdapter adapter) {
        super(itemView);
        mainAdapter = adapter;
        realm = Realm.getDefaultInstance();
        ButterKnife.bind(this, itemView);

    }


    @OnClick({R.id.edit_image, R.id.delete_image})
    public void onClick(View v) {
        if(v.getId() == R.id.edit_image) {
            editAlarm(v.getContext());
        } else if(v.getId() == R.id.delete_image) {
            deleteAlarm();
        }
    }

    private void editAlarm(Context context) {
        Intent editIntent = new Intent(context, AlarmEditActivity.class);
        editIntent.putExtra("alarm_id", itemId.getText());
        context.startActivity(editIntent);
    }

    private void deleteAlarm() {
        realm.beginTransaction();
        realm.where(AlarmInfo.class).equalTo("id", Integer.parseInt(itemId.getText().toString())).findFirst().deleteFromRealm();
        realm.commitTransaction();
        Log.d("자리",getPosition()+"");
        mainAdapter.notifyItemRemoved(getPosition());
    }

}
