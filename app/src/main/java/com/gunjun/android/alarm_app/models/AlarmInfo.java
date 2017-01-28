package com.gunjun.android.alarm_app.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gunjunLee on 2017-01-26.
 */

public class AlarmInfo extends RealmObject {

    @PrimaryKey
    private int id;
    private int hour;
    private int minute;
    private String week;
    private int timeDivide;
    private String memo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public int getTimeDivide() {
        return timeDivide;
    }

    public void setTimeDivide(int timeDivide) {
        this.timeDivide = timeDivide;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
