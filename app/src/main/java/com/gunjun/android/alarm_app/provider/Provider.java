package com.gunjun.android.alarm_app.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.gunjun.android.alarm_app.models.AlarmInfo;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by gunjunLee on 2017-01-28.
 */

public class Provider extends ContentProvider  {

    static final String[] sColums = new String[] {"id", "hour", "minute", "week", "timeDivide", "memo"};
    private Realm mRealm;

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        mRealm = Realm.getDefaultInstance();
        RealmQuery<AlarmInfo> query = mRealm.where(AlarmInfo.class);
        RealmResults<AlarmInfo> results = query.findAll();

        MatrixCursor matrixCursor = new MatrixCursor(sColums);
        for(AlarmInfo info : results){
            Object[] rowData = new Object[]{info.getId(), info.getHour(), info.getMinute(), info.getWeek(), info.getTimeDivide(), info.getMemo()};
            matrixCursor.addRow(rowData);
        }
        return matrixCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
