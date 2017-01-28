package com.gunjun.android.alarm_app;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.gunjun.android.alarm_app.models.AlarmInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmQuery;

public class AlarmDetailActivity extends AppCompatActivity {

    @BindView(R.id.txt_day_week_detail)
    public TextView dayWeek;

    @BindView(R.id.txt_time_detail)
    public TextView time;

    @BindView(R.id.time_divide_detail)
    public TextView timeDivide;

    @BindView(R.id.txt_memo_detail)
    public TextView txtMemo;

    private Realm realm;
    private AlarmInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_detail);
        realm = Realm.getDefaultInstance();
        ButterKnife.bind(this);
        Toolbar toolbarView = (Toolbar) this.findViewById(R.id.toolbar);

        if (null != toolbarView) {
            this.setSupportActionBar(toolbarView);

            this.getSupportActionBar().setDisplayShowTitleEnabled(false);
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        viewInfo();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void viewInfo() {
        Intent editIntent = getIntent();
        RealmQuery<AlarmInfo> query = realm.where(AlarmInfo.class).equalTo("id", editIntent.getIntExtra("alarm_id", -1));
        info = query.findFirst();
        dayWeek.setText("(" + info.getWeek() + ")");
        time.setText((info.getHour() < 10 ? "0" : "") + info.getHour() + ":" + (info.getMinute() < 10 ? "0" : "") + info.getMinute());
        timeDivide.setText(info.getTimeDivide() == 0 ? "AM" : "PM");
        txtMemo.setText(info.getMemo());
    }



}
