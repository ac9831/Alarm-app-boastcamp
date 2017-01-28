package com.gunjun.android.alarm_app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.gunjun.android.alarm_app.models.AlarmInfo;
import com.gunjun.android.alarm_app.receiver.BootReceiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmQuery;

public class AlarmEditActivity extends AppCompatActivity {

    @BindView(R.id.divide_picker)
    NumberPicker pickerDivide;

    @BindView(R.id.hour_picker)
    NumberPicker pickerHour;

    @BindView(R.id.minute_picker)
    NumberPicker pickerMinute;

    @BindView(R.id.edit_memo)
    TextView txtMemo;

    @BindView(R.id.sunday)
    ToggleButton sunBut;

    @BindView(R.id.monday)
    ToggleButton monBut;

    @BindView(R.id.tuesday)
    ToggleButton tuesBut;

    @BindView(R.id.wednesday)
    ToggleButton wedBut;

    @BindView(R.id.thursday)
    ToggleButton thursBut;

    @BindView(R.id.friday)
    ToggleButton friBut;

    @BindView(R.id.saturday)
    ToggleButton saturBut;

    @BindView(R.id.week_txt)
    TextView weekTxt;

    private Realm realm;
    private AlarmInfo info;
    private int[] week = new int[7];
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_edit);
        realm = Realm.getDefaultInstance();
        ButterKnife.bind(this);

        pickerDivide.setMinValue(0);
        pickerDivide.setMaxValue(1);
        pickerDivide.setWrapSelectorWheel(false);
        pickerDivide.setDisplayedValues(new String[]{"AM","PM"});

        pickerHour.setMinValue(0);
        pickerHour.setMaxValue(12);

        pickerMinute.setMinValue(0);
        pickerMinute.setMaxValue(59);
        pickerMinute.setOnLongPressUpdateInterval(100);

        Intent editIntent = getIntent();
        if(editIntent.getStringExtra("alarm_id") != null) {
            RealmQuery<AlarmInfo> query = realm.where(AlarmInfo.class).equalTo("id", Integer.parseInt(editIntent.getStringExtra("alarm_id")));
            id = Integer.parseInt(editIntent.getStringExtra("alarm_id"));
            info = query.findFirst();
            pickerDivide.setValue(info.getTimeDivide());
            pickerHour.setValue(info.getHour());
            pickerMinute.setValue(info.getMinute());
            txtMemo.setText(info.getMemo());

            if(info.getWeek() != null) {
                String[] s = info.getWeek().split(",");
                for(int i=0;i<s.length;i++) {
                    switch (s[i].replace(" ","")) {
                        case "월" :
                            monBut.setChecked(true);
                            week[0] = 1;
                            break;
                        case "화" :
                            tuesBut.setChecked(true);
                            week[1] = 1;
                            break;
                        case "수" :
                            wedBut.setChecked(true);
                            week[2] = 1;
                            break;
                        case "목" :
                            thursBut.setChecked(true);
                            week[3] = 1;
                            break;
                        case "금" :
                            friBut.setChecked(true);
                            week[4] = 1;
                            break;
                        case "토" :
                            saturBut.setChecked(true);
                            week[5] = 1;
                            break;
                        case "일" :
                            sunBut.setChecked(true);
                            week[6] = 1;
                            break;
                    }
                }
            }
        }

    }

    @OnClick({R.id.sunday, R.id.monday, R.id.tuesday, R.id.wednesday, R.id.thursday, R.id.friday, R.id.saturday, R.id.alarm_submit,R.id.alarm_cancel})
    public void onClick(View v) {

        if(v.getId() == R.id.monday) {
            if(monBut.isChecked()) {
                week[0] = 1;
            } else {
                week[0] = 0;
            }
        } else if(v.getId() == R.id.tuesday) {
            if(tuesBut.isChecked()) {
                week[1] = 1;
            } else {
                week[1] = 0;
            }
        } else if(v.getId() == R.id.wednesday) {
            if(wedBut.isChecked()) {
                week[2] = 1;
            } else {
                week[2] = 0;
            }
        } else if(v.getId() == R.id.thursday) {
            if(thursBut.isChecked()) {
                week[3] = 1;
            } else {
                week[3] = 0;
            }
        } else if(v.getId() == R.id.friday) {
            if(friBut.isChecked()) {
                week[4] = 1;
            } else {
                week[4] = 0;
            }
        } else if(v.getId() == R.id.saturday) {
            if(saturBut.isChecked()) {
                week[5] = 1;
            } else {
                week[5] = 0;
            }
        }else if(v.getId() == R.id.sunday) {
            if(sunBut.isChecked()) {
                week[6] = 1;
            } else {
                week[6] = 0;
            }
        } else if(v.getId() == R.id.alarm_submit) {
            realm.beginTransaction();
            AlarmInfo alarmInfo;
            if(id != 0) {
                alarmInfo = realm.where(AlarmInfo.class).equalTo("id",id).findFirst();
            } else {
                int nextID = 0;
                if(realm.where(AlarmInfo.class).findFirst() == null) {
                    nextID = 1;
                } else {
                    nextID = (realm.where(AlarmInfo.class).max("id")).intValue() + 1;
                }
                alarmInfo = realm.createObject(AlarmInfo.class, nextID);
            }

            alarmInfo.setHour(pickerHour.getValue());
            alarmInfo.setMinute(pickerMinute.getValue());
            alarmInfo.setTimeDivide(pickerDivide.getValue());
            alarmInfo.setMemo(txtMemo.getText().toString());
            List<String> list = new ArrayList<String>();
            String[] week_txt = {"월", "화", "수", "목", "금", "토", "일"};
            Boolean weeks = false;

            for(int i=0;i<week.length;i++) {
                if(week[i] == 1) {
                    list.add(week_txt[i]);
                    weeks = true;
                }
            }
            alarmInfo.setWeek(list.toString().replace("[","").replace("]",""));
            realm.commitTransaction();

            registerAlarm(this, alarmInfo, weeks);

            realm.close();
            finish();

        } else if(v.getId() == R.id.alarm_cancel) {
            finish();
        }
    }

    public void registerAlarm(Context context, AlarmInfo info, boolean F) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Calendar curTime = Calendar.getInstance();
        Calendar currentTime = Calendar.getInstance();
        curTime.set(Calendar.YEAR, currentTime.get(Calendar.YEAR));
        curTime.set(Calendar.MONTH, currentTime.get(Calendar.MONTH));
        curTime.set(Calendar.DATE, currentTime.get(Calendar.DATE));
        curTime.set(Calendar.HOUR, info.getHour());
        curTime.set(Calendar.MINUTE, info.getMinute());
        curTime.set(Calendar.SECOND, 59);
        curTime.set(Calendar.AM_PM, info.getTimeDivide());


        if(curTime.getTimeInMillis() < currentTime.getTimeInMillis()) {
            curTime.set(Calendar.DATE, currentTime.get(Calendar.DATE) + 1);
            curTime.set(Calendar.SECOND, 0);
        } else {
            curTime.set(Calendar.SECOND, 0);
        }

        Long alarmTime = curTime.getTimeInMillis();

        Intent intent = new Intent(context, BootReceiver.class);
        intent.putExtra("id",info.getId());
        PendingIntent pIntent = PendingIntent.getBroadcast(context, info.getId(), intent, PendingIntent.FLAG_CANCEL_CURRENT);

        if(F) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarmTime, 24*60*60*1000, pIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pIntent);
            Log.d("ADASDFSADF", alarmTime+"");
        }
    }

}
