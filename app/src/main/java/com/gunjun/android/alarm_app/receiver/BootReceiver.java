package com.gunjun.android.alarm_app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.gunjun.android.alarm_app.AlarmActivity;
import com.gunjun.android.alarm_app.MainActivity;
import com.gunjun.android.alarm_app.models.AlarmInfo;

import java.util.Calendar;

import io.realm.Realm;

/**
 * Created by gunjunLee on 2017-01-27.
 */

public class BootReceiver extends BroadcastReceiver {

    private Realm realm;

    @Override
    public void onReceive(Context context, Intent intent) {
        realm = Realm.getDefaultInstance();

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Bundle extra = intent.getExtras();

            if (extra != null) {
               int id = extra.getInt("id");
                realm.beginTransaction();
                AlarmInfo info = realm.where(AlarmInfo.class).equalTo("id",id).findFirst();
                realm.commitTransaction();

                Calendar calendar = Calendar.getInstance();
                String[] s = info.getWeek().split(",");
                int[] week = new int[8];
                for(int i=0;i<s.length;i++) {
                    switch (s[i].replace(" ","")) {
                        case "월" :
                            week[2] = 1;
                            break;
                        case "화" :
                            week[3] = 1;
                            break;
                        case "수" :
                            week[4] = 1;
                            break;
                        case "목" :
                            week[5] = 1;
                            break;
                        case "금" :
                            week[6] = 1;
                            break;
                        case "토" :
                            week[7] = 1;
                            break;
                        case "일" :
                            week[1] = 1;
                            break;
                    }
                }

                if(week[calendar.get(Calendar.DAY_OF_WEEK)] == 1) {
                    context.startActivity(intent);
                }

            } else {
                context.startActivity(intent);
            }
        }
    }
}
