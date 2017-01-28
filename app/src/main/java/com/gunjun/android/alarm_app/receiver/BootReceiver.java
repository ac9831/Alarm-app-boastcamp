package com.gunjun.android.alarm_app.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import com.gunjun.android.alarm_app.AlarmActivity;
import com.gunjun.android.alarm_app.models.AlarmInfo;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by gunjunLee on 2017-01-27.
 */

public class BootReceiver extends BroadcastReceiver {

    private Realm realm;
    private static PowerManager.WakeLock sCpuWakeLock;

    @Override
    public void onReceive(Context context, Intent intent) {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
        Intent alarmIntent = new Intent(context, AlarmActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, alarmIntent, 0);

        int id = intent.getIntExtra("id", -1);
        Calendar calendar = Calendar.getInstance();
        if(id != -1){
            realm.beginTransaction();
            AlarmInfo info = realm.where(AlarmInfo.class).equalTo("id", id).findFirst();
            realm.commitTransaction();
            wakeUp(context);
            if (info.getWeek().length() > 0) {

                int[] week = new int[8];
                if (info.getWeek().length() > 1) {
                    String[] s = info.getWeek().split(",");
                    for (int j = 0; j < s.length; j++) {
                        switch (s[j].replace(" ", "")) {
                            case "월":
                                week[2] = 1;
                                break;
                            case "화":
                                week[3] = 1;
                                break;
                            case "수":
                                week[4] = 1;
                                break;
                            case "목":
                                week[5] = 1;
                                break;
                            case "금":
                                week[6] = 1;
                                break;
                            case "토":
                                week[7] = 1;
                                break;
                            case "일":
                                week[1] = 1;
                                break;
                        }
                    }
                } else {
                    String s = info.getWeek();
                    switch (s) {
                        case "월":
                            week[2] = 1;
                            break;
                        case "화":
                            week[3] = 1;
                            break;
                        case "수":
                            week[4] = 1;
                            break;
                        case "목":
                            week[5] = 1;
                            break;
                        case "금":
                            week[6] = 1;
                            break;
                        case "토":
                            week[7] = 1;
                            break;
                        case "일":
                            week[1] = 1;
                            break;
                    }
                }

                if (week[calendar.get(Calendar.DAY_OF_WEEK)] == 1) {
                    try {
                        pendingIntent.send();
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    pendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
            }
        } else {
            realm.beginTransaction();
            RealmResults<AlarmInfo> info = realm.where(AlarmInfo.class).findAll();
            realm.commitTransaction();
            for(int i=0;i<info.size();i++) {
                if (info.get(i).getHour() == (int) calendar.get(Calendar.HOUR) && (info.get(i).getMinute() <= ((int) calendar.get(Calendar.MINUTE)) + 1 || info.get(i).getMinute() >= ((int) calendar.get(Calendar.MINUTE)) - 1)) {

                    wakeUp(context);
                    if (info.get(i).getWeek().length() > 0) {

                        int[] week = new int[8];
                        if (info.get(i).getWeek().length() > 1) {
                            String[] s = info.get(i).getWeek().split(",");
                            for (int j = 0; j < s.length; j++) {
                                switch (s[j].replace(" ", "")) {
                                    case "월":
                                        week[2] = 1;
                                        break;
                                    case "화":
                                        week[3] = 1;
                                        break;
                                    case "수":
                                        week[4] = 1;
                                        break;
                                    case "목":
                                        week[5] = 1;
                                        break;
                                    case "금":
                                        week[6] = 1;
                                        break;
                                    case "토":
                                        week[7] = 1;
                                        break;
                                    case "일":
                                        week[1] = 1;
                                        break;
                                }
                            }
                        } else {
                            String s = info.get(i).getWeek();
                            switch (s) {
                                case "월":
                                    week[2] = 1;
                                    break;
                                case "화":
                                    week[3] = 1;
                                    break;
                                case "수":
                                    week[4] = 1;
                                    break;
                                case "목":
                                    week[5] = 1;
                                    break;
                                case "금":
                                    week[6] = 1;
                                    break;
                                case "토":
                                    week[7] = 1;
                                    break;
                                case "일":
                                    week[1] = 1;
                                    break;
                            }
                        }

                        if (week[calendar.get(Calendar.DAY_OF_WEEK)] == 1) {
                            try {
                                pendingIntent.send();
                            } catch (PendingIntent.CanceledException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        try {
                            pendingIntent.send();
                        } catch (PendingIntent.CanceledException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void wakeUp(Context context) {
        if (sCpuWakeLock != null) {
            return;
        }
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        sCpuWakeLock = pm.newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                        PowerManager.ACQUIRE_CAUSES_WAKEUP |
                        PowerManager.ON_AFTER_RELEASE, "hi");

        sCpuWakeLock.acquire();


        if (sCpuWakeLock != null) {
            sCpuWakeLock.release();
            sCpuWakeLock = null;
        }
    }
}
